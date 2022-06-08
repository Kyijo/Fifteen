package Fifteen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Puzzle implements ActionListener {
	private String[] w;
	private int empty;
	private int clicks;
	private int x;
	private int size;
	private JButton[][] board;
	private JFrame frame;
	private JPanel panel;

	// declaration of variables
	public Puzzle() {
		clicks = 0;
		x = 4;
		size = x * x;
		w = new String[size - 1];
		empty = x * x;
		board = new JButton[x][x];
		panel = new JPanel();
		frame = new JFrame("FifteenGame");

		// winstate - sorted board
		for (int i = 1; i < size; i++) {
			w[i - 1] = Integer.toString(i);
		}

		Arrays.asList(w);
	}

	// initialiaze board
	void playFifteen() {
		ArrayList<Integer> list = new ArrayList<Integer>(size);
		for (boolean isSolvable = false; isSolvable == false;) {
			for (int i = 0; i < size; i++) {
				list.add(i, i);
				Collections.shuffle(list);
				isSolvable = isSolvable(list);
			}

		}
		for (int i = 0; i < size; i++) {
			int r = i / x;
			int d = i % x;
			board[r][d] = new JButton(String.valueOf(list.get(i)));
			if (list.get(i) == 0) {
				empty = i;
				board[r][d].setVisible(false);
			}
			board[r][d].setBackground(Color.DARK_GRAY);
			board[r][d].setForeground(Color.LIGHT_GRAY);
			board[r][d].addActionListener(this);
			panel.add(board[r][d]);

		}

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 400);
		frame.setSize(450, 450);
		frame.setVisible(true);
		panel.setLayout(new GridLayout(x, x));
		Container c = frame.getContentPane();
		c.add(panel, BorderLayout.CENTER);
		c.setBackground(Color.white);

	}

	// Checks if the board is sorted or not
	public boolean SortedFifteen() {
		for (int i = w.length - 1; i >= 0; i--) {
			String a = board[i / x][i % x].getText();
			if (!(a.equals(w[i]))) {
				return false;
			}
		}
		return true;
	}

	// Checks if board is solvable or not
	private boolean isSolvable(ArrayList<Integer> list) {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < i; j++) {
				if (list.get(j) > list.get(i)) {
					sum++;
				}
			}
		}
		return sum % 2 == 0;
	}

	// Move Buttons if you click on em
	public void actionPerformed(ActionEvent g) {
		JButton pressed = (JButton) g.getSource();
		clicks++;
		int a = indexOf(pressed.getText());
		int r = a / x;
		int d = a % x;
		makeMove(r, d);

		// If board is sorted returns Message
		if (SortedFifteen()) {
			JOptionPane.showMessageDialog(pressed, "You won!" + "\nMoves: " + clicks);

		}

	}

	// When you click on Button, this method return that they move to blank button -
	// thanks to getIndex
	private boolean makeMove(int r, int d) {
		int emptyR = empty / x;
		int emptyD = empty % x;
		int rDiff = emptyR - r;
		int dDiff = emptyD - d;
	    boolean inR = (r == emptyR);
		boolean inD = (d == emptyD);
		boolean isNDiag = inR | inD;

		if (isNDiag) {
			int diff = Math.abs(dDiff);

			if (dDiff < 0 & inR) {
				for (int i = 0; i < diff; i++) {
					board[emptyR][emptyD + i].setText(board[emptyR][emptyD + (i + 1)].getText());
				}

			} else if (dDiff > 0 & inR) {
				for (int i = 0; i < diff; i++) {
					board[emptyR][emptyD - i].setText(board[emptyR][emptyD - (i + 1)].getText());
				}
			}
			diff = Math.abs(rDiff);
			if (rDiff < 0 & inD) {
				for (int i = 0; i < diff; i++) {
					board[emptyR + i][emptyD].setText(board[emptyR + (i + 1)][emptyD].getText());
				}
			} else if (rDiff > 0 & inD) {
				for (int i = 0; i < diff; i++) {
					board[emptyR - i][emptyD].setText(board[emptyR - (i + 1)][emptyD].getText());
				}
			}
			board[emptyR][emptyD].setVisible(true);
			board[r][d].setText(Integer.toString(0));
			board[r][d].setVisible(false);
			empty = getIndex(r, d);

		}
		return true;
	}

	// Gets index of button so it can move up/down/left/right
	private int getIndex(int i, int j) {
		return (j + i * x);
	}

	// index of button you click on
	public int indexOf(String s) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getText() == s) {
					return getIndex(i, j);
				}
			}
		}
		return -1;
	}
}
