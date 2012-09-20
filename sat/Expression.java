package sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expression {
	
	List<Clause> clauses;
	
	public Expression() {
		this.clauses = new ArrayList<Clause>();
	}
	
	public Expression(Clause... clauses) {
		this.clauses = Arrays.asList(clauses);
	}
	
	public Expression(Iterator i, Expression c) {
		this();
		for(Clause clause : c.clauses) {
			clauses.add(new Clause(i,clause));
		}
	}
	
	public Expression(Expression... expressions) {
		this();
		for(Expression expr : expressions)
			this.clauses.addAll(expr.getClauses());
	}
	
	public Expression and(Expression e) {
		Expression expr = new Expression();
		expr.clauses.addAll(getClauses());
		expr.clauses.addAll(e.getClauses());
		return expr;
	}
	
	// apply distributive property
	public Expression or(Clause c) {
		Expression expr = new Expression();
		for(Clause clause : clauses) {
			expr.clauses.add(clause.or(c));
		}
		return expr;
	}
	
	public List<Clause> getClauses() {
		return clauses;
	}
}
