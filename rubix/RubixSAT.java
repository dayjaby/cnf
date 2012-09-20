package rubix;

import sat.Clause;
import sat.Expression;
import sat.Index;
import sat.Iterator;
import sat.Literal;
import sat.Loops;
import sat.Value;
import sat.Variable;

public class RubixSAT {

	RubixCube rubix;

	public RubixSAT(RubixCube rubix) {
		this.rubix = rubix;
	}

	public Expression getExpression(int states) {
		return initialState().and(solvedState(states))
				.and(oneSolvedState(states)).and(moves(states))
				.and(exactlyOneTurnInEachState(states))
				.and(removeSymmetries(states))
				.and(removeDuplicates(states))
				;
	}

	protected Expression removeSymmetries(int states) {
		final Iterator s = new Iterator();
		return Loops.and(1, states, s, new Value<Expression>() {
			public Expression get() {
				return new Expression(new Clause(new Literal(new Variable("t",s,new Index(0)),true),
												new Literal(new Variable("t",s.plus(1),new Index(1)),true)),
											new Clause(new Literal(new Variable("t",s,new Index(2)),true),
														new Literal(new Variable("t",s.plus(1),new Index(3)),true)),
											new Clause(new Literal(new Variable("t",s,new Index(4)),true),
																new Literal(new Variable("t",s.plus(1),new Index(5)),true)));
			}
		});
	}
	
	protected Expression removeDuplicates(int states) {
		final Iterator s = new Iterator();
		
		final Iterator i = new Iterator();
		return Loops.and(1, states-1, s, new Value<Expression>() {
			public Expression get() {
				return Loops.and(0,5,i,new Value<Expression>() {
					public Expression get() {
						return new Expression(new Clause(new Literal(new Variable("t",s,i),true),
											new Literal(new Variable("t",s.plus(1),i),true)));
					}
				});
			}
		});
	}

	/**
	 * Variables: c_i_j_s_k = k'th color bit of the j'th facelet in the i'th
	 * face at the s'th state s_s = the s'th state is a solved state
	 */

	/** Returns an expression that encodes a RubixCube into state 1 **/
	protected Expression initialState() {
		final Iterator i = new Iterator(); // iterator for the faces
		final Iterator j = new Iterator(); // iterator for the facelets
		final Iterator k = new Iterator(); // iterator for the 3 bits
		return Loops.and(0, 5, i, new Value<Expression>() {
			public Expression get() {
				return Loops.and(0, 8, j, new Value<Expression>() {
					public Expression get() {
						// get the color from our rubix cube
						return color(i, j, new Index(1),
								new Index(rubix.getColor(i.get(), j.get())));
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

	protected Expression oneSolvedState(final int states) {
		Variable vState = new Variable("s");
		return exactlyOne(vState, 1, states);
	}

	protected Variable colorBitVar(Index face, Index facelet, Index state,
			Index bit) {
		return new Variable("c", face, facelet, state, bit);
	}

	protected Variable faceColorBit(Index face, Index bit) {
		return new Variable("c", face, new Index(4), new Index(1), bit);
	}

	protected Expression colorBit(Index face, Index facelet, Index state,
			final Index cface, final Index cfacelet, final Index cstate,
			Index bit) {
		return new Expression(new Clause(new Literal(colorBitVar(face, facelet,
				state, bit)), new Literal(colorBitVar(cface, cfacelet, cstate,
				bit), true)), new Clause(new Literal(colorBitVar(face, facelet,
				state, bit), true), new Literal(colorBitVar(cface, cfacelet,
				cstate, bit))));
	}

	protected Expression color(final Index face, final Index facelet,
			final Index state, final Index cface, final Index cfacelet,
			final Index cstate) {
		final Iterator k = new Iterator();
		return Loops.and(0, 2, k, new Value<Expression>() {
			public Expression get() {
				return colorBit(face, facelet, state, cface, cfacelet, cstate,
						k);
			}
		});
	}

	protected Expression color(final Index face, final Index facelet,
			final Index state, final Index color) {
		final Iterator k = new Iterator();
		return Loops.and(0, 2, k, new Value<Expression>() {
			public Expression get() {
				boolean isColorBitSet = (color.get() >> k.get() & 1) == 1;

				return new Expression(new Clause(new Literal(colorBitVar(face,
						facelet, state, k), !isColorBitSet)));
			}
		});
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
	protected Expression colorCorrect(Index face, Index facelet, Index state) {
		return color(face, facelet, state, face);
	}

	protected Expression exactlyOne(final Variable var, int start, int end) {
		return AMO(var, start, end).and(new Expression(ALO(var, start, end)));
	}

	protected Clause ALO(final Variable var, int start, int end) {
		final Iterator i = new Iterator();
		return Loops.or(start, end, i, new Value<Clause>() {
			public Clause get() {
				return new Clause(new Literal(new Variable(var, i)));
			}
		});
	}

	protected Expression AMO(final Variable var, final int start, final int end) {
		final Iterator i = new Iterator();
		final Iterator j = new Iterator();
		return Loops.and(start, end, i, new Value<Expression>() {
			public Expression get() {
				return Loops.and(start, end, j, new Value<Expression>() {
					public Expression get() {
						if (i.get() < j.get()) {
							return new Expression(new Clause(new Literal(
									new Variable(var, i), true), new Literal(
									new Variable(var, j), true)));
						} else {
							return new Expression();
						}
					}
				});
			}
		});
	}


	protected Expression moves(final int states) {
		final Iterator s = new Iterator();
		final Iterator i = new Iterator();
		final Iterator t = new Iterator();
		final Iterator j = new Iterator();
		final Iterator f = new Iterator();

		return Loops.and(0, 5, i, new Value<Expression>() {
			public Expression get() {
				RubixPermutation perm = RubixPermutation.IDENTITY;

				switch (i.get()) {
				case 0:
					perm = RubixPermutation.FRONT;
					break;
				case 1:
					perm = RubixPermutation.BACK;
					break;
				case 2:
					perm = RubixPermutation.LEFT;
					break;
				case 3:
					perm = RubixPermutation.RIGHT;
					break;
				case 4:
					perm = RubixPermutation.UP;
					break;
				case 5:
					perm = RubixPermutation.DOWN;
					break;
				}
				final RubixPermutation permutation = perm;
				return Loops.and(1, states, s, new Value<Expression>() {
					public Expression get() {

						return Loops.and(0, 8, j, new Value<Expression>() {
							public Expression get() {
								return Loops.and(0, 5, f,
										new Value<Expression>() {
											public Expression get() {
												FaceletLocation loc = FaceletLocation
														.get(f.get(), j.get());
												FaceletLocation nloc = permutation
														.relocate(loc);
												if (nloc.equals(loc)) {
													return color(
															new Index(nloc
																	.getFace()),
															new Index(
																	nloc.getFacelet()),
															s.plus(1),
															new Index(loc
																	.getFace()),
															new Index(
																	loc.getFacelet()),
															s)
															.or(new Clause(
																	new Literal(
																			new Variable(
																					"t",
																					s,
																					i),
																			true)));
												} else {
													return Loops
															.and(0,
																	2,
																	t,
																	new Value<Expression>() {
																		public Expression get() {
																			RubixPermutation perm = permutation;
																			switch (t
																					.get()) {
																			case 1:
																				perm = perm
																						.twice();
																				break;
																			case 2:
																				perm = perm
																						.reverse();
																				break;
																			}
																			FaceletLocation loc = FaceletLocation
																					.get(f.get(),
																							j.get());
																			FaceletLocation nloc = perm
																					.relocate(loc);

																			return color(
																					new Index(
																							nloc.getFace()),
																					new Index(
																							nloc.getFacelet()),
																					s.plus(1),
																					new Index(
																							loc.getFace()),
																					new Index(
																							loc.getFacelet()),
																					s)
																					.or(new Clause(
																							new Literal(
																									new Variable(
																											"t",
																											s,
																											i,
																											t),
																									true)));
																		}
																	});
												}
											}
										});
							}
						});
					}
				});
			}
		});
	}

	protected Expression exactlyOneTurnInEachState(int states) {
		final Iterator s = new Iterator();
		return Loops.and(1, states - 1, s, new Value<Expression>() {
			public Expression get() {
				return exactlyOneTurn(s);
			}
		});
	}

	protected Expression exactlyOneTurn(final Index state) {
		final Iterator i = new Iterator();
		return exactlyOne(new Variable("t", state), 0, 5).and(
				Loops.and(0, 5, i, new Value<Expression>() {
					public Expression get() {
						return faceTurn(i, state);
					}
				}));
	}

	// e.g. -dk V exactly-one(Dk, Dk', Dk2)
	protected Expression faceTurn(Index face, Index state) {
		Variable vFace = new Variable("t", state, face);
		return exactlyOne(vFace, 0, 2).or(
				new Clause(new Literal(new Variable("t", state, face), true)));
	}

	int uniques = 0;

	protected String getUniqueName() {
		return "UNIQUENAME" + (uniques++);
	}
}
