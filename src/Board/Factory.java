package Board;
import App.Clock;
import App.Game;
import File.Picture;
import Logic.BitBoard;
import Logic.Piece;
import Logic.PieceType;
import Logic.Movement.*;
import LookUpTables.KnightMoves;

import java.awt.*;
import static Logic.Movement.mouse;

public class Factory {

    public static final int CELL_SIZE = 60;
    private final BitBoard bitBoard;
    private Piece  currentPiece;
    private final boolean ai;
    private boolean white;
    private Rectangle rectangle;

    public Factory(boolean ai) {
        this.ai = ai;
        this.bitBoard = new BitBoard();
        this.white = Game.isPLAYER_WHITE();

    }

    public static Factory createBoard(boolean ai) {
        return ai ? new AiBoard() : new FriendBoard();
    }

    public void update() {
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();
        int mouseCol = mouseX / CELL_SIZE;
        int mouseRow = mouseY / CELL_SIZE;

        moveTimer();

        // ---- שלב 1: בחירה בלחיצה ----
        if (mouse.isPressed() && currentPiece == null) {
            PieceType pieceType = this.bitBoard.getPieceType(mouseCol, mouseRow);

            if (pieceType != null) {
                Piece tempPiece = new Piece(mouseX, mouseY, pieceType);


                if (tempPiece.isWhite() == white) {
                    currentPiece = tempPiece;
                    rectangle = new Rectangle(currentPiece.getPrevX(), currentPiece.getPrevY(), -1 * CELL_SIZE, -1 * CELL_SIZE);

                }
            }
        }

        // ---- שלב 2: גרירה חיה ----
        if (mouse.isDragged() && currentPiece != null) {
            currentPiece.setCurrX(mouseX);
            currentPiece.setCurrY(mouseY);
            rectangle.setSize(mouseX, mouseY);

        }

        // ---- שלב 3: שחרור (סיום המהלך) ----
        if (!mouse.isPressed() && !mouse.isDragged() && currentPiece != null) {

            rectangle.setSize(mouseX, mouseY);
            PieceType pieceType = this.bitBoard.getPieceType(mouseCol, mouseRow);
            boolean moved = false;

            if (pieceType != null) { // יש כלי ביעד
                if (this.bitBoard.removePiece(mouseCol, mouseRow, !currentPiece.isWhite())) {
                    moved = true;
                } else {
                    // אם היעד הוא כלי שלי → מחליף בחירה
                    Piece tempPiece = new Piece(mouseX, mouseY, pieceType);
                    if (tempPiece.isWhite() == white) {
                        currentPiece = tempPiece;
                        rectangle = new Rectangle(currentPiece.getPrevX(), currentPiece.getPrevY(), -1 * CELL_SIZE, -1 * CELL_SIZE);

                    }

                }
            } else {
                moved = true; // תא ריק
            }

            if (moved) {
                bitBoard.movePiece(
                        currentPiece.getPrevX() / CELL_SIZE,
                        currentPiece.getPrevY() / CELL_SIZE,
                        mouseCol,
                        mouseRow
                );
                currentPiece = null;
                white = !white; // החלפת תור
            }

        }


    }

    private long lastUpdateTime = System.currentTimeMillis();
    private void moveTimer() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= 1000) { // עברה שנייה
            if (white) Clock.whiteTimer.move();
            else Clock.blackTimer.move();

            lastUpdateTime += 1000; // מעדכן לשנייה הבאה
        }
    }




    public void drawGame(Graphics g) {
        // ---- ציור לוח ----
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                g.setColor(new Color(205, 143, 33).darker());
                if ((row + col) % 2 != 0) {
                    g.fillRect(
                            col * Factory.CELL_SIZE,
                            row * Factory.CELL_SIZE,
                            Factory.CELL_SIZE,
                            Factory.CELL_SIZE
                    );
                }
                g.setColor(new Color(205, 143, 33).darker().darker());
                g.drawRect(
                        col * Factory.CELL_SIZE,
                        row * Factory.CELL_SIZE,
                        Factory.CELL_SIZE - 1,
                        Factory.CELL_SIZE - 1
                );
            }
        }

        // ---- ציור המלבן הצהוב לפני החתיכות ----
        if (rectangle != null) {
            Graphics2D g2d = (Graphics2D) g; // המרה ל-Graphics2D
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2d.setColor(Color.orange);

            g2d.fillRect(rectangle.x / CELL_SIZE * CELL_SIZE, rectangle.y / CELL_SIZE * CELL_SIZE, CELL_SIZE - 1, CELL_SIZE - 1);
            g2d.fillRect(rectangle.width / CELL_SIZE * CELL_SIZE, rectangle.height / CELL_SIZE * CELL_SIZE, CELL_SIZE - 1, CELL_SIZE - 1);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }


        // ---- ציור החתיכות ----
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean check = true;
                if (currentPiece != null) {
                    if (currentPiece.getPrevX() / CELL_SIZE == col && currentPiece.getPrevY() / CELL_SIZE == row) {
                        if (mouse.isDragged()) {
                            check = false;
                        }

                    }
                }


                PieceType type = this.bitBoard.getPieceType(col, row);
                if (type != null && check) {
                    g.drawImage(
                            Picture.getImage(type),
                            col * Factory.CELL_SIZE,
                            row * Factory.CELL_SIZE,
                            Factory.CELL_SIZE,
                            Factory.CELL_SIZE,
                            null
                    );
                }

            }
        }
        if (currentPiece != null) {
            g.drawImage(
                    Picture.getImage(currentPiece.getType()),
                    currentPiece.getCurrX() - CELL_SIZE / 2,
                    currentPiece.getCurrY() - CELL_SIZE / 2,
                    Factory.CELL_SIZE,
                    Factory.CELL_SIZE,
                    null
            );

        }


    }

    public boolean isAi() {
        return ai;
    }
}

