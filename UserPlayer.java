import java.util.ArrayList;
import java.util.Scanner;

//sub-class extends Player
public class UserPlayer extends Player{
    private String name;
    private Scanner input;

    //constructor
    public UserPlayer(Scanner input, String name) {
        this.input = input;
        this.name = name;
    }

    //@override //overrides toString method to return the name field
    public String toString() {
        return name;
    }

    //@override //overrides choosemove
    public MiniCheckers chooseMove(MiniCheckers board) {
        //Print out the current board that was passed in as an argument
        System.out.println("Current board:\n" + board.toString());

        //Use the possibleMoves method of the argument to get the options the user can choose from and print them out
        ArrayList<MiniCheckers> possibleMoves = board.possibleMoves(this);

        System.out.println("Possible Moves: ");
        for (int i = 0; i < possibleMoves.size(); i++) {
            MiniCheckers possibleMove = possibleMoves.get(i);

            System.out.println(possibleMove.toString());
        }

        //Ask the user to select move and get the user's response using the Scanner object
        System.out.print("Select a move: ");
        int moveSelected = input.nextInt();

        //Return move selected by the user
        return possibleMoves.get(moveSelected);
    }
}