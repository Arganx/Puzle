package model;

import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;

/**
 * Created by qwerty on 08-Apr-17.
 */
public class Tile extends Rectangle {
    private BufferedImage part;
    private int num;
    private double orgX;
    private double orgY;

    public boolean isAlreadypressed() {
        return alreadypressed;
    }

    public void setAlreadypressed(boolean alreadypressed) {
        this.alreadypressed = alreadypressed;
    }

    private boolean alreadypressed;


    public Tile(int width, int height, BufferedImage part, int num) {
        super(width, height);
        this.part = part;
        this.num = num;
    }

    public Tile(BufferedImage part, int id) {
        this.part = part;
        this.num = id;
    }


    public BufferedImage getPart() {
        return part;
    }

    public int getNum() {
        return num;
    }

    public void setPart(BufferedImage part) {
        this.part = part;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getOrgX() {
        return orgX;
    }

    public void setOrgX(double orgX) {
        this.orgX = orgX;
    }

    public double getOrgY() {
        return orgY;
    }

    public void setOrgY(double orgY) {
        this.orgY = orgY;
    }
}
