import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

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


//    public static void makeHand(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) { //given information, returns best possible hand type
//
//
//        ArrayList<Cards> allCards = new ArrayList<>();
//        allCards.addAll(cardsInHand);
//        allCards.addAll(communityCards);
//
//        boolean flushAndStraight = isFlushAndStraight(cardsInHand, communityCards);
////        boolean straight = isStraight(cardsInHand, communityCards);
//
//        if (flushAndStraight){
//            if (cardsInHand.get(0).value >= 10 && cardsInHand.get(1).value >= 10) { //royal flush
//                for (Cards card : cardsInHand) {
//                    if (card.value == 13) {
//                        //create royal flush hand
//                    } else { //straight flush
//
//                    }
//                }
//            } else { //straight flush
//
//            }
//        } if ()
//
//
//    }
//
//    private static boolean isFlushAndStraight(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//
//    }
//
//    private static boolean isStraight(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//
//    }
//
//    private static void compareHands(ArrayList<Cards> hand1, ArrayList<Cards> hand2) {
//
//    }
//
//    private void buildRoyalFlush(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 10;
//    }
//
//    private void buildStraightFlush(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 9;
//    }
//
//    private void buildfFourOfAKind(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 8;
//    }
//
//    private void buildFullHouse(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 7;
//    }
//
//    private void buildFlush(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 6;
//    }
//
//    private void buildStraight(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 5;
//    }
//
//    private void buildThreeOfAKind(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 4;
//    }
//
//    private void buildTwoPair(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 3;
//    }
//
//    private void buildOnePair(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 2;
//    }
//
//    private void buildHighCard(ArrayList<Cards> cardsInHand, ArrayList<Cards> communityCards) {
//        handType = 1;
//    }
//
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
    ArrayList<Cards> optimizedHand = new ArrayList<>();
    int handStrength;
    int tiebreakerStrength;
    ArrayList<Integer> handStrengthCollection = new ArrayList<>();
    ArrayList<Integer> tiebreakerCollection = new ArrayList<>();

    Hand(ArrayList<Cards> cardsInHand,  ArrayList<Cards> communityCards) {
        this.cardsInHand = cardsInHand;
        this.communityCards = communityCards;
    }

    public void calculateHandStrength() { //recurse through all possibliities

        //for each personal unknown, optimize and add to handStrength collection
        //for each opponent unknown, do the same

        //average handStrength of both; use average of tiebreaker if not

    }

    private void optimizeHand() { //create the best possible hand with given information



    }

    private boolean isRoyalFlush() { //each 'is' function will check if hand is possible, then build it if yes

        if (true) { //placeholding
            handStrength = 10;
            return true;
        }
        return false;

    }

    private boolean isStraightFlush() { //checks for straight flush

        if (true) { //placeholding
            handStrength = 9;
            return true;
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

        if (true) { //placeholding
            handStrength = 5;
            return true;
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

    handComparison(ArrayList<Cards> hand1, ArrayList<Cards> hand2) {

        this.hand1 = hand1;
        this.hand2 = hand2;

    }

    public void compare() {

    }

}