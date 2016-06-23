package problem;

import individual.TMOIndividual;

public interface IProblem {
	public double f1(TMOIndividual x);

	public double f2(TMOIndividual x);

	public int getLimit();
}
