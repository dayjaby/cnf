package sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Variable {
	
	private String name;
	private List<Index> indices;
	
	public Variable(Variable var, Index... indices) {
		this.name = var.name;
		this.indices = new ArrayList<Index>(var.indices);
		this.indices.addAll(Arrays.asList(indices));
	}

	public Variable(String name, Index... indices) {
		this.indices = new ArrayList<Index>(Arrays.asList(indices));
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
		Variable var = new Variable(name);
		for(Index index : indices) {
			if(i.equals(index)) {
				var.indices.add(repl);
			} else {
				var.indices.add(index);
			}
		}
		return var;
	}

}
