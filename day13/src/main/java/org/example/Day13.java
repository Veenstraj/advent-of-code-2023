package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day13 {
    private static final String inputfile = "./day13/target/classes/inputTst.txt";
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
                    sum += findVerticalReflection(true);
                    sum += 100 * findHorizontalReflection(true);

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

    private static int findVerticalReflection(boolean findSmudge) {
        System.out.printf("sizex=%d, sizey=%d, findsmudge=%b%n", _sizex, _sizey, findSmudge);

        int[] reflection = new int[_sizex - 1];
        int[] reflectiony = new int[_sizex - 1];
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex - 1; x++) {
                if (reflection[x] == 0) {
                    for (int dx = 0; dx <= x && (x + dx + 1) < _sizex; dx++) {
                        System.out.printf("X checking %s %c to %s %c: ",
                                prtCoord(x - dx, y), get(x - dx, y), prtCoord(x + dx + 1, y), get(x + dx + 1, y));
                        if (get(x - dx, y) != get(x + dx + 1, y)) {
                            reflection[x]++;
                            reflectiony[x] = y;
                            System.out.printf("Reflection %d = %d (set)%n", x + 1, reflection[x]);
                            if (reflection[x] > 2) break;
                        } else {
                            System.out.printf("Reflection %d = %d%n", x + 1, reflection[x]);
                        }
                    }
                }
            }
        }

        int answer = 0;
        for (int x = 0; x < _sizex - 1; x++) {
            System.out.println("reflection at x=" + (x + 1) + " is " + reflection[x]);
            if (reflection[x] == 1 && findSmudge) {
                System.out.printf("Smudge found at %s%n", prtCoord(x, reflectiony[x]));
                // repair smudge
                set(x, reflectiony[x], get(x, reflectiony[x]) == '.' ? '#' : '.');
                int possibleRefection = findVerticalReflection(false); //again, with repaired mirror
                if (possibleRefection == 0 || possibleRefection == (reflection[x] + 1)) {
                    System.out.println("no new reflection (" + possibleRefection + ")");
                    possibleRefection = findHorizontalReflection(false);
                    if (possibleRefection == 0) {
                        System.out.println("no succes,restore smudge and continue with next possibility");
                        set(x, reflectiony[x], get(x, reflectiony[x]) == '.' ? '#' : '.');
                    } else {
                        System.out.println("Found horizontal reflection");
                        return 0;
                    }
                } else {
                    return possibleRefection + 1;
                }
            }
            if (!findSmudge && reflection[x] == 0) {
                System.out.println("answer=" + (x + 1));
                answer += x + 1;
                return answer;
            }
        }
        return answer;
    }

    private static int findHorizontalReflection(boolean findSmudge) {
        System.out.printf("sizex=%d, sizey=%d, findsmudge=%b%n", _sizex, _sizey, findSmudge);

        int[] reflection = new int[_sizey - 1];
        int[] reflectionx = new int[_sizey - 1];
        for (int x = 0; x < _sizex; x++) {
            for (int y = 0; y < _sizey - 1; y++) {
                if (reflection[y] == 0) {
                    for (int dy = 0; dy <= y && (y + dy + 1) < _sizey; dy++) {
                        System.out.printf("Y checking %s %c to %s %c: ",
                                prtCoord(x, y - dy), get(x, y - dy), prtCoord(x, y + dy + 1), get(x, y + dy + 1));
                        if (get(x, y - dy) != get(x, y + dy + 1)) {
                            reflection[y]++;
                            reflectionx[y] = x;
                            System.out.printf("Reflection %d = %d (set)%n", y + 1, reflection[y]);
                            if (reflection[y] > 2) break;
                        } else {
                            System.out.printf("Reflection %d = %d%n", y + 1, reflection[y]);
                        }
                    }
                }
            }
        }

        int answer = 0;
        for (int y = 0; y < _sizey - 1; y++) {
            System.out.println("reflection at y=" + (y + 1) + " is " + reflection[y]);
            if (reflection[y] == 1 && findSmudge) {
                System.out.printf("Smudge found at %s%n", prtCoord(reflectionx[y], y));
//                // repair smudge
//                set(reflectionx[y], y, get(reflectionx[y],y) == '.' ? '#': '.');
//                return findHorizontalReflection(false); //again, with repaired mirror
            }
            if (reflection[y] == 0) {
                System.out.println("answer=" + (y + 1));
                answer += y + 1;
            }

        }
        return answer;

    }

    private static char get(int x, int y) {
        return _map[y][x];
    }

    private static void set(int x, int y, char a) {
        _map[y][x] = a;
    }

    private static String prtCoord(int x, int y) {
        return String.format("(%d,%d)", y + 1, x + 1);
    }
}