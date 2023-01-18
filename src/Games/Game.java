package Games;

import Games.CardGames.CardPlayer;

public abstract class Game {

    public static String[][] character_set;
    private int win_status;
    protected int win_condition;
    private CardPlayer current_Card_player;


    public Game(int status, int condition, String[][] character_set){
        set_win_status(status);
        // set_current_player(p);
        this.win_condition = condition;
        this.character_set = character_set;
    }

    public void set_currentPlayer(CardPlayer p){
        current_Card_player = p;
    }


    public int get_win_status(){
        return this.win_status;
    }
    public void set_win_status(int status){
        this.win_status = status;
    }

    public CardPlayer get_current_player(){
        return this.current_Card_player;
    }

    public void set_current_player(CardPlayer p){
        this.current_Card_player = p;
    }

    public int get_win_condition(){
        return this.win_condition;
    }


}
