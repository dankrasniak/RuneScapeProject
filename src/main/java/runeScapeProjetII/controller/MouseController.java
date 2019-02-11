package runeScapeProjetII.controller;

import runeScapeProjetII.observer.Observer;
import runeScapeProjetII.view.MirrorScreenPanel;
import runeScapeProjetII.view.drawable.SquareFrame;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener, Observer {

    private MirrorScreenPanel view;

    private int mouseX;
    private int mouseY;
    private int FIELD_SIZE;

    public MouseController(int fieldSize) {
        this.FIELD_SIZE = fieldSize;
    }

    public void initialize(MirrorScreenPanel view) {
        this.view = view;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void update(Object arg) {
        view.drawElement(new SquareFrame(Color.cyan, mouseX, mouseY, FIELD_SIZE));
    }

}
