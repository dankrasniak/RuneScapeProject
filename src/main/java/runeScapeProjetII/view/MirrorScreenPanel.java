package runeScapeProjetII.view;

import runeScapeProjetII.controller.logic.LogicController;
import runeScapeProjetII.controller.MouseController;
import runeScapeProjetII.observer.Observable;
import runeScapeProjetII.observer.Observer;
import runeScapeProjetII.view.drawable.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class MirrorScreenPanel extends JPanel implements Observable {

    private Robot robot;
    private BufferedImage gameMirror;
    private final Rectangle CAPTURE_RECT;

    MirrorScreenPanel(MouseController mouseController, int x, int y, int width, int height, LogicController logicController) {
        this.addMouseListener(mouseController);
        this.addMouseMotionListener(mouseController);

        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        CAPTURE_RECT = new Rectangle(x, y, width, height);

        this.setSize(new Dimension(width, height));
        this.setVisible(true);

        mouseController.initialize(this);
        this.addObserver(mouseController);
        this.addObserver(logicController);
    }

    public void paint(Graphics g) {
        gameMirror = robot.createScreenCapture(CAPTURE_RECT);
        notifyObservers();
        g.drawImage(gameMirror, 0, 0, null);
    }

    public void drawElement(Drawable drawable) {
        Graphics g = gameMirror.getGraphics();
        drawable.draw(g);
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
        observers.forEach(o -> o.update(gameMirror));
    }
    // endregion
}
