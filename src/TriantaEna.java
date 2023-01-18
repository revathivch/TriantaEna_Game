import Games.CardGames.Card;
import Games.CardGames.CardPlayer;
import Games.CardGames.Dealer;

import java.util.ArrayList;

import java.util.Scanner;

public class TriantaEna extends Games.Game {
    Dealer dealer;
    ArrayList<Games.CardGames.CardPlayer> players;
    Games.CardGames.Deck[] decks;
    final String[][] character_set;

    TriantaEna(int num_players, ArrayList<String> player_names, String banker, int money, String[][] character_set) {
        super(0, 27, character_set);    // Initializing game class which is the super class


        players = new ArrayList<Games.CardGames.CardPlayer>(num_players-1);

        // Creating players and dealer objects based on user input
        for(int i=0; i<player_names.size(); i++){
            if(player_names.get(i).equals(banker)){
                dealer = new Dealer(player_names.get(i), 0, 0, money*3);
            }
            else{
                Games.CardGames.CardPlayer player = new Games.CardGames.CardPlayer(player_names.get(i), 0, 0, 0, money);
                players.add(player);
            }
        }

        decks = new Games.CardGames.Deck[2];

        for(int i=0; i<decks.length; i++){
            decks[i] = new Games.CardGames.Deck(52,character_set);
        }

        this.character_set = character_set;

        play_game();

    }

    public void print_record(CardPlayer p){
        String name = p.get_name();
        String status = p.get_current_status();
        String tot_hand = "";
        String hand_val = "*";
        String rem_amount = "*";

        if(p.equals(this.get_current_player())){

            for(int j=0; j<p.get_hand().size(); j++){

                tot_hand = tot_hand + " " + p.get_hand().get(j).get_name();
            }
            hand_val = String.valueOf(p.get_hand_value());
            rem_amount = String.valueOf(p.get_remaining_money());
        }

        else{
            for(int j=0; j<p.get_hand().size(); j++){

                if(p.get_hand().get(j).get_facing_up()){
                    tot_hand = tot_hand + " " + p.get_hand().get(j).get_name();
                }
                else{
                    tot_hand = tot_hand + "*" ;
                }
            }

            rem_amount = String.valueOf(p.get_remaining_money());
        }
        System.out.println("       " + name + "             " + status + "                       " + tot_hand + "                       " + hand_val + "                     " + rem_amount);
        System.out.println("-------------------  -------------------  ---------------------------  --------------------- -------------------");
    }

    public void displayGameStatus(){
        System.out.println("-------------------  -------------------  ---------------------------  --------------------- -------------------");
        System.out.println("      Players              Status                     Hand                  Hand Value         Remaining Amount");

        for(int i=0; i<players.size(); i++) {
            print_record(players.get(i));
        }

        print_record(dealer);
    }


    public void Part1(CardPlayer p){

        Scanner console = new Scanner(System.in);

        this.set_current_player(p);
        // Round one of the game

        if(p.checkEligibilityToPlay() && p.get_remaining_money()!=0){

            displayGameStatus();

            System.out.println(p.get_name() + ": Would you like to 'BET' or 'FOLD'? (Y/N)");
            String input = console.next();

            p.set_current_status(input);

            if(input.equals("BET")){
                System.out.println("Your current balance is: " + String.valueOf(p.get_remaining_money()) + " Please enter the amount you would like to BET on:");
                int val = console.nextInt();

                while(!p.checkEligibilityToBet(val)){
                    System.out.println("Please enter an amount less than your current balance");
                    val = console.nextInt();
                }

                p.set_remaining_money( p.get_remaining_money() - val);
                p.set_bet_so_far( p.get_bet_so_far() + val);

                p.set_current_status("BET");


            }

            else if(input.equals("FOLD")){
                p.set_current_status("FOLD");
            }
            else{
                System.out.println("Please enter valid input (BET/FOLD)");
            }
        }
    }

    public void Part2(CardPlayer p){
        Scanner console = new Scanner(System.in);
        this.set_current_player(p);

        if(p.checkEligibilityToPlay()){

            displayGameStatus();

            System.out.println(p.get_name() + ": Would you like to 'HIT' or 'STAND'?");
            String input = console.next();

            if(input.equals("HIT")){

                do{
                    Games.CardGames.Card card = dealer.get_top_card_from_deck(decks, character_set, true);

                    if(card.get_face().equals("A")){
                        card.set_customised_value();
                    }
                    p.add_to_hand(card);
                    p.set_current_status("HIT");

                    displayGameStatus();

                    if(p.check_busted(31)){
                        System.out.println(p.check_busted(31));
                        p.set_current_status("BUST");
                        dealer.add_amount(p.get_bet_so_far());
                        p.set_bet_so_far(0);
                    }
                    else{
                        System.out.println(p.get_name() +" Would you like to 'HIT' or 'STAND'?");
                        input = console.next();
                    }

                }while(input.equals("HIT") && !p.get_current_status().equals("BUST"));

            }

            if(input.equals("STAND") && !p.get_current_status().equals("BUST")){
                p.set_current_status("STAND");
            }
            else{
                System.out.println("Please enter valid input (HIT/STAND)");
            }
        }
    }

    public void Part3(){
        Scanner console = new Scanner(System.in);

        this.set_current_player(dealer);

        while(dealer.get_hand_value() < 27){
            System.out.println("Dealer picks another card: ");
            displayGameStatus();
            dealer.self_assign(1, decks, character_set, true);
        }

        // If the dealer gets busted, the dealer distributes bet money of each standing player
        if(dealer.get_hand_value() > 31){
            for(int i=0; i<players.size(); i++) {
                if (players.get(i).get_current_status().equals("STAND")) {
                    dealer.make_transaction_to_player(players.get(i));
                }
            }
        }
        else{
            this.win_condition = dealer.get_hand_value();

            int flag = 0;
            for(int i=0; i<players.size(); i++){
                if(players.get(i).get_current_status().equals("STAND")){
                    if(players.get(i).get_hand_value()> win_condition){
                        System.out.println("Congratulations " + players.get(i).get_name() + "! You have won the game");
                        flag = 1;

                        dealer.make_transaction_to_player(players.get(i));
                    }
                    else if(players.get(i).get_hand_value()==win_condition){
                        if(isTrianaEna(players.get(i))){
                            System.out.println("Congratulations "+ players.get(i).get_name() + "! You have won the game");
                            flag = 1;

                            dealer.make_transaction_to_player(players.get(i));

                        }
                    }
                }
            }
            // The dealer will win even if it was draw, except for when there was a draw with a TrianaEna on the player hand
            if(flag ==0){
                System.out.println("The dealer wins !");
                dealer.make_transaction_from_players(players);
            }
        }
    }


    public boolean check_standing_player_exists(){
        for(int i=0; i<players.size(); i++){
            if(players.get(i).get_current_status().equals("STAND")){
                return true;
            }
        }
        return false;
    }

    public boolean isTrianaEna(CardPlayer p){
        int cA = 0; // Count of Aces
        int cF = 0; // Count of face cards
        for(int i=0; i<p.get_hand().size(); i++){
            if(p.get_hand().get(i).get_face() == "A"){
                cA++;
            }
            else if(p.get_hand().get(i).get_face() == "K" || p.get_hand().get(i).get_face() == "Q" || p.get_hand().get(i).get_face() == "J"){
                cF++;
            }
        }

        if(cA == 1 && cF == 2){
            return true;
        }

        return false;
    }

    public boolean check_all_players_broke(){
        int flag = 0;
        for(int i=0; i<players.size(); i++){
            if(players.get(i).get_remaining_money() != 0){
                flag = 1;
                break;
            }
        }
        if(flag == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void rotate_dealer(){
        int dealer_amount = dealer.get_remaining_money();
        int max_value = dealer_amount;
        int rich_player_pos = 0;
        for(int i=0; i<players.size(); i++){
            if(players.get(i).get_remaining_money()>max_value){
                rich_player_pos = i;
                max_value = players.get(i).get_remaining_money();
            }
        }

        int hand_value = players.get(rich_player_pos).get_hand_value();
        players.get(rich_player_pos).set_hand_value(dealer.get_hand_value());
        dealer.set_hand_value(hand_value);

        String current_status = players.get(rich_player_pos).get_current_status();
        players.get(rich_player_pos).set_current_status(dealer.get_current_status());
        dealer.set_current_status(current_status);

        int remaining_money = players.get(rich_player_pos).get_remaining_money();
        players.get(rich_player_pos).set_remaining_money(dealer.get_remaining_money());
        dealer.set_remaining_money(remaining_money);

        String name = players.get(rich_player_pos).get_name();
        players.get(rich_player_pos).set_name(dealer.get_name());
        dealer.set_name(name);

        ArrayList<Card> hand = players.get(rich_player_pos).get_hand();
        players.get(rich_player_pos).set_hand(dealer.get_hand());
        dealer.set_hand(hand);

    }

    public void play_game(){

        String keep_playing = "Y";

        int first_play = 0;

        do {

            if(first_play != 0){

                // Resetting all the players
                for(int i=0; i<players.size(); i++){
                    players.get(i).reset();
                    first_play = 1;
                }
                dealer.reset();

                rotate_dealer();
            }

            dealer.shuffle_card(decks);

            // Starting Part1 of the game
            Scanner console = new Scanner(System.in);
            dealer.self_assign(1, decks, character_set, true);
            dealer.distribute_cards(players, 1, decks, character_set, false);

            for (int i = 0; i < players.size(); i++) {
                Part1(players.get(i));
            }


            // Starting round two of the game
            dealer.distribute_cards(players, 2, decks, character_set, true);

            for (int i = 0; i < players.size(); i++) {
                Part2(players.get(i));
            }


            //Starting Part 3 of the game

            if (check_standing_player_exists()) {    // Go to part three only if there are any standing players that the dealer can compete with
                Part3();
                displayGameStatus();
            }
            else {
                System.out.println("There are no standing players left, game restarts");
            }

            if(!check_all_players_broke()){
                System.out.println("Would you like to continue playing? (Y/N)");
                keep_playing = console.next();
            }
            else{
                keep_playing = "N";
            }


        }while(keep_playing.equals("Y"));

    }

}
