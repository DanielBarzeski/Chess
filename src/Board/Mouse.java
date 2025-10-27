package Board;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Data.Constants.CELL_SIZE;

public class Mouse extends MouseAdapter {
    public static final Mouse mouse = new Mouse();
    private boolean pressed, dragged;
    private int x, y;

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        setCoordinates(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragged = true;
        setCoordinates(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        dragged = false;
        setCoordinates(e.getX(), e.getY());
    }

    private void setCoordinates(int x, int y) {
        if (x >= 0 && x < CELL_SIZE * 8) this.x = x;
        if (y >= 0 && y < CELL_SIZE * 8) this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isDragged() {
        return dragged;
    }

    public boolean isPressed() {
        return pressed;
    }

}
