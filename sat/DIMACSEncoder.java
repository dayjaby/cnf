package sat;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DIMACSEncoder {

	PrintStream stream;
	Expression expression;

	public DIMACSEncoder(Expression expr) {
		expression = expr;
		clauses = expr.getClauses().size();
		vars = 0;
		for (Clause clause : expr.getClauses()) {
			for (Literal lit : clause.getLiterals()) {
				Variable var = lit.getVariable();
				if (!mappedVars.containsKey(var.getName())) {
					mappedVars.put(var.getName(), ++vars);
				}
			}
		}
	}

	public void writeDIMACSTo(OutputStream stream) {
		PrintWriter writer = new PrintWriter(stream);
		writer.println(getHeader());
		for (Clause clause : expression.getClauses()) {
			for (Literal lit : clause.getLiterals()) {
				writer.print((lit.getSign() ? "-" : "")
						+ mappedVars.get(lit.getVariable().getName())
								.toString() + ' ');
			}
			writer.println("0");
			writer.flush();
		}
	}

	public void writeMappingTo(PrintStream stream) {
		for (Entry<String, Integer> mapping : mappedVars.entrySet()) {
			stream.println(mapping.getKey() + " " + mapping.getValue());
		}
	}

	public String getHeader() {
		// important: expression must be encoded first!
		return "p cnf " + vars + " " + clauses;
	}

	Map<String, Integer> mappedVars = new HashMap<String, Integer>();
	int vars = 0;
	int clauses = 0;

}
