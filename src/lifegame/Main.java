package lifegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main implements Runnable{

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main());
	}

	public void run() {
		BoardModel model = new BoardModel(12, 12);

		JFrame frame = new JFrame("Lifegame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(400, 300));
		frame.setMinimumSize(new Dimension(300, 200));

		base.setLayout(new BorderLayout());
		JButton button_u = new JButton("Undo");
		BoardView view = new BoardView(model, button_u, this);
		base.add(view, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		base.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());

		JButton button_n = new JButton("Next");
		buttonPanel.add(button_n);
		button_n.addActionListener(view);

		buttonPanel.add(button_u);
		button_u.setEnabled(false);
		button_u.addActionListener(view);

		JButton button_new = new JButton("New Game");
		buttonPanel.add(button_new);
		button_new.addActionListener(view);

		frame.pack();
		frame.setVisible(true);
	}
}
