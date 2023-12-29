package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day13 {
    private static final String inputfile = "./day13/target/classes/input.txt";
    private static char[][] _map = new char[20][20];
    private static int _sizex = 0;
    private static int _sizey = 0;

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();

            int sum = 0;
            _sizey = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    _map[_sizey++] = line.trim().toCharArray();
                } else {
                    System.out.println("New map...");
                    // end of pattern, do the magic
                    _sizex = _map[0].length;
                    sum += findVerticalReflection();
                    sum += 100 * findHorizontalReflection();

                    // new pattern
                    _sizey = 0;
                    _map = new char[20][20];
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();


            System.out.println("sum=" + sum);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static int findVerticalReflection() {
        boolean[] reflection = new boolean[_sizex - 1];
        Arrays.fill(reflection, true);
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex - 1; x++) {
                if (reflection[x]) {
                    for (int dx = 0; dx <= x && (x + dx + 1) < _sizex; dx++) {
                        System.out.printf("x checking (%d,%d)=%c to (%d,%d)=%c: ",
                                y + 1, x - dx + 1, _map[y][x - dx], y + 1, x + dx + 2, _map[y][x + dx + 1]);
                        if (_map[y][x - dx] != _map[y][x + dx + 1]) {
                            reflection[x] = false;
                            System.out.printf("Reflection %d = %b (set)%n", x + 1, reflection[x]);
                            break;
                        }
                        System.out.printf("Reflection %d = %b%n", x + 1, reflection[x]);
                    }
                }
            }
        }

        int answer = 0;
        for (int x = 0; x < _sizex - 1; x++) {
            if (reflection[x]) {
                System.out.println("reflection at x=" + (x + 1));
                answer += x + 1;
            }
        }
        return answer;
    }

    private static int findHorizontalReflection() {
        boolean[] reflection = new boolean[_sizey - 1];
        Arrays.fill(reflection, true);
        for (int x = 0; x < _sizex; x++) {
            for (int y = 0; y < _sizey - 1; y++) {
                if (reflection[y]) {
                    for (int dy = 0; dy <= y && (y + dy + 1) < _sizey; dy++) {
                        System.out.printf("checking (%d,%d)=%c to (%d,%d)=%c: ",
                                y - dy + 1, x + 1, _map[y - dy][x], y + dy + 2, x + 1, _map[y + dy + 2][x]);
                        if (_map[y - dy][x] != _map[y + dy + 1][x]) {
                            reflection[y] = false;
                            System.out.printf("Reflection %d = %b (set)%n", y + 1, reflection[y]);
                            break;
                        }
                        System.out.printf("Reflection %d = %b%n", y + 1, reflection[y]);
                    }
                }
            }
        }

        int answer = 0;
        for (int y = 0; y < _sizey - 1; y++) {
            if (reflection[y]) {
                System.out.println("reflection at y=" + (y + 1));
                answer += y + 1;
            }

        }
        return answer;

    }
}