package rubix;

import sat.Clause;
import sat.Expression;
import sat.Index;
import sat.Iterator;
import sat.Literal;
import sat.Loops;
import sat.Value;
import sat.Variable;

public class RubixSAT5 extends RubixSAT {

	protected Expression AMO(final Variable var, final int start, final int end) {
		// x0 -> 0 0 <-> -x0 | s1 | s0
		// x1 -> 0 1 <-> -x1 | s1 | -s0
		// x2 -> 1 0 <-> -x2 | -s1 | s0
		// x3 not existent <-> -s1 | -s0
		final String uname = getUniqueName();
		final int n = (int) Math.ceil(Math.log(1 + end - start) / Math.log(2));
		final Iterator i = new Iterator();
		final Iterator s = new Iterator();
		return Loops.and(0, (int) Math.pow(2, n)-1, i, new Value<Expression>() {
			public Expression get() {
				Clause clause = i.get() <= 1 + end - start ? new Clause(
						new Literal(new Variable(var, i),true)) : new Clause();
				return new Expression(clause.or(Loops.or(0, n - 1, s,
						new Value<Clause>() {
							public Clause get() {
								Integer v = i.get() - start;
								return new Clause(new Literal(new Variable(
										uname, s),
										(v.intValue() >> s.get() & 1) == 1));
							}
						})));
			}
		});
	}


}
