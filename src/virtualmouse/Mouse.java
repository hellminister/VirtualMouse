/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualmouse;


import newvirtualmouse.DisplayArea;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author user
 */
public class Mouse {

    private Robot mouse;
    
    private DisplayArea playground;

    private Point mousePosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    private final Object lock = new Object();

    public Mouse() throws AWTException {
        playground = new DisplayArea();
        mouse = new Robot();
    }

    public void pressButton(int button){
        synchronized (lock) {
            mouse.mousePress(InputEvent.getMaskForButton(button));
        }
    }

    public void clickButton(int button) {
        synchronized (lock) {
            pressButton(button);
            releaseButton(button);
        }
    }

    public void releaseButton(int button){
        synchronized (lock) {
            mouse.mouseRelease(InputEvent.getMaskForButton(button));
        }
    }

    public void teleportMouse(int x, int y) {
        synchronized (lock) {
            mouse.mouseMove(x, y);
        }
    }

    public void moveMouseTo(Point position){
        synchronized (lock) {
            moveMouseTo(position.x, position.y);
        }
    }

    public void moveMouseTo(int x, int y){
        synchronized (lock) {
            moveMouseBy(x - mousePosition().x, y - mousePosition().y);
        }
    }

    public void calculateMoveIncrementsValuesTo(Point position){
        synchronized (lock) {
            calculateMoveIncrementsValuesTo(position.x, position.y);
        }
    }

    public void calculateMoveIncrementsValuesTo(int x, int y){
        synchronized (lock) {
            calculateMoveIncrementsValuesFor(x - mousePosition().x, y - mousePosition().y);
        }
    }


    private float turnincrementx = 0;
    private float turnincrementy = 0;
    private float nextturny = 0;
    private float nextturnx = 0;

    private int signx = 0;
    private int signy = 0;

    private int moveXleft = 0;
    private int moveYleft = 0;

    private float currentturn = 1;

     public void calculateMoveIncrementsValuesFor(int x, int y){
        synchronized (lock) {
             signx = (x != 0 ? x / Math.abs(x) : 0);
             signy = (y != 0 ? y / Math.abs(y) : 0);

             moveXleft = Math.abs(x);
             moveYleft = Math.abs(y);
             nextturnx = 0;
             nextturny = 0;

            turnincrementx = 1;
            turnincrementy = 1;

            if (moveXleft > moveYleft) {
                turnincrementy = (y != 0 ? Math.abs((float)x / y) : 0);
            } else if (moveYleft > moveXleft) {
                turnincrementx = (x != 0 ? Math.abs((float)y / x) : 0);
            }

            nextturny += turnincrementy;
            nextturnx += turnincrementx;
            currentturn = 1;
        }
    }

    public void moveByOneIncrement(){
        synchronized (lock) {
            int tempx = mousePosition().x;
            int tempy = mousePosition().y;
            if (currentturn >= nextturnx && moveXleft > 0) {
                moveXleft--;
                tempx += signx;
                if (!playground.isInBounds(tempx, mousePosition().y)) {
                    tempx = mousePosition().x;
                }
                nextturnx += turnincrementx;
            }
            if (currentturn >= nextturny && moveYleft > 0) {
                moveYleft--;
                tempy += signy;
                if (!playground.isInBounds(mousePosition().x, tempy)) {
                    tempy = mousePosition().y;
                }
                nextturny += turnincrementy;
            }

            mouse.mouseMove(tempx, tempy);
            currentturn++;
        }

    }

    public boolean hasMoreMove(){
        synchronized (lock) {
            return moveXleft > 0 || moveYleft > 0;
        }
    }

    public void moveMouseBy(int x, int y){
        synchronized (lock) {

            calculateMoveIncrementsValuesFor(x, y);

            while (hasMoreMove()) {
                moveByOneIncrement();
                delay(1);
            }
        }
    }

    public Point getPosition() {
        synchronized (lock) {
            return new Point(mousePosition());
        }
    }

    public Robot getRobot(){
         return mouse;
    }

    public BufferedImage getScreenShot(int screenNumber) {
        return mouse.createScreenCapture(playground.getBoundsForScreen(screenNumber));
    }

    public void delay(int i) {
         mouse.delay(i);
    }
}
