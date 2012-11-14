package rubix;

import sat.Clause;
import sat.Expression;
import sat.Index;
import sat.Iterator;
import sat.Literal;
import sat.Loops;
import sat.StateBasedSATProblem;
import sat.Value;
import sat.Variable;

public class RubixOneBitPerColor extends RubixSAT{


	protected Expression color(final Index face, final Index facelet,
			final Index state, final Index cface, final Index cfacelet,
			final Index cstate) {
		final Iterator i = new Iterator();
		return Loops.and(0, 5, i, new Value<Expression>() {
			public Expression get() {
				return new Expression(new Clause(new Literal(new Variable("c", face,
						facelet, state, i)), new Literal(new Variable("c", cface,
						cfacelet, cstate, i), true)), new Clause(new Literal(
						new Variable("c", face, facelet, state, i), true),
						new Literal(new Variable("c", cface, cfacelet, cstate, i))));
			}
		});
	}

	protected Expression color(final Index face, final Index facelet,
			final Index state, final Index color) {
		return new Expression(new Clause(new Literal(new Variable("c", face,
				facelet, state, color))));	
	}
	
	protected Expression colorsValid(final int states) {
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		final Iterator s = new Iterator();
		return Loops.and(1, states, s, new Value<Expression>() {
			public Expression get() {
				return Loops.and(0, 5, i, new Value<Expression>() {
					public Expression get() {
						return Loops.and(0, 8, j, new Value<Expression>() {
							public Expression get() {
								return exactlyOne(new Variable("c",i,j,s),0,5);
							}
						});
					}
				});
			}
		});
	}


}
