import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class Characters {

    String name;
    Cards card1;
    Cards card2;
    boolean folded = false;

    Characters(String name) {

        this.name = name;

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

    Player (String name) {
        super(name);
    }

    public static void takeTurn(ArrayList<Cards> cardsInPlay) {

    }

}


class NPC extends Characters {

    NPC (String name) {
        super(name);
        assignTell();
    }

    public static void takeTurn(ArrayList<Cards> cardsInPlay) {

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