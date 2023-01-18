package Games.CardGames;

import java.util.ArrayList;

import java.util.Collections;

public class Deck {

    private int deck_size;
    private ArrayList<Games.CardGames.Card> card_list = new ArrayList<Games.CardGames.Card>();

    public Deck(int size, String[][] character_set){
        this.set_deck_size(size);
        this.InitializeDeck(character_set);
    }

    public ArrayList<Card> get_card_list(){
        return this.card_list;
    }

    public void set_deck_size(int size){
        this.deck_size = size;
    }

    public int get_deck_size(){
        this.deck_size = this.card_list.size();
        return(this.deck_size);
    }

    public void InitializeDeck(String[][] char_set){
        for(Suit s : Suit.values()){
            for(int i=0; i< char_set.length; i++){
                String card_name = s + char_set[i][0];
                Card c = new Card(card_name, s, char_set[i][0]);
                this.card_list.add(c);
            }
        }
    }

    public void ShuffleDeck(){
        Collections.shuffle(card_list);
    }

    public void DisplayDeck(){
        for(int i=0; i<this.card_list.size(); i++){
            System.out.println(this.card_list.get(i).get_name());
        }
    }

    public int isEmpty(){
        if(deck_size == 0){
            return 1;
        }
        else{
            return 0;
        }
    }


}
