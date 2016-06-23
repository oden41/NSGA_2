package problem;

import individual.TMOIndividual;

public class TProblem1 implements IProblem {

	private int fLimit;

	public TProblem1() {
		fLimit = 5;
	}

	@Override
	public double f1(TMOIndividual x) {
		double x1 = x.getVector().getElement(0);
		double x2 = x.getVector().getElement(1);
		return x1 * x1 - x1 * x2 + x2 * x2 - x1 - x2 + 1;
	}

	@Override
	public double f2(TMOIndividual x) {
		double x1 = x.getVector().getElement(0);
		double x2 = x.getVector().getElement(1);
		return x1 * x1 + x1 * x2 + x2 * x2 + 2 * x1 + 4 * x2 + 4;
	}

	@Override
	public int getLimit() {
		return fLimit;
	}
}
