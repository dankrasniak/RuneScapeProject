package runeScapeProjetII.view;

import runeScapeProjetII.controller.MouseController;
import runeScapeProjetII.view.drawable.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MirrorScreenPanel extends JPanel {

    private Robot robot;
    private BufferedImage gameMirror;
    private final Rectangle CAPTURE_RECT;

    MirrorScreenPanel(MouseController mouseController, int x, int y, int width, int height) {
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
    }

    public void paint(Graphics g) {
        gameMirror = robot.createScreenCapture(CAPTURE_RECT);
        g.drawImage(gameMirror, 0, 0, null);
    }

    public void drawElement(Drawable drawable) {
        Graphics g = gameMirror.getGraphics();
        drawable.draw(g);
    }
}
