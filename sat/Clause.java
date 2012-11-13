package sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Clause {
	
	List<Literal> literals;
	
	public Clause() {
		this.literals = new ArrayList<Literal>();
	}
	
	public Clause(Iterator i, Clause c) {
		this();
		for(Literal lit : c.literals) {
			literals.add(new Literal(i,lit));
		}
	}
	
	public Clause(Literal... literals) {
		this.literals = new ArrayList<Literal>(Arrays.asList(literals));
	}
	
	public Clause or(Clause c) {
		Clause clause = new Clause();
		clause.literals.addAll(literals);
		clause.literals.addAll(c.literals);
		return clause;
	}
	
	public List<Literal> getLiterals() {
		return literals;
	}
	
	public Expression negate() {
		Expression expr = new Expression();
		for(Literal lit : literals) {
			expr.clauses.add(new Clause(lit.negate()));
		}
		return expr;
	}

}
