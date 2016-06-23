package individual;

import report.rex_jgg.TIndividual;

public class TMOIndividual extends TIndividual implements Comparable {

	private int fRank;
	private double fCrowdDistance;
	private double f1Value;
	private double f2Value;

	public TMOIndividual() {
		super();
	}

	@Override
	public TMOIndividual clone() {
		TMOIndividual individual = new TMOIndividual();
		individual.copyFrom(this);
		individual.fRank = fRank;
		individual.fCrowdDistance = fCrowdDistance;
		individual.f1Value = f1Value;
		individual.f2Value = f2Value;
		return individual;
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

		return fCrowdDistance > otherIndividual.fCrowdDistance ? -1 : 1;
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

}
