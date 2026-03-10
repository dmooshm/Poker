import processing.core.PApplet;
//import static java.lang.Math.PI;

public class Cards implements Comparable<Cards> {

    PApplet parent;

    String suit; //options: "
    int value;
    static boolean faceUp = false;

    Cards(PApplet parent, String suit, int value) {
        this.suit = suit;
        this.value = value;
        this.parent = parent;
    }

    @Override
    public int compareTo(Cards other) {
        return Integer.compare(this.value, other.value);
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    float cardWidth = 60; //54 internal size
    float cardHeight = 86; //80 internal size

    public void drawFaceDown(int xpos, int ypos) {

        parent.fill(255, 50, 50); //building card
        parent.strokeWeight(3);
        parent.stroke(0);
        parent.rect(xpos - cardWidth/2, ypos - cardHeight/2, cardWidth, cardHeight, 10);


    }

    public void drawFaceUp(int xpos, int ypos) {

        parent.fill(255); //building card
        parent.strokeWeight(3);
        parent.stroke(0);
        parent.rect(xpos - cardWidth/2, ypos - cardHeight/2, cardWidth, cardHeight, 10);

        String suitLetter = "";
        String valueLetter = "";
        if (suit.equals("Clubs")) {
            parent.fill(0);
            suitLetter = "C";
        } else if (suit.equals("Spades")) {
            parent.fill(0);
            suitLetter = "S";
        } else if (suit.equals("Hearts")) {
            parent.fill(255, 0, 0);
            suitLetter = "H";
        } else if (suit.equals("Diamonds")) {
            parent.fill(255, 0, 0);
            suitLetter = "D";
        }
        switch (value) {
            case 2 -> valueLetter = "2";
            case 3 -> valueLetter = "3";
            case 4 -> valueLetter = "4";
            case 5 -> valueLetter = "5";
            case 6 -> valueLetter = "6";
            case 7 -> valueLetter = "7";
            case 8 -> valueLetter = "8";
            case 9 -> valueLetter = "9";
            case 10 -> valueLetter = "10";
            case 11 -> valueLetter = "J";
            case 12 -> valueLetter = "Q";
            case 13 -> valueLetter = "K";
            case 14 -> valueLetter = "A";
        }
        parent.textSize(20);
        parent.textAlign(parent.CENTER, parent.CENTER);
        parent.text(valueLetter, xpos, ypos - 10);
        parent.text(suitLetter, xpos, ypos + 10);



    }

//    public static void moveCard(int xpos, int ypos) {
//        this.xpos = xpos;
//        this.ypos = ypos;
//    }

}