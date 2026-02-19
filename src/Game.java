import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import static java.util.Collections.fill;

public class Game extends PApplet {

    public static int numberOfPlayers;
    public static ArrayList<Characters> characterList = new ArrayList<>();
    public static String currentScreen;
    public static ArrayList<Cards> cardsInPlay = new ArrayList<>();

    public static void main(String[] args) {
        PApplet.main("Game");
    }

    public void setup() {

        System.out.println("program started");
        UI UI = new UI(this);
        background(100);
        frameRate(60);
        createShuffledDeck();
        currentScreen = "openingScreen";
        numberOfPlayers = 5; //test (placeholder)
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
        UI UI = new UI(this);
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
                    case 0 -> 1; //ace
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

    public UI(PApplet parent) {
        this.parent = parent;
    }

    public void openingScreen() {
//        parent.fill(0);
//        parent.textSize(128);
//        parent.text("word", 40, 120);

        Buttons instructionsButton = new Buttons(parent);

        instructionsButton.makeButton(Game.screenWidth/2, Game.screenHeight/2, 400, 200, 30, "hello");
        if (parent.mousePressed && instructionsButton.clickable) {
            Game.currentScreen = "instructions";
        }


        //find way to input number of players

    }

    public void instructions() {
        parent.text("Hello", 400, 400);
    }

    public void gameplay() {

        Cards Cards = new Cards(parent,"spades",1);

//        Cards.drawFaceDown(400, 400, 45);
        Cards.drawFaceUp(400, 400, 45);
        renderCards();

    }

    public void createPlayers() throws Exception {

        Game.characterList.add(new Player(playerName));

        loadData("src/names.csv");
        Random randomNumber = new Random();

        for (int i = 1; i < Game.numberOfPlayers; i++) {
            int rand = randomNumber.nextInt(randomNPCNames.size());
            String randomName = randomNPCNames.get(rand);
            randomNPCNames.remove(randomName);
            Game.characterList.add(new NPC(randomName));
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

        for (int i = 0; i < Game.numberOfPlayers; i++) {
//            Game.characterList.get(i).card1.drawFaceUp(400, 400, 0);
//            Game.characterList.get(i).card2.drawFaceUp(400, 300, 0);

            Game.characterList.get(i).card1.drawFaceDown(400, 400, 0);
            Game.characterList.get(i).card2.drawFaceDown(400, 300, 0);
        }

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

    public void flop() {

        Cards card1 = Game.cardDeck.get(cardIndex);
        Cards card2 = Game.cardDeck.get(cardIndex + 1);
        Cards card3 = Game.cardDeck.get(cardIndex + 2);
        cardIndex += 3;

        //find way of taking turns

    }

}

class Buttons {

    PApplet parent;
    public Buttons(PApplet parent) {
        this.parent = parent;
    }

    boolean clickable = false;

    public void makeButton(float xpos, float ypos, float width, float height, float fillet, String text) {

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
        parent.textSize(50);
        parent.text(text, xpos - width / 2, ypos - height / 2, width, height);

    }

}