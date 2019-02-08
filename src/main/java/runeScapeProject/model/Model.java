package runeScapeProject.model;

import runeScapeProject.observer.Observer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Model extends Observer {

    void mouseReleased(MouseEvent e);

    void keyReleased(KeyEvent e, int posX, int posY);
}
