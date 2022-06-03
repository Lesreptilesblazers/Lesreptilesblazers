package app;

public class Player {
    private String playerName;          //On créer les valeurs en prvée pour pas qu'elles soient accéssiblent
    private int timeTook;
    private int points;

    public Player(String playerName) {
        this.playerName = playerName;
    }           //on fait des fonctions mais enft c'est des méthodes car on est dans une classes pour les définir et les recupérer (getter setter)

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
