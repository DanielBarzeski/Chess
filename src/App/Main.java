package App;

import static MagicBitboard.AttacksLoader.loadAttacksFromFile;
import static MagicBitboard.AttacksLoader.testLoadedAttacks;

public class Main {


//    private static void printBitboard(long bitboard) {
//        System.out.println("\n  a b c d e f g h");
//        for (int rank = 7; rank >= 0; rank--) {
//            print((rank + 1) + " ");
//            for (int file = 0; file < 8; file++) {
//                int square = rank * 8 + file;
//                if ((bitboard & (1L << square)) != 0) {
//                    print("X ");
//                } else {
//                    print(". ");
//                }
//            }
//            println("");
//        }
//        println("");
//    }


    // ===== דוגמה לשימוש =====
    public static void main(String[] args) {
        // טוען את המערך מהקובץ
        long[][] ATTACKS = loadAttacksFromFile("attacks_output.txt");

        // בודק שהטעינה עבדה
         testLoadedAttacks(ATTACKS);


        // עכשיו אפשר להשתמש ב-ATTACKS בדיוק כמו במערך הרגיל!
        // לדוגמה:
        // long moves = getRookMoves(ATTACKS, MAGIC_NUMBERS, SHIFTS, BLOCKERS, 28, 0L);
    }
}