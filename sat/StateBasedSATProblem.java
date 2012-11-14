package sat;

public abstract class StateBasedSATProblem<State> {
	public Expression getExpression(State initial, int states) {
		return initialState(initial).and(solvedState(states))
				.and(oneSolvedState(states)).and(moves(states))
				.and(exactlyOneMoveInEachState(states))
				;
	}
	
	protected abstract Expression initialState(State initial);
	protected abstract Expression solvedState(int states);
	protected abstract Expression moves(int states);
	protected abstract Expression exactlyOneMoveInEachState(int states);
	protected abstract Expression oneSolvedState(final int states);
	
	protected Expression exactlyOne(final Variable var, int start, int end) {
		return AMO(var, start, end).and(new Expression(ALO(var, start, end)));
	}

	protected Clause ALO(final Variable var, int start, int end) {
		final Iterator i = new Iterator();
		return Loops.or(start, end, i, new Value<Clause>() {
			public Clause get() {
				return new Clause(new Literal(new Variable(var, i)));
			}
		});
	}

	protected Expression AMO(final Variable var, final int start, final int end) {
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		return Loops.and(start, end, i, new Value<Expression>() {
			public Expression get() {
				return Loops.and(start, end, j, new Value<Expression>() {
					public Expression get() {
						if (i.get() < j.get()) {
							return new Expression(new Clause(new Literal(
									new Variable(var, i), true), new Literal(
									new Variable(var, j), true)));
						} else {
							return new Expression();
						}
					}
				});
			}
		});
	}

	
	
}
