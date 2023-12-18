package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 {
    static List<Card> _cardList = new ArrayList<>();

    public static void main(String[] args) {
        //opgave1();
        opgave2();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day4/target/classes/input.txt"));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {
                String[] cards = line.split(":");
                String[] numbers = cards[1].trim().split("\\|");
                String[] winningNumbers = numbers[0].trim().split(" ");
                String[] myNumbers = numbers[1].trim().split(" ");

                int points = 0;
                System.out.printf(cards[0] + ":");
                for (String winningNumber : winningNumbers) {
                    for (String myNumber : myNumbers) {
                        if (!myNumber.trim().isEmpty() && !winningNumber.trim().isEmpty() &&
                                (Integer.parseInt(myNumber) == Integer.parseInt(winningNumber))) {
                            points = (points == 0) ? points = 1 : points * 2;
                            System.out.println("Winning number I own: " + myNumber + ", points: " + points);
                        }
                    }
                }
                System.out.println("points=" + points);
                sum += points;

                // read next line
                line = reader.readLine();
            }
            System.out.println("sum=" + sum);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void opgave2() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day4/target/classes/input.txt"));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {
                String[] cards = line.split(":");
                String[] numbers = cards[1].trim().split("\\|");
                String[] winningNumbers = numbers[0].trim().split(" ");
                String[] myNumbers = numbers[1].trim().split(" ");

                Card card = new Card(Integer.parseInt(cards[0].substring(5).trim()));
                Arrays.stream(winningNumbers)
                        .filter(n -> !n.trim().isEmpty())
                        .forEach(n -> card.winningNumbers.add(Integer.parseInt(n)));

                Arrays.stream(myNumbers)
                        .filter(n -> !n.trim().isEmpty())
                        .forEach(n -> card.myNumbers.add(Integer.parseInt(n)));

                _cardList.add(card);

                // read next line
                line = reader.readLine();
            }

            berekenCopies();
            for (Card card : _cardList) {
                System.out.println(card.cardNumber + ":" + card.nrOfInstances + ", wins:" + card.nrOfWins());
                sum += card.nrOfInstances;
            }
            System.out.println("sum=" + sum);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void berekenCopies() {

        for (Card card : _cardList) {
            System.out.println("Handling card " + card.cardNumber + ", wins: " + card.nrOfWins() + ", instances:" + card.nrOfInstances);
            for (int x = 0; x < card.nrOfInstances; x++) {
                for (int i = 0; i < card.nrOfWins(); i++) {
                    _cardList.get(card.cardNumber + i).nrOfInstances++;
                    //      System.out.println("Card " + (card.cardNumber + 1 + i) + " instances: " + _cardList.get(card.cardNumber + i).nrOfInstances);
                }
            }
        }

    }

    private static class Card {
        public int cardNumber;

        public Card(int cardNumber) {
            this.cardNumber = cardNumber;
            this.nrOfInstances = 1;
        }

        public List<Integer> winningNumbers = new ArrayList<>();
        public List<Integer> myNumbers = new ArrayList<>();
        public int nrOfInstances;

        public Integer nrOfWins() {
            return (int) myNumbers.stream().filter(n -> winningNumbers.contains(n)).count();
        }
    }
}