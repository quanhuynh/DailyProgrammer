import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.*;

public class Conway extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel[] myLabels;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 30;
	Timer timer;
	private static final int interval = 750;
	
	public Conway() {
		super("Conway's Game of Life");
		setSize(WIDTH*10, HEIGHT*10);
		setResizable(false);
		setLayout(new GridLayout(HEIGHT, WIDTH));
		myLabels = new JLabel[WIDTH*HEIGHT];
		
		for (int i=0; i<myLabels.length; i++) {
			JLabel cell = new JLabel("");
			cell.setOpaque(true);
			myLabels[i] = cell;
			//Remember to implement starting condition, right now everything's dead/alive
			cell.setBackground(Color.white);
			int x = i % WIDTH;
			int y = i / WIDTH;
			cell.setName(x + " " + y + " " + "dead");
			cell.setBorder(BorderFactory.createLineBorder(Color.lightGray));
			cell.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					JLabel clickedCell = (JLabel) e.getSource();
					if (isAlive(clickedCell)) {
						changeStatus(clickedCell, "dead");
						killCell(clickedCell);
					} else {
						changeStatus(clickedCell, "alive");
						reviveCell(clickedCell);
					}
			    }
			});
			add(cell);
		}
		
		ActionListener updater = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				for (JLabel cell : myLabels) {
					updateCell(cell);
				}
				updateBoard();
			}
		};
		initConditions();
		new Timer(interval, updater).start();
		setVisible(true);
	}

	public void initConditions() {
		int x1 = WIDTH/2;
		int y1 = HEIGHT/2;
		changeStatus(myLabels[getIndex(x1, y1)], "alive");
		changeStatus(myLabels[getIndex(x1-1, y1)], "alive");
		changeStatus(myLabels[getIndex(x1+1, y1)], "alive");
	}
	
	public void updateCell(JLabel cell) {
		int neighbors = countNeighbors(cell);
		if (isAlive(cell)) {
			//	neighbors < 2 || neighbors > 3: die
			// neighbors == 2 || neighbors == 3: do nothing
			if (neighbors < 2 || neighbors > 3) {
				changeStatus(cell, "dead");
			} else {}
		} else {
			// neighbors == 3: live
			// else: do nothing
			if (neighbors == 3) {
				changeStatus(cell, "alive");
			} else {}
		}
	}
	
	public void updateBoard() {
		for (int i=0; i<myLabels.length; i++) {
			JLabel curCell = myLabels[i];
			String status = getStatus(curCell);
			if (status.equals("alive")) {
				reviveCell(curCell);
			} else if (status.equals("dead")) {
				killCell(curCell);
			}
		}
	}
	
	public void killCell(JLabel cell) {
		cell.setBackground(Color.white);
	}
	
	public void reviveCell(JLabel cell) {
		cell.setBackground(Color.black);
	}
	
	public boolean isAlive(JLabel cell) {
		//Check for color, not status
		return cell.getBackground() == Color.black;
	}
	
	public int countNeighbors(JLabel cell) {
		int count = 0;
		int[] coords = getCoordinate(cell);
		int x = coords[0];
		int y = coords[1];
		int[] xRange = {-1, 1};
		int[] yRange = {-1, 1};
		if (x == 0) {
			xRange[0] = 0;
		} else if (x == WIDTH-1) {
			xRange[1] = 0;
		}
		if (y == 0) {
			yRange[0] = 0;
		} else if (y == HEIGHT-1) {
			yRange[1] = 0;
		}
		for (int i=xRange[0]; i<xRange[1]+1; i++) {
			for (int j=yRange[0]; j<yRange[1]+1; j++) {
				if (i == 0 && j == 0) {
					continue;
				} else {
					JLabel neighborCell = myLabels[getIndex(x+i, y+j)];
					if (isAlive(neighborCell)) {
						count++;
					}
				}
			}
		}
		return count;
	}
	
	public String getStatus(JLabel cell) {
		return cell.getName().split(" ")[2];
	}
	
	public void changeStatus(JLabel cell, String status) {
		int[]  coords = getCoordinate(cell);
		int x = coords[0];
		int y = coords[1];
		cell.setName(x + " " + y + " " + status);
	}
	
	public int[] getCoordinate(JLabel cell) {
		int[] coordinate = new int[2];
		String[] name = cell.getName().split(" ");
		coordinate[0] = Integer.parseInt(name[0]);
		coordinate[1] = Integer.parseInt(name[1]);
		return coordinate;
	}
	
	public int getIndex(int x, int y) {
		return x + y*WIDTH;
	}
	
	public static void main(String[] args) {
		new Conway();
	}

}