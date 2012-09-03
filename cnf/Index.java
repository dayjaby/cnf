package cnf;

public class Index {
	Integer index;
	
	public Integer get() {
		return this.index;
	}
	
	public Index(final Integer cons) {
		index = new Integer(cons);
	}
	
	public Index() {
		index = 0;
	}
}
