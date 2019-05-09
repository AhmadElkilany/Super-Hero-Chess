package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import exceptions.InvalidPowerDirectionException;
import exceptions.InvalidPowerTargetException;
import exceptions.InvalidPowerUseException;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.game.*;
import model.pieces.Piece;
import model.pieces.heroes.ActivatablePowerHero;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.NonActivatablePowerHero;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKickP1;
import model.pieces.sidekicks.SideKickP2;

public class GameWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player p1;
	private Player p2;
	private JButton[][] board;
	private JPanel pay1;
	private JPanel pay2;
	private JButton[] pl1;
	private JButton[] pl2;
	private JPanel b;
	private JPanel dir;
	private Game g;
	private JPanel dead1;
	private JPanel dead2;
	private JButton[][] d1;
	private JButton[][] d2;
	private JPanel directions;
	private JButton curPlay;
	private JButton indd1;
	private JButton indd2;

	public static void main(String[] args) {
		GameWindow w = new GameWindow();
		String n1 = JOptionPane.showInputDialog(w, "Enter player1's name:", "Super Hero Chess",
				JOptionPane.INFORMATION_MESSAGE);
		String n2 = JOptionPane.showInputDialog(w, "Enter player2's name:", "Super Hero Chess",
				JOptionPane.INFORMATION_MESSAGE);
		GameWindow w1 = new GameWindow(n1, n2);
		w1.setVisible(true);

	}

	public GameWindow(String a, String c) {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1300, 900);

		JLabel l1 = new JLabel();
		JLabel l2 = new JLabel();
		l1.setText(a);
		l2.setText(c);
		JPanel names = new JPanel(new BorderLayout());
		names.add(l2, BorderLayout.EAST);
		names.add(l1, BorderLayout.WEST);
		curPlay = new JButton();
		curPlay.setBackground(Color.WHITE);
		curPlay.setText("Current player:" + a);
		names.add(curPlay, BorderLayout.CENTER);
		setTitle("Super Hero Chess");
		getContentPane().setLayout(new BorderLayout());
		b = new JPanel(new GridLayout(7, 6));
		pay1 = new JPanel(new GridLayout(6, 1));
		pay2 = new JPanel(new GridLayout(6, 1));
		pl1 = new JButton[6];
		pl2 = new JButton[6];
		dir = new JPanel(new FlowLayout());
		directions = new JPanel(new GridLayout(3, 2));
		indd1 = new JButton("Dead characters of " + a);
		indd2 = new JButton("Dead characters of " + c);

		board = new JButton[7][6];
		p1 = new Player(a);
		p2 = new Player(c);
		g = new Game(p1, p2);
		getContentPane().add(b, BorderLayout.CENTER);

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (g.getCellAt(i, j).getPiece() == null) {
					JButton but = new JButton();
					board[i][j] = but;
					if ((i + j) % 2 == 0)
						but.setBackground(Color.lightGray);
					else
						but.setBackground(Color.CYAN);
					b.add(but);
					but.addActionListener(this);

				} else {

					JButton but = new JButton();
					board[i][j] = but;
					if ((i + j) % 2 == 0)
						but.setBackground(Color.lightGray);

					else
						but.setBackground(Color.CYAN);
					b.add(but);
					but.addActionListener(this);
					String power = "";
					String name = "";
					if (g.getCellAt(i, j).getPiece() instanceof SideKickP1) {
						name = "Side Kick player 1";
						Icon aa = new ImageIcon("sk1.png");
						but.setIcon(aa);
					}
					if (g.getCellAt(i, j).getPiece() instanceof SideKickP2) {
						name = "Side kick player 2";
						Icon aa = new ImageIcon("sk2.png");
						but.setIcon(aa);

					}
					if (g.getCellAt(i, j).getPiece() instanceof NonActivatablePowerHero) {

						if (g.getCellAt(i, j).getPiece() instanceof Armored) {

							name = "Armored";
							if (((Armored) g.getCellAt(i, j).getPiece()).isArmorUp()) {
								if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
									Icon aas = new ImageIcon("a1up.png");
									but.setIcon(aas);
								} else {
									Icon aas = new ImageIcon("a2up.png");
									but.setIcon(aas);
								}
								power = "Armor up";
							} else {
								if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
									Icon aas = new ImageIcon("a1down.png");
									but.setIcon(aas);
								} else {
									Icon aas = new ImageIcon("a2down.png");
									but.setIcon(aas);
								}
								power = "Armor down";
							}
						}
						if (g.getCellAt(i, j).getPiece() instanceof Speedster) {
							name = "Speedster";
							if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
								Icon aas = new ImageIcon("speedster1.png");
								but.setIcon(aas);
							} else {
								Icon aas = new ImageIcon("speedster2.png");
								but.setIcon(aas);
							}
						}
					} else {
						if (g.getCellAt(i, j).getPiece() instanceof ActivatablePowerHero) {

							if (((ActivatablePowerHero) g.getCellAt(i, j).getPiece()).isPowerUsed())
								power = "power is used";
							else {
								power = "power not used";
							}
							if (g.getCellAt(i, j).getPiece() instanceof Medic) {
								if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
									Icon aas = new ImageIcon("medic1.jpg");
									but.setIcon(aas);
								} else {
									Icon aas = new ImageIcon("medic2.png");
									but.setIcon(aas);
								}
								name = "Medic";
							} else {
								if (g.getCellAt(i, j).getPiece() instanceof Ranged) {
									name = "Ranged";
									if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
										Icon aas = new ImageIcon("ranged1.png");
										but.setIcon(aas);
									} else {
										Icon aas = new ImageIcon("ranged2.jpg");
										but.setIcon(aas);
									}
								} else {
									if (g.getCellAt(i, j).getPiece() instanceof Super) {
										if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
											Icon aas = new ImageIcon("super1.png");
											but.setIcon(aas);
										} else {
											Icon aas = new ImageIcon("super2.png");
											but.setIcon(aas);
										}
										name = "Super";

									} else {
										if (g.getCellAt(i, j).getPiece() instanceof Tech) {
											if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
												Icon aas = new ImageIcon("tech1.png");
												but.setIcon(aas);
											} else {
												Icon aas = new ImageIcon("tech2.png");
												but.setIcon(aas);
											}
											name = "Tech";
										}
									}
								}
							}
						}

					}
					if (power.equals(""))
						board[i][j].setToolTipText(g.getCellAt(i, j).getPiece().getName() + "," + name + ","
								+ g.getCellAt(i, j).getPiece().getOwner().getName());
					else
						board[i][j].setToolTipText(g.getCellAt(i, j).getPiece().getName() + "," + name + "," + power
								+ "," + g.getCellAt(i, j).getPiece().getOwner().getName());
				}
			}
		}

		JButton down = new JButton("Down");
		JButton downRight = new JButton("DownRight");
		JButton downLeft = new JButton("DownLeft");
		JButton up = new JButton("Up");
		JButton upRight = new JButton("UpRight");
		JButton upLeft = new JButton("UpLeft");
		JButton left = new JButton("Left");
		JButton right = new JButton("Right");
		JButton usePower = new JButton("UsePower");
		JButton hack = new JButton("Hack");

		JButton restore = new JButton("Restore");

		down.addActionListener(this);
		downRight.addActionListener(this);
		downLeft.addActionListener(this);
		up.addActionListener(this);
		upRight.addActionListener(this);
		upLeft.addActionListener(this);
		left.addActionListener(this);
		right.addActionListener(this);
		usePower.addActionListener(this);
		hack.addActionListener(this);

		restore.addActionListener(this);

		dead1 = new JPanel(new GridLayout(3, 3));
		dead2 = new JPanel(new GridLayout(3, 3));
		d1 = new JButton[3][3];
		d2 = new JButton[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton b1 = new JButton();
				JButton b2 = new JButton();
				dead1.add(b1);
				dead2.add(b2);
				d1[i][j] = b1;
				d2[i][j] = b2;
				b1.addActionListener(this);
				b2.addActionListener(this);
			}
		}
		dir.add(indd1);
		dir.add(dead1);
		directions.add(right);
		directions.add(left);
		directions.add(upLeft);
		directions.add(upRight);
		directions.add(up);
		directions.add(downLeft);
		directions.add(downRight);
		directions.add(down);
		directions.add(hack);
		directions.add(restore);
		directions.add(usePower);
		dir.add(directions);
		dir.add(dead2);
		dir.add(indd2);

		getContentPane().add(dir, BorderLayout.SOUTH);
		for (int i = 0; i < 6; i++) {
			JButton beg1 = new JButton();
			JButton beg2 = new JButton();
			beg1.setBackground(Color.BLACK);
			pl1[i] = beg1;
			beg2.setBackground(Color.BLACK);
			pl2[i] = beg2;
			pay1.add(beg1);
			pay2.add(beg2);
		}
		getContentPane().add(pay1, BorderLayout.WEST);
		getContentPane().add(pay2, BorderLayout.EAST);

		getContentPane().add(names, BorderLayout.NORTH);
	}

	public void resetDead() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				d1[i][j].setText(null);
				d2[i][j].setText(null);

			}
		}
	}

	public void updateDead() {
		resetDead();
		ArrayList<Piece> t1 = new ArrayList<>();
		ArrayList<Piece> t2 = new ArrayList<>();
		int l1 = g.getPlayer1().getDeadCharacters().size();
		for (int i = 0; i < l1; i++) {
			t1.add(g.getPlayer1().getDeadCharacters().get(0));
			g.getPlayer1().getDeadCharacters().add(g.getPlayer1().getDeadCharacters().remove(0));
		}
		int l2 = g.getPlayer2().getDeadCharacters().size();
		for (int i = 0; i < l2; i++) {
			t2.add(g.getPlayer2().getDeadCharacters().get(0));
			g.getPlayer2().getDeadCharacters().add(g.getPlayer2().getDeadCharacters().remove(0));
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (t1.isEmpty())
					break;
				String p = t1.remove(0).getName();
				d1[i][j].setText(p);
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (t2.isEmpty())
					break;
				String p = t2.remove(0).getName();
				d2[i][j].setText(p);
			}
		}
	}

	public GameWindow() {
		// TODO Auto-generated constructor stub
	}

	public void showException(String x) {
		JOptionPane.showMessageDialog(this, x, "", JOptionPane.INFORMATION_MESSAGE);
		return;
	}

	public void updatePayLoad(Player p) {
		if (p == g.getPlayer1()) {
			int a = 6 - g.getPlayer2().getPayloadPos();
			for (int i = 0; i < 6; i++) {
				if (i < a)
					pl2[i].setBackground(Color.BLACK);
				else
					pl2[i].setBackground(Color.BLUE);
			}
		} else {
			int a = 6 - g.getPlayer1().getPayloadPos();
			for (int i = 0; i < 6; i++) {
				if (i < a)
					pl1[i].setBackground(Color.BLACK);
				else
					pl1[i].setBackground(Color.BLUE);
			}
		}
	}

	public void updateBoard() {
		curPlay.setText("Current player:" + g.getCurrentPlayer().getName());
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (g.getCellAt(i, j).getPiece() == null) {

					board[i][j].setIcon(null);
					board[i][j].setToolTipText(null);

				} else {

					String power = "";
					String name = "";
					if (g.getCellAt(i, j).getPiece() instanceof SideKickP1) {
						Icon aa = new ImageIcon("sk1.png");
						board[i][j].setIcon(aa);
						name = "Side Kick player 1";

					}
					if (g.getCellAt(i, j).getPiece() instanceof SideKickP2) {
						Icon aa = new ImageIcon("sk2.png");

						name = "Side kick player 2";
						board[i][j].setIcon(aa);

					}
					if (g.getCellAt(i, j).getPiece() instanceof NonActivatablePowerHero) {

						if (g.getCellAt(i, j).getPiece() instanceof Armored) {

							name = "Armored";
							if (((Armored) g.getCellAt(i, j).getPiece()).isArmorUp()) {
								if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
									Icon aas = new ImageIcon("a1up.png");
									board[i][j].setIcon(aas);
								} else {
									Icon aas = new ImageIcon("a2up.png");
									board[i][j].setIcon(aas);
								}
								power = "Armor up";
							} else {
								if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
									Icon aas = new ImageIcon("a1down.jpg");
									board[i][j].setIcon(aas);
								} else {
									Icon aas = new ImageIcon("a2d.jpg");
									board[i][j].setIcon(aas);
								}
								power = "Armor down";
							}
						}
						if (g.getCellAt(i, j).getPiece() instanceof Speedster) {
							name = "Speedster";
							if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
								Icon aas = new ImageIcon("speedster1.png");
								board[i][j].setIcon(aas);
							} else {
								Icon aas = new ImageIcon("speedster2.png");
								board[i][j].setIcon(aas);
							}
						}
					} else {
						if (g.getCellAt(i, j).getPiece() instanceof ActivatablePowerHero) {

							if (((ActivatablePowerHero) g.getCellAt(i, j).getPiece()).isPowerUsed())
								power = "power is used";
							else {
								power = "power not used";
							}
							if (g.getCellAt(i, j).getPiece() instanceof Medic) {
								if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
									Icon aas = new ImageIcon("medic1.jpg");
									board[i][j].setIcon(aas);
								} else {
									Icon aas = new ImageIcon("medic2.png");
									board[i][j].setIcon(aas);
								}
								name = "Medic";
							} else {
								if (g.getCellAt(i, j).getPiece() instanceof Ranged) {
									name = "Ranged";
									if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
										Icon aas = new ImageIcon("ranged1.png");
										board[i][j].setIcon(aas);
									} else {
										Icon aas = new ImageIcon("ranged2.jpg");
										board[i][j].setIcon(aas);
									}

								} else {
									if (g.getCellAt(i, j).getPiece() instanceof Super) {
										if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
											Icon aas = new ImageIcon("super1.png");
											board[i][j].setIcon(aas);
										} else {
											Icon aas = new ImageIcon("super2.png");
											board[i][j].setIcon(aas);
										}
										name = "Super";
									} else {
										if (g.getCellAt(i, j).getPiece() instanceof Tech) {
											if (g.getCellAt(i, j).getPiece().getOwner() == g.getPlayer1()) {
												Icon aas = new ImageIcon("tech1.png");
												board[i][j].setIcon(aas);
											} else {
												Icon aas = new ImageIcon("tech2.png");
												board[i][j].setIcon(aas);
											}
											name = "Tech";
										}
									}
								}
							}
						}

					}
					if (power.equals(""))
						board[i][j].setToolTipText(g.getCellAt(i, j).getPiece().getName() + "," + name + ","
								+ g.getCellAt(i, j).getPiece().getOwner().getName());
					else
						board[i][j].setToolTipText(g.getCellAt(i, j).getPiece().getName() + "," + name + "," + power
								+ "," + g.getCellAt(i, j).getPiece().getOwner().getName());
				}
			}
		}

	}

	private Piece temp = null;
	private boolean p = false;

	private Piece target = null;

	public void actionPerformed(ActionEvent e) {

		if (temp == null) {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					if (board[i][j] == e.getSource()) {
						temp = g.getCellAt(i, j).getPiece();
						break;
					}
				}
			}
		} else {
			if (e.getActionCommand().equalsIgnoreCase("right") && p == false) {
				try {
					temp.move(Direction.RIGHT);
					this.updatePayLoad(g.getCurrentPlayer());
					this.updateBoard();
					temp = null;
					this.updateDead();
					if (g.getCurrentPlayer() == g.getPlayer1()) {
						if (g.getPlayer2().getPayloadPos() >= 6) {
							int in = JOptionPane.showConfirmDialog(null, "game finshed",
									g.getPlayer2().getName() + " won", JOptionPane.DEFAULT_OPTION);
							if (in == 0) {
								System.exit(0);
							}
						}
					} else {
						if (g.getPlayer1().getPayloadPos() >= 6) {
							int in = JOptionPane.showConfirmDialog(null, "game finshed",
									g.getPlayer1().getName() + " won", JOptionPane.DEFAULT_OPTION);
							if (in == 0) {
								System.exit(0);
							}
						}

					}
				} catch (UnallowedMovementException e1) {
					e1 = new UnallowedMovementException("unallowed movement", temp, Direction.RIGHT);
					JOptionPane.showMessageDialog(null, e1.getMessage());
					temp = null;

				} catch (OccupiedCellException e1) {
					e1 = new OccupiedCellException("Here is a friendly ", temp, Direction.RIGHT);
					JOptionPane.showMessageDialog(null, e1.getMessage());
					temp = null;

				} catch (WrongTurnException e1) {
					e1 = new WrongTurnException("It is not your turn", temp);
					JOptionPane.showMessageDialog(null, e1.getMessage());
					temp = null;
				}
			} else {
				if (e.getActionCommand().equalsIgnoreCase("upright") && p == false) {
					try {
						temp.move(Direction.UPRIGHT);
						this.updatePayLoad(g.getCurrentPlayer());
						this.updateBoard();
						this.updateDead();
						temp = null;
						if (g.getCurrentPlayer() == g.getPlayer1()) {
							if (g.getPlayer2().getPayloadPos() >= 6) {
								int in = JOptionPane.showConfirmDialog(null, "game finshed",
										g.getPlayer2().getName() + " won", JOptionPane.DEFAULT_OPTION);
								if (in == 0) {
									System.exit(0);
								}
							}
						} else {
							if (g.getPlayer1().getPayloadPos() >= 6) {
								int in = JOptionPane.showConfirmDialog(null, "game finshed",
										g.getPlayer1().getName() + " won", JOptionPane.DEFAULT_OPTION);
								if (in == 0) {
									System.exit(0);
								}
							}
						}
					} catch (UnallowedMovementException e1) {
						e1 = new UnallowedMovementException("unallowed movement", temp, Direction.UPRIGHT);
						JOptionPane.showMessageDialog(null, e1.getMessage());
						temp = null;

					} catch (OccupiedCellException e1) {
						e1 = new OccupiedCellException("Here is a friendly ", temp, Direction.UPRIGHT);
						JOptionPane.showMessageDialog(null, e1.getMessage());
						temp = null;

					} catch (WrongTurnException e1) {
						e1 = new WrongTurnException("It is not your turn", temp);
						JOptionPane.showMessageDialog(null, e1.getMessage());
						temp = null;
					}

				} else {
					if (e.getActionCommand().equalsIgnoreCase("downright") && p == false) {
						try {
							temp.move(Direction.DOWNRIGHT);
							this.updatePayLoad(g.getCurrentPlayer());
							this.updateBoard();
							temp = null;
							this.updateDead();
							if (g.getCurrentPlayer() == g.getPlayer1()) {
								if (g.getPlayer2().getPayloadPos() >= 6) {
									int in = JOptionPane.showConfirmDialog(null, "game finshed",
											g.getPlayer2().getName() + " won", JOptionPane.DEFAULT_OPTION);
									if (in == 0) {
										System.exit(0);
									}
								}
							} else {
								if (g.getPlayer1().getPayloadPos() >= 6) {
									int in = JOptionPane.showConfirmDialog(null, "game finshed",
											g.getPlayer1().getName() + " won", JOptionPane.DEFAULT_OPTION);
									if (in == 0) {
										System.exit(0);
									}
								}
							}
						} catch (UnallowedMovementException e1) {
							e1 = new UnallowedMovementException("unallowed movement", temp, Direction.DOWNRIGHT);
							JOptionPane.showMessageDialog(null, e1.getMessage());
							temp = null;

						} catch (OccupiedCellException e1) {
							e1 = new OccupiedCellException("Here is a friendly ", temp, Direction.DOWNRIGHT);
							JOptionPane.showMessageDialog(null, e1.getMessage());
							temp = null;

						} catch (WrongTurnException e1) {
							e1 = new WrongTurnException("It is not your turn", temp);
							JOptionPane.showMessageDialog(null, e1.getMessage());
							temp = null;
						}

					} else {
						if (e.getActionCommand().equalsIgnoreCase("down") && p == false) {
							try {
								temp.move(Direction.DOWN);
								this.updatePayLoad(g.getCurrentPlayer());
								this.updateBoard();
								temp = null;
								this.updateDead();
								if (g.getCurrentPlayer() == g.getPlayer1()) {
									if (g.getPlayer2().getPayloadPos() >= 6) {
										int in = JOptionPane.showConfirmDialog(null, "game finshed",
												g.getPlayer2().getName() + " won", JOptionPane.DEFAULT_OPTION);
										if (in == 0) {
											System.exit(0);
										}
									}
								} else {
									if (g.getPlayer1().getPayloadPos() >= 6) {
										int in = JOptionPane.showConfirmDialog(null, "game finshed",
												g.getPlayer1().getName() + " won", JOptionPane.DEFAULT_OPTION);
										if (in == 0) {
											System.exit(0);
										}
									}
								}
							} catch (UnallowedMovementException e1) {
								e1 = new UnallowedMovementException("unallowed movement", temp, Direction.DOWN);
								JOptionPane.showMessageDialog(null, e1.getMessage());
								temp = null;

							} catch (OccupiedCellException e1) {
								e1 = new OccupiedCellException("Here is a friendly ", temp, Direction.DOWN);
								JOptionPane.showMessageDialog(null, e1.getMessage());
								temp = null;

							} catch (WrongTurnException e1) {
								e1 = new WrongTurnException("It is not your turn", temp);
								JOptionPane.showMessageDialog(null, e1.getMessage());
								temp = null;
							}
						} else {
							if (e.getActionCommand().equalsIgnoreCase("DownLeft") && p == false) {
								try {
									temp.move(Direction.DOWNLEFT);
									this.updatePayLoad(g.getCurrentPlayer());
									this.updateBoard();
									temp = null;
									this.updateDead();
									if (g.getCurrentPlayer() == g.getPlayer1()) {
										if (g.getPlayer2().getPayloadPos() >= 6) {
											int in = JOptionPane.showConfirmDialog(null, "game finshed",
													g.getPlayer2().getName() + " won", JOptionPane.DEFAULT_OPTION);
											if (in == 0) {
												System.exit(0);
											}
										}
									} else {
										if (g.getPlayer1().getPayloadPos() >= 6) {
											int in = JOptionPane.showConfirmDialog(null, "game finshed",
													g.getPlayer1().getName() + " won", JOptionPane.DEFAULT_OPTION);
											if (in == 0) {
												System.exit(0);
											}
										}
									}
								} catch (UnallowedMovementException e1) {
									e1 = new UnallowedMovementException("unallowed movement", temp, Direction.DOWNLEFT);
									JOptionPane.showMessageDialog(null, e1.getMessage());
									temp = null;

								} catch (OccupiedCellException e1) {
									e1 = new OccupiedCellException("Here is a friendly ", temp, Direction.DOWNLEFT);
									JOptionPane.showMessageDialog(null, e1.getMessage());
									temp = null;

								} catch (WrongTurnException e1) {
									e1 = new WrongTurnException("It is not your turn", temp);
									JOptionPane.showMessageDialog(null, e1.getMessage());
									temp = null;
								}
							} else {
								if (e.getActionCommand().equalsIgnoreCase("left") && p == false) {
									try {
										temp.move(Direction.LEFT);
										this.updatePayLoad(g.getCurrentPlayer());
										this.updateBoard();
										temp = null;
										this.updateDead();
										if (g.getCurrentPlayer() == g.getPlayer1()) {
											if (g.getPlayer2().getPayloadPos() >= 6) {
												int in = JOptionPane.showConfirmDialog(null, "game finshed",
														g.getPlayer2().getName() + " won", JOptionPane.DEFAULT_OPTION);
												if (in == 0) {
													System.exit(0);
												}
											}
										} else {
											if (g.getPlayer1().getPayloadPos() >= 6) {
												int in = JOptionPane.showConfirmDialog(null, "game finshed",
														g.getPlayer1().getName() + " won", JOptionPane.DEFAULT_OPTION);
												if (in == 0) {
													System.exit(0);
												}
											}
										}
									} catch (UnallowedMovementException e1) {
										e1 = new UnallowedMovementException("unallowed movement", temp, Direction.LEFT);
										JOptionPane.showMessageDialog(null, e1.getMessage());
										temp = null;

									} catch (OccupiedCellException e1) {
										e1 = new OccupiedCellException("Here is a friendly ", temp, Direction.LEFT);
										JOptionPane.showMessageDialog(null, e1.getMessage());
										temp = null;

									} catch (WrongTurnException e1) {
										e1 = new WrongTurnException("It is not your turn", temp);
										JOptionPane.showMessageDialog(null, e1.getMessage());
										temp = null;
									}
								} else {
									if (e.getActionCommand().equalsIgnoreCase("up") && p == false) {
										try {
											temp.move(Direction.UP);
											this.updatePayLoad(g.getCurrentPlayer());
											this.updateBoard();
											temp = null;
											this.updateDead();
											if (g.getCurrentPlayer() == g.getPlayer1()) {
												if (g.getPlayer2().getPayloadPos() >= 6) {
													int in = JOptionPane.showConfirmDialog(null, "game finshed",
															g.getPlayer2().getName() + " won",
															JOptionPane.DEFAULT_OPTION);
													if (in == 0) {
														System.exit(0);
													}
												}
											} else {
												if (g.getPlayer1().getPayloadPos() >= 6) {
													int in = JOptionPane.showConfirmDialog(null, "game finshed",
															g.getPlayer1().getName() + " won",
															JOptionPane.DEFAULT_OPTION);
													if (in == 0) {
														System.exit(0);
													}
												}
											}
										} catch (UnallowedMovementException e1) {
											e1 = new UnallowedMovementException("unallowed movement", temp,
													Direction.UP);
											JOptionPane.showMessageDialog(null, e1.getMessage());
											temp = null;

										} catch (OccupiedCellException e1) {
											e1 = new OccupiedCellException("Here is a friendly ", temp, Direction.UP);
											JOptionPane.showMessageDialog(null, e1.getMessage());
											temp = null;

										} catch (WrongTurnException e1) {
											e1 = new WrongTurnException("It is not your turn", temp);
											JOptionPane.showMessageDialog(null, e1.getMessage());
											temp = null;
										}
									} else {
										if (e.getActionCommand().equalsIgnoreCase("upleft") && p == false) {
											try {
												temp.move(Direction.UPLEFT);
												this.updatePayLoad(g.getCurrentPlayer());
												this.updateBoard();
												temp = null;
												this.updateDead();
												if (g.getCurrentPlayer() == g.getPlayer1()) {
													if (g.getPlayer2().getPayloadPos() >= 6) {
														int in = JOptionPane.showConfirmDialog(null, "game finshed",
																g.getPlayer2().getName() + " won",
																JOptionPane.DEFAULT_OPTION);
														if (in == 0) {
															System.exit(0);
														}
													}
												} else {
													if (g.getPlayer1().getPayloadPos() >= 6) {
														int in = JOptionPane.showConfirmDialog(null, "game finshed",
																g.getPlayer1().getName() + " won",
																JOptionPane.DEFAULT_OPTION);
														if (in == 0) {
															System.exit(0);
														}
													}
												}
											} catch (UnallowedMovementException e1) {
												e1 = new UnallowedMovementException("unallowed movement", temp,
														Direction.UPLEFT);
												JOptionPane.showMessageDialog(null, e1.getMessage());
												temp = null;

											} catch (OccupiedCellException e1) {
												e1 = new OccupiedCellException("Here is a friendly ", temp,
														Direction.UPLEFT);
												JOptionPane.showMessageDialog(null, e1.getMessage());
												temp = null;

											} catch (WrongTurnException e1) {
												e1 = new WrongTurnException("It is not your turn", temp);
												JOptionPane.showMessageDialog(null, e1.getMessage());
												temp = null;
											}

										} else {
											if (e.getActionCommand().equalsIgnoreCase("usepower")) {
												if (temp instanceof ActivatablePowerHero)
													p = true;

											} else {
												if (p == true) {
													if (e.getActionCommand().equalsIgnoreCase("up")
															&& (temp instanceof Ranged || temp instanceof Super)) {
														try {
															((ActivatablePowerHero) temp).usePower(Direction.UP, null,
																	null);
															temp = null;
															this.updateDead();
															updateBoard();
															updatePayLoad(g.getCurrentPlayer());
															p = false;
															if (g.getCurrentPlayer() == g.getPlayer1()) {
																if (g.getPlayer2().getPayloadPos() >= 6) {
																	int in = JOptionPane.showConfirmDialog(null,
																			"game finshed",
																			g.getPlayer2().getName() + " won",
																			JOptionPane.DEFAULT_OPTION);
																	if (in == 0) {
																		System.exit(0);
																	}
																}
															} else {
																if (g.getPlayer1().getPayloadPos() >= 6) {
																	int in = JOptionPane.showConfirmDialog(null,
																			"game finshed",
																			g.getPlayer1().getName() + " won",
																			JOptionPane.DEFAULT_OPTION);
																	if (in == 0) {
																		System.exit(0);
																	}
																}

															}
														} catch (InvalidPowerUseException e1) {
															e1 = new InvalidPowerDirectionException(
																	"Can't use power in this direction", temp,
																	Direction.UP);
															JOptionPane.showMessageDialog(null, e1.getMessage());
															temp = null;
															p = false;
														} catch (WrongTurnException e1) {
															e1 = new WrongTurnException("That's not you turn", temp);
															JOptionPane.showMessageDialog(null, e1.getMessage());
															temp = null;
															p = false;
														}
													}
													if (e.getActionCommand().equalsIgnoreCase("down")
															&& (temp instanceof Ranged || temp instanceof Super)) {
														try {
															((ActivatablePowerHero) temp).usePower(Direction.DOWN, null,
																	null);
															temp = null;
															this.updateDead();
															updateBoard();
															updatePayLoad(g.getCurrentPlayer());
															p = false;
															if (g.getCurrentPlayer() == g.getPlayer1()) {
																if (g.getPlayer2().getPayloadPos() >= 6) {
																	int in = JOptionPane.showConfirmDialog(null,
																			"game finshed",
																			g.getPlayer2().getName() + " won",
																			JOptionPane.DEFAULT_OPTION);
																	if (in == 0) {
																		System.exit(0);
																	}
																}
															} else {
																if (g.getPlayer1().getPayloadPos() >= 6) {
																	int in = JOptionPane.showConfirmDialog(null,
																			"game finshed",
																			g.getPlayer1().getName() + " won",
																			JOptionPane.DEFAULT_OPTION);
																	if (in == 0) {
																		System.exit(0);
																	}
																}

															}
														} catch (InvalidPowerUseException e1) {
															e1 = new InvalidPowerDirectionException(
																	"Can't use power in this direction", temp,
																	Direction.DOWN);
															JOptionPane.showMessageDialog(null, e1.getMessage());
															temp = null;
															p = false;
														} catch (WrongTurnException e1) {
															e1 = new WrongTurnException("That's not you turn", temp);
															JOptionPane.showMessageDialog(null, e1.getMessage());
															temp = null;
															p = false;
														}

													}
													if (e.getActionCommand().equalsIgnoreCase("right")
															&& (temp instanceof Ranged || temp instanceof Super)) {
														try {
															((ActivatablePowerHero) temp).usePower(Direction.RIGHT,
																	null, null);
															temp = null;
															this.updateDead();
															updateBoard();
															updatePayLoad(g.getCurrentPlayer());
															p = false;
															if (g.getCurrentPlayer() == g.getPlayer1()) {
																if (g.getPlayer2().getPayloadPos() >= 6) {
																	int in = JOptionPane.showConfirmDialog(null,
																			"game finshed",
																			g.getPlayer2().getName() + " won",
																			JOptionPane.DEFAULT_OPTION);
																	if (in == 0) {
																		System.exit(0);
																	}
																}
															} else {
																if (g.getPlayer1().getPayloadPos() >= 6) {
																	int in = JOptionPane.showConfirmDialog(null,
																			"game finshed",
																			g.getPlayer1().getName() + " won",
																			JOptionPane.DEFAULT_OPTION);
																	if (in == 0) {
																		System.exit(0);
																	}
																}

															}
														} catch (InvalidPowerUseException e1) {
															e1 = new InvalidPowerDirectionException(
																	"Can't use power in this direction", temp,
																	Direction.RIGHT);
															JOptionPane.showMessageDialog(null, e1.getMessage());
															temp = null;
															p = false;
														} catch (WrongTurnException e1) {
															e1 = new WrongTurnException("That's not you turn", temp);
															JOptionPane.showMessageDialog(null, e1.getMessage());
															temp = null;
															p = false;
														}
													}

													if (e.getActionCommand().equalsIgnoreCase("left")
															&& (temp instanceof Ranged || temp instanceof Super)) {
														try {
															((ActivatablePowerHero) temp).usePower(Direction.LEFT, null,
																	null);
															temp = null;
															this.updateDead();
															updateBoard();
															updatePayLoad(g.getCurrentPlayer());
															p = false;
															if (g.getCurrentPlayer() == g.getPlayer1()) {
																if (g.getPlayer2().getPayloadPos() >= 6) {
																	int in = JOptionPane.showConfirmDialog(null,
																			"game finshed",
																			g.getPlayer2().getName() + " won",
																			JOptionPane.DEFAULT_OPTION);
																	if (in == 0) {
																		System.exit(0);
																	}
																}
															} else {
																if (g.getPlayer1().getPayloadPos() >= 6) {
																	int in = JOptionPane.showConfirmDialog(null,
																			"game finshed",
																			g.getPlayer1().getName() + " won",
																			JOptionPane.DEFAULT_OPTION);
																	if (in == 0) {
																		System.exit(0);
																	}
																}

															}
														} catch (InvalidPowerUseException e1) {
															e1 = new InvalidPowerDirectionException(
																	"Can't use power in this direction", temp,
																	Direction.LEFT);
															JOptionPane.showMessageDialog(null, e1.getMessage());
															temp = null;
															p = false;
														} catch (WrongTurnException e1) {
															e1 = new WrongTurnException("That's not you turn", temp);
															JOptionPane.showMessageDialog(null, e1.getMessage());
															temp = null;
															p = false;
														}

													}
													if (temp instanceof Medic) {
														if (target == null) {
															int pos = 0;
															for (int i = 0; i < 3; i++) {
																for (int j = 0; j < 3; j++) {
																	if (d1[i][j] == e.getSource())
																		target = g.getPlayer1().getDeadCharacters()
																				.get(pos);
																	else
																		pos++;
																}
																if (target == null)
																	break;
															}
															if (target == null) {
																int x = 0;
																for (int i = 0; i < 3; i++) {
																	for (int j = 0; j < 3; j++) {
																		if (d2[i][j] == e.getSource())
																			target = g.getPlayer2().getDeadCharacters()
																					.get(x);
																		else
																			x++;
																	}
																	if (target != null)
																		break;
																}
															}
														} else {
															if (e.getActionCommand().equalsIgnoreCase("up")) {
																try {
																	((Medic) temp).usePower(Direction.UP, target, null);
																	target = null;
																	temp = null;
																	p = false;
																	updateBoard();
																	updateDead();
																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"Can't revive this", temp, target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																}
															}
															if (e.getActionCommand().equalsIgnoreCase("upright")) {
																try {
																	((Medic) temp).usePower(Direction.UPRIGHT, target,
																			null);
																	target = null;
																	temp = null;
																	p = false;
																	updateBoard();
																	updateDead();
																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"Can't revive this", temp, target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																}
															}
															if (e.getActionCommand().equalsIgnoreCase("right")) {
																try {
																	((Medic) temp).usePower(Direction.RIGHT, target,
																			null);
																	target = null;
																	temp = null;
																	p = false;
																	updateBoard();
																	updateDead();
																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"Can't revive this", temp, target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																}
															}
															if (e.getActionCommand().equalsIgnoreCase("downright")) {
																try {
																	((Medic) temp).usePower(Direction.DOWNRIGHT, target,
																			null);
																	target = null;
																	temp = null;
																	p = false;
																	updateBoard();
																	updateDead();
																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"Can't revive this", temp, target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																}
															}
															if (e.getActionCommand().equalsIgnoreCase("down")) {
																try {
																	((Medic) temp).usePower(Direction.DOWN, target,
																			null);
																	target = null;
																	temp = null;
																	p = false;
																	updateBoard();
																	updateDead();
																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"Can't revive this", temp, target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																}
															}
															if (e.getActionCommand().equalsIgnoreCase("downleft")) {
																try {
																	((Medic) temp).usePower(Direction.DOWNLEFT, target,
																			null);
																	target = null;
																	temp = null;
																	p = false;
																	updateBoard();
																	updateDead();
																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"Can't revive this", temp, target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																}
															}
															if (e.getActionCommand().equalsIgnoreCase("left")) {
																try {
																	((Medic) temp).usePower(Direction.LEFT, target,
																			null);
																	target = null;
																	temp = null;
																	p = false;
																	updateBoard();
																	updateDead();
																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"Can't revive this", temp, target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																}
															}
															if (e.getActionCommand().equalsIgnoreCase("upleft")) {
																try {
																	((Medic) temp).usePower(Direction.UPLEFT, target,
																			null);
																	target = null;
																	temp = null;
																	p = false;
																	updateBoard();
																	updateDead();
																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"Can't revive this", temp, target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																	p = false;
																}
															}
														}
													}

												} else {
													if (temp instanceof Tech) {
														if (target == null) {
															for (int x = 0; x < 7; x++) {
																for (int y = 0; y < 6; y++) {
																	if (board[x][y] == e.getSource()) {
																		target = g.getCellAt(x, y).getPiece();
																	}
																}
															}
														} else {
															if (e.getActionCommand().equalsIgnoreCase("restore")
																	|| e.getActionCommand().equalsIgnoreCase("hack")) {
																try {
																	((Tech) temp).usePower(null, target, null);
																	this.updateBoard();
																	this.updateDead();
																	this.updatePayLoad(g.getCurrentPlayer());
																	temp = null;
																	target = null;

																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"Can't use power on this piece", temp,
																			target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	temp = null;
																	target = null;

																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	temp = null;
																	target = null;

																}

															} else {
																int x = 0;
																int y = 0;
																for (int i = 0; i < 7; i++) {
																	for (int j = 0; j < 6; j++) {
																		if (board[i][j] == e.getSource()) {
																			x = i;
																			y = j;
																		}
																	}
																}
																Point newPos = new Point(x, y);
																try {
																	((Tech) temp).usePower(null, target, newPos);
																	target = null;
																	temp = null;
																	updateBoard();
																} catch (InvalidPowerUseException e1) {
																	e1 = new InvalidPowerTargetException(
																			"This cell is occupied", temp, target);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	temp = null;
																	target = null;
																} catch (WrongTurnException e1) {
																	e1 = new WrongTurnException("That's not you turn",
																			temp);
																	JOptionPane.showMessageDialog(null,
																			e1.getMessage());
																	target = null;
																	temp = null;
																}
															}
														}
													}
												}
											}

										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
