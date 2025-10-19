package File;

import App.Game;
import Logic.PieceType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Picture {

    // ---- ENUM פנימי עבור כל התמונות ----
    public enum ImageType {
        CHESS("chess"),
        BLACK_PAWN("blackPawn"),
        WHITE_PAWN("whitePawn"),
        BLACK_KNIGHT("blackKnight"),
        WHITE_KNIGHT("whiteKnight"),
        BLACK_BISHOP("blackBishop"),
        WHITE_BISHOP("whiteBishop"),
        BLACK_ROOK("blackRook"),
        WHITE_ROOK("whiteRook"),
        BLACK_QUEEN("blackQueen"),
        WHITE_QUEEN("whiteQueen"),
        BLACK_KING("blackKing"),
        WHITE_KING("whiteKing");

        private final BufferedImage image;

        ImageType(String filename) {
            this.image = loadImage(filename);
        }

        private static BufferedImage loadImage(String name) {
            try {
                return ImageIO.read(new File("images/" + name + ".png"));
            } catch (IOException e) {
                System.out.println("Image " + name + ".png does not exist.");
                BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = img.createGraphics();
                g2d.setColor(Color.white);
                g2d.fillRect(0, 0, 64, 64);
                g2d.dispose();
                return img;
            }
        }

        public BufferedImage getImage() {
            return image;
        }
    }

    public static BufferedImage getImage(PieceType type) {
        boolean playerWhite = Game.isPLAYER_WHITE(); // השחקן הנוכחי

        // אם השחקן שחור, הפוך את הצבע של החתיכה
        if (!playerWhite) {
            type = switch (type) {
                case WHITE_KING -> PieceType.BLACK_KING;
                case WHITE_QUEEN -> PieceType.BLACK_QUEEN;
                case WHITE_ROOK -> PieceType.BLACK_ROOK;
                case WHITE_BISHOP -> PieceType.BLACK_BISHOP;
                case WHITE_KNIGHT -> PieceType.BLACK_KNIGHT;
                case WHITE_PAWN -> PieceType.BLACK_PAWN;
                case BLACK_KING -> PieceType.WHITE_KING;
                case BLACK_QUEEN -> PieceType.WHITE_QUEEN;
                case BLACK_ROOK -> PieceType.WHITE_ROOK;
                case BLACK_BISHOP -> PieceType.WHITE_BISHOP;
                case BLACK_KNIGHT -> PieceType.WHITE_KNIGHT;
                case BLACK_PAWN -> PieceType.WHITE_PAWN;
            };
        }

        // מחזיר את התמונה הרגילה מה-enum
        return switch (type) {
            case WHITE_KING -> ImageType.WHITE_KING.getImage();
            case WHITE_QUEEN -> ImageType.WHITE_QUEEN.getImage();
            case WHITE_ROOK -> ImageType.WHITE_ROOK.getImage();
            case WHITE_BISHOP -> ImageType.WHITE_BISHOP.getImage();
            case WHITE_KNIGHT -> ImageType.WHITE_KNIGHT.getImage();
            case WHITE_PAWN -> ImageType.WHITE_PAWN.getImage();
            case BLACK_KING -> ImageType.BLACK_KING.getImage();
            case BLACK_QUEEN -> ImageType.BLACK_QUEEN.getImage();
            case BLACK_ROOK -> ImageType.BLACK_ROOK.getImage();
            case BLACK_BISHOP -> ImageType.BLACK_BISHOP.getImage();
            case BLACK_KNIGHT -> ImageType.BLACK_KNIGHT.getImage();
            case BLACK_PAWN -> ImageType.BLACK_PAWN.getImage();
        };
    }

}
