import processing.core.PApplet;

public class Chips {

    PApplet parent;
    public Chips(PApplet parent) {
        this.parent = parent;
    }

    boolean clickable = false;
    int radius = 80;
    String valueLetter = "";

    public void makeChips(int xpos, int ypos, int value) {

        int r = 0;
        int g = 0;
        int b = 0;
        int k = 0;

        if (parent.dist(parent.mouseX, parent.mouseY, xpos, ypos) < radius/2) {
            r = -50;
            g = -50;
            b = -50;
            k = 125;
            clickable = true;
        } else {
            r = 0;
            g = 0;
            b = 0;
            k = 200;
            clickable = false;
        }
        if (value == 500) { //yellow
            r += 255;
            g += 255;
            b = 0;
        } else if (value == 100) { //blue
            r = 0;
            g = 0;
            b += 255;
        } else if (value == 50) { //orange
            r += 255;
            g += 165;
            b = 0;
        } else if (value == 10) { //green
            r = 0;
            g += 255;
            b = 0;
        } else if (value == 5) { //red
            r += 255;
            g = 0;
            b = 0;
        }
        parent.fill(r, g, b);
        parent.circle(xpos, ypos, radius);
        parent.fill(k);
        parent.circle(xpos, ypos, radius - 12);

        parent.fill(0);
        parent.textAlign(parent.CENTER, parent.CENTER);
        parent.textSize(35);
        parent.text(Integer.toString(value), xpos, ypos);

    }

}
