import processing.core.PApplet;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.util.Collections;

public class Characters {

    String name;
    Hand hand;
    Cards card1;
    Cards card2;
    boolean folded = false;

    Characters(String name, Hand hand) {

        this.name = name;
        this.hand = hand;

    }

    public void receiveCards(Cards card1, Cards card2) {

        this.card1 = card1;
        this.card2 = card2;

//        if (playerNumber == 0) {
//            card1.drawFaceUp(Game.screenWidth/2-40, Game.screenHeight/2, 0);
//            card2.drawFaceUp(Game.screenWidth/2+40, Game.screenHeight/2, 0);
//        } else {
////            card1.drawFaceDown();
////            card2.drawFaceDown();
//        }

        //playerNumebr is indexed beginning at zero

    }

}


class Player extends Characters{

    Player(String name, Hand hand) {
        super(name, hand);
    }

    public static void takeTurn(ArrayList<Cards> cardsInPlay) {

    }

}


class NPC extends Characters {

    NPC (String name, Hand hand) {
        super(name, hand);
    }

    public static void takeTurn(ArrayList<Cards> cardsInPlay, ArrayList<Cards> communityCards) {

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

    ArrayList<Cards> cardsInHand = new ArrayList<>();
    ArrayList<Cards> opponentCardsInHand = new ArrayList<>();
    ArrayList<Cards> communityCards = new ArrayList<>();
    ArrayList<Cards> aggregateHand = new ArrayList<>();
    ArrayList<Cards> opponentAggregateHand = new ArrayList<>();
    ArrayList<Cards> optimizedHand = new ArrayList<>();
    int handStrength;
    int tiebreakerStrength;
    ArrayList<Integer> handStrengthCollection = new ArrayList<>();
    ArrayList<Integer> tiebreakerCollection = new ArrayList<>();
    ArrayList<Cards> orderedHand = new ArrayList<>();


    Hand(ArrayList<Cards> cardsInHand,  ArrayList<Cards> communityCards) {
        this.cardsInHand = cardsInHand;
        this.communityCards = communityCards;
        aggregateHand.addAll(communityCards);
        aggregateHand.addAll(cardsInHand);
        opponentAggregateHand.addAll(communityCards);
        opponentAggregateHand.addAll(opponentCardsInHand);
        orderedHand.addAll(aggregateHand);
    }

    public void calculateHandStrength() { //recurse through all possibliities

        //for each personal unknown, optimize and add to handStrength collection
        //for each opponent unknown, do the same

        //average handStrength of both; use average of tiebreaker if not

    }

    private ArrayList<Cards> optimizeHand() { //create the best possible hand with given information

        ArrayList<Cards> finalHand = new ArrayList<>();
        finalHand.addAll(aggregateHand);
        Collections.sort(finalHand);

        if (isRoyalFlush()) {
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
        } else if (isStraightFlush()) {
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
        } else if (isFourOfAKind()) {
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
        } else if (isFullHouse()) {
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
        } else if (isFlush()) {
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
        } else if (isStraight()) {
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
        } else if (isThreeOfAKind()) {
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
        } else if (isTwoPair()) {
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
        } else if (isOnePair()) {
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

    Cards straightFlushHighCard;

    private boolean isRoyalFlush() { //each 'is' function will check if hand is possible, then build it if yes

        if (isStraightFlush() && straightFlushHighCard.getValue() == 14) {
            return true;
        }
        return false;

    }

    private boolean isStraightFlush() { //checks for straight flush

        Collections.sort(orderedHand);

        for (int i = 0; i < 3; i++) {
            if (SHAC5(orderedHand.get(i), orderedHand.get(i+1), orderedHand.get(i+2), orderedHand.get(i+3), orderedHand.get(i+4))) {
                straightFlushHighCard = orderedHand.get(i);
                return true;
            }
        }
        return false;

    }

    private boolean SHAC5(Cards card0, Cards card1, Cards card2, Cards card3, Cards card4) {
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

    private boolean SHAC2(Cards card0, Cards card1) { //same hand and consecutive
        if (card0.value == card1.value + 1) {
            if (card0.suit == card1.suit) {
                return true;
            }
        }
        return false;
    }

    private boolean isFourOfAKind() {

        if (true) { //placeholding
            handStrength = 8;
            return true;
        }
        return false;

    }

    private boolean isFullHouse() {

        if (true) { //placeholding
            handStrength = 7;
            return true;
        }
        return false;

    }

    private boolean isFlush() {

        if (true) { //placeholding
            handStrength = 6;
            return true;
        }
        return false;

    }

    private boolean isStraight() {

        Collections.sort(orderedHand);

        for (int i = 0; i < 3; i++) {
            if (orderedHand.get(i).value == orderedHand.get(i+1).value + 1 &&
            orderedHand.get(i+1).value == orderedHand.get(i+2).value + 1 &&
            orderedHand.get(i+2).value == orderedHand.get(i+3).value + 1 &&
            orderedHand.get(i+3).value == orderedHand.get(i+4).value + 1) {
                return true;
            }
        }
        return false;

    }

    private boolean isThreeOfAKind() {

        if (true) { //placeholding
            handStrength = 4;
            return true;
        }
        return false;

    }

    private boolean isTwoPair() {

        if (true) { //placeholding
            handStrength = 3;
            return true;
        }
        return false;

    }

    private boolean isOnePair() {

        if (true) { //placeholding
            handStrength = 2;
            return true;
        }
        return false;

    }

    private boolean isHighCard() {

        if  (true) { //placeholding
            handStrength = 1;
            return true;
        }
        return false;

    }

}


class handComparison {

    ArrayList<Cards> hand1 = new ArrayList<>();
    ArrayList<Cards> hand2 = new ArrayList<>();
    int hand1Strength;
    int hand2Strength;

    handComparison(ArrayList<Cards> hand1, ArrayList<Cards> hand2) {

        this.hand1 = hand1;
        this.hand2 = hand2;

    }

    public void compare() {



    }

}