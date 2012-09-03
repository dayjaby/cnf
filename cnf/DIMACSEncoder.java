package cnf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DIMACSEncoder extends Encoder {
	
	String encoding;
	
	public DIMACSEncoder(Expression expr) {
		String code = encode(expr);
		encoding = getHeader() + code;
	}

	@Override
	protected String encode(Variable var) {
		if(!mappedVars.containsKey(var.getName())) {
			mappedVars.put(var.getName(), ++vars);
		}
		//System.out.println("Var: " + var.getName());
		return mappedVars.get(var.getName()).toString();
	}

	@Override
	protected String encode(Literal lit) {
		return ( lit.getSign() ? "-" : "" ) + encode(lit.getVariable());
			
	}
	
	@Override
	protected String encode(Clause clause) {
		clauses++;
		String out = "";
		List<Literal> literals = clause.getLiterals();
		for(int i=0;i<literals.size();i++) {
			out += encode(literals.get(i));
			if(i<literals.size()-1)
				out += ' ';
		}
		return out;
	}	
	
	@Override
	public String getHeader() {
		// important: expression must be encoded first!
		return "p cnf " + vars + " " + clauses + "\n";
	}

	@Override
	public String getEncoding() {
		return encoding;
	}
	
	@Override
	protected String encode(Expression expr) {
		String out = "";
		List<Clause> clauses = expr.getClauses();
		for(int i=0;i<clauses.size();i++) {
			out += encode(clauses.get(i));
			if(i<clauses.size()-1)
				out += '\n';
		}
		return out;
	}
	
	
	Map<String,Integer> mappedVars = new HashMap<String,Integer>();
	int vars = 0;
	int clauses = 0;
	
}
