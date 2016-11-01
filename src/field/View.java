package field;

import cell.Cell;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class View extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int GRID_SIZE = 10;
	private Field theField;
	MouseHandler mouseHandler = new MouseHandler();
	MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();

	public View(Field field) {
		theField = field;
		
	}
	
	public void addMouse(){
	    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    addMouseListener(mouseHandler);
	    addMouseMotionListener(mouseMotionHandler);
	}
	
	public void removeMouse(){
	    setCursor(Cursor.getDefaultCursor());
	    removeMouseListener(mouseHandler);
	    removeMouseMotionListener(mouseMotionHandler);
	}
	
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
	
	private Cell getCell(Point2D p) {
        int col = (int) p.getX()/GRID_SIZE;
        int row = (int) p.getY()/GRID_SIZE;
        Cell cell = theField.get(row,col);
        return cell;
	}
	
	private class MouseHandler extends MouseAdapter {
	    public void mousePressed(MouseEvent e) {
	        Cell cell = getCell(e.getPoint());
	        if(cell.isAlive()){
	            cell.die();
	        } else {
	            cell.reborn();
	        }
	        repaint();
	    }
	}
	
	private class MouseMotionHandler implements MouseMotionListener {

        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub
            Cell cell = getCell(e.getPoint());
            if(cell.isAlive()){
                cell.die();
            } else {
                cell.reborn();
            }
            repaint();
        }

        public void mouseMoved(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
	}
}
