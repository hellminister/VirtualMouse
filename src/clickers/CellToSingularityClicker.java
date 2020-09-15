package clickers;

import virtualmouse.Mouse;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CellToSingularityClicker {

    public static void main(String[] args) throws AWTException, InterruptedException {
        Mouse mouse = new Mouse();



        //mouse.teleportMouse(1860,730);
        mouse.moveMouseTo(1443,798);

        Point position = new Point(mouse.getPosition());


        int count = 0;

        while (notMoved(mouse, position)){
            mouse.clickButton(1);

            mouse.delay(1000);

            if (count++ > 1000000){
                mouse.moveMouseBy(100, 100);
                mouse.moveMouseBy(-100,-100);
                count = 0;
            }
        }
    }

    private static boolean notMoved(Mouse mouse, Point position) {

        return mouse.getPosition().equals(position);
    }
}
