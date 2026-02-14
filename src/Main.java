import processing.core.PApplet;

public class Main extends PApplet {

    public void settings() {
        size(400, 400);  // Window size: 400x400 pixels
    }

    public void setup() {
        background(255);  // White background
    }

    public void draw() {
        // Draw a circle that follows your mouse
        fill(255, 0, 0);  // Red color
        ellipse(mouseX, mouseY, 50, 50);
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}