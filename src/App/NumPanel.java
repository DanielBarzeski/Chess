package App;

import Board.Factory;
import File.Picture;

import javax.swing.*;
import java.awt.*;

public class NumPanel extends JPanel {
    public NumPanel(int width, int height, int hGap, int vGap) {
        setLayout(new FlowLayout(FlowLayout.CENTER, hGap, vGap));
        setBackground(new Color(123, 63, 0));
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Game.isVISIBLE()) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, (int) (Factory.CELL_SIZE / 2.5)));
            for (int i = 0; i < 8; i++) {
                char letter = Game.isPLAYER_WHITE() ? (char) ('A' + i) : (char) ('H' - i);
                g.drawString(String.valueOf(letter),
                        (int) (Factory.CELL_SIZE / 1.15) + i * Factory.CELL_SIZE,
                        (int) (Factory.CELL_SIZE / 2.5)
                );
                g.drawString(String.valueOf(letter),
                        (int) (Factory.CELL_SIZE / 1.15) + i * Factory.CELL_SIZE,
                        (int) (8.9 * Factory.CELL_SIZE)
                );
                int number = Game.isPLAYER_WHITE() ? 8 - i : i + 1;
                g.drawString(String.valueOf(number),
                        (int) (Factory.CELL_SIZE / 5.65),
                        (int) (1.15 * Factory.CELL_SIZE) + i * Factory.CELL_SIZE
                );
                g.drawString(String.valueOf(number),
                        (int) (Factory.CELL_SIZE * 8.65),
                        (int) (1.15 * Factory.CELL_SIZE) + i * Factory.CELL_SIZE
                );
            }
            g.setColor(new Color(205, 143, 33).darker().darker());
            g.drawRect(
                    Factory.CELL_SIZE / 2 - 1,
                    Factory.CELL_SIZE / 2 - 1,
                    getWidth() - Factory.CELL_SIZE + 1,
                    getHeight() - Factory.CELL_SIZE + 1
            );
        } else g.drawImage(Picture.ImageType.CHESS.getImage(), 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.white);
        g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
    }

}

