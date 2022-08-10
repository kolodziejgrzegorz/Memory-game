package org.memorygame;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameMechanics {

    private List<Word> wordList;
    private List<Score> scoreList;
    private final String wordsFilePath = "Words.txt";
    private final String scoreFilePath = "Score.txt";
    private MyFile myFile = new MyFile();

    public GameMechanics() {
    }

    public void start() {
        wordList = myFile.readWordsList(wordsFilePath);
        scoreList = myFile.readScore(scoreFilePath);
        menu();
    }

    private void menu() {
        int choice;
        boolean exit = false;
        while (!exit) {
        System.out.println(
                """
                        1.Display menu
                        2.Play level easy
                        3.Play level hard
                        4.Display score table
                        5.Exit""");
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();

            switch (choice) {
                case 2 -> levelEasy();
                case 3 -> levelHard();
                case 4 -> displayScoreTable();
                case 5 -> exit = true;
            }
        }
    }

    private void levelEasy() {
        String level = "easy";
        int guessChances = 10;
        Word[][] words = new Word[2][2];
        play(words, level, guessChances);
    }

    private void levelHard() {
        String level = "hard";
        int guessChances = 15;
        Word[][] words = new Word[2][8];
        play(words, level, guessChances);
    }

    private void displayScoreTable() {
        if (myFile.readScore(scoreFilePath) == null) {
            System.out.println("Score table is empty");
        } else {
            System.out.println(myFile.readScore(scoreFilePath));
        }
    }

    private void play(Word[][] words, String level, int guessChances) {
        Instant start = Instant.now();
        int guessingTries = 0;
        int wordLeft = words[0].length;
        Random rand = new Random();
        for (int j = 0; j < words[0].length; j++) {
            words[0][j] = wordList.get(rand.nextInt(wordList.size()));
            words[1][j] = new Word(words[0][j].getWord());
        }
        // call method twice for better randomize
        randomize(words);
        randomize(words);

        int exit = 1;
        while (exit != 0) {
            displayMatrix(words, level, guessChances);

            System.out.println("position of first word:");
            Scanner s = new Scanner(System.in);
            String position1 = s.nextLine().toUpperCase();
            int row1;
            int column1;
            if (validPosition(position1, words[0].length)) {
                row1 = position1.charAt(0) == 'A' ? 0 : 1;
                column1 = Integer.parseInt(position1.substring(1)) - 1;
            } else {
                System.out.print("bad position name, please use letter A or B and number");
                System.out.print(words[0].length > 4 ? " 1 2 3 4 5 6 7 8" : "   1 2 3 4");
                System.out.println(" example: A2");

                continue;
            }
            words[row1][column1].setDiscovered(true);
            displayMatrix(words, level, guessChances);

            System.out.println("position of second word:");
            s = new Scanner(System.in);
            String position2 = s.nextLine().toUpperCase();
            int row2;
            int column2;
            if (validPosition(position2, words[0].length)) {
                row2 = position2.charAt(0) == 'A' ? 0 : 1;
                column2 = Integer.parseInt(position2.substring(1)) - 1;
            } else {
                System.out.print("bad position name, please use letter A or B and number");
                System.out.print(words[0].length > 4 ? " 1 2 3 4 5 6 7 8" : "   1 2 3 4");
                System.out.println(" example: A2");
                words[row1][column1].setDiscovered(false);
                continue;
            }

            if (words[row1][column1].getWord().equals(words[row2][column2].getWord()) && ((row1 != row2) || (column1 != column2))) {
                wordLeft--;
                words[row2][column2].setDiscovered(true);
            } else {
                guessChances--;
                guessingTries++;
                words[row1][column1].setDiscovered(false);
            }
            if (guessChances < 1) {
                System.out.println("Game Over");
                exit = 0;
            }
            if (wordLeft < 1) {
                System.out.println("You win!");
                exit = 0;
            }
        }
        Instant end = Instant.now();
        long guessingTime = Duration.between(start, end).toSeconds();
        System.out.println("You solved the memory game after " + guessingTries + " chances. It took you "
                + guessingTime + " seconds");
        saveScore(guessingTries, guessingTime);
    }

    private void randomize(Word[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                Random rn = new Random();
                Word temp = tab[i][j];
                int newI = rn.nextInt(2);
                int newJ = rn.nextInt(1000) % tab.length;
                tab[i][j] = tab[newI][newJ];
                tab[newI][newJ] = temp;
            }
        }
    }

    private void displayMatrix(Word[][] tab, String level, int guessChances) {
        System.out.println("---------------------------------------------");
        System.out.println("Level: " + level);
        System.out.println("Guess chances: " + guessChances);
        System.out.println("---------------------------------------------");
        System.out.println(tab[0].length > 4 ? "   1 2 3 4 5 6 7 8" : "   1 2 3 4");
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i == 0 ? "A: " : "B: ");
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j].isDiscovered()) {
                    System.out.print(tab[i][j].getWord() + " ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    private boolean validPosition(String position, int columnCount) {
        if (position != null) {
            String row = position.substring(0, 1);
            int column;
            try {
                column = Integer.parseInt(position.substring(1)) - 1;
            } catch (Exception e) {
                return false;
            }
            if (row.equals("A") || row.equals("B")) {
                if ((column >= 0) && (column <= columnCount)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void saveScore(int guessingTries, long guessingTime) {
        System.out.println("Your name: ");
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();
        Score score = new Score(name, LocalDate.now(), guessingTime, guessingTries);

        if(scoreList == null){
            scoreList = new ArrayList<>();
        }
        scoreList.add(score);
        myFile.saveScore(scoreList, scoreFilePath);
        displayScoreTable();
    }
}
