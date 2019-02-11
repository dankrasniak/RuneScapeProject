package runeScapeProjetII.view;

import runeScapeProjetII.controller.KeyboardController;
import runeScapeProjetII.controller.LogicController;
import runeScapeProjetII.controller.MouseController;

import javax.swing.*;
import java.awt.*;

public class MainWindowView extends JFrame implements Runnable {

    // region CONSTANTS
    private static final int WAIT_TIME = 25;
    private static final int GAME_WIDTH = 740;
    private static final int GAME_HEIGHT = 500;
    private final Rectangle CAPTURE_RECT = new Rectangle(-8, 0, GAME_WIDTH, GAME_HEIGHT);
    // endregion

    private boolean RUNNING = true;

    public void initialize(MouseController mouseController, KeyboardController keyboardController, LogicController logicController) {
        this.add(new MirrorScreenPanel(mouseController, 0, 0, GAME_WIDTH, GAME_HEIGHT, logicController));

        this.addKeyListener(keyboardController);

        this.setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setLocation(CAPTURE_RECT.x + CAPTURE_RECT.width + 40, CAPTURE_RECT.y);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        new Thread(this).start();
    }

    private void applicationLoop() {
        this.requestFocusInWindow();
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    //region mandatory
    public void run() {
        while (RUNNING)
            applicationLoop();
    }

    public void close() {
        RUNNING = false;
        System.exit(0);
    }
    //endregion
}
