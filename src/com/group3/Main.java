package com.group3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    // Written by Zeng Yinuo, Du Liangkai
    public static void main(String[] args) {
        printRules();

        List<Card> cards = shuffle(initAllCards());
        List<Card> cardSet1 = new ArrayList<>();
        List<Card> cardSet2 = new ArrayList<>();

        for (int i = 0; i < 27; i++) {
            cardSet1.add(cards.get(i));
        }
        for (int i = 27; i < 54; i++) {
            cardSet2.add(cards.get(i));
        }

        Player player1 = new Player();
        Player player2 = new Player();

        player1.initCards(cardSet1);
        player2.initCards(cardSet2);

        Player playerFirst = getWhoPlayFirst() > 50 ? player1 : player2;
        Player playerSecond = playerFirst == player1 ? player2 : player1;

        Player winner;
        int counter = 0;
        long startTime = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();
            long timeGap = (currentTime - startTime) / 1000;
            if (playerFirst.getCardNumber() == 0 || playerSecond.getCardNumber() == 0) {
                System.out.println("There is one player whose cards are empty, game over.");
                System.out.println("Totally takes " + (currentTime - startTime) + "ms.");
                break;
            }
            if (timeGap > 10) {
                System.out.println("Time is up, game over.");
                break;
            }
            winner = play(playerFirst, playerSecond);
            if (winner != playerFirst) {
                playerSecond = playerFirst;
                playerFirst = winner;
            }
            System.out.println("Current winner is " + getPlayerName(winner, player1, player2) + ", Round " + counter);
            counter++;
        }

        if (player1.getCardNumber() > player2.getCardNumber()) {
            System.out.println("Player1 is winner !");
        } else if (player1.getCardNumber() == player2.getCardNumber()) {
            System.out.println("Player1 and Player2 are winners !");
        } else {
            System.out.println("Player2 is winner !");
        }
    }

    // Written by Zeng Yinuo
    static List<Card> initAllCards() {
        List<Card> cards = new ArrayList<>();
        for (Decor decor : Decor.values()) {
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(decor, i);
                cards.add(card);
            }
        }

        Card blackJoker = new Card(Decor.Club, 14);
        Card redJoker = new Card(Decor.Club, 15);
        cards.add(blackJoker);
        cards.add(redJoker);

        return cards;
    }

    // Written by Zhang Mengpei
    static List<Card> shuffle(List<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }

    // Written by Du Liangkai
    static int getWhoPlayFirst() {
        return new Random().nextInt(100);
    }

    // Written by Du Liangkai
    static Player play(Player player1, Player player2) {
        Card card1 = player1.playCard();
        Card card2 = player2.playCard();

        boolean card1IsSmaller = card1.compare(card2);
        System.out.println(card1.toString() + ", " + card2.toString());

        if (card1IsSmaller) {
            player2.getCard(card1);
            player2.getCard(card2);
            return player2;
        } else {
            player1.getCard(card1);
            player1.getCard(card2);
            return player1;
        }
    }

    static String getPlayerName(Player player, Player player1, Player player2) {
        return player == player1 ? "Player1" : "Player2";
    }

    static void printRules() {
        System.out.println("## 游戏规则\n" +
                "\n" +
                "两名玩家逐个出牌（每张牌随机抽取），第一局随机决定谁先出，第二局开始，谁的牌大（按照大小规则）谁先出。如果玩家A大，那么将玩家B的牌拿到玩家A的纸牌中。\n" +
                "\n" +
                "### 大小规则\n" +
                "\n" +
                "- 大王>小王>其他牌\n" +
                "- 比较牌的点数大：K > Q > J > 10 > 9 > 8 > 7 > 6 > 5 > 4 > 3 > 2 > A\n" +
                "- 如果牌的点数相同，则按照花色比较：Spade(黑桃) < Heart(红桃) < Diamond(方片) < Club(梅花)\n\n");
        System.out.println("------------------------------------------------\n");
    }
}
