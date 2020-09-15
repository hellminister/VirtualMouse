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
public class DisplayArea {

    private Screen[] screens;

    public DisplayArea() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        screens = new Screen[gs.length];

        for(int i = 0; i < gs.length; i++)
        {
            screens[i] = new Screen(i, gs[i]);
        }
    }

    public boolean isInBounds(int x, int y){
        boolean inbound = false;

            for(Screen cs : screens){
                inbound = inbound || cs.isInBounds(x, y);
            }


        return inbound;
    }

    public Rectangle getBoundsForScreen(int screenNum) {
        return screens[screenNum].getBounds();
    }
}
