package sat;

public class Iterator extends Index {

	public void set(Integer index) {
		this.index = index;
	}
	
	public Index toIndex() {
		return new Index(this.index);
	}

}
