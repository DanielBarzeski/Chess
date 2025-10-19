package App;

import Board.Factory;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private final MenuPanel menuPanel;
    private final GamePanel gamePanel;
    private final DataPanel dataPanel;
    private final NumPanel numPanel;
    private final ControlPanel controlPanel;

    public MainPanel() {
        int size = Factory.CELL_SIZE * 8 + Factory.CELL_SIZE;
        setBackground(new Color(205, 143, 33).brighter());
        setPreferredSize(new Dimension(size + 89, size + 120));
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(new Color(205, 143, 33).brighter());
        wrapperPanel.setPreferredSize(new Dimension(size + 10, size + 120));

        this.menuPanel = new MenuPanel(size, 50);
        wrapperPanel.add(this.menuPanel);

        numPanel = new NumPanel(size, size, Factory.CELL_SIZE / 2, Factory.CELL_SIZE / 2);
        this.gamePanel = new GamePanel(size - Factory.CELL_SIZE, size - Factory.CELL_SIZE);
        numPanel.add(this.gamePanel);
        wrapperPanel.add(numPanel);

        this.dataPanel = new DataPanel(size, 50);
        wrapperPanel.add(this.dataPanel);

        this.controlPanel = new ControlPanel(75, size + 110);
        add(wrapperPanel);
        add(this.controlPanel);
    }

    public void run() {
        new Timer(20, e -> {
            this.gamePanel.update();
            this.menuPanel.update();
            this.dataPanel.repaint();
            this.controlPanel.update();
            this.numPanel.repaint();
        }).start();
    }
}
