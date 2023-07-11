package lifegame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardView extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

	private int cols;
	private int rows;
	private boolean[][] cells;
	private int w,h,cellwidth,x_pressed,y_pressed;
	private BoardModel m;
	private JButton undobutton;
	private Main main;

	public BoardView(BoardModel model, JButton button_u, Main a) {
		m = model;
		cols = m.getCols();
		rows = m.getRows();
		cells = m.getCells();
		undobutton = button_u;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		main = a;
	}


	private boolean isAlive(int x, int y) {
		if(cells[x][y] == true) {
			return true;
		}else {
			return false;
		}
	}


	public void actionPerformed(ActionEvent e) {
		String button;
		button = e.getActionCommand();

		if(button.equals("Next")) {
			m.next();
			cells = m.getCells();
			repaint();
			undobutton.setEnabled(m.isUndoable());
		}else if(button.equals("Undo")){
			m.undo();
			cells = m.getCells();
			repaint();
			undobutton.setEnabled(m.isUndoable());
		}else if(button.equals("New Game")) {
			main.run();
		}
	}


	@Override
	public void paint(Graphics g) {

		super.paint(g);

		w = this.getWidth();
		h = this.getHeight();


		if(rows <= cols) {
			cellwidth = (w - 20)/cols;
			if(rows*cellwidth > h) {
				cellwidth = (h - 20)/rows;
			}
		}else {
			cellwidth = (h - 20)/rows;
			if(cols*cellwidth > w) {
				cellwidth = (w - 20)/cols;
			}
		}


		for(int a=0;a<=rows;a++) {
			g.drawLine((w-(cols*cellwidth))/2, (h-(rows*cellwidth))/2+a*cellwidth, (w+(cols*cellwidth))/2, (h-(rows*cellwidth))/2+a*cellwidth);
		}

		for(int b=0;b<=cols;b++) {
			g.drawLine((w-(cols*cellwidth))/2+b*cellwidth, (h-(rows*cellwidth))/2, (w-(cols*cellwidth))/2+b*cellwidth, (h+(rows*cellwidth))/2);
		}


		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				if(isAlive(i,j)) {
					g.fillRect((w-(cols*cellwidth))/2+j*cellwidth, (h-(rows*cellwidth))/2+i*cellwidth, cellwidth, cellwidth);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

    public void mouseEntered(MouseEvent e) {

	}

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
    	int x,y;

    	x = e.getX();
    	y = e.getY();


    	int j;
    	if(((w-(cols*cellwidth))/2 <= x) && (x < (w+(cols*cellwidth))/2)) {
    		j = (x-(w-(cols*cellwidth))/2)/cellwidth;
    		x_pressed = j;
    	}else {
    		return;
    	}

    	int i;
    	if(((h-(rows*cellwidth))/2 <= y) && (y < (h+(rows*cellwidth))/2)) {
    		i = (y-(h-(rows*cellwidth))/2)/cellwidth;
    		y_pressed = i;
    	}else {
    		return;
    	}



    	m.changeCellState(j, i);
		cells = m.getCells();
    	repaint();
    	undobutton.setEnabled(m.isUndoable());

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    	int x,y;
    	x = e.getX();
    	y = e.getY();

    	int x_dragged,y_dragged;
    	x_dragged = x_pressed;
    	y_dragged = y_pressed;


    	int j;
    	if(((w-(cols*cellwidth))/2 <= x) && (x < (w+(cols*cellwidth))/2)) {
    		j = (x-(w-(cols*cellwidth))/2)/cellwidth;
    	}else {
    		return;
    	}

    	int i;
    	if(((h-(rows*cellwidth))/2 <= y) && (y < (h+(rows*cellwidth))/2)) {
    		i = (y-(h-(rows*cellwidth))/2)/cellwidth;
    	}else {
    		return;
    	}


    	while(j == x_dragged && i == y_dragged) {
    		return;
    	}


    	m.changeCellState(j, i);
		cells = m.getCells();
    	repaint();
    	undobutton.setEnabled(m.isUndoable());
    	x_pressed = j;
    	y_pressed = i;


    }

    public void mouseMoved(MouseEvent e) {

    }

}
