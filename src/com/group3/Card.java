package com.group3;

import java.util.Arrays;

enum Decor {
    Spade, Heart, Diamond, Club
}

public class Card {
    private Decor decor;

    private int number;

    // Written by Du Liangkai
    public Card(Decor decor, int number) {
        this.decor = decor;
        this.number = number;
    }

    // Written by Du Liangkai
    public void setDecor(Decor decor) {
        this.decor = decor;
    }

    // Written by Du Liangkai
    public Decor getDecor() {
        return this.decor;
    }

    // Written by Du Liangkai
    public void setNumber(int number) {
        this.number = number;
    }

    // Written by Du Liangkai
    public int getNumber() {
        return this.number;
    }

    // Written by Zhu Zhiyuan, Zhou Peng
    public boolean compare(Card other) {
        if (this.number < other.number) {
            return true;
        } else if (this.number > other.number) {
            return false;
        } else {
            int index1 = Arrays.asList(Decor.values()).indexOf(this.decor);
            int index2 = Arrays.asList(Decor.values()).indexOf(other.decor);
            return index1 < index2;
        }
    }

    // Written by Zeng Yinuo
    @Override
    public String toString() {
        return "Card{" +
                "decor=" + decor +
                ", number=" + number +
                '}';
    }
}
