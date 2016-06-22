package nsga_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import individual.TMOIndividual;
import problem.IProblem;
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
		rex = new TRex(random);
		fProblem = problem;

		initialGen();
	}


	/**
	 * 1世代アルゴリズムを進める
	 *
	 */
	public void doOneIteration() {
		nonDominatedSort(fPopulation);
		calcCrowdingDistance(fPopulation);

		TMOIndividual[] parents = selectByTournament();
		TMOIndividual[] children = (TMOIndividual[]) rex.makeOffspring(parents, fNoOfChildren);
		for (TMOIndividual tmoIndividual : children) {
			evaluateFunction(tmoIndividual);
		}

		ArrayList<TMOIndividual> uArrayList = new ArrayList<>(fPopulation);
		Collections.addAll(uArrayList, children);
		nonDominatedSort(uArrayList);
		calcCrowdingDistance(uArrayList);

		//Sに相当
		fPopulation.clear();
		for (int i = 0; fPopulation.size() < fPopulationSize; i++) {
			final int rank = i;
			uArrayList.stream().filter(e -> e.getRank() == rank).collect(Collectors.toList());
		}

		if(fPopulation.size() > fPopulationSize){
			//CCOに基づきソート
			fPopulation.sort((a,b) -> a.compareTo(b));
			int removeCount = fPopulation.size() - fPopulationSize;
			for (int i = removeCount; i > 0; i--) {
				fPopulation.remove(fPopulationSize + i - 1);
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
			fPopulation.add(individual);
		}
	}

	private void evaluateFunction(TMOIndividual x){
		x.setF1Value(fProblem.f1(x));
		x.setF2Value(fProblem.f2(x));
	}

	private void nonDominatedSort(ArrayList<TMOIndividual> population) {

	}

	private void calcCrowdingDistance(ArrayList<TMOIndividual> population) {

	}

	private TMOIndividual[] selectByTournament() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
