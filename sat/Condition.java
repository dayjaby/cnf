package sat;


public abstract class Condition {
	
	public abstract boolean validate();
	
	public static Condition TRUE = new Condition() {
		public boolean validate() {
			return true;
		}
	};
	public static Condition FALSE = new Condition() {
		public boolean validate() {
			return false;
		}
	};

	public Condition negate() {
		return new Condition() {
			public boolean validate() {
				return !Condition.this.validate();
			}
		};
	}
	
	public Condition and(final Condition c) {
		return new Condition() {
			public boolean validate() {
				return Condition.this.validate() && c.validate();
			}
		};
	}
	
	public Condition or(final Condition c) {
		return new Condition() {
			public boolean validate() {
				return Condition.this.validate() || c.validate();
			}
		};
	}
	
	public Condition implies(final Condition c) {
		return new Condition() {
			public boolean validate() {
				return !Condition.this.validate() || c.validate();
			}
		};
	}
	
	public Condition equals(final Condition c) {
		return new Condition() {
			public boolean validate() {
				return Condition.this.validate() == c.validate();
			}
		};
	}
	
	public Condition xor(final Condition c) {
		return new Condition() {
			public boolean validate() {
				return Condition.this.validate() != c.validate();
			}
		};
	}
}
