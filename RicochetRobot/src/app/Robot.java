package app;

import javafx.scene.image.Image;

import java.net.URISyntaxException;

public class Robot {
    private int initX,initY;
    private int demX,demY;
    private int number;
    private Image image;

    public Robot(int initX, int initY, int number) {
        this.initX = initX;
        this.initY = initY;
        demX = initX;
        demY = initY;
        this.number = number;

        try {
            image = new Image(getClass().getClassLoader().getResource(number+".png")     //donne une couleur au robot(charge l'image du robot)
                    .toURI().toString(),30,30,true,true);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public int getInitX() {
        return initX;
    }

    public void setInitX(int initX) {
        this.initX = initX;
    }

    public int getInitY() {
        return initY;
    }

    public void setInitY(int initY) {
        this.initY = initY;
    }

    public int getDemX() {
        return demX;
    }

    public void setDemX(int demX) {
        this.demX = demX;
    }

    public int getDemY() {
        return demY;
    }

    public void setDemY(int demY) {
        this.demY = demY;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Image getImage() {
        return image;
    }
}
