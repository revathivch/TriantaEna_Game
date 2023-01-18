package Games.CardGames;
import java.util.ArrayList;

public class CardPlayer extends Games.Player{

    int hand_value;
    private int bet_so_far;
    private String current_status;
    private int remaining_money;

    ArrayList<Card> hand = new ArrayList<Card>();


    public CardPlayer(String name, int cp, int score, int bet_so_far, int remaining_money) {
        super(name, cp, score);  // Since we are extending from the class Player, in this constructor, we are also initializing the players name, count of cards belonging to the player and the players score by calling super class which has all these parameters
        this.hand_value = 0;
        this.bet_so_far = bet_so_far;
        this.remaining_money = remaining_money;
        this.current_status = " ";
    }

    // Constructor Overloading to incorporate the properties dealer requires
    public CardPlayer(String name, int cp, int score, int remaining_money) {
        super(name, cp, score);  // Since we are extending from the class Player, in this constructor, we are also initializing the players name, count of cards belonging to the player and the players score by calling super class which has all these parameters
        this.hand_value = 0;
        this.bet_so_far = bet_so_far;
        this.remaining_money = remaining_money;
        this.current_status = " ";
    }

    public void reset(){
        this.hand_value = 0;
        this.bet_so_far = 0;
        this.current_status = " ";
        this.hand = new ArrayList<Card>();
    }

    public ArrayList<Card> get_hand(){
        return hand;
    }

    public void set_hand(ArrayList<Card> cards){
        this.hand = cards;
    }

    public boolean check_busted(int bust_value){
        if(this.hand_value > bust_value){
            return true;
        }
        else{
            return false;
        }
    }

    public void add_to_hand(Card c){
        hand.add(c);
        this.hand_value = this.hand_value + c.get_value();
    }

    public void print_current_hand(){
        for(int i=0; i<this.hand.size(); i++){
            System.out.println(this.hand.get(i).get_suit() + this.hand.get(i).get_face());
        }
    }

    public boolean checkEligibilityToPlay(){

        if(this.current_status.equals("FOLD") || this.current_status.equals("BUST") ){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkEligibilityToBet(int val){
        if(this.remaining_money < val){
            return false;
        }
        else{
            return true;
        }
    }

    public String get_name(){
        return super.name;
    }

    public void set_name(String name){
        this.name = name;
    }

    public int get_hand_value(){
        return this.hand_value;
    }

    public void set_hand_value(int val){
        this.hand_value = val;
    }

    public int get_bet_so_far(){
        return this.bet_so_far;
    }

    public void set_bet_so_far(int val){
        this.bet_so_far = val;
    }

    public String get_current_status(){
        return this.current_status;
    }

    public void set_current_status(String val){
        this.current_status = val;
    }

    public int get_remaining_money(){
        return this.remaining_money;
    }

    public void set_remaining_money(int val){     // Adds value to the remaining amount
        this.remaining_money = val;
    }

    public void add_amount(int val){
        this.remaining_money = this.remaining_money + val;
    }

}
