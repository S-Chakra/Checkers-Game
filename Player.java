//abstract class
public abstract class Player{

//abstract method
//depends on type of player(User/AI) --> overriden by subclasses
public abstract MiniCheckers chooseMove(MiniCheckers board);

//concrete method
public double boardValue(MiniCheckers board) {
    if (board.checkWin(this)) {
        return 1.0; //player has won the current board
        } else if (board.checkLose(this)){
            return -1.0; //player has lost the current board
            } else {
                return 0.0; //game continues
                }
    }
}