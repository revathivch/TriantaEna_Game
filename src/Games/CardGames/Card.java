package Games.CardGames;

import java.util.Scanner;


public class Card extends Games.Piece{
    private Suit suit;
    private String face;
    private int value;

    private boolean facing_up;

    public Card(String name, Suit suit, String face){
        super(name);
        this.suit = suit;
        this.set_face(face);
        this.facing_up = false;     // By default facing down
        this.set_value(Games.Game.character_set);  //char_set["face_name","value"]
    }


    public boolean get_facing_up(){
        return this.facing_up;
    }

    public void set_facing_up(boolean facing_up){
        this.facing_up = facing_up;
    }

    public int get_value(){
        return this.value;
    }

    public void set_value(int val){
        this.value = val;
    }
    public String get_name(){
        return super.get_name();
    }

    public void set_name(String name){
        super.set_name(name);
    }

    public String get_face(){
        return(this.face);
    }
    public void set_face(String ch){
        this.face = ch;
    }

    public Suit get_suit(){
        return(this.suit);
    }
    public void set_suit(Suit s){
        this.suit = s;
    }
    public void set_value(String[][] char_set)
    {
        for(int i=0; i<char_set.length; i++){
            if(char_set[i][0].equals(this.face)){
                this.value = Integer.parseInt(char_set[i][1]);
            }

        }
    }

    public void set_customised_value(){
        Scanner console = new Scanner(System.in);
        System.out.println("The card you have picked is an Ace, would you like to assign it value 1 or 11? (1/11)");
        int card_val = console.nextInt();
        if(card_val == 1){
            this.set_value(1);
        }
        else if(card_val == 11){
            this.set_value(11);
        }
    }

}
