import java.util.Scanner;
import java.util.ArrayList;

public class Play {
    public static void start(){
        Scanner console = new Scanner(System.in);


        System.out.println("Please enter the number of players:");
        int num_players = console.nextInt();

        ArrayList<String>player_names = new ArrayList<String>();

        for( int i=0; i<num_players; i++){
            System.out.println("Please enter the name of player " + String.valueOf(i+1));
            String name = console.next();
            player_names.add(name);
        }

        System.out.println("Enter the name of the dealer/banker: ");
        String banker = console.next();

        System.out.println("Enter the amount of money every player is beginning with");
        int money = console.nextInt();

        String[][] character_set= {{"A","11"}, {"K", "10"}, {"Q", "10"}, {"J", "10"}, {"10","10"}, {"9","9"}, {"8", "8"}, {"7", "7"}, {"6","6"}, {"5","5"}, {"4", "4"}, {"3", "3"}, {"2","2"}};


        TriantaEna T = new TriantaEna(num_players, player_names, banker, money, character_set);


    }
}
