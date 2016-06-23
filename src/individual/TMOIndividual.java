package individual;

import java.util.ArrayList;

import report.rex_jgg.TIndividual;

public class TMOIndividual extends TIndividual implements Comparable {

	private int fRank;
	private double fCrowdDistance;
	private double f1Value;
	private double f2Value;

	// 非優越ソートで用いる
	public ArrayList<TMOIndividual> S;
	public int n;

	public TMOIndividual() {
		super();
		S = new ArrayList<>();
	}

	/**
	 * Sのみコピーしない
	 *
	 * @see report.rex_jgg.TIndividual#clone()
	 */
	@Override
	public TMOIndividual clone() {
		TMOIndividual individual = new TMOIndividual();
		individual.copyFrom(this);
		individual.fRank = fRank;
		individual.setCrowdDistance(fCrowdDistance);
		individual.f1Value = f1Value;
		individual.f2Value = f2Value;
		return individual;
	}

	@Override
	public TMOIndividual copyFrom(TIndividual individual) {
		super.copyFrom(individual);
		return this;
	}

	@Override
	public String toString() {
		return f1Value + "," + f2Value;
	}

	public int getRank() {
		return fRank;
	}

	public void setRank(int fRank) {
		this.fRank = fRank;
	}

	/**
	 * CCOにもとづいてソート
	 *
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(Object other) {
		TMOIndividual otherIndividual = (TMOIndividual) other;
		if (fRank != otherIndividual.fRank)
			return fRank - otherIndividual.fRank;

		return getCrowdDistance() > otherIndividual.getCrowdDistance() ? -1 : 1;
	}

	public double getF1Value() {
		return f1Value;
	}

	public void setF1Value(double f1Value) {
		this.f1Value = f1Value;
	}

	public double getF2Value() {
		return f2Value;
	}

	public void setF2Value(double f2Value) {
		this.f2Value = f2Value;
	}

	public double getCrowdDistance() {
		return fCrowdDistance;
	}

	public void setCrowdDistance(double crowdDistance) {
		fCrowdDistance = crowdDistance;
	}

	/**
	 * this > other (thisはotherを優越する)かどうかを判定
	 *
	 * @param other
	 * @return
	 */
	public boolean isDominant(TMOIndividual other) {
		return f1Value <= other.f1Value && f2Value <= other.f2Value && (f1Value < other.f1Value || f2Value < other.f2Value);
	}
}
