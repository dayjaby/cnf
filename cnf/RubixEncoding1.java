package cnf;

public class RubixEncoding1 {

	/**
	 * Variables: c_i_j_s_k = k'th color bit of the j'th facelet in the i'th
	 * face at the s'th state s_s = the s'th state is a solved state
	 */

	/** Returns an expression that encodes a RubixCube into state 1 **/
	public Expression initialState(final RubixCube rubix) {
		final Iterator i = new Iterator(); // iterator for the faces
		final Iterator j = new Iterator(); // iterator for the facelets
		final Iterator k = new Iterator(); // iterator for the 3 bits
		return Loops.and(0, 5, i, new Value<Expression>() {
			public Expression get() {
				return Loops.and(0, 8, j, new Value<Expression>() {
					public Expression get() {
						// get the color from our rubix cube
						final int color = rubix.getColor(i.get(), j.get());
						return Loops.and(0, 2, k, new Value<Expression>() {
							public Expression get() {
								// return the negative color bit variable if the
								// color bit isn't set
								boolean isColorBitSet = (color >> k.get() & 1) == 1;

								return new Expression(new Clause(new Literal(
										colorBit(i, j, new Index(1), k),
										!isColorBitSet)));
							}
						});
					}
				});
			}
		});
	}

	/** Returns an expression that defines a state as solved **/
	public Expression solvedState(final int states) {
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		final Iterator s = new Iterator();
		return Loops.and(1, states, s, new Value<Expression>() {
			public Expression get() {
				return Loops.and(0, 5, i, new Value<Expression>() {
					public Expression get() {
						return Loops.and(0, 8, j, new Value<Expression>() {
							public Expression get() {
								return colorCorrect(i, j, s).or(
										new Clause(new Literal(new Variable(
												"s", s), true)));
							}
						});
					}
				});
			}
		});
	}
	
	public Expression atLeastOneSolvedState(final int states) {
		return new Expression(ALO("s", 1, states));
	}

	protected Variable colorBit(Index face, Index facelet, Index state,
			Index bit) {
		return new Variable("c", face, facelet, state, bit);
	}

	protected Variable faceColorBit(Index face, Index bit) {
		return new Variable("c", face, new Index(4), new Index(1), bit);
	}

	/**
	 * Returns an expression that can be described by this equation:
	 * color(face,facelet,state) == color(face,4,1)
	 * 
	 * @param face
	 *            An element of {0..5}
	 * @param facelet
	 *            An element of {0..8}
	 * @param state
	 *            The current state, state >= 1
	 * @return
	 */
	public Expression colorCorrect(Index face, Index facelet, Index state) {
		return colorBitCorrect(face, facelet, state, new Index(0)).and(
				colorBitCorrect(face, facelet, state, new Index(1))).and(
				colorBitCorrect(face, facelet, state, new Index(2)));
	}

	/**
	 * Returns an expression that can be described by this equation:
	 * colorBit(face,facelet,state,bit) == colorBit(face,4,1,bit)
	 * 
	 * @param bit
	 *            An element of {0..2}
	 **/
	protected Expression colorBitCorrect(Index face, Index facelet,
			Index state, Index bit) {
		return new Expression(new Clause(new Literal(colorBit(face, facelet,
				state, bit)), new Literal(faceColorBit(face, bit), true)),
				new Clause(new Literal(colorBit(face, facelet, state, bit),
						true), new Literal(faceColorBit(face, bit))));
	}

	protected Clause ALO(final String name, int start, int end) {
		final Iterator i = new Iterator();
		return Loops.or(start, end, i, new Value<Clause>() {
			public Clause get() {
				return new Clause(new Literal(new Variable(name, i)));
			}
		});
	}

	protected Expression AMO(final String name, final int start, final int end) {
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		return Loops.and(start, end, i, new Value<Expression>() {
			public Expression get() {
				return Loops.and(start, end, j, new Value<Expression>() {
					public Expression get() {
						if (i.get() < j.get()) {
							return new Expression(new Clause(new Literal(
									new Variable(name, i), true), new Literal(
									new Variable(name, j), true)));
						} else {
							return new Expression();
						}
					}
				});
			}
		});
	}

	protected Expression AMO2(final String name, final int start, final int end) {
		// x0 -> 0 0 <-> -x0 | s1 | s0
		// x1 -> 0 1 <-> -x1 | s1 | -s0
		// x2 -> 1 0 <-> -x2 | -s1 | s0
		// x3 != ex <-> -s1 | -s0
		final String uname = getUniqueName();
		final int n = (int) Math.ceil(Math.log(1 + end - start) / Math.log(2));
		final Iterator i = new Iterator();
		final Iterator s = new Iterator();
		return Loops.and(1, (int) Math.pow(2, n), i, new Value<Expression>() {
			public Expression get() {
				Clause clause = i.get() <= 1 + end - start ? new Clause(
						new Literal(new Variable(name, i))) : new Clause();
				return new Expression(clause.or(Loops.or(0, n - 1, s,
						new Value<Clause>() {
							public Clause get() {
								Integer v = i.get() - start;
								return new Clause(new Literal(new Variable(
										uname, s),
										(v.intValue() >> s.get() & 1) == 1));
							}
						})));
			}
		});
	}

	int uniques = 0;

	public String getUniqueName() {
		return "UNIQUENAME" + (uniques++);
	}
}
