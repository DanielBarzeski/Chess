package LookUpTables;

import java.util.Random;

public class MagicFinder {

    private static final Random random = new Random();

    /**
     * מחשבת magic numbers לצריח
     * @param masks מערך של 64 מסכות (occupancy masks)
     * @return מערך של 64 magic numbers
     */
    public static long[] findRookMagics(long[] masks) {
        System.out.println("=== מחפש Magic Numbers לצריח ===\n");
        System.out.println("זה עלול לקחת כמה דקות...\n");

        long[] magics = new long[64];

        for (int square = 0; square < 64; square++) {
            long mask = masks[square];
            int bits = countBits(mask);

            System.out.print("משבצת " + square + " (" + squareName(square) + "): ");
            System.out.print(bits + " ביטים, מחפש...");

            long startTime = System.currentTimeMillis();
            magics[square] = findMagicNumber(square, mask, bits);
            long endTime = System.currentTimeMillis();

            if (magics[square] != 0L) {
                System.out.println(" נמצא! (" + (endTime - startTime) + " ms)");
            } else {
                System.out.println(" כשל!");
            }
        }

        System.out.println("\n✅ סיימתי למצוא את כל ה-Magic Numbers!\n");
        return magics;
    }

    /**
     * מדפיסה את המספרים הקסומים בפורמט הקסדצימלי
     */
    public static void printMagics(long[] magics) {
        System.out.println("=== Magic Numbers (פורמט קומפקטי) ===\n");
        System.out.println("public static final long[] ROOK_MAGICS = {");

        for (int rank = 0; rank < 8; rank++) {
            System.out.print("    ");
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                System.out.print("0x" + String.format("%016X", magics[square]) + "L");
                if (square < 63) {
                    System.out.print(", ");
                }
                if (file == 7 && rank < 7) {
                    System.out.println();
                }
            }
        }
        System.out.println("\n};");

        System.out.println("\n=== Magic Numbers (עם הערות) ===\n");
        System.out.println("public static final long[] ROOK_MAGICS = {");

        for (int rank = 0; rank < 8; rank++) {
            System.out.println("        // Rank " + (rank + 1));
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                System.out.print("        0x" + String.format("%016X", magics[square]) + "L");
                if (square < 63) {
                    System.out.print(",");
                }
                System.out.println(" // " + squareName(square) + " (" + square + ")");
            }
            if (rank < 7) System.out.println();
        }
        System.out.println("};");
    }

    private static long findMagicNumber(int square, long mask, int bits) {
        int occupancyCount = 1 << bits;
        long[] occupancies = new long[occupancyCount];
        long[] attacks = new long[occupancyCount];
        long[] used = new long[occupancyCount];

        // יצירת כל הווריאציות האפשריות של תפוסה
        for (int i = 0; i < occupancyCount; i++) {
            occupancies[i] = getOccupancyVariation(i, mask);
            attacks[i] = generateRookAttacks(square, occupancies[i]);
        }

        // חיפוש magic number מתאים
        for (int attempt = 0; attempt < 100_000_000; attempt++) {
            long magic = randomLong();

            // בדיקה שיש מספיק ביטים גבוהים
            if (countBits((mask * magic) & 0xFF00_0000_0000_0000L) < 6) {
                continue;
            }

            // איפוס מערך ה-used
            for (int i = 0; i < occupancyCount; i++) {
                used[i] = 0L;
            }

            // בדיקה שאין קולייזיות
            boolean fail = false;
            for (int i = 0; i < occupancyCount; i++) {
                int index = (int)((occupancies[i] * magic) >>> (64 - bits));

                if (used[index] == 0L) {
                    used[index] = attacks[i];
                } else if (used[index] != attacks[i]) {
                    fail = true;
                    break;
                }
            }

            if (!fail) {
                return magic;
            }
        }

        return 0L; // לא נמצא magic number מתאים
    }

    /**
     * מייצר מספר רנדומלי עם מעט ביטים (sparse)
     */
    private static long randomLong() {
        return random.nextLong() & random.nextLong() & random.nextLong();
    }

    /**
     * מייצר ווריאציה ספציפית של תפוסה על בסיס אינדקס ומסכה
     */
    public static long getOccupancyVariation(int index, long mask) {
        long occupancy = 0L;
        int[] bits = new int[64];
        int bitCount = 0;

        // מציאת כל הביטים המופעלים במסכה
        for (int i = 0; i < 64; i++) {
            if ((mask & (1L << i)) != 0) {
                bits[bitCount++] = i;
            }
        }

        // יצירת ווריאציה על בסיס האינדקס
        for (int i = 0; i < bitCount; i++) {
            if ((index & (1 << i)) != 0) {
                occupancy |= (1L << bits[i]);
            }
        }

        return occupancy;
    }

    /**
     * מייצר את כל המשבצות שהצריח יכול לתקוף מהמשבצת הנתונה
     */
    public static long generateRookAttacks(int square, long occupancy) {
        long attacks = 0L;
        int rank = square / 8;
        int file = square % 8;

        // כיוון למעלה
        for (int r = rank + 1; r < 8; r++) {
            int sq = r * 8 + file;
            attacks |= (1L << sq);
            if ((occupancy & (1L << sq)) != 0) break;
        }

        // כיוון למטה
        for (int r = rank - 1; r >= 0; r--) {
            int sq = r * 8 + file;
            attacks |= (1L << sq);
            if ((occupancy & (1L << sq)) != 0) break;
        }

        // כיוון ימינה
        for (int f = file + 1; f < 8; f++) {
            int sq = rank * 8 + f;
            attacks |= (1L << sq);
            if ((occupancy & (1L << sq)) != 0) break;
        }

        // כיוון שמאלה
        for (int f = file - 1; f >= 0; f--) {
            int sq = rank * 8 + f;
            attacks |= (1L << sq);
            if ((occupancy & (1L << sq)) != 0) break;
        }

        return attacks;
    }

    /**
     * סופר כמה ביטים מופעלים במספר
     */
    private static int countBits(long bitboard) {
        return Long.bitCount(bitboard); // פשוט יותר ומהיר יותר!
    }

    /**
     * ממיר אינדקס משבצת לשם (למשל 0 -> "a1")
     */
    private static String squareName(int square) {
        int file = square % 8;
        int rank = square / 8;
        return "" + (char)('a' + file) + (rank + 1);
    }
}