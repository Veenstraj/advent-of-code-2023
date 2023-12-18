package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
    private static final List<char[]> _lines = new ArrayList<>();
    private static int _maxX, _maxY;

    public static void main(String[] args) {

        leesInput();
        parseSchematic();
    }

    public static void leesInput() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day3/target/classes/input.txt"));
            String line = reader.readLine();

            _maxY = 0;
            _maxX = 10000;

            while (line != null) {
                if (line.trim().length() > 0) {
                    _lines.add(line.toCharArray());
                    _maxY++;
                    if (line.trim().length() < _maxX) {
                        _maxX = line.trim().length();
                    }
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
            System.out.println("MaxX=" + _maxX + " MaxY=" + _maxY);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void parseSchematic() {
        int sum = 0;
        for (int y = 0; y < _maxY; y++) {
            StringBuilder partNumber = new StringBuilder();
            boolean adjacent = false;
            for (int x = 0; x < _maxX; x++) {
                if (isDigit(x, y)) {
                    partNumber.append(getDigit(x, y));
                    if (adjacentToSymbol(x, y)) {
                        adjacent = true;
                    }
                } else {
                    // geen digit, kijk of een partnumber kan worden bepaald
                    if (partNumber.length() > 0) {
                        if (adjacent) {
                            sum += Integer.parseInt(partNumber.toString());
                            System.out.println("(" + (y + 1) + "," + (x - partNumber.length() + 1) + ") partnr " + partNumber + " sum = " + sum);
                        } else {
                            System.out.println("(" + (y + 1) + "," + (x - partNumber.length() + 1) + ") skip nr " + partNumber + " sum = " + sum);
                        }
                    }

                    partNumber = new StringBuilder();
                    adjacent = false;
                }
            }
        }
    }

    private static char getDigit(int x, int y) {
        if (x < 0 || x >= _maxX || y < 0 || y >= _maxY) return '\t';
        return _lines.get(y)[x];
    }

    private static boolean isDigit(int x, int y) {
        char a = getDigit(x, y);
        return a == '0' || a == '1' || a == '2' || a == '3' || a == '4' || a == '5' || a == '6' || a == '7' || a == '8' || a == '9';
    }

    private static boolean isDot(int x, int y) {
        return getDigit(x, y) == '.';
    }

    private static boolean isSymbol(int x, int y) {
        return !isDigit(x, y) && !isDot(x, y) && getDigit(x, y) != '\t';
    }

    private static boolean adjacentToSymbol(int x, int y) {
        return isSymbol(x - 1, y - 1)
                || isSymbol(x, y - 1)
                || isSymbol(x + 1, y - 1)
                || isSymbol(x - 1, y)
                || isSymbol(x + 1, y)
                || isSymbol(x - 1, y + 1)
                || isSymbol(x, y + 1)
                || isSymbol(x + 1, y + 1);
    }
}