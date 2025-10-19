package MagicBitboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttacksLoader {

    /**
     * קורא את מערך ההתקפות מקובץ טקסט
     * @param fileName שם הקובץ (למשל "attacks_output.txt")
     * @return המערך הדו-מימדי ATTACKS
     */
    public static long[][] loadAttacksFromFile(String fileName) {
        long[][] ATTACKS = new long[64][];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int currentSquare = -1;
            List<Long> currentList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // מחפש שורה שמתחילה ב-"ATTACKS["
                if (line.startsWith("ATTACKS[") && line.contains("] = {")) {
                    // שמירת המשבצת הקודמת אם היא קיימת
                    if (currentSquare >= 0) {
                        ATTACKS[currentSquare] = listToArray(currentList);
                        currentList.clear();
                    }

                    // חילוץ מספר המשבצת
                    int start = line.indexOf('[') + 1;
                    int end = line.indexOf(']');
                    currentSquare = Integer.parseInt(line.substring(start, end));

                }
                // מחפש שורות עם ערכי hex
                else if (line.startsWith("0x") && currentSquare >= 0) {
                    // מסיר את הפסיק בסוף אם יש
                    String hexValue = line.replace(",", "").trim();

                    // המרה מ-hex string ל-long
                    // מסיר את ה-"0x" בהתחלה ואת ה-"L" בסוף
                    hexValue = hexValue.substring(2); // מסיר "0x"
                    if (hexValue.endsWith("L")) {
                        hexValue = hexValue.substring(0, hexValue.length() - 1);
                    }

                    long value = Long.parseUnsignedLong(hexValue, 16);
                    currentList.add(value);
                }
                // בודק אם זה סוף המערך של המשבצת הנוכחית
                else if (line.equals("};") && currentSquare >= 0) {
                    ATTACKS[currentSquare] = listToArray(currentList);
                    currentList.clear();
                    currentSquare = -1;
                }
            }

            System.out.println("✅ הקובץ נטען בהצלחה!");
            printLoadingStats(ATTACKS);

        } catch (IOException e) {
            System.err.println("❌ שגיאה בקריאת הקובץ: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("❌ שגיאה בהמרת מספר: " + e.getMessage());
            e.printStackTrace();
        }

        return ATTACKS;
    }

    /**
     * ממיר List<Long> למערך long[]
     */
    private static long[] listToArray(List<Long> list) {
        long[] array = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * מדפיס סטטיסטיקות על המערך שנטען
     */
    private static void printLoadingStats(long[][] ATTACKS) {
        int totalEntries = 0;
        int loadedSquares = 0;

        for (int i = 0; i < 64; i++) {
            if (ATTACKS[i] != null) {
                loadedSquares++;
                totalEntries += ATTACKS[i].length;
            }
        }

        System.out.println("\n=== סטטיסטיקות טעינה ===");
        System.out.println("משבצות שנטענו: " + loadedSquares + " / 64");
        System.out.println("סך הכל ערכים: " + totalEntries);
        System.out.println("זיכרון משוער: " + (totalEntries * 8 / 1024) + " KB");
    }

    /**
     * מחזיר מהלכי צריח (שימוש במערך שנטען)
     */
    public static long getRookMoves(long[][] ATTACKS, long[] MAGIC_NUMBERS,
                                    int[] SHIFTS, long[] BLOCKERS,
                                    int square, long occupancy) {
        long relevantOccupancy = occupancy & BLOCKERS[square];
        int index = (int) ((relevantOccupancy * MAGIC_NUMBERS[square]) >>> SHIFTS[square]);
        return ATTACKS[square][index];
    }

    /**
     * פונקציה לבדיקה
     */
    public static void testLoadedAttacks(long[][] ATTACKS) {
        System.out.println("\n=== בדיקת מערך שנטען ===\n");

        // בודק כמה משבצות ראשונות
        for (int square = 0; square < 64; square++) {
            if (ATTACKS[square] != null) {
                String squareName = getSquareName(square);
                System.out.println("Square " + square + " (" + squareName + "): "
                        + ATTACKS[square].length + " entries");

                // מדפיס 3 ערכים ראשונים
                for (int i = 0; i < Math.min(3, ATTACKS[square].length); i++) {
                    System.out.println("  [" + i + "] = " + formatHex(ATTACKS[square][i]));
                }
            }
        }
    }

    private static String formatHex(long value) {
        return String.format("0x%016XL", value);
    }

    private static String getSquareName(int square) {
        int file = square % 8;
        int rank = square / 8;
        return "" + (char)('a' + file) + (rank + 1);
    }


}