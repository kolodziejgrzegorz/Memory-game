package org.memorygame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyFile {
    public MyFile() {
    }

    public List<Word> readWordsList(String filePath) {
        List<Word> wordsList = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            //todo
            throw new RuntimeException(e);
        }
        while (scanner.hasNext()) {
            wordsList.add(new Word(scanner.next()));
        }
        return wordsList;
    }


    public void saveScore(List<Score> scoreList, String fileName) {
        try {
            FileOutputStream writeData = new FileOutputStream(fileName);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(scoreList);
            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            //todo
            e.printStackTrace();
        }
    }

    public List<Score> readScore(String filePath) {
        List<Score> scoreList= null;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream input = new ObjectInputStream(fis);
            scoreList = (List<Score>) input.readObject();
        } catch (Exception e) {
            //todo
        }
        return scoreList;
    }
}