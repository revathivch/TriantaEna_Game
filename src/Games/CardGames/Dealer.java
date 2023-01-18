package Games.CardGames;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Dealer extends CardPlayer{

    private int hand_value;
    private String current_status;
    private int remaining_money;
    ArrayList<Card> hand = new ArrayList<Card>();
    public Dealer(String name, int cp, int score, int remaining_money) {
        super(name, cp, score, remaining_money);

        this.hand_value = 0;
        this.remaining_money = remaining_money;
        this.current_status = " ";
    }

    public void shuffle_card(Deck[] d){
        for(int i=0; i<d.length; i++){
            d[i].ShuffleDeck();
        }
    }

    public ArrayList<Card> get_hand(){
        return hand;
    }

    public Deck get_deck_to_remove(Deck[] decks, String[][] character_set){
        for(int i=0; i<decks.length; i++){
            if(decks[i].isEmpty() == 0){
                return decks[i];
            }
        }

        for(int i=0; i<decks.length; i++){
            decks[i].InitializeDeck(character_set);
            decks[i].ShuffleDeck();
        }

        return decks[0];
    }

    public Card get_top_card_from_deck(Deck[] decks, String[][] character_set, boolean facing_up){
        Deck d = get_deck_to_remove(decks, character_set);
        Card card = d.get_card_list().get(0);
        card.set_facing_up(facing_up);
        d.get_card_list().remove(0);
        return card;
    }
    public void distribute_cards(ArrayList<CardPlayer> players, int num_cards, Deck[] decks, String[][] character_set, boolean facing_up){

        for(int i=0; i<players.size(); i++){
            if(players.get(i).checkEligibilityToPlay()){
                for(int j=0; j<num_cards; j++){
                    Card card = get_top_card_from_deck(decks, character_set, facing_up);
                    if(card.get_face().equals("A")){
                        card.set_customised_value();
                    }
                    players.get(i).add_to_hand(card);
                }
            }
        }
    }

    public void add_to_hand(Card c){
        hand.add(c);
        super.hand_value = super.hand_value + c.get_value();
    }

    public void reset(){
        this.hand_value = 0;
        this.current_status = " ";
        this.hand = new ArrayList<Card>();
    }

    public void self_assign(int num_cards, Deck[] decks, String[][] character_set, boolean facing_up){
        Card card = get_top_card_from_deck(decks, character_set, facing_up);
        if(card.get_face().equals("A")){
            card.set_customised_value();
        }
        add_to_hand(card);
    }

    public void make_transaction_to_player(CardPlayer p){
        // Banker needs to give money to player
        if(this.get_remaining_money()>=p.get_bet_so_far()){   // But the banker can give the money only if it has money
            p.add_amount(p.get_bet_so_far());
            this.set_remaining_money(this.get_remaining_money()-p.get_bet_so_far());
            p.set_bet_so_far(0);
        }
        else{
            System.out.println("Sorry, but the bank doesn't have enough money to satisfy the bet. We will return how much ever possible");
            p.add_amount(this.get_remaining_money());
            p.set_bet_so_far(0);
        }
    }

    public void make_transaction_from_players(ArrayList<CardPlayer> players){
        for(int i=0; i<players.size(); i++){
            if(players.get(i).get_current_status()=="STAND"){
                this.add_amount(players.get(i).get_bet_so_far());
                players.get(i).set_bet_so_far(0);
            }
        }
    }
}
