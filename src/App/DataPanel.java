package App;

import Board.Factory;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {

    public DataPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(143, 100, 23));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, Factory.CELL_SIZE / 3));
        g.setColor(Color.black);
        g.drawString("Black player :", getWidth() / 2 + 5, 20);
        g.drawString("remaining time: " + Clock.blackTimer, getWidth() / 2 + 5, 40);
        g.setColor(Color.white);
        g.drawString("White player :", 10, 20);
        g.drawString("remaining time: " + Clock.whiteTimer, 10, 40);
        g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
        g.setColor(new Color(89, 38, 11));
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
        g.drawRect(3, 3, getWidth() - 7, getHeight() - 7);
        g.drawRect(4, 4, getWidth() - 9, getHeight() - 9);
    }
}
