package Logic;

import Board.Factory;

import static Board.Factory.CELL_SIZE;

public class Piece {
    private int currX, currY,prevX,prevY;
    private PieceType type;
    private boolean white;

    public Piece(int mouseX, int mouseY, PieceType pieceType) {
        this.currX = -1 * CELL_SIZE;
        this.currY = -1 * CELL_SIZE;
        this.prevX = mouseX;
        this.prevY = mouseY;
        this.white = pieceType.ordinal() < 6;
        this.type = pieceType;
    }


    public int getCurrX() {
        return currX;
    }

    public int getCurrY() {
        return currY;
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setCurrX(int currX) {
        this.currX = currX;
    }

    public void setCurrY(int currY) {
        this.currY = currY;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public void setType(PieceType type) {
        this.type = type;
    }



    public PieceType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "currX:" + currX / CELL_SIZE + ", currY:" + currY / CELL_SIZE+ ", prevX:" + prevX/ CELL_SIZE + ", prevY:" + prevY/ CELL_SIZE + ", type:" + type;
    }
}
