package runeScapeProject.model;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;
import runeScapeProject.Config;
import runeScapeProject.View;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class ChickenClassifierModel implements Model {

    // region CONSTANTS
    private final Size MIN = new Size(30, 30);
    private final Size MAX = new Size(50, 50);
    // endregion

    private LinkedList<Point> pois = new LinkedList<>();

    // region STATE VARIABLES
    private boolean combatAllowed = true;
    private boolean combatFinished = true;
    private boolean enableSearchEngine = true;
    // endregion

    // region CONSTRUCTOR CONSTANTS
    private final View view;
    private final CascadeClassifier classifier;
    private Robot robot;
    // endregion
    public ChickenClassifierModel(View view, CascadeClassifier classifier) {
        this.view = view;
        this.classifier = classifier;
        try { this.robot = new Robot(); } catch (AWTException e1) { e1.printStackTrace(); }
        assert robot != null;
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            FileManagement.save(view.getSubImage(e.getX(), e.getY()), true);
        else
            FileManagement.save(view.getSubImage(e.getX(), e.getY()), false);
    }

    public void keyReleased(KeyEvent e, int posX, int posY) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_SPACE:
                combatAllowed = !combatAllowed;
            break;

            case KeyEvent.VK_S:
                enableSearchEngine = !enableSearchEngine;
            break;
        }
    }

    public void update(Object arg) {
        if (enableSearchEngine) {
            pois = findChickens((BufferedImage) arg);
            pois.forEach(c -> view.drawRect(Color.red, c.x, c.y));
        }

        if (combatAllowed && enableSearchEngine && !pois.isEmpty() && combatFinished) {

            int whichChicken = new Random().nextInt(pois.size());
            int posX = pois.get(whichChicken).x;
            int posY = pois.get(whichChicken).y;

            robot.mouseMove(posX, posY);
            try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);

            combatFinished = false;
            TimerTask task = new TimerTask() { public void run() {

                int newX = (Config.SUBIMAGE_END_X - Config.SUBIMAGE_START_X)/2;
                int newY = (Config.SUBIMAGE_END_Y - Config.SUBIMAGE_START_Y)/2;

//                if (posX > newX)
//                    newX = posX - newX;
//                else newX += posX;
//
//                if (posY > newY)
//                    newY = posY - newY;
//                else newY += posY;

                newX = 2*Config.SUBIMAGE_START_X + 2*newX - posX;
                newY = 2*Config.SUBIMAGE_START_Y + 2*newY - posY;

                robot.mouseMove(newX, newY);
                try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }

                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

                combatFinished = true;
            }};
            new Timer("Timer")
                    .schedule(task, Config.NEXT_CLICK_DELAY + (long)(new Random().nextGaussian()*100.));
        }
    }

    private LinkedList<Point> findChickens(BufferedImage img) {

        if (Config.SHOW_GRID_RECTANGLE)
            view.drawCustomRectangle(Color.red,
                    Config.SUBIMAGE_START_X, Config.SUBIMAGE_START_Y,
                    Config.SUBIMAGE_END_X, Config.SUBIMAGE_END_Y);

        MatOfRect chickenDetections = new MatOfRect();
        Mat myImg = img2Mat(img.getSubimage(
                Config.SUBIMAGE_START_X, Config.SUBIMAGE_START_Y,
                Config.SUBIMAGE_END_X, Config.SUBIMAGE_END_Y));

        classifier.detectMultiScale(myImg, chickenDetections, 1.6, 5, 0, MIN, MAX);
        return chickenDetections.toList().stream()
                .map(p -> new Point(p.x + Config.SUBIMAGE_START_X + Config.OFFSET,
                        p.y + Config.SUBIMAGE_START_Y + Config.OFFSET))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static Mat img2Mat(BufferedImage in) {
        Mat out;
        byte[] data;
        int r, g, b;

        if (in.getType() == BufferedImage.TYPE_INT_RGB) {
            out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC3);
            data = new byte[in.getHeight() * in.getWidth() * (int) out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
            for (int i = 0; i < dataBuff.length; i++) {
                data[i * 3] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                data[i * 3 + 2] = (byte) ((dataBuff[i] >> 0) & 0xFF);
            }
        } else {
            out = new Mat(240, 320, CvType.CV_8UC1);
            data = new byte[320 * 240 * (int) out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, 320, 240, null, 0, 320);
            for (int i = 0; i < dataBuff.length; i++) {
                r = (byte) ((dataBuff[i] >> 16) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i] >> 0) & 0xFF);
                data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
            }
        }
        out.put(0, 0, data);
        return out;
    }
}
