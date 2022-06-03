package app;

import javafx.scene.control.Label;

public class Tile extends Label {
    private boolean hasRobot;
    public int destination = -1;
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

    String[] color = new String[]{"#FE1300", "#018200", "#019DED", "#FFA302"};

            //définie la couleur de la tuile et ensuite la largeur des cotés de la tuile (en fonction de si il y a un mur l46 a 49)
    public void updateStyle(){
        setStyle("-fx-background-color:"+(highlighted? "green": (destination != -1 ? color[destination] : "#F4F4F4"))+"; " +
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
