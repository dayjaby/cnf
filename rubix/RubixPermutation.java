package rubix;


public class RubixPermutation {

	
	public FaceletLocation relocate(FaceletLocation loc) {
		return loc;
	}
	
	public boolean equals(RubixPermutation perm) {
		for(FaceletLocation loc : FaceletLocation.ALL) {
			if(!relocate(loc).equals(perm.relocate(loc))) return false;
		}
		return true;
	}
	
	public RubixPermutation reverse() {
		final RubixPermutation origin = this;
		return new RubixPermutation() {
			public FaceletLocation relocate(FaceletLocation floc) {
				for(FaceletLocation loc : FaceletLocation.ALL) {
					if(origin.relocate(loc).equals(floc)) return loc;
				}
				return floc;
			}
		};
	}
	
	public RubixPermutation twice() {
		return concat(this);
	}
	
	public RubixPermutation concat(final RubixPermutation rp) {
		final RubixPermutation origin = this;
		return new RubixPermutation() {
			public FaceletLocation relocate(FaceletLocation loc) {
				return rp.relocate(origin.relocate(loc));
			}
		};
	}
	
	public static final RubixPermutation IDENTITY = new RubixPermutation();
	public static final RubixPermutation FRONT = turnFaceClockwise(0).concat(new RubixPermutation() {
		public FaceletLocation relocate(FaceletLocation loc) {
			switch(loc.getFace()) {
			case 5:
				switch(loc.getFacelet()) {
				case 0: return FaceletLocation.get(2,2);
				case 1: return FaceletLocation.get(2,5);
				case 2: return FaceletLocation.get(2,8);
				}
				break;
			case 4:
				switch(loc.getFacelet()) {
				case 6: return FaceletLocation.get(3,0);
				case 7: return FaceletLocation.get(3,3);
				case 8: return FaceletLocation.get(3,6);
				}
				break;
			case 2:
				switch(loc.getFacelet()) {
				case 8: return FaceletLocation.get(4,6);
				case 5: return FaceletLocation.get(4,7);
				case 2: return FaceletLocation.get(4,8);
				}
				break;
			case 3:
				switch(loc.getFacelet()) {
				case 6: return FaceletLocation.get(5,0);
				case 3: return FaceletLocation.get(5,1);
				case 0: return FaceletLocation.get(5,2);
				}	
				break;			
			}
			return loc;
		}
	});
	
	public static final RubixPermutation BACK = turnFaceClockwise(1).concat(new RubixPermutation() {
		public FaceletLocation relocate(FaceletLocation loc) {
			switch(loc.getFace()) {
			case 4:
				switch(loc.getFacelet()) {
				case 2: return FaceletLocation.get(2,0);
				case 1: return FaceletLocation.get(2,3);
				case 0: return FaceletLocation.get(2,6);
				}
				break;
			case 5:
				switch(loc.getFacelet()) {
				case 8: return FaceletLocation.get(3,2);
				case 7: return FaceletLocation.get(3,5);
				case 6: return FaceletLocation.get(3,8);
				}
				break;
			case 3:
				switch(loc.getFacelet()) {
				case 2: return FaceletLocation.get(4,0);
				case 5: return FaceletLocation.get(4,1);
				case 8: return FaceletLocation.get(4,2);
				}
				break;
			case 2:
				switch(loc.getFacelet()) {
				case 0: return FaceletLocation.get(5,6);
				case 3: return FaceletLocation.get(5,7);
				case 6: return FaceletLocation.get(5,8);
				}	
				break;			
			}
			return loc;
		}
	});	
	
	public static final RubixPermutation LEFT = turnFaceClockwise(2).concat(new RubixPermutation() {
		public FaceletLocation relocate(FaceletLocation loc) {
			switch(loc.getFace()) {
			case 4:
				switch(loc.getFacelet()) {
				case 0: return FaceletLocation.get(0,0);
				case 3: return FaceletLocation.get(0,3);
				case 6: return FaceletLocation.get(0,6);
				}
				break;
			case 5:
				switch(loc.getFacelet()) {
				case 0: return FaceletLocation.get(1,8);
				case 3: return FaceletLocation.get(1,5);
				case 6: return FaceletLocation.get(1,2);
				}
				break;
			case 1:
				switch(loc.getFacelet()) {
				case 8: return FaceletLocation.get(4,0);
				case 5: return FaceletLocation.get(4,3);
				case 2: return FaceletLocation.get(4,6);
				}
				break;
			case 0:
				switch(loc.getFacelet()) {
				case 0: return FaceletLocation.get(5,0);
				case 3: return FaceletLocation.get(5,3);
				case 6: return FaceletLocation.get(5,6);
				}		
				break;		
			}
			return loc;
		}
	});	
	
	public static final RubixPermutation RIGHT = turnFaceClockwise(3).concat(new RubixPermutation() {
		public FaceletLocation relocate(FaceletLocation loc) {
			switch(loc.getFace()) {
			case 5:
				switch(loc.getFacelet()) {
				case 2: return FaceletLocation.get(0,2);
				case 5: return FaceletLocation.get(0,5);
				case 8: return FaceletLocation.get(0,8);
				}
				break;
			case 4:
				switch(loc.getFacelet()) {
				case 8: return FaceletLocation.get(1,0);
				case 5: return FaceletLocation.get(1,3);
				case 2: return FaceletLocation.get(1,6);
				}
				break;
			case 0:
				switch(loc.getFacelet()) {
				case 2: return FaceletLocation.get(4,2);
				case 5: return FaceletLocation.get(4,5);
				case 8: return FaceletLocation.get(4,8);
				}
				break;
			case 1:
				switch(loc.getFacelet()) {
				case 6: return FaceletLocation.get(5,2);
				case 3: return FaceletLocation.get(5,5);
				case 0: return FaceletLocation.get(5,8);
				}	
				break;			
			}
			return loc;
		}
	});	
	
	public static final RubixPermutation UP = turnFaceClockwise(4).concat(new RubixPermutation() {
		public FaceletLocation relocate(FaceletLocation loc) {
			switch(loc.getFace()) {
			case 3:
				switch(loc.getFacelet()) {
				case 0: return FaceletLocation.get(0,0);
				case 1: return FaceletLocation.get(0,1);
				case 2: return FaceletLocation.get(0,2);
				}
				break;
			case 2:
				switch(loc.getFacelet()) {
				case 0: return FaceletLocation.get(1,0);
				case 1: return FaceletLocation.get(1,1);
				case 2: return FaceletLocation.get(1,2);
				}
				break;
			case 0:
				switch(loc.getFacelet()) {
				case 0: return FaceletLocation.get(2,0);
				case 1: return FaceletLocation.get(2,1);
				case 2: return FaceletLocation.get(2,2);
				}
				break;
			case 1:
				switch(loc.getFacelet()) {
				case 0: return FaceletLocation.get(3,0);
				case 1: return FaceletLocation.get(3,1);
				case 2: return FaceletLocation.get(3,2);
				}	
				break;			
			}
			return loc;
		}
	});	
	
	public static final RubixPermutation DOWN = turnFaceClockwise(5).concat(new RubixPermutation() {
		public FaceletLocation relocate(FaceletLocation loc) {
			switch(loc.getFace()) {
			case 2:
				switch(loc.getFacelet()) {
				case 6: return FaceletLocation.get(0,6);
				case 7: return FaceletLocation.get(0,7);
				case 8: return FaceletLocation.get(0,8);
				}
				break;
			case 3:
				switch(loc.getFacelet()) {
				case 6: return FaceletLocation.get(1,6);
				case 7: return FaceletLocation.get(1,7);
				case 8: return FaceletLocation.get(1,8);
				}
				break;
			case 1:
				switch(loc.getFacelet()) {
				case 6: return FaceletLocation.get(2,6);
				case 7: return FaceletLocation.get(2,7);
				case 8: return FaceletLocation.get(2,8);
				}
				break;
			case 0:
				switch(loc.getFacelet()) {
				case 6: return FaceletLocation.get(3,6);
				case 7: return FaceletLocation.get(3,7);
				case 8: return FaceletLocation.get(3,8);
				}	
				break;			
			}
			return loc;
		}
	});	
	
	private static RubixPermutation turnFaceClockwise(final int face) {
		return new RubixPermutation() {
			public FaceletLocation relocate(FaceletLocation loc) {
				if(loc.getFace() == face) {
					switch(loc.getFacelet()) {
					case 0: return FaceletLocation.get(face, 2);
					case 1: return FaceletLocation.get(face, 5);
					case 2: return FaceletLocation.get(face, 8);
					case 3: return FaceletLocation.get(face, 1);
					case 4: return FaceletLocation.get(face, 4);
					case 5: return FaceletLocation.get(face, 7);
					case 6: return FaceletLocation.get(face, 0);
					case 7: return FaceletLocation.get(face, 3);
					case 8: return FaceletLocation.get(face, 6);
					}
				}
				return loc;
			}
		};
	}
}
