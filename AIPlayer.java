import java.util.ArrayList;

//sub-class extends Player
public class AIPlayer extends Player {
    private String name;
    private Player opponent;

    // Constructor
    public AIPlayer(String name, Player opponent) {
        this.name = name;
        this.opponent = opponent;
    }

    // Getter and setter for opponent
    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    @Override
    public String toString() {
        return name + " (AI)";
    }

    // Implementing abstract method chooseMove from Player class
    @Override
    public MiniCheckers chooseMove(MiniCheckers board) {
        //get list of all possible moves
        ArrayList<MiniCheckers> possibleMoves = board.possibleMoves(this);
        double maxVal = Double.NEGATIVE_INFINITY;
        MiniCheckers bestMove = null;

        //iterate through possible moves and fins max
        for (MiniCheckers move : possibleMoves) {
            double val = minValue(move, 10); // Use 10 as the initial depth
            if (val > maxVal) {
                maxVal = val;
                bestMove = move;
            }
        }
        return bestMove;
    }

    //calculates maxValue for player
    public double maxValue(MiniCheckers game, int depth) {
        //3 base cases
        //(1) Check if the player wins
        if (game.checkWin(this)) {
            return 10.0;
        }
        //(2)Check if the player loses
        if (game.checkLose(this)) {
            return -10.0;
        }
        //(3)Check if the depth limit is reached
        if (depth < 1) {
            // Calculate and return the relative value of the player's pieces

            double retVal;
            char myColor;
            char oppColor;

            if (this == game.getRed()) { 
                myColor = 'r';
                oppColor = 'b';
            } else {
                myColor = 'b';
                oppColor = 'r';
            }

            int numMyCheckers = game.countChecker(myColor);
            int numMyKings = game.countChecker(Character.toUpperCase(myColor));
            int numOppCheckers = game.countChecker(oppColor);
            int numOppKings = game.countChecker(Character.toUpperCase(oppColor));

            retVal = (numMyCheckers + (3 * numMyKings)) - (numOppCheckers + (3 * numOppKings));

            return retVal;
        }
        // Recursive case: Check possible moves and find the max value
        ArrayList<MiniCheckers> possibleMoves = game.possibleMoves(this);
        double maxVal = Double.NEGATIVE_INFINITY;

        for (MiniCheckers move : possibleMoves) {
            double val = minValue(move, depth - 1);
            maxVal = Math.max(maxVal, val);
        }
        return maxVal;
    }

    //calculates minValue for opponent
    public double minValue(MiniCheckers game, int depth) {
        //3 base cases
        //(1)Check if the opponent wins
        if (game.checkWin(this.opponent)) {
            return 10.0;
        }
        //(2)Check if the opponent loses
        if (game.checkLose(this.opponent)) {
            return -10.0;
        }
        //(3)Check if the depth limit is reached
        if (depth < 1) {
            // Calculate and return the relative value of the opponent's pieces

            double retVal;
            char myColor;
            char oppColor;

            if (this == game.getRed()) {
                myColor = 'r';
                oppColor = 'b';
            } else {
                myColor = 'b';
                oppColor = 'r';
            }

            int numMyCheckers = game.countChecker(myColor);
            int numMyKings = game.countChecker(Character.toUpperCase(myColor));
            int numOppCheckers = game.countChecker(oppColor);
            int numOppKings = game.countChecker(Character.toUpperCase(oppColor));

            retVal = (numMyCheckers + (3 * numMyKings)) - (numOppCheckers + (3 * numOppKings));

            return retVal;
        }

        //Recursive case: Check possible moves and find the min value
        ArrayList<MiniCheckers> possibleMoves = game.possibleMoves(this.opponent);
        double minVal = Double.POSITIVE_INFINITY;

        for (MiniCheckers move : possibleMoves) {
            double val = maxValue(move, depth - 1);
            minVal = Math.min(minVal, val);
        }
        return minVal;
    }

        public double boardValue(MiniCheckers board) {
        return maxValue(board, 10); 
    }
} 