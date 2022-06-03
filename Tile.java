package app;

import javafx.scene.control.Label;

public class Tile extends Label {
    private boolean hasRobot;
    public boolean destination;
    public boolean closed[] = {false,false,false,false};
    private boolean highlighted = false;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private Robot robot;

    public Tile(){
        setPrefSize(WIDTH,HEIGHT);
        setStyle("-fx-background-color:white");
        updateStyle();
    }

    public void closeTop(){
        closed[0] = true;
        updateStyle();
    }

    public void closeRight(){
        closed[1] = true;
        updateStyle();
    }

    public void closeBottom(){
        closed[2] = true;
        updateStyle();
    }

    public void closeLeft(){
        closed[3] = true;
        updateStyle();

    }


    private void updateStyle(){
        setStyle("-fx-background-color:"+(highlighted? "green": (destination ? "brown":"white"))+"; " +
                "-fx-border-color:black;" +
                " -fx-border-width: " +
                (closed[0] ? "5 " :"0.1 ") +
                (closed[1] ? "5 " :"0.1 ")+
                (closed[2] ? "5 " :"0.1 ") +
                (closed[3] ? "5 " :"0.1 "));
    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }
}
