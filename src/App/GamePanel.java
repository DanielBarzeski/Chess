package App;

import Board.Factory;
import Logic.Movement;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final OptionPanel optionPanel;
    private Factory board;

    public GamePanel(int width, int height) {
        setBackground(new Color(205, 143, 33));
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        this.optionPanel = new OptionPanel();
        this.optionPanel.setAiButton(e_ -> actionPerformed(true));
        this.optionPanel.setFriendButton(e_ -> actionPerformed(false));
        addMouseMotionListener(Movement.mouse);
        addMouseListener(Movement.mouse);
        Movement.mouse.resetCoordinates();
        add(this.optionPanel, new GridBagConstraints());
    }

    private void actionPerformed(boolean ai) {
        Game.setVISIBLE(true);
        Game.START();
        this.optionPanel.setVisible(false);
        this.board = Factory.createBoard(ai);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (this.board != null) {
            this.board.drawGame(g);
        }
    }

    public void update() {
        if (Game.isVISIBLE()) {
            if (this.board != null) {
                setOpaque(true);
                if (Game.isRESTARTING()) {
                    Clock.whiteTimer.reset();
                    Clock.blackTimer.reset();
                    this.board = Factory.createBoard(this.board.isAi());
                    Game.setRESTARTING(false);
                }
                if (!Game.isFINISHED() && !Game.isPAUSED()) {
                    this.board.update();
                }
            }
        } else {
            this.board = null;
            setOpaque(false);
            Clock.whiteTimer.reset();
            Clock.blackTimer.reset();
            this.optionPanel.setVisible(true);
        }
        repaint();
    }

}
