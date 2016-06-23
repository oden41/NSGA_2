package main;

import individual.TMOIndividual;
import nsga_2.TNSGA_2;
import problem.IProblem;
import problem.TProblem2;

public class TMain {

	public static void main(String[] args) {
		IProblem problem = new TProblem2();
		TNSGA_2 tnsga_2 = new TNSGA_2(problem);

		while (!tnsga_2.isEnd()) {
			tnsga_2.doOneIteration();
		}

		for (TMOIndividual individual : tnsga_2.getPopulation()) {
			System.out.println(individual.toString());
		}
	}
}
