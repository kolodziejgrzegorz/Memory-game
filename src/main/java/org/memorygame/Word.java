package org.memorygame;

import java.util.Objects;

public class Word {
    private String word;
    private boolean isDiscovered;

    public Word(String word) {
        this.word = word;
        isDiscovered = false;
    }

    String getWord() {
        return word;
    }

    void setWord(String word) {
        this.word = word;
    }

    boolean isDiscovered() {
        return isDiscovered;
    }

    void setDiscovered(boolean discovered) {
        isDiscovered = discovered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}
