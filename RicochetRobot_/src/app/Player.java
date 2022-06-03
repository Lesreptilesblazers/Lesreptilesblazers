package app;

public class Player {
    private String playerName;
    private int timeTook;
    private int points;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTimeTook() {
        return timeTook;
    }

    public void setTimeTook(int timeTook) {
        this.timeTook = timeTook;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
