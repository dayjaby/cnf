package sat;

public class Index {
	Integer index;
	
	public Integer get() {
		return this.index;
	}
	
	public Index(final Index cons) {
		this(cons.get());
	}
	
	public Index(final Integer cons) {
		index = new Integer(cons);
	}
	
	public Index plus(final Integer add) {
		return new Index(index+add);
	}
	
	public void incr() {
		index = index + 1;
	}
	
	public Index() {
		index = 0;
	}
}
