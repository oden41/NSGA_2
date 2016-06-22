package problem;

import individual.TMOIndividual;

public class TProblem2 implements IProblem {

	private int fLimit;

	public TProblem2() {
		fLimit = 3;
	}

	@Override
	public double f1(TMOIndividual x) {
		return Math.pow(2, -0.5) * Math.sqrt(Math.pow(x.getVector().getElement(0) - 1, 2) + Math.pow(x.getVector().getElement(1), 2));
	}

	@Override
	public double f2(TMOIndividual x) {
		return Math.pow(2, -0.25) * Math.sqrt(Math.pow(x.getVector().getElement(0), 2) + Math.pow(x.getVector().getElement(1) - 1, 2));
	}

	@Override
	public int getLimit() {
		return fLimit;
	}
}
