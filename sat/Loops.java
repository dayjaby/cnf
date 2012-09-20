package sat;

public class Loops {

	public static Clause or(int first, int last, Iterator i,Value<Clause> val) {
		Clause clause = new Clause();
		for (int n = first; n <= last; n++) {
			i.set(n);
			Clause innerclause = val.get();
				for (Literal alit : innerclause.literals)
					clause.literals.add(new Literal(i, alit));
		}
		return clause;
	}

	public static Expression and(int first, int last, Iterator i, Value<Expression> val) {
		Expression expr = new Expression();
		for (int n = first; n <= last; n++) {
			i.set(n);
			Expression innerexpr = val.get();
				for (Clause clause : innerexpr.clauses)
					expr.clauses.add(new Clause(i, clause));
			
		}
		return expr;
	}
}
