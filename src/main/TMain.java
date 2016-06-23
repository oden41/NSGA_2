package main;

import nsga_2.TNSGA_2;
import problem.IProblem;
import problem.TProblem1;

public class TMain {

	public static void main(String[] args) {
		IProblem problem = new TProblem1();
		TNSGA_2 tnsga_2 = new TNSGA_2(problem);

		while (!tnsga_2.isEnd()) {
			tnsga_2.doOneIteration();
		}
	}
}
