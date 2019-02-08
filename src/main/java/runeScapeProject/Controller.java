package runeScapeProject;

import runeScapeProject.model.Model;
import runeScapeProject.observer.Observer;

import java.awt.*;
import java.awt.event.*;

public class Controller implements MouseMotionListener, MouseListener, KeyListener, Observer {

    // region CONSTANTS
    private final View view;
    private final Model model;
    // endregion

    private int mouseX;
    private int mouseY;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public void update(Object arg) {
        view.drawRect(Color.cyan, mouseX, mouseY);
    }

    // region MOUSE_LISTENER
    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        model.mouseReleased(e);
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
    // endregion

    // region KEY_LISTENER
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            view.close();
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            model.keyReleased(e, mouseX, mouseY);
        if (e.getKeyCode() == KeyEvent.VK_S)
            model.keyReleased(e, mouseX, mouseY);
    }
    // endregion
}
