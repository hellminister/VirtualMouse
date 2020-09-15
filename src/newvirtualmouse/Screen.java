/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newvirtualmouse;

import java.awt.*;

/**
 * 
 * @author user
 */
public class Screen {

    private int       screenNum;

    private Rectangle bounds;

    public Screen(int screenNum, GraphicsDevice gs) {
        this.screenNum = screenNum;

        GraphicsConfiguration gc = gs.getDefaultConfiguration();

        bounds = gc.getBounds();

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getScreenNum() {
        return screenNum;
    }

    public boolean isInBounds(int x, int y) {
        return bounds.contains(x, y);
    }

}
