package cnf;

public class Literal {
	private Variable variable;
	private boolean signed;

	
	public Literal(Iterator i, Literal lit) {
		this(lit.getVariable().setIteratorValue(i, i.toIndex()),lit.getSign());
	}
	
	public Literal(Variable v) {
		this(v,false);
	}
	
	public Literal(Variable v, boolean signed) {
		this.variable = v;
		this.signed = signed;
	}
	
	public Variable getVariable() {
		return variable;
	}
	
	public boolean getSign() {
		return signed;
	}
}
