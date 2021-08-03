package com.group3;

import java.util.List;
import java.util.Random;

public class Player {
    private List<Card> cards;

    // Written by Zeng Yinuo
    public int getCardNumber() {
        return this.cards.size();
    }

    // Written by Zeng Yinuo
    public void initCards(List<Card> cards) {
        this.cards = cards;
    }

    // Written by Zhang Mengpei, Liu Xuan, Ou Linyu
    public Card playCard() {
        Random random = new Random();
        int target = random.nextInt(this.getCardNumber());
        Card currentCard = this.cards.get(target);
        this.cards.remove(target);

        return new Card(currentCard.getDecor(), currentCard.getNumber());
    }

    // Written by Zeng Yinuo
    public void getCard(Card card) {
        this.cards.add(card);
    }
}
