package rubix;

import sat.Clause;
import sat.Expression;
import sat.Index;
import sat.Iterator;
import sat.Literal;
import sat.Loops;
import sat.Value;
import sat.Variable;

public class RubixSAT4 extends RubixSAT {

	protected Expression AMO(final Variable var, final int start, final int end) {
		final int n = 1 + end - start;
		final int p = (int) Math.ceil(Math.sqrt(n));
		final int q = (int) Math.ceil(n / (double) p);
		final String u = getUniqueName();
		final String v = getUniqueName();
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		return super.AMO(new Variable(u), 1, p)
				.and(super.AMO(new Variable(v), 1, q))
				.and(Loops.and(1, p, i, new Value<Expression>() {
					public Expression get() {
						return Loops.and(1, q, j, new Value<Expression>() {
							public Expression get() {
								int k = (i.get() - 1) * q + j.get();
								if (k > n) {
									return new Expression();
								} else {
									return new Expression(
											new Clause(
													new Literal(new Variable(
															var, new Index(k)),
															true), new Literal(
															new Variable(u, i))),
											new Clause(
													new Literal(new Variable(
															var, new Index(k)),
															true), new Literal(
															new Variable(v, j))));
								}
							}
						});
					}
				}));
	}
	
	protected Expression oneSolvedState(final int states) {
		Variable vState = new Variable("s");
		return new Expression(ALO(vState, 1, states));
	}

	

}
