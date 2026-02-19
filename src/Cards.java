import processing.core.PApplet;
//import static java.lang.Math.PI;

public class Cards {

    PApplet parent;

    static String suit; //options: "
    static int value;
    static boolean faceUp = false;

    Cards(PApplet parent, String suit, int value) {
        this.suit = suit;
        this.value = value;
        this.parent = parent;
    }

    float cardWidth = 60; //54 internal size
    float cardHeight = 86; //80 internal size

    public void drawFaceDown(float xpos, float ypos, float angle) {

        parent.pushMatrix();

        parent.translate(xpos, ypos);
        parent.rotate(parent.radians(angle));

        parent.fill(255, 50, 50); //building card
        parent.strokeWeight(6);
        parent.stroke(0);
        parent.rect(-cardWidth / 2, -cardHeight / 2, cardWidth, cardHeight, 10);

//        parent.stroke(255); //building pattern on card
//        parent.strokeWeight(2);
//        float xbase = -cardWidth/2 + 4.0f;
//        float ybase = -cardHeight/2 + 4.0f;
//        for (int xinstance = 0; xinstance < 3; xinstance++) {
//            for (int yinstance = 0; yinstance < 4; yinstance++) {
//                if (xinstance % 2 == 0) {
//                    parent.line((xbase + 18 * xinstance + 9.0f), (ybase + 20 * yinstance), (xbase + 18 * xinstance + 9.0f), (ybase + 20 * yinstance + 20 / 3f));
//                } else {
//                    parent.line((xbase + 18 * xinstance + 9.0f + 9.0f), (ybase + 20 * yinstance), (xbase + 18 * xinstance + 9.0f + 9.0f), (ybase + 20 * yinstance + 20 / 3f));
//                }
//            }
//        }

        parent.fill(255);
        parent.textAlign(parent.CENTER, parent.CENTER);
        parent.text("back", -cardWidth/2 + 20, -cardHeight/2 + 20);

        parent.popMatrix();


    }

    public void drawFaceUp(float xpos, float ypos, float angle) {

        parent.pushMatrix();

        parent.translate(xpos, ypos);
        parent.rotate(parent.radians(angle));

        parent.fill(255); //building card
        parent.strokeWeight(3);
        parent.stroke(0);
        parent.rect(-cardWidth / 2, -cardHeight / 2, cardWidth, cardHeight, 10);

        parent.fill(0);
        parent.textAlign(parent.CENTER, parent.CENTER);
        parent.text("front", -cardWidth/2 + 20, -cardHeight/2 + 20);

        parent.popMatrix();

    }

//    public static void moveCard(int xpos, int ypos) {
//        this.xpos = xpos;
//        this.ypos = ypos;
//    }

}