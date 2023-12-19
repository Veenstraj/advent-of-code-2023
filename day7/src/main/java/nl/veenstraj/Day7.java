package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day7 {

    private static final HashMap<Character, Integer> _cardRanking = new HashMap<>() {
        {
            put('A', 13);
            put('K', 12);
            put('Q', 11);
            put('T', 9);
            put('9', 8);
            put('8', 7);
            put('7', 6);
            put('6', 5);
            put('5', 4);
            put('4', 3);
            put('3', 2);
            put('2', 1);
            put('J', 0);
        }
    };

    private static final List<Hand> _hands = new ArrayList<>();

    public static void main(String[] args) {
        leesInput();
        opgave2();
    }

    public static void leesInput() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day7/target/classes/input.txt"));
            String line = reader.readLine();

            int nrOfHands = 0;
            while (line != null) {

                if (!line.trim().isEmpty()) {
                    String[] hands = line.split(" ");
                    Hand hand = new Hand();
                    hand.hand = hands[0].toCharArray();
                    hand.bid = Integer.parseInt(hands[1].trim());
                    _hands.add(hand);
                    nrOfHands++;
                }
                // read next line
                line = reader.readLine();
            }
            System.out.println("Aantal handen =" + nrOfHands);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void opgave2() {
        List<Hand> hands = _hands.stream()
                .sorted()
                .toList();

        int winnings = 0;
        for (int ranking = 1; ranking <= hands.size(); ranking++) {
            winnings += ranking * hands.get(ranking - 1).bid;
            System.out.println("Ranking: " + ranking + ", Hand=" + Arrays.toString(hands.get(ranking - 1).hand) + ", bid=" + hands.get(ranking - 1).bid);
        }
        System.out.println("Total winnings=" + winnings);
    }

    static class Hand implements Comparable<Hand> {
        char[] hand;
        int bid;

        @Override
        public int compareTo(Hand other) {
            int typeThis = determineType(this.hand);
            int typeThat = determineType(other.hand);
            if ((typeThis - typeThat) != 0) return (typeThis - typeThat);

            int rankThis = 0;
            int rankThat = 0;
            for (int cardnr = 0; cardnr < 5; cardnr++) {
                rankThat = _cardRanking.get(other.hand[cardnr]);
                rankThis = _cardRanking.get(this.hand[cardnr]);
                if ((rankThis - rankThat) != 0) break;
            }
            return (rankThis - rankThat);
        }

        private int determineType(char[] hand) {
            // count the number of occurrences of each card
            HashMap<Character, Integer> occ = new HashMap<>();
            for (char a : hand) {
                if (occ.containsKey(a)) {
                    occ.put(a, occ.get(a) + 1);
                } else {
                    occ.put(a, 1);
                }
            }
            // five of a kind
            if (occ.containsValue(5)) return 7;
            if (occ.containsValue(4) && occ.containsKey('J') && occ.get('J') == 1) return 7;
            if (occ.containsValue(3) && occ.containsKey('J') && occ.get('J') == 2) return 7;
            if (occ.containsValue(2) && occ.containsKey('J') && occ.get('J') == 3) return 7;
            if (occ.containsValue(1) && occ.containsKey('J') && occ.get('J') == 4) return 7;
            // four of a kind
            if (occ.containsValue(4)) return 6;
            if (occ.containsValue(3) && occ.containsKey('J') && occ.get('J') == 1) return 6;
            if (occ.containsValue(2) && occ.containsKey('J') && occ.get('J') == 2 && aantalValues(occ, 2) == 2)
                return 6;
            if (occ.containsValue(1) && occ.containsKey('J') && occ.get('J') == 3) return 6;
            // Ful house
            if (occ.containsValue(3) && occ.containsValue(2)) return 5;
            if (occ.containsValue(2) && occ.containsKey('J') && occ.get('J') == 1 && occ.keySet().size() == 3)
                return 5;
            // Three of a kind
            if (occ.containsValue(3)) return 4;
            if (occ.containsValue(2) && occ.containsKey('J') && occ.get('J') >= 1) return 4;
            // two pair
            if (occ.containsValue(2) && occ.containsValue(1) && aantalValues(occ, 2) == 2) return 3;
            if (occ.containsValue(2) && occ.containsValue(1) && occ.containsKey('J')) return 3;
            // one pair
            if (occ.containsValue(2) && occ.keySet().size() > 3) return 2;
            if (occ.containsKey('J')) return 2;
            // high card
            if (occ.keySet().size() == 5) return 1;
            // de rest
            return 0;
        }

        private long aantalValues(HashMap<Character, Integer> map, int value) {
            return map.values().stream().filter(v -> v == value).count();
        }
    }
}
