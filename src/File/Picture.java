package File;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Picture {
    public enum ImageType {
        WHITE_PAWN("whitePawn"),
        WHITE_KNIGHT("whiteKnight"),
        WHITE_BISHOP("whiteBishop"),
        WHITE_ROOK("whiteRook"),
        WHITE_QUEEN("whiteQueen"),
        WHITE_KING("whiteKing"),
        BLACK_PAWN("blackPawn"),
        BLACK_KNIGHT("blackKnight"),
        BLACK_BISHOP("blackBishop"),
        BLACK_ROOK("blackRook"),
        BLACK_QUEEN("blackQueen"),
        BLACK_KING("blackKing"),
        CHESS("chess"),
        NUMBERS("nums"),
        NUMBERS_REVERSED("numsReversed"),;

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

    public static BufferedImage getImage(byte pieceValue) {
        return ImageType.values()[pieceValue].getImage();
    }

}
