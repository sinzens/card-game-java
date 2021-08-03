package com.group3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int MaxTime = 10;

    // Written by Zeng Yinuo
    public static void main(String[] args) {
        printRules();
        startGame();
    }

    // Written by Zeng Yinuo
    // Initialize 54 cards
    static List<Card> initAllCards() {
        List<Card> cards = new ArrayList<>();

        // Generate cards with different decors and numbers
        for (Decor decor : Decor.values()) {
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(decor, i);
                cards.add(card);
            }
        }

        // Add black and red Joker to the cards
        Card blackJoker = new Card(Decor.Club, 14);
        Card redJoker = new Card(Decor.Club, 15);
        cards.add(blackJoker);
        cards.add(redJoker);

        return cards;
    }

    // Written by Zhang Mengpei
    // Shuffle the cards
    static List<Card> shuffle(List<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }

    // Written by Du Liangkai
    // Decide which player should play the first card
    static int getWhoPlayFirst() {
        return new Random().nextInt(100);
    }

    // Written by Du Liangkai
    // One round play
    static Player play(Player player1, Player player2) {
        Card card1 = player1.playCard();
        Card card2 = player2.playCard();

        // Decide which player wins the round by comparing cards
        boolean card1IsSmaller = card1.compare(card2);
        System.out.printf("First player's card: %s%n", card1);
        System.out.printf("Second player's card: %s%n", card2);

        // If a player wins, takes both 2 cards
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

    // Written by Zeng Yinuo
    // Find out which player the given player is (player1 or player2)
    static String getPlayerName(Player player, Player player1, Player player2) {
        if (player == player1) {
            return "Player1";
        }
        if (player == player2) {
            return "Player2";
        }
        return "Player-Unknown";
    }

    // Written by Zeng Yinuo, Du Liangkai
    // Main process of game
    static void startGame() {
        // Initialize 54 cards and shuffle
        List<Card> cards = shuffle(initAllCards());
        List<Card> cardSet1 = new ArrayList<>();
        List<Card> cardSet2 = new ArrayList<>();

        // Divide into 2 sets of cards
        for (int i = 0; i < 27; i++) {
            cardSet1.add(cards.get(i));
        }
        for (int i = 27; i < 54; i++) {
            cardSet2.add(cards.get(i));
        }

        // Give cards to 2 players
        Player player1 = new Player();
        Player player2 = new Player();
        player1.initCards(cardSet1);
        player2.initCards(cardSet2);

        // Decide which player plays the first card
        Player playerFirst = getWhoPlayFirst() > 50 ? player1 : player2;
        Player playerSecond = playerFirst == player1 ? player2 : player1;

        Player winner;
        int counter = 0;
        long startTime = System.currentTimeMillis();
        // Main loop, breaks when time is up or one player has no card left
        while (true) {
            // Count the time
            long currentTime = System.currentTimeMillis();
            long timeGap = (currentTime - startTime) / 1000;
            // If one player's cards are empty, game over
            if (playerFirst.getCardNumber() == 0 || playerSecond.getCardNumber() == 0) {
                System.out.println("There is one player whose cards are empty, game over.");
                System.out.printf("Totally takes %d ms.%n", currentTime - startTime);
                break;
            }
            // If time is up, game over
            if (timeGap > MaxTime) {
                System.out.println("Time is up, game over.");
                break;
            }

            // Update the winner by swapping players
            winner = play(playerFirst, playerSecond);
            if (winner != playerFirst) {
                playerSecond = playerFirst;
                playerFirst = winner;
            }

            System.out.printf("Round %d, winner is %s.%n", counter + 1, getPlayerName(winner, player1, player2));
            System.out.println("----------------------------------");
            counter++;
        }

        // Decide which player wins
        if (player1.getCardNumber() > player2.getCardNumber()) {
            System.out.println("Player1 is winner !");
        } else if (player1.getCardNumber() == player2.getCardNumber()) {
            System.out.println("Player1 and Player2 are winners !");
        } else {
            System.out.println("Player2 is winner !");
        }

        System.out.println("\n------------------------------------------------\n");
    }

    // Written by Zeng Yinuo
    // Print the game rules
    static void printRules() {
        System.out.println("""
                ## 游戏规则

                两名玩家逐个出牌（每张牌随机抽取），第一局随机决定谁先出，第二局开始，谁的牌大（按照大小规则）谁先出。如果玩家A大，那么将玩家B的牌拿到玩家A的纸牌中。

                ### 大小规则

                - 大王>小王>其他牌
                - 比较牌的点数大：K > Q > J > 10 > 9 > 8 > 7 > 6 > 5 > 4 > 3 > 2 > A
                - 如果牌的点数相同，则按照花色比较：Spade(黑桃) < Heart(红桃) < Diamond(方片) < Club(梅花)

                """);
        System.out.println("------------------------------------------------\n");
    }
}
