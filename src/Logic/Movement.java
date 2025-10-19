package Logic;

import Board.Factory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Movement extends MouseAdapter {
    public static final Movement mouse = new Movement();
    private boolean pressed, dragged;
    private int x, y;

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        this.x = e.getX();
        this.y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragged = true;
        this.x = e.getX();
        this.y = e.getY();


    }



    public void resetCoordinates() {
        this.x = this.y = -1 * Factory.CELL_SIZE;
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
    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        dragged = false;
        this.x = e.getX();
        this.y = e.getY();
    }



}
