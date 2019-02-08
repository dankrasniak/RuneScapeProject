package runeScapeProject;

import runeScapeProject.observer.Observable;
import runeScapeProject.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class View extends JFrame implements Runnable, Observable {

    // region CONSTANTS
    private static final int WAIT_TIME = 25;
    private static final int GAME_WIDTH = 740;
    private static final int GAME_HEIGHT = 500;
    private static final int FIELD_SIZE = 30;
    private final Rectangle CAPTURE_RECT = new Rectangle(-8, 0, GAME_WIDTH, GAME_HEIGHT);

    private BufferedImage cap;
    private Robot robot;
    // endregion

    private boolean RUNNING = true;

    public void initialize(Controller controller) {

        // Listeners
        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);
        this.addKeyListener(controller);

        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        this.setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setLocation(CAPTURE_RECT.x + CAPTURE_RECT.width + 40, CAPTURE_RECT.y);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        new Thread(this).start();
    }

    public void close() {
        RUNNING = false;
        System.exit(0);
    }

    public void paint(Graphics g) {
        cap = robot.createScreenCapture(CAPTURE_RECT);
        notifyObservers();
        g.drawImage(cap, 0, 0, null);
    }

    public void drawRect(Color color, int posX, int posY) {
        Graphics g = cap.getGraphics();
        g.setColor(color);
        g.drawRect(posX-FIELD_SIZE/2, posY-FIELD_SIZE/2, FIELD_SIZE, FIELD_SIZE);
    }

    public void drawCustomRectangle(Color color, int startX, int startY, int endX, int endY) {
        Graphics g = cap.getGraphics();
        g.setColor(color);
        g.drawRect(startX, startY, endX, endY);
    }

    public BufferedImage getSubImage(int posX, int posY) {
        return robot.createScreenCapture(CAPTURE_RECT)
                .getSubimage(posX-FIELD_SIZE/2, posY-FIELD_SIZE/2, FIELD_SIZE, FIELD_SIZE);
    }

    public void run() {
        while (RUNNING) {
            this.requestFocusInWindow();
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    // region OBSERVABLE
    private final LinkedList<Observer> observers = new LinkedList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        observers.forEach(o -> o.update(cap));
    }
    // endregion
}
