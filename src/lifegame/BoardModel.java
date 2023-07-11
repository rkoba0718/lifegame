package lifegame;

import java.util.ArrayList;

public class BoardModel {

	private int cols;
	private int rows;

	private boolean[][] cells;

	private ArrayList<BoardListener> listeners;

	private ArrayList<boolean[][]> undolist;

	public BoardModel(int c, int r) {
		cols = c;
		rows = r;
		cells = new boolean[rows][cols];
		listeners = new ArrayList<BoardListener>();
		undolist = new ArrayList<boolean[][]>();
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public boolean[][] getCells(){
		return cells;
	}

	public void changeCellState(int x, int y) {

		boolean[][] prevcells = new boolean[rows][cols];

		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				prevcells[i][j] = cells[i][j];
			}
		}

		if(undolist.size() >= 32) {
			undolist.remove(0);
			undolist.add(prevcells);
		}else {
			undolist.add(prevcells);
		}

		if(cells[y][x] == true) {
			cells[y][x] = false;
		}else {
			cells[y][x] = true;
		}
		fireUpdate();
	}

	public void addListener(BoardListener listener) {
		listeners.add(listener);
	}

	private void fireUpdate() {
		for(BoardListener listener: listeners) {
			listener.updated(this);
		}
	}


	public void next() {

		boolean[][] prevcells = new boolean[rows][cols];

		for(int c=0;c<rows;c++) {
			for(int d=0;d<cols;d++) {
				prevcells[c][d] = cells[c][d];
			}
		}

		if(undolist.size() >= 32) {
			undolist.remove(0);
			undolist.add(prevcells);
		}else {
			undolist.add(prevcells);
		}


		boolean[][] nextcells = new boolean[rows+2][cols+2];
		for(int a=0;a<rows+2;a++) {
			for(int b=0;b<cols+2;b++) {
				if(a == 0 || a == rows+1) {
					nextcells[a][b] = false;
				}else if(b == 0 || b == cols+1) {
					nextcells[a][b] = false;
				}else {
					nextcells[a][b] = cells[a-1][b-1];
				}
			}
		}


		int counter = 0;
		for(int i=1;i<rows+1;i++) {
			for(int j=1;j<cols+1;j++) {
				for(int k=i-1;k<=i+1;k++) {
					for(int l=j-1;l<=j+1;l++) {
						if(nextcells[k][l] == true){
							counter++;
						}
					}
				}

				if(nextcells[i][j] == true) {
					counter = counter - 1;
				}

				if(nextcells[i][j] == true && counter != 2 && counter != 3){
					cells[i-1][j-1] = false;
				}else if(nextcells[i][j] == false && counter == 3) {
					cells[i-1][j-1] = true;
				}

				counter = 0;
			}
		}

		fireUpdate();
	}


	public void undo() {

		cells = undolist.get(undolist.size()-1);
		undolist.remove(undolist.size()-1);

		fireUpdate();
	}


	public boolean isUndoable() {
		if(undolist.size() < 1) {
			return false;
		}else {
			return true;
		}
	}

}
