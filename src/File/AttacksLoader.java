package File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AttacksLoader {

    public static long[][] loadAttacks(String fileName) {
        long[][] ATTACKS = new long[64][];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int currentSquare = -1;
            List<Long> currentList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("ATTACKS[") && line.contains("] = {")) {
                    if (currentSquare >= 0) {
                        ATTACKS[currentSquare] = listToArray(currentList);
                        currentList.clear();
                    }

                    int start = line.indexOf('[') + 1;
                    int end = line.indexOf(']');
                    currentSquare = Integer.parseInt(line.substring(start, end));

                }
                else if (line.startsWith("0x") && currentSquare >= 0) {
                    String hexValue = line.replace(",", "").trim();

                    hexValue = hexValue.substring(2); // מסיר "0x"
                    if (hexValue.endsWith("L")) {
                        hexValue = hexValue.substring(0, hexValue.length() - 1);
                    }

                    long value = Long.parseUnsignedLong(hexValue, 16);
                    currentList.add(value);
                }
                else if (line.equals("};") && currentSquare >= 0) {
                    ATTACKS[currentSquare] = listToArray(currentList);
                    currentList.clear();
                    currentSquare = -1;
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
        }

        return ATTACKS;
    }

    private static long[] listToArray(List<Long> list) {
        long[] array = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

}