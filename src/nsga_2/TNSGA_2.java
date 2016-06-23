package nsga_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import individual.TMOIndividual;
import problem.IProblem;
import report.rex_jgg.TIndividual;
import report.rex_jgg.TRex;

public class TNSGA_2 {

	private int fDimension = 2;
	private int fPopulationSize = 28;
	private int fSampleSize = 2;
	private int fNoOfParents = 3;
	private int fNoOfChildren = 10;
	private int fGeneration;
	private int fMaxGeneration = 1000;

	private Random random;
	private ArrayList<TMOIndividual> fPopulation;
	private IProblem fProblem;
	private TRex rex;

	public TNSGA_2(IProblem problem) {
		fGeneration = 0;
		random = new Random();
		fPopulation = new ArrayList<>();
		rex = new TRex(random);
		fProblem = problem;

		initialGen();
	}

	/**
	 * 1世代アルゴリズムを進める
	 *
	 */
	public void doOneIteration() {
		nonDominatedSort(getPopulation());
		calcCrowdingDistance(getPopulation());

		TMOIndividual[] parents = selectByTournament();
		TIndividual[] tempchildren = rex.makeOffspring(parents, fNoOfChildren);
		TMOIndividual[] children = new TMOIndividual[tempchildren.length];
		for (int i = 0; i < children.length; i++) {
			children[i] = new TMOIndividual();
			children[i].copyFrom(tempchildren[i]);
		}
		for (TMOIndividual tmoIndividual : children) {
			evaluateFunction(tmoIndividual);
		}

		ArrayList<TMOIndividual> uArrayList = new ArrayList<>(getPopulation());
		Collections.addAll(uArrayList, children);
		nonDominatedSort(uArrayList);
		calcCrowdingDistance(uArrayList);

		// Sに相当
		getPopulation().clear();
		for (int i = 0; getPopulation().size() < fPopulationSize; i++) {
			final int rank = i;
			getPopulation().addAll(uArrayList.stream().filter(e -> e.getRank() == rank).collect(Collectors.toList()));
		}

		if (getPopulation().size() > fPopulationSize) {
			// CCOに基づきソート
			getPopulation().sort((a, b) -> a.compareTo(b));
			int removeCount = getPopulation().size() - fPopulationSize;
			for (int i = removeCount; i > 0; i--) {
				getPopulation().remove(fPopulationSize + i - 1);
			}
		}

		fGeneration++;
	}

	public boolean isEnd() {
		return fGeneration >= fMaxGeneration;
	}

	private void initialGen() {
		for (int i = 0; i < fPopulationSize; i++) {
			TMOIndividual individual = new TMOIndividual();
			individual.getVector().setDimension(fDimension);
			for (int j = 0; j < fDimension; j++) {
				individual.getVector().setElement(j, random.nextDouble() * fProblem.getLimit() * 2 - fProblem.getLimit());
			}
			evaluateFunction(individual);
			getPopulation().add(individual);
		}
	}

	private void evaluateFunction(TMOIndividual x) {
		x.setF1Value(fProblem.f1(x));
		x.setF2Value(fProblem.f2(x));
	}

	private void nonDominatedSort(ArrayList<TMOIndividual> population) {
		ArrayList<TMOIndividual> F = new ArrayList<>();
		for (int i = 0; i < population.size(); i++) {
			TMOIndividual x = population.get(i);
			x.S.clear();
			x.n = 0;
			for (int j = 0; j < population.size(); j++) {
				TMOIndividual y = population.get(j);
				if (x.isDominant(y))
					x.S.add(y);
				else if (y.isDominant(x))
					x.n++;
			}
			if (x.n == 0) {
				F.add(x);
				x.setRank(1);
			}
		}
		int i = 1;
		while (!F.isEmpty()) {
			ArrayList<TMOIndividual> Q = new ArrayList<>();
			for (TMOIndividual x : F) {
				for (TMOIndividual y : x.S) {
					y.n--;
					if (y.n == 0) {
						y.setRank(i + 1);
						Q.add(y);
					}
				}
			}
			i++;
			F.clear();
			F.addAll(Q);
		}

	}

	private void calcCrowdingDistance(ArrayList<TMOIndividual> population) {
		population.forEach(action -> action.setCrowdDistance(0));
		// 2目的関数なので2で固定
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				population.sort((a, b) -> Double.compare(a.getF1Value(), b.getF1Value()));
				population.get(0).setCrowdDistance(Double.POSITIVE_INFINITY);
				population.get(population.size() - 1).setCrowdDistance(Double.POSITIVE_INFINITY);
				for (int j = 1; j < population.size() - 1; j++) {
					double d = population.get(j).getCrowdDistance();
					d += Math.abs(population.get(j + 1).getF1Value() - population.get(j - 1).getF1Value());
					population.get(j).setCrowdDistance(d);
				}
			}
			else {
				population.sort((a, b) -> Double.compare(a.getF2Value(), b.getF2Value()));
				population.get(0).setCrowdDistance(Double.POSITIVE_INFINITY);
				population.get(population.size() - 1).setCrowdDistance(Double.POSITIVE_INFINITY);
				for (int j = 1; j < population.size() - 1; j++) {
					double d = population.get(j).getCrowdDistance();
					d += Math.abs(population.get(j + 1).getF2Value() - population.get(j - 1).getF2Value());
					population.get(j).setCrowdDistance(d);
				}
			}
		}
	}

	/**
	 * fPopulatoinから親個体をトーナメント方式で選択
	 *
	 * @return
	 */
	private TMOIndividual[] selectByTournament() {
		TMOIndividual[] parents = new TMOIndividual[fNoOfParents];
		for (int i = 0; i < fNoOfParents; i++) {
			shuffle();
			TMOIndividual cand1 = getPopulation().remove(0);
			TMOIndividual cand2 = getPopulation().remove(0);
			if (cand1.compareTo(cand2) >= 0) {
				parents[i] = cand1;
				getPopulation().add(cand2);
			}
			else {
				parents[i] = cand2;
				getPopulation().add(cand1);
			}
		}
		for (int i = 0; i < parents.length; i++) {
			getPopulation().add(parents[i].clone());
		}
		return parents;
	}

	/**
	 * fPopulationの要素をランダムに入れ替えるメソッド<br>
	 * これにより非復元抽出の処理が簡潔になる
	 *
	 */
	public void shuffle() {
		for (int i = 0; i < getPopulation().size(); i++) {
			int index = random.nextInt(getPopulation().size() - i) + i;
			swap(i, index);
		}
	}

	/**
	 * index1とindex2にある要素を入れ替える
	 *
	 * @param index1
	 * @param index2
	 */
	public void swap(int index1, int index2) {
		TMOIndividual temp = getPopulation().get(index1).clone();
		getPopulation().set(index1, getPopulation().get(index2));
		getPopulation().set(index2, temp);
	}

	public ArrayList<TMOIndividual> getPopulation() {
		return fPopulation;
	}
}
