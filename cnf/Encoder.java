package cnf;


public abstract class Encoder {
	protected abstract String encode(Variable var);
	protected abstract String encode(Literal lit);
	protected abstract String encode(Clause clause);
	protected abstract String encode(Expression expr);
	protected abstract String getHeader();
	
	public abstract String getEncoding();
}
