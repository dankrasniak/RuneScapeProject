package runeScapeProjetII.view.drawable;

import java.awt.*;

public class SquareFrame implements Drawable {

    private Color color;
    private int posX;
    private int posY;
    private int fieldSize;

    public SquareFrame(Color color, int posX, int posY, int fieldSize)  {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.fieldSize = fieldSize;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(posX-fieldSize/2, posY-fieldSize/2, fieldSize, fieldSize);
    }
}
