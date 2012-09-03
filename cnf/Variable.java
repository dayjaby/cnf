package cnf;

import java.util.Arrays;
import java.util.List;

public class Variable {
	
	private String name;
	private List<Index> indices;

	public Variable(String name, Index... indices) {
		this.indices = Arrays.asList(indices);
		this.name = name;
	}
	
	public String getName() {
		String s = name;
		for(Index i : indices) {
			s += "_" + i.get();
		}
		return s;
	}

	
	public Variable setIteratorValue(Index i, Index repl) {
		Variable var = new Variable(name,(Index[])indices.toArray());
		if(var.indices.contains(i)) {
			int index = var.indices.indexOf(i);
			var.indices.set(index,repl);
		}
		return var;
	}

}
