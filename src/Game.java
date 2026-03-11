import processing.core.PApplet;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import static java.util.Collections.fill;

public class Game extends PApplet {

    UI UI;

    public static int numberOfPlayers;
    public static int startingBalance = 2000;
    public static ArrayList<Characters> characterList = new ArrayList<>();
    public static String currentScreen;
    public static ArrayList<Cards> cardsInPlay = new ArrayList<>();
    public static int currentTurn = 0;
    public static int minimumBet = 0;
    public static int pot;

    public static void main(String[] args) {
        PApplet.main("Game");
    }

    public void setup() {

        UI = new UI(this);
        System.out.println("program started");
        background(100);
        frameRate(60);
        createShuffledDeck();
        currentScreen = "openingScreen";
        numberOfPlayers = 5; //test (placeholder)
        startingBalance = 2000; //test, again
        try {
            UI.createPlayers();
            UI.dealCards();
        } catch (Exception e) {
            System.out.println("Error: could not create players");
            e.printStackTrace();
        }

    }

    public static int screenWidth = 800;
    public static int screenHeight = 800;

    public void settings() {
        size(screenWidth, screenHeight);
        smooth(8);  // Enable anti-aliasing
    }

    public void draw() {
        background(255);
//        UI.openingScreen();
//        UI.instructions();

        if (currentScreen.equals("openingScreen")) {
            UI.openingScreen();
        }
        if (currentScreen.equals("instructions")){
            UI.instructions();
        }
        if (currentScreen.equals("gameplay")) {
            UI.gameplay();
        }
    }

    public static ArrayList<Cards> cardDeck = new ArrayList<>();

    public void createShuffledDeck() {
        for (int i = 0; i < 4; i++) { //all suits
            for (int j = 0; j < 13; j++) { //all values
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

                Cards currentCard = new Cards(this, suit, value);
                cardDeck.add(currentCard);
            }
        }
        Collections.shuffle(cardDeck);  // Shuffle ONCE after all cards added
    }

}


class UI {

    PApplet parent;
    String playerName;
    ArrayList<String> randomNPCNames = new ArrayList<>();
    int cardIndex = 0;
    boolean revealCards = false;

    public UI(PApplet parent) {
        this.parent = parent;
    }

    public void openingScreen() {
        parent.fill(0);
        parent.textSize(55);
        parent.textAlign(parent.CENTER, parent.CENTER);
        parent.text("Welcome to Texas Hold'Em Poker!", Game.screenWidth/2, Game.screenHeight/2 - 250);

        Buttons instructionsButton = new Buttons(parent);

        instructionsButton.makeButton(Game.screenWidth/2, Game.screenHeight/2, 300, 100, 30, "instructions", 50);
        if (parent.mousePressed && instructionsButton.clickable) {
            Game.currentScreen = "instructions";
        }

        Buttons beginButton = new Buttons(parent);
        beginButton.makeButton(Game.screenWidth/2, Game.screenHeight/2 + 150, 300, 100, 30, "begin game", 50);
        if (parent.mousePressed && beginButton.clickable) {
            Game.currentScreen = "gameplay";
        }
//        parent.fill(255, 0, 0);
//        parent.circle(parent.mouseX, parent.mouseY, 30);


        //find way to input number of players

    }

    public void instructions() {
        parent.fill(0);
        parent.textSize(55);
        parent.textAlign(parent.CENTER, parent.CENTER);
        parent.text("Instructions!", Game.screenWidth/2, Game.screenHeight/2);

        Buttons homeButton = new Buttons(parent);
        homeButton.makeButton(Game.screenWidth/2, Game.screenHeight/2 + 200, 300, 100, 30, "home menu", 50);
        if (parent.mousePressed && homeButton.clickable) {
            Game.currentScreen = "openingScreen";
        }
    }

    public void gameplay() {

        parent.fill(0, 100, 0);
        parent.rect(Game.screenWidth/2 - 345, Game.screenHeight/2  - 275, 690, 350, 45);
        renderCards();
        renderNames();
//        renderChips();
        Game.characterList.get(Game.currentTurn).takeTurn();
        renderMinBetAndPot();

    }

    public void createPlayers() throws Exception {

        Hand playerHand = new Hand(new ArrayList<>(), new ArrayList<>());
        Game.characterList.add(new Player("You", playerHand, Game.startingBalance, parent));

        loadData("src/names.csv");
        Random randomNumber = new Random();

        for (int i = 1; i < Game.numberOfPlayers; i++) {

            int rand = randomNumber.nextInt(randomNPCNames.size());
            String randomName = randomNPCNames.get(rand);
            randomNPCNames.remove(randomName);
            Hand npcHand = new Hand(new ArrayList<>(), new ArrayList<>());
            Game.characterList.add(new NPC(randomName, npcHand, Game.startingBalance, parent));

        }
    }

    public void dealCards() {

        for (int i = 0; i < Game.numberOfPlayers; i++) {
            Cards card1 = Game.cardDeck.get(cardIndex);
            Cards card2 = Game.cardDeck.get(cardIndex + 1);
            Game.characterList.get(i).receiveCards(card1, card2);
            cardIndex += 2;
        }

    }

    public void renderCards() {

        revealCards = false; //test

        Game.characterList.get(0).card1.drawFaceUp(Game.screenWidth/2 + 30, Game.screenHeight/2+ 125);
        Game.characterList.get(0).card2.drawFaceUp(Game.screenWidth/2 - 30, Game.screenHeight/2 + 125);

        if (revealCards) {
            Game.characterList.get(1).card1.drawFaceUp(Game.screenWidth/2 + 30 + 250, Game.screenHeight/2 + 125);
            Game.characterList.get(1).card2.drawFaceUp(Game.screenWidth/2 - 30 + 250, Game.screenHeight/2 + 125);

            Game.characterList.get(4).card1.drawFaceUp(Game.screenWidth/2 + 30 - 250, Game.screenHeight/2 + 125);
            Game.characterList.get(4).card2.drawFaceUp(Game.screenWidth/2 - 30 - 250, Game.screenHeight/2 + 125);

            Game.characterList.get(2).card1.drawFaceUp(Game.screenWidth/2 + 30 + 150, Game.screenHeight/2 - 325);
            Game.characterList.get(2).card2.drawFaceUp(Game.screenWidth/2 - 30 + 150, Game.screenHeight/2 - 325);

            Game.characterList.get(3).card1.drawFaceUp(Game.screenWidth/2 + 30 - 150, Game.screenHeight/2 - 325);
            Game.characterList.get(3).card2.drawFaceUp(Game.screenWidth/2 - 30 - 150, Game.screenHeight/2 - 325);
        } else { //NPC cards face down
            Game.characterList.get(1).card1.drawFaceDown(Game.screenWidth/2 + 30 + 250, Game.screenHeight/2 + 125);
            Game.characterList.get(1).card2.drawFaceDown(Game.screenWidth/2 - 30 + 250, Game.screenHeight/2 + 125);

            Game.characterList.get(4).card1.drawFaceDown(Game.screenWidth/2 + 30 - 250, Game.screenHeight/2 + 125);
            Game.characterList.get(4).card2.drawFaceDown(Game.screenWidth/2 - 30 - 250, Game.screenHeight/2 + 125);

            Game.characterList.get(2).card1.drawFaceDown(Game.screenWidth/2 + 30 + 150, Game.screenHeight/2 - 325);
            Game.characterList.get(2).card2.drawFaceDown(Game.screenWidth/2 - 30 + 150, Game.screenHeight/2 - 325);

            Game.characterList.get(3).card1.drawFaceDown(Game.screenWidth/2 + 30 - 150, Game.screenHeight/2 - 325);
            Game.characterList.get(3).card2.drawFaceDown(Game.screenWidth/2 - 30 - 150, Game.screenHeight/2 - 325);
        }

    }

    public void renderNames() {

        parent.fill(0);
        parent.textSize(30);
        parent.textAlign(parent.CENTER, parent.CENTER);

        parent.text("You", Game.screenWidth/2, Game.screenHeight/2 + 185);
        parent.text(Game.characterList.get(1).name, Game.screenWidth/2 + 250, Game.screenHeight/2 + 185);
        parent.text(Game.characterList.get(4).name, Game.screenWidth/2 - 250, Game.screenHeight/2 + 185);
        parent.text(Game.characterList.get(2).name, Game.screenWidth/2 + 150, Game.screenHeight/2 - 385);
        parent.text(Game.characterList.get(3).name, Game.screenWidth/2 - 150, Game.screenHeight/2 - 385);
//        parent.text(1, Game.screenWidth/2 + 250, Game.screenHeight/2 + 185);
//        parent.text(4, Game.screenWidth/2 - 250, Game.screenHeight/2 + 185);
//        parent.text(2, Game.screenWidth/2 + 150, Game.screenHeight/2 - 385);
//        parent.text(3, Game.screenWidth/2 - 150, Game.screenHeight/2 - 385);

    }

    public void renderMinBetAndPot() {

        parent.fill(255);
        parent.textSize(20);
        parent.textAlign(parent.LEFT, parent.CENTER);

        parent.text("Pot: " + Game.pot, Game.screenWidth/2 - 325, Game.screenHeight/2 - 215);
        parent.text("Minimum Bet: " + Game.minimumBet, Game.screenWidth/2 - 325, Game.screenHeight/2 - 240);
//        parent.text("Balance: " + Game.characterList.get(Game.currentTurn).balance, Game.screenWidth/2 - 325, Game.screenHeight/2 - 240);

    }

    public void renderChips() {

        Chips fiveHundred = new Chips(parent);
        fiveHundred.makeChips(200, 700, 500);
        if (parent.mousePressed && fiveHundred.clickable) {
            if (Game.characterList.get(Game.currentTurn).bet + 500 <= Game.characterList.get(Game.currentTurn).balance) {
                Game.characterList.get(Game.currentTurn).bet += 500;
                parent.delay(200);
            }
        }

        Chips oneHundred = new Chips(parent);
        oneHundred.makeChips(300, 700, 100);
        if (parent.mousePressed && oneHundred.clickable) {
            if (Game.characterList.get(Game.currentTurn).bet + 100 <= Game.characterList.get(Game.currentTurn).balance) {
                Game.characterList.get(Game.currentTurn).bet += 100;
                parent.delay(200);
            }
        }

        Chips fifty = new Chips(parent);
        fifty.makeChips(400, 700, 50);
        if (parent.mousePressed && fifty.clickable) {
            if (Game.characterList.get(Game.currentTurn).bet + 50 <= Game.characterList.get(Game.currentTurn).balance) {
                Game.characterList.get(Game.currentTurn).bet += 50;
                parent.delay(200);
            }
        }

        Chips ten = new Chips(parent);
        ten.makeChips(500, 700, 10);
        if (parent.mousePressed && ten.clickable) {
            if (Game.characterList.get(Game.currentTurn).bet + 10 <= Game.characterList.get(Game.currentTurn).balance) {
                Game.characterList.get(Game.currentTurn).bet += 10;
                parent.delay(200);
            }
        }

        Chips five = new Chips(parent);
        five.makeChips(600, 700, 5);
        if (parent.mousePressed && five.clickable) {
            if (Game.characterList.get(Game.currentTurn).bet + 5 <= Game.characterList.get(Game.currentTurn).balance) {
                Game.characterList.get(Game.currentTurn).bet += 5;
                parent.delay(200);
            }
        }

    }

    public void renderBetAndPot() {

    }

    public void loadData(String filename) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();  // Read the single line

        String[] names = line.split(", ");
        for (String name : names) {
            randomNPCNames.add(name);
        }

        br.close();

    }

    public void preFlop() {



    }

    public void flop() {



    }

    public void turn() {



    }

    public void river() {



    }

    public int compare2Hands(ArrayList<Card> hand1, ArrayList<Card> hand2) {

        return 0; //placeholder

    }

    public int compare5Hands(ArrayList<Cards> hand0, ArrayList<Card> hand1, ArrayList<Card> hand2, ArrayList<Card> hand3, ArrayList<Card> hand4) {

        return 0; //placeholder

    }

}

class Buttons {

    PApplet parent;
    public Buttons(PApplet parent) {
        this.parent = parent;
    }

    boolean clickable = false;

    public void makeButton(float xpos, float ypos, float width, float height, float fillet, String text, int fontSize) {

//        parent.fill (20);

        if (parent.mouseX >= xpos - width/2 && parent.mouseX <= xpos + width/2 && parent.mouseY >= ypos - height/2 && parent.mouseY <= ypos + height/2) {
            parent.fill (150);
            clickable = true;
        } else {
            parent.fill (230);
            clickable = false;
        }
        parent.rect(xpos - width / 2, ypos - height / 2, width, height, fillet);
        parent.textAlign(parent.CENTER, parent.CENTER);
        parent.fill(0);
        parent.textSize(fontSize);
        parent.text(text, xpos - width / 2, ypos - height / 2, width, height);

    }

}