package Board;

import java.awt.*;

import static Board.Mouse.mouse;
import static Data.Constants.CELL_SIZE;

public class FriendBoard extends Factory {

    public FriendBoard() {
        super(false);
    }

    @Override
    public void drawGame(Graphics g) {
        super.drawGame(g);
    }

    @Override
    public void update() {
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();
        int mouseCol = mouseX / CELL_SIZE;
        int mouseRow = mouseY / CELL_SIZE;

        if (mouse.isPressed() && !mouse.isDragged()) {
            byte pieceValue = getPosition().getPieceValue(mouseRow * 8 + mouseCol);
            setHoldingPiece(false);
            if (pieceValue != -1) {
                Piece tempPiece = new Piece(mouseX, mouseY, pieceValue);
                boolean isSameColorAsTurn = (tempPiece.isWhite() == getPosition().isWhiteTurn());
                if (isSameColorAsTurn) {
                    setHoldingPiece(true);
                    setCurrentPiece(tempPiece);
                    getAvailableMoves().clear();
                    long legalMoves = getPosition().getLegalMoves(mouseRow * 8 + mouseCol);
                    while (legalMoves != 0) {
                        int square = Long.numberOfTrailingZeros(legalMoves);
                        int row = square / 8;
                        int col = square % 8;
                        getAvailableMoves().add(new Point(col, row));
                        legalMoves &= legalMoves - 1;
                    }
                    getFromSquare().move(mouseCol, mouseRow);
                }
            }
        }

        if (getCurrentPiece() == null) return;

        if (mouse.isPressed()) {
            if (!mouse.isDragged() || (mouse.isDragged() && isHoldingPiece())) {
                getCurrentPiece().setCurrX(mouseX);
                getCurrentPiece().setCurrY(mouseY);
                getToSquare().move(mouseCol, mouseRow);
            } else if (mouseCol != getCurrentPiece().getCurrCol() || mouseRow != getCurrentPiece().getCurrRow()) {
                getCurrentPiece().resetCurrLocation();
                getToSquare().move(-1,-1);
            }
        } else if (!mouse.isPressed() && !mouse.isDragged()) {
            for (Point availableMove : getAvailableMoves()) {
                boolean inCol = (availableMove.x == getCurrentPiece().getCurrCol());
                boolean inRow = (availableMove.y == getCurrentPiece().getCurrRow());
                if (inCol && inRow) {
                    getPosition().movePiece(
                            getCurrentPiece().getPrevRow() * 8 + getCurrentPiece().getPrevCol(),
                            getCurrentPiece().getCurrRow() * 8 + getCurrentPiece().getCurrCol()
                    );
                    getPosition().switchTurn();
                    setCurrentPiece(null);
                    return;
                }
            }
            getToSquare().move(-1, -1);
        }
    }
}
