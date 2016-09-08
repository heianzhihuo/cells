package field;

import cell.Cell;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int GRID_SIZE = 5;
	private Field theField;

	public View(Field field) {
		theField = field;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int row = 0; row < theField.getHeight(); row++) {
			for (int col = 0; col < theField.getWidth(); col++) {
				Cell cell = theField.get(row, col);
				cell.draw(g, col * GRID_SIZE, row * GRID_SIZE, GRID_SIZE);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(theField.getWidth() * GRID_SIZE + 1, theField.getHeight() * GRID_SIZE + 1);
	}

	public static void main(String[] args) {
		Field field = new Field(10, 10);
		for (int row = 0; row < field.getHeight(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				field.place(row, col, new Cell());
			}
		}
		View view = new View(field);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Cells");
		frame.pack();
		frame.setVisible(true);
	}

}
