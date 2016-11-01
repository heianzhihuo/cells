package cellmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import cell.Cell;
import field.Field;
import field.View;

public class CellMachine implements ActionListener {

    private Field field;
    View view;
    private JFrame frame;
    private JPanel panel;
    private JButton stop_b, speedUp, speedDown, resetButton, clearButton,
            changeButton;
    private boolean suspended = true;
    private int speed = 400;

    public CellMachine(int height, int width) {
        field = new Field(height, width);

        initData();

        view = new View(field);
        frame = new JFrame();
        panel = new JPanel();

        packButton();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Cells");
        frame.add(panel, "North");
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
        run();
    }

    private void initData() {
        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                field.place(row, col, new Cell());
            }
        }

        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.get(row, col);
                if (Math.random() < 0.2) {
                    cell.reborn();
                } else {
                    cell.die();
                }
            }
        }
    }

    private void packButton() {
        stop_b = new JButton();
        stop_b.setText("开始/暂停");
        panel.add(stop_b);
        stop_b.addActionListener(this);

        speedUp = new JButton();
        speedUp.setText("加速");
        panel.add(speedUp);
        speedUp.addActionListener(this);

        speedDown = new JButton();
        speedDown.setText("减速");
        panel.add(speedDown);
        speedDown.addActionListener(this);

        resetButton = new JButton();
        resetButton.setText("重置");
        panel.add(resetButton);
        resetButton.addActionListener(this);

        clearButton = new JButton();
        clearButton.setText("清空");
        panel.add(clearButton);
        clearButton.addActionListener(this);

        changeButton = new JButton();
        changeButton.setText("修改");
        panel.add(changeButton);
        changeButton.addActionListener(this);
    }

    private void clear() {
        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.get(row, col);
                cell.die();
            }
        }
        suspended = true;
        speed = 400;
        frame.repaint();
    }

    private void reset() {
        initData();
        suspended = true;
        speed = 400;
        frame.repaint();
    }
    
    private void change() {
        suspended = true;
        view.addMouse();
    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == stop_b) {
            suspended = !suspended;
            if(!suspended){
                view.removeMouse();
            }
        } else if (e.getSource() == speedUp) {
            speed -= 50;
            speed = speed > 0 ? speed : 1;
        } else if (e.getSource() == speedDown) {
            speed += 50;
        } else if (e.getSource() == resetButton) {
            reset();
        } else if (e.getSource() == clearButton) {
            clear();
        } else if (e.getSource() == changeButton) {
            change();
        }
    }

    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            try {
                Thread.sleep(speed);
                while (suspended) {
                    Thread.sleep(speed);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int row = 0; row < field.getHeight(); row++) {
                for (int col = 0; col < field.getWidth(); col++) {
                    Cell cell = field.get(row, col);
                    Cell[] neighbour = field.getNeighbour(row, col);
                    int numOfLive = 0;
                    for (Cell c : neighbour) {
                        if (c.isAlive()) {
                            numOfLive++;
                        }
                    }
                    cell.numOfNeighbourLive = numOfLive;
                }
            }

            for (int row = 0; row < field.getHeight(); row++) {
                for (int col = 0; col < field.getWidth(); col++) {
                    Cell cell = field.get(row, col);
                    int numOfLive = cell.numOfNeighbourLive;
                    if (cell.isAlive()) {
                        if (numOfLive < 2 || numOfLive > 3) {
                            cell.die();
                        }
                    } else if (numOfLive == 3) {
                        cell.reborn();
                    }
                }
            }
            frame.repaint();
        }
    }

    public static void main(String[] args) {
        new CellMachine(64, 64);
    }
}
