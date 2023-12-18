package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day3 {
    private static final Character NON_PRINTABLE_CHAR = '\t';
    private static final List<char[]> _lines = new ArrayList<>();
    private static int _maxX, _maxY;

    private static final List<AdjacentStar> _adjacentStarList = new ArrayList<>();

    public static void main(String[] args) {

        leesInput();
        //part1();
        part2();
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

    public static void part1() {
        int sum = 0;
        for (int y = 0; y < _maxY; y++) {
            StringBuilder partNumber = new StringBuilder();
            boolean adjacent = false;
            for (int x = 0; x <= _maxX; x++) {
                if (isDigit(x, y)) {
                    partNumber.append(getChar(x, y));
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
        System.out.println("som=" + sum);
    }

    public static void part2() {
        int sum = 0;
        for (int y = 0; y < _maxY; y++) {
            StringBuilder partNumber = new StringBuilder();
            boolean adjacent = false;
            for (int x = 0; x <= _maxX; x++) {
                if (isDigit(x, y)) {
                    partNumber.append(getChar(x, y));
                    if (adjacentToSymbol(x, y)) adjacent = true;
                } else {
                    // geen digit, kijk of een partnumber kan worden bepaald
                    if (partNumber.length() > 0) {
                        if (adjacent) {
                            findAdjacentSymbols(x - partNumber.length(), y, partNumber.toString());
                        }
                    }

                    partNumber = new StringBuilder();
                    adjacent = false;
                }
            }
        }
        // now go through the list of found *-symbols
        for (AdjacentStar adjacentStar : _adjacentStarList) {
            // only match when exactly 2 partnumbers are linked
            if (adjacentStar.partNumbersList.size() == 2) {
                int partNumber1 = Integer.parseInt(adjacentStar.partNumbersList.get(0));
                int partNumber2 = Integer.parseInt(adjacentStar.partNumbersList.get(1));
                sum += partNumber1 * partNumber2;
                System.out.println("(" + (adjacentStar.y + 1) + "," + (adjacentStar.x + 1) + ") PartNumber1=" + partNumber1 + ", PartNumber2=" + partNumber2 + ", sum=" + sum);
            } else {
                System.out.println("(" + (adjacentStar.y + 1) + "," + (adjacentStar.x + 1) + ") #partNumbers=" + adjacentStar.partNumbersList.size());
            }
        }
        System.out.println("som=" + sum);
    }

    private static char getChar(int x, int y) {
        if (x < 0 || x >= _maxX || y < 0 || y >= _maxY) return NON_PRINTABLE_CHAR;
        return _lines.get(y)[x];
    }

    private static boolean isDigit(int x, int y) {
        char a = getChar(x, y);
        for (int i = '0'; i <= '9'; i++) {
            if (a == i) return true;
        }
        return false;
    }

    private static boolean isDot(int x, int y) {
        return getChar(x, y) == '.';
    }

    private static boolean isSymbol(int x, int y) {
        return !isDigit(x, y) && !isDot(x, y) && (getChar(x, y) != NON_PRINTABLE_CHAR);
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

    private static void findAdjacentSymbols(int x, int y, String partNumber) {
        findSymbolAndAdd(x - 1, y - 1, partNumber);
        findSymbolAndAdd(x - 1, y, partNumber);
        findSymbolAndAdd(x - 1, y + 1, partNumber);
        int i = 0;
        for (; i < partNumber.length(); i++) {
            findSymbolAndAdd(x + i, y - 1, partNumber);
            findSymbolAndAdd(x + i, y + 1, partNumber);
        }
        findSymbolAndAdd(x + i, y - 1, partNumber);
        findSymbolAndAdd(x + i, y, partNumber);
        findSymbolAndAdd(x + i, y + 1, partNumber);
    }

    private static void findSymbolAndAdd(int x, int y, String partNumber) {
        if (isSymbol(x, y) && getChar(x, y) == '*') {
            boolean added = false;
            for (AdjacentStar symbol : _adjacentStarList) {
                if (symbol.x == x && symbol.y == y) {
                    symbol.partNumbersList.add(partNumber);
                    added = true;
                }
            }
            if (!added) {
                _adjacentStarList.add(new AdjacentStar(x, y, partNumber));
            }
        }
    }

    static class AdjacentStar {
        public int x;
        public int y;

        public AdjacentStar(int x, int y, String partNumber) {
            this.x = x;
            this.y = y;
            this.partNumbersList = new ArrayList<>();
            this.partNumbersList.add(partNumber);
        }

        public List<String> partNumbersList;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AdjacentStar that = (AdjacentStar) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}