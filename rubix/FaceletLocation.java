package rubix;

public class FaceletLocation {
	private FaceletLocation(int face, int facelet) {
		this.face = face;
		this.facelet = facelet;
	}
	
	int face;
	int facelet;
	
	public int getFace() { return face; }
	public int getFacelet() { return facelet; }
	public boolean equals(FaceletLocation fl) {
		return face == fl.face && facelet == fl.facelet;
	}
	
	public static FaceletLocation get(int face, int facelet) {
		return ALL[facelet+face*9];
	}
	
	public static final FaceletLocation[] ALL = getAll(); 
	private static FaceletLocation[] getAll() {
		FaceletLocation[] locs = new FaceletLocation[54];
		for(int i=0;i<6;i++) {
			for(int j=0;j<9;j++) {
				locs[j+i*9] = new FaceletLocation(i,j);
			}
		}
		return locs;
	}
}
