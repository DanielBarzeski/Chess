package Board;

import App.Clock;
import File.Picture;
import Logic.Position;

import java.awt.*;
import java.util.ArrayList;

import static Data.Constants.CELL_SIZE;
import static Data.Variables.theWhitePlayerIsDown;
import static Board.Mouse.mouse;

public class Factory {
    private final Position position;
    private Piece currentPiece;
    private final boolean ai;
    private boolean holdingPiece;
    private final ArrayList<Point> availableMoves;
    private final Point fromSquare, toSquare;

    public Factory(boolean ai) {
        this.ai = ai;
        this.position = new Position(theWhitePlayerIsDown());
        this.availableMoves = new ArrayList<>();
        this.fromSquare = new Point(-1, -1);
        this.toSquare = new Point(-1, -1);
    }

    public static Factory createBoard(boolean ai) {
        return ai ? new AiBoard() : new FriendBoard();
    }

    public void update() {
    }

    public void updateClock() {
        if (this.position.isWhiteTurn()) Clock.whiteTimer.move();
        else Clock.blackTimer.move();
    }

    public void drawGame(Graphics g) {
        drawBoard(g);
        drawSquares(g);
        drawPieces(g);
    }

    private void drawBoard(Graphics g) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) g.setColor(new Color(205, 143, 33).darker());
                else g.setColor(new Color(205, 143, 33));
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void drawSquares(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.orange);
        g2d.fillRect(toSquare.x * CELL_SIZE, toSquare.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g2d.fillRect(fromSquare.x * CELL_SIZE, fromSquare.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        if (currentPiece != null) {
            g2d.setColor(new Color(70, 130, 180));
            for (Point availableMove : availableMoves) {
                g2d.fillRect(availableMove.x * CELL_SIZE, availableMove.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    private void drawPieces(Graphics g) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean paint = true;
                if (currentPiece != null) {
                    if (currentPiece.getPrevCol() == col && currentPiece.getPrevRow() == row) {
                        if (mouse.isDragged()) {
                            if (holdingPiece) {
                                paint = false;
                            }
                        }
                    }
                }

                byte pieceValue = this.position.getPieceValue(row * 8 + col);
                if (pieceValue != -1 && paint) {
                    g.drawImage(Picture.getImage(pieceValue),
                            col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, null
                    );
                }

            }
        }
        if (currentPiece != null) {
            if (mouse.isDragged()) {
                if (holdingPiece) {
                    g.drawImage(Picture.getImage(currentPiece.getValue()),
                            currentPiece.getCurrX() - CELL_SIZE / 2, currentPiece.getCurrY() - CELL_SIZE / 2,
                            CELL_SIZE, CELL_SIZE, null
                    );
                }
            }

        }
    }

    public boolean isAi() {
        return ai;
    }

    public Point getFromSquare() {
        return fromSquare;
    }

    public Point getToSquare() {
        return toSquare;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public ArrayList<Point> getAvailableMoves() {
        return availableMoves;
    }

    public void setHoldingPiece(boolean holdingPiece) {
        this.holdingPiece = holdingPiece;
    }

    public boolean isHoldingPiece() {
        return holdingPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public Position getPosition() {
        return position;
    }
}

