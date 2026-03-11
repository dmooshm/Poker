import processing.core.PApplet;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.util.Collections;

public class Characters {

    PApplet parent;
    String name;
    Hand hand;
    int balance;
    Cards card1;
    Cards card2;
    boolean folded = false;
    UI UI;
    public int bet;

    Characters(String name, Hand hand, int balance, PApplet parent) {

        this.name = name;
        this.hand = hand;
        this.balance = balance;
        this.parent = parent;
        this.UI = new UI(parent); // ← move it here

    }

    ArrayList<Cards> finalHand = new ArrayList<>();
    int handType;
    //10-royal flush
    //9-straight flush
    //8-four of a kind
    //7-full house
    //6-flush
    //5-straight
    //4-three of a kind
    //3-two of a kind
    //2-one of a kind
    //1-high card

    public void receiveCards(Cards card1, Cards card2) {

        hand.cardsInHand.add(card1);
        hand.cardsInHand.add(card2);

        this.card1 = card1;
        this.card2 = card2;

    }

    public void registerCommunityCard(Cards card) {

        hand.communityCards.add(card);

    }

    public void generateFinalHand() {

    }

    public void bet() {

    }

    public void call() {

    }

    public void raise() {

    }

    public void fold() {

    }

    public void takeTurn() {

        if (folded) {
            return;
        }

    }

    public void nextTurn() {
        Game.currentTurn = (Game.currentTurn + 1) % Game.numberOfPlayers;
    }

}


class Player extends Characters{

    Player(String name, Hand hand, int balance, PApplet parent) {
        super(name, hand, balance, parent);
    }

    boolean betting;

    @Override
    public void takeTurn() {

        if (!betting) {

            boolean firstTurn = true;

            if (firstTurn) {
                Buttons betButton = new Buttons(parent);
                betButton.makeButton(Game.screenWidth / 2, Game.screenHeight / 2 + 275, 100, 50, 10, "bet", 35);
                if (parent.mousePressed && betButton.clickable) {
                    betting = true;
                    raise();
                }
                firstTurn = false;

            } else {

                Buttons callButton = new Buttons(parent);
                callButton.makeButton(Game.screenWidth / 2 - 125, Game.screenHeight / 2 + 275, 100, 50, 10, "call", 35);
                if (parent.mousePressed && callButton.clickable) {
                    betting = true;
                    call();
                }

                Buttons raiseButton = new Buttons(parent);
                raiseButton.makeButton(Game.screenWidth / 2, Game.screenHeight / 2 + 275, 100, 50, 10, "raise", 35);
                if (parent.mousePressed && raiseButton.clickable) {
                    betting = true;
                    raise();
                }

                Buttons foldButton = new Buttons(parent);
                foldButton.makeButton(Game.screenWidth / 2 + 125, Game.screenHeight / 2 + 275, 100, 50, 10, "fold", 35);
                if (parent.mousePressed && foldButton.clickable) {
                    betting = true;
                    fold();
                }

            }

        } else {
            UI.renderChips();
            parent.fill(0);
            parent.textSize(40);
            parent.textAlign(parent.CENTER, parent.CENTER);
            parent.text("Bet: " + bet, Game.screenWidth / 2, Game.screenHeight / 2 + 225);
            Buttons betButton = new Buttons(parent);
            betButton.makeButton(725, 700, 115, 40, 10, "make bet", 25);
            if (parent.mousePressed && betButton.clickable) {

            }
        }

    }

    @Override
    public void raise() {

    }

}


class NPC extends Characters {

    NPC (String name, Hand hand, int balance, PApplet parent) {
        super(name, hand, balance, parent);
    }

    public void takeTurn() {

        System.out.println("took turn");

    }

    public static void calculateProbability() {

    }

    public static void assignTell() {

    }

    public static void thinkingAnimation() {

    }

    public static void revealCards() {

    }

}


class Hand {

    PApplet parent;

    ArrayList<Cards> cardsInHand = new ArrayList<>();
    ArrayList<Cards> communityCards = new ArrayList<>();
    ArrayList<Cards> aggregateHand = new ArrayList<>();


    Hand(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
        this.cardsInHand = cardsInHand;
        this.communityCards = communityCards;
    }

    ArrayList<ArrayList<Cards>> allPossibleHands = new ArrayList<>();

    public double calculateHandStrength() { //recurse through all possibliities; positive means player is stronger, negative means opponent is stronger

        ArrayList<Cards> testHand = new ArrayList<>();
        ArrayList<Cards> fullDeck = new ArrayList<>();
        ArrayList<Cards> playerComplimentaryDeck = new ArrayList<>();
        ArrayList<Cards> opponentComplimentaryDeck = new ArrayList<>();
        double averagePlayerStrength;
        double averageOpponentStrength;
        double strengthDifference;

        for (int i = 0; i < 4; i++) { //creating 52 card deck to subtract from
            for (int j = 0; j < 13; j++) {
                String suit = switch (i) {
                    case 0 -> "Spades";
                    case 1 -> "Hearts";
                    case 2 -> "Clubs";
                    case 3 -> "Diamonds";
                    default -> null;
                };

                int value = switch (j) {
                    case 0 -> 14; //ace
                    case 1 -> 2;
                    case 2 -> 3;
                    case 3 -> 4;
                    case 4 -> 5;
                    case 5 -> 6;
                    case 6 -> 7;
                    case 7 -> 8;
                    case 8 -> 9;
                    case 9 -> 10;
                    case 10 -> 11; //jack
                    case 11 -> 12; //queen
                    case 12 -> 13; //king
                    default -> 0;
                };

                Cards currentCard = new Cards(parent, suit, value);
                fullDeck.add(currentCard);
            }
        }

        playerComplimentaryDeck.addAll(fullDeck);
        playerComplimentaryDeck.removeAll(aggregateHand);
        makeCombinations(playerComplimentaryDeck, 7-aggregateHand.size());
        averagePlayerStrength = findAverageStrength(allPossibleHands);

        allPossibleHands.clear();
        opponentComplimentaryDeck.addAll(fullDeck);
        opponentComplimentaryDeck.removeAll(communityCards);
        makeCombinations(opponentComplimentaryDeck, 7-communityCards.size());
        averageOpponentStrength = findAverageStrength(allPossibleHands);

        strengthDifference = averagePlayerStrength - averageOpponentStrength;
        return strengthDifference;

    }

    public double findAverageStrength(ArrayList<ArrayList<Cards>> allPossibleHands) {

        double handsChecked = 0;
        double sumStrength = 0;
        double averageStrength;

        for (int i = 0; i < allPossibleHands.size(); i++) {
            ArrayList<Cards> cards = allPossibleHands.get(i);
            handsChecked++;
            if (isRoyalFlush(cards)) {
                sumStrength+=10;
            } else if (isStraightFlush(cards)) {
                sumStrength+=9;
            } else if (isFourOfAKind(cards)) {
                sumStrength+=8;
            } else if (isFullHouse(cards)) {
                sumStrength+=7;
            } else if (isFlush(cards)) {
                sumStrength+=6;
            } else if (isStraight(cards)) {
                sumStrength+=5;
            } else if (isThreeOfAKind(cards)) {
                sumStrength+=4;
            } else if (isTwoPair(cards)) {
                sumStrength+=3;
            } else if (isOnePair(cards)) {
                sumStrength+=2;
            } else { //high card
                sumStrength+=1;
            }
        }

        averageStrength = sumStrength/handsChecked;
        return averageStrength;

    }

    public void makeCombinations(ArrayList<Cards> cardsToChooseFrom, int setLength) { //from chat; check if it actually works
        makeCombinationsHelper(cardsToChooseFrom, setLength, 0, new ArrayList<>());
    }

    private void makeCombinationsHelper(ArrayList<Cards> cardsToChooseFrom, int setLength, int start, ArrayList<Cards> possibleHand) {
        if (possibleHand.size() == setLength) {
            allPossibleHands.add(new ArrayList<>(possibleHand)); // IMPORTANT: copy
            return;
        }

        for (int i = start; i < cardsToChooseFrom.size(); i++) {
            possibleHand.add(cardsToChooseFrom.get(i));
            makeCombinationsHelper(cardsToChooseFrom, setLength, i + 1, possibleHand);
            possibleHand.remove(possibleHand.size() - 1); // backtrack
        }
    }

    public static ArrayList<Cards> optimizeHand(ArrayList<Cards> finalHand) { //create the best possible hand with given information

        Collections.sort(finalHand);

        if (isRoyalFlush(finalHand)) {
            finalHand.removeIf(card -> card.getValue() < 10);
            ArrayList<Cards> aces = new ArrayList<>();
            String suit;
            for (Cards cards : finalHand) {
                if (cards.value == 14) {
                    aces.add(cards);
                }
            }
            if (aces.size() == 1) {
                suit = aces.get(0).getSuit();
            } else { //2 aces
                if (aces.get(0).getSuit() == finalHand.get(2).getSuit()) {
                    suit = aces.get(0).getSuit();
                } else { //the second ace's suit matches with the others
                    suit = aces.get(1).getSuit();
                }
            }
            finalHand.removeIf(card -> card.getSuit() != suit);
        } else if (isStraightFlush(finalHand)) {
            String suit;
            Cards highCard = finalHand.get(0);
            if (highCard.suit.equals(finalHand.get(1).suit) && highCard.suit.equals(finalHand.get(2).suit)) {
                suit = highCard.suit;
            } else if (finalHand.get(1).suit.equals(finalHand.get(2).suit) && finalHand.get(1).suit.equals(finalHand.get(3).suit)) {
                suit = finalHand.get(1).suit;
            } else {
                suit = finalHand.get(2).suit;
            }
            finalHand.removeIf(card -> card.getSuit() != suit);
            int size = finalHand.size();
            if (size == 7) {
                finalHand.remove(size-1);
                finalHand.remove(size-2);
            } else if (size == 6) {
                finalHand.remove(size-1);
            } //final case is 5, requiring no changes
        } else if (isFourOfAKind(finalHand)) {
            int matches = 0;
            int value;
            ArrayList<Cards> remaining3 = new ArrayList<>();
            remaining3.addAll(finalHand);
            for (int i = 1; i < 7; i++) {
                if (finalHand.get(0).value == finalHand.get(i).value) {
                    matches++;
                }
            }
            if (matches == 3) {
                value = finalHand.get(0).value;
            } else {
                matches = 0;
                for (int i = 2; i < 7; i++) {
                    if (finalHand.get(1).value == finalHand.get(i).value) {
                        matches++;
                    }
                }
                if (matches == 3) {
                    value = finalHand.get(1).value;
                } else {
                    matches = 0;
                    for (int i = 3; i < 7; i++) {
                        if (finalHand.get(2).value == finalHand.get(i).value) {
                            matches++;
                        }
                    }
                    if (matches == 3) {
                        value = finalHand.get(2).value;
                    } else {
                        value = finalHand.get(3).value;
                    }
                }
            }
            remaining3.removeIf(card -> card.getValue() == value);
            remaining3.remove(0);
            finalHand.removeAll(remaining3);
        } else if (isFullHouse(finalHand)) {
            int matches = 0;
            int value1; //value for the groupA
            ArrayList<Cards> remaining4 = new ArrayList<>();
            remaining4.addAll(finalHand);
            int value2; //value for the groupB
            for (int i = 1; i < 7; i++) {
                if (finalHand.get(0).value == finalHand.get(i).value) {
                    matches++;
                }
            }
            if (matches == 2) {
                value1 = finalHand.get(0).value;
            } else {
                matches = 0;
                for (int i = 2; i < 7; i++) {
                    if (finalHand.get(1).value == finalHand.get(i).value) {
                        matches++;
                    }
                }
                if (matches == 2) {
                    value1 = finalHand.get(1).value;
                } else {
                    matches = 0;
                    for (int i = 3; i < 7; i++) {
                        if (finalHand.get(2).value == finalHand.get(i).value) {
                            matches++;
                        }
                    }
                    if (matches == 2) {
                        value1 = finalHand.get(2).value;
                    } else {
                        matches = 0;
                        for (int i = 4; i < 7; i++) {
                            if (finalHand.get(3).value == finalHand.get(i).value) {
                                matches++;
                            }
                        }
                        if (matches == 2) {
                            value1 = finalHand.get(3).value;
                        } else {
                            matches = 0;
                            for (int i = 5; i < 7; i++) {
                                if (finalHand.get(4).value == finalHand.get(i).value) {
                                }
                            }
                            if (matches == 2) {
                                value1 = finalHand.get(4).value;
                            } else {
                                value1 = 0; //failsafe
                            }
                        }
                    }
                }
            }
            remaining4.removeIf(card -> card.getValue() == value1);
            matches = 0;
            for (int i = 1; i < 4; i++) {
                if (remaining4.get(0).value == finalHand.get(i).value) {
                    matches++;
                }
            }
            if (matches >= 1) {
                value2 = remaining4.get(0).value;
            } else {
                matches = 0;
                for (int i = 2; i < 4; i++) {
                    if (remaining4.get(1).value == finalHand.get(i).value) {
                        matches++;
                    }
                }
                if (matches >= 1) {
                    value2 = remaining4.get(1).value;
                } else {
                    matches = 0;
                    for (int i = 3; i < 4; i++) {
                        if (remaining4.get(2).value == finalHand.get(i).value) {
                            matches++;
                        }
                    }
                    if (matches >= 2) {
                        value2 = remaining4.get(2).value;
                    } else {
                        value2 = 0;
                    }
                }
            }
            finalHand.removeIf(card -> card.value != value1 && card.value != value2);
            if (finalHand.size() == 6) {
                finalHand.remove(5);
            }
        } else if (isFlush(finalHand)) {
            int matches = 0;
            String suit;
            for (int i = 1; i < 7; i++) {
                if (finalHand.get(0).value == finalHand.get(i).value) {
                    matches++;
                }
            }
            if (matches >= 5) {
                suit = finalHand.get(0).suit;
            } else {
                for (int i = 2; i < 7; i++) {
                    if (finalHand.get(1).value == finalHand.get(i).value) {
                        matches++;
                    }
                }
                if (matches >= 5) {
                    suit = finalHand.get(1).suit;
                } else {
                    for (int i = 3; i < 7; i++) {
                        if (finalHand.get(2).value == finalHand.get(i).value) {
                            matches++;
                        }
                    }
                    if (matches >= 5) {
                        suit = finalHand.get(2).suit;
                    } else {
                        suit = "";
                    }
                }
            }
            finalHand.removeIf(card -> card.suit != suit);
            if (finalHand.size() == 7) {
                finalHand.remove(6);
                finalHand.remove(5);
            } else if (finalHand.size() == 6) {
                finalHand.remove(5);
            }
        } else if (isStraight(finalHand)) {
            boolean segment1 = true;
            boolean segment2 = true;
            boolean segment3 = true;
            for (int i = 0; i < 5; i++) {
                if (finalHand.get(i).value != finalHand.get(i+1).value - 1) {
                    segment1 = false;
                }
            }
            for (int i = 1; i < 6; i++) {
                if (finalHand.get(i).value != finalHand.get(i+1).value - 1) {
                    segment2 = false;
                }
            }
            for (int i = 2; i < 7; i++) {
                if (finalHand.get(i).value != finalHand.get(i+1).value - 1) {
                    segment3 = false;
                }
            }
            if (segment1) {
                finalHand.remove(6);
                finalHand.remove(5);
            } else if (segment2) {
                finalHand.remove(6);
                finalHand.remove(0);
            } else {
                finalHand.remove(0);
                finalHand.remove(1);
            }
        } else if (isThreeOfAKind(finalHand)) {
            int matches = 0;
            int value;
            ArrayList<Cards> remaining4 = new ArrayList<>();
            remaining4.addAll(finalHand);
            for (int i = 1; i < 7; i++) {
                if (finalHand.get(0).value == finalHand.get(i).value) {
                    matches++;
                }
            }
            if (matches == 2) {
                value = finalHand.get(0).value;
            } else {
                matches = 0;
                for (int i = 2; i < 7; i++) {
                    if (finalHand.get(1).value == finalHand.get(i).value) {
                        matches++;
                    }
                }
                if (matches == 2) {
                    value = finalHand.get(1).value;
                } else {
                    matches = 0;
                    for (int i = 3; i < 7; i++) {
                        if (finalHand.get(2).value == finalHand.get(i).value) {
                            matches++;
                        }
                    }
                    if (matches == 2) {
                        value = finalHand.get(2).value;
                    } else {
                        matches = 0;
                        for (int i = 4; i < 7; i++) {
                            if (finalHand.get(3).value == finalHand.get(i).value) {
                                matches++;
                            }
                        }
                        if (matches == 2) {
                            value = finalHand.get(3).value;
                        } else {
                            value = 0;
                        }
                    }
                }
            }
            remaining4.removeIf(card -> card.getValue() == value);
            remaining4.remove(0);
            finalHand.removeAll(remaining4);
        } else if (isTwoPair(finalHand)) {
            int value1 = 0;
            int value2 = 0;
            boolean found1 = false;
            boolean found2 = false;
            ArrayList<Cards> remaining5 = new ArrayList<>();
            ArrayList<Cards> remaining3 = new ArrayList<>();
            remaining5.addAll(finalHand);
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (i != j && finalHand.get(i).value == finalHand.get(j).value) {
                        value1 = finalHand.get(i).value;
                        found1 = true;
                        break;
                    }
                }
                if (found1) break;
            }
            final int pairValue1 = value1;
            remaining5.removeIf(card -> card.value == pairValue1);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i != j && finalHand.get(i).value == finalHand.get(j).value) {
                        value2 = finalHand.get(i).value;
                        found2 = true;
                        break;
                    }
                }
                if (found2) break;
            }
            final int pairValue2 = value2;
            remaining3.removeIf(card -> card.value == pairValue2);
            remaining3.remove(0);
            finalHand.removeAll(remaining3);
        } else if (isOnePair(finalHand)) {
            int value = 0;
            boolean found = false;
            ArrayList<Cards> remaining5 = new ArrayList<>();
            remaining5.addAll(finalHand);
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (i != j && finalHand.get(i).value == finalHand.get(j).value) {
                        value = finalHand.get(i).value;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            for (int i = 0; i < 3; i++) {
                remaining5.remove(i);
            }
            finalHand.removeAll(remaining5); //remove the lowest 2
        } else { //high card
            int size = finalHand.size();
            finalHand.remove(size-1);
            finalHand.remove(size-2);
        }

        return finalHand;

    }

    static Cards straightFlushHighCard;

    private static boolean isRoyalFlush(ArrayList<Cards> cardsToCheck) { //each 'is' function will check if hand is possible, then build it if yes

        if (isStraightFlush(cardsToCheck) && straightFlushHighCard.getValue() == 14) {
            return true;
        }
        return false;

    }

    private static boolean isStraightFlush(ArrayList<Cards> cardsToCheck) { //checks for straight flush

        Collections.sort(cardsToCheck);

        for (int i = 0; i < 3; i++) {
            if (SHAC5(cardsToCheck.get(i), cardsToCheck.get(i+1), cardsToCheck.get(i+2), cardsToCheck.get(i+3), cardsToCheck.get(i+4))) {
                straightFlushHighCard = cardsToCheck.get(i);
                return true;
            }
        }
        return false;

    }

    private static boolean SHAC5(Cards card0, Cards card1, Cards card2, Cards card3, Cards card4) {
        if (SHAC2(card0, card1)) {
            if (SHAC2(card1, card2)) {
                if (SHAC2(card2, card3)) {
                    if (SHAC2(card3, card4)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean SHAC2(Cards card0, Cards card1) { //same hand and consecutive
        if (card0.value == card1.value + 1) {
            if (card0.suit == card1.suit) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFourOfAKind(ArrayList<Cards> cardsToCheck) {

        Collections.sort(cardsToCheck);

        for (int i = 0; i < 4; i++) {
            if (isEqual4(cardsToCheck.get(i), cardsToCheck.get(i+1), cardsToCheck.get(i+2), cardsToCheck.get(i+3))) {
                return true;
            }
        }
        return false;

    }

    private static boolean isEqual4(Cards card0, Cards card1, Cards card2, Cards card3) {

        if (isEqual2(card0, card1) && isEqual2(card2, card3) && isEqual2(card1, card2)) {
            return true;
        }
        return false;

    }

    private static boolean isEqual2(Cards card0, Cards card1) {

        if (card0.value == card1.value) {
            return true;
        }
        return false;

    }

    private static boolean isFullHouse(ArrayList<Cards> cardsToCheck) {

        Collections.sort(cardsToCheck);

        for (int i = 0; i < 5; i++) {
            if (isEqual2(cardsToCheck.get(i), cardsToCheck.get(i+1))) {
                if (isEqual3(cardsToCheck.get(i), cardsToCheck.get(i+1), cardsToCheck.get(i+2))) {
                    for (int j = i; j < i+4; i++) {
                        if (isEqual2(cardsToCheck.get(j), cardsToCheck.get(j+1))) {
                            return true;
                        }
                    }
                } else {
                    for (int j = i; j < 5; j++) {
                        if (isEqual3(cardsToCheck.get(j), cardsToCheck.get(j+1), cardsToCheck.get(j+2))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    private static boolean isEqual3(Cards card0, Cards card1, Cards card2) {

        if (isEqual2(card0, card1) && isEqual2(card1, card2)) {
            return true;
        }
        return false;

    }

    private static boolean isFlush(ArrayList<Cards> cardsToCheck) {

        Collections.sort(cardsToCheck);

        for (int i = 0; i < 3; i++) {
            if (sameSuit5(cardsToCheck.get(i), cardsToCheck.get(i+1), cardsToCheck.get(i+2), cardsToCheck.get(i+3), cardsToCheck.get(i+4))) {
                return true;
            }
        }
        return false;

    }

    private static boolean sameSuit5(Cards card0, Cards card1, Cards card2, Cards card3, Cards card4) {

        if (sameSuit2(card0, card1) && sameSuit2(card1, card2) && sameSuit2(card2, card3) && sameSuit2(card3, card4)) {
            return true;
        }
        return false;

    }

    private static boolean sameSuit2(Cards card0, Cards card1) {

        if (card0.suit.equals(card1.suit)) {
            return true;
        }
        return false;

    }

    private static boolean isStraight(ArrayList<Cards> cardsToCheck) {

        Collections.sort(cardsToCheck);

        for (int i = 0; i < 3; i++) {
            if (cardsToCheck.get(i).value == cardsToCheck.get(i+1).value + 1 &&
            cardsToCheck.get(i+1).value == cardsToCheck.get(i+2).value + 1 &&
            cardsToCheck.get(i+2).value == cardsToCheck.get(i+3).value + 1 &&
            cardsToCheck.get(i+3).value == cardsToCheck.get(i+4).value + 1) {
                return true;
            }
        }
        return false;

    }

    private static boolean isThreeOfAKind(ArrayList<Cards> cardsToCheck) {

        Collections.sort(cardsToCheck);

        for (int i = 0; i < 5; i++) {
            if (isEqual3(cardsToCheck.get(i), cardsToCheck.get(i+1), cardsToCheck.get(i+2))) {
                return true;
            }
        }
        return false;

    }

    private static boolean isTwoPair(ArrayList<Cards> cardsToCheck) {

        Collections.sort(cardsToCheck);

        for (int i = 0; i < 4; i++) {
            if (isEqual2(cardsToCheck.get(i), cardsToCheck.get(i+1))) {
                for (int j = i; j < 6; j++) {
                    if (isEqual2(cardsToCheck.get(j), cardsToCheck.get(j+1))) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private static boolean isOnePair(ArrayList<Cards> cardsToCheck) {

        Collections.sort(cardsToCheck);

        for (int i = 0; i < 6; i++){
            if (isEqual2(cardsToCheck.get(i), cardsToCheck.get(i+1))) {
                return true;
            }
        }
        return false;

    }

//    private boolean isHighCard() {
//
//        if  (true) { //placeholding
//            return true;
//        }
//        return false;
//
//    }

}


//class handComparison {
//
//    ArrayList<Cards> hand1 = new ArrayList<>();
//    ArrayList<Cards> hand2 = new ArrayList<>();
//    ArrayList<Cards> optimizedHand1 = new ArrayList<>();
//    ArrayList<Cards> optimizedHand2 = new ArrayList<>();
//    int hand1Strength;
//    int hand2Strength;
//    int hand1Tiebreaker;
//    int hand2Tiebreaker;
//
//    handComparison(ArrayList<Cards> hand1, ArrayList<Cards> hand2) {
//
//        this.hand1 = hand1;
//        this.hand2 = hand2;
//
//    }
//
//    public void compare() {
//
//        optimizedHand1 = Hand.optimizeHand(hand1);
//        optimizedHand2 = Hand.optimizeHand(hand2);
//
//    }
//
//    private int determineStrength() {
//
//    }
//
//}