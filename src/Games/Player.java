package Games;

public abstract class Player {
    protected String name;
    private int count_pieces; // Stores the number of pieces that belong to a particular player
    private int score;

    public Player(String name, int cp, int score){
        this.name = name;
        this.count_pieces = cp;
        this.score = score;
    }

}
