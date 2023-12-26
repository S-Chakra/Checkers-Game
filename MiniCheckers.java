import java.util.ArrayList;

public class MiniCheckers {
    private char[][] board; //2D char array;5x5
    private Player red;
    private Player black;

    public MiniCheckers(Player red, Player black) {
        //takes 2 player arguements
        this.red = red;
        this.black = black;
        //initializing board
        this.board = new char[][] {
            //c0  c1   c2   c3   c4
            {'r', '.', 'r', '.', 'r'}, //row0
            {'.', '_', '.', '_', '.'}, //row1
            {'_', '.', '_', '.', '_'}, //row2
            {'.', '_', '.', '_', '.'}, //row3
            {'b', '.', 'b', '.', 'b'}  //row4
            };
        }
    //getter and setter for board
    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    //getter and setter for red player
    public Player getRed() {
        return red;
    }

    public void setRed(Player red) {
        this.red = red;
    }

    //getter and setter for black player
    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public int countChecker(char color) {
        int charCount = 0;
        int row;
        int column;

        // Iterating through rows and columns of the board
        for (row = 0; row < board.length; row++) {
            for (column = 0; column < board[row].length; column++) {
                if (board[row][column] == color) { //yx format
                    charCount++;
                }
            }
        }
        return charCount; //returns the number of characters on the board
    }

    public boolean checkWin(Player player) {
        char colorOfOpponent;

        //Determine the color of the opponent
        //If opponent has no checkers left on the board, player wins. Uses countChecker method
        if (player == red){
            colorOfOpponent = 'b';
            if(countChecker(colorOfOpponent) == 0){
                return true;
            }
        } else if(player == black){
            colorOfOpponent = 'r';
            if(countChecker(colorOfOpponent) == 0){
                return true;
            }
        }

        return false; //there are zero of the opponent's checkers on the board
    }

    public boolean checkLose(Player player) {
        //checks if the opponent is winner by using checkWin method. If opponent = winner --> player = loser
        Player opponent;
        if(player == red){
            opponent = black;
            return checkWin(opponent);
        }
        else if(player == black){
            opponent = red;
            return checkWin(opponent);
        }
        return false;
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                res += board[i][j];
            }
            res += "\n";
        }
        return res;
    }

    private MiniCheckers makeClone() {
        /* provided code. You may call this method, but you should NOT modify it */
        MiniCheckers newMiniCheckers = new MiniCheckers(this.red, this.black);
        char[][] newBoard = newMiniCheckers.getBoard();
        char[][] curBoard = this.getBoard();
        for (int i = 0; i < curBoard.length; i++) {
            for (int j = 0; j < curBoard[i].length; j++) {
                newBoard[i][j] = curBoard[i][j];
            }
        }
        newMiniCheckers.setBoard(newBoard);
        return newMiniCheckers;
    }

    private MiniCheckers movePiece(int startRow, int startCol, int endRow, int endCol){
        /* provided code. You may call this method, but you should NOT modify it */
        MiniCheckers move = this.makeClone();
        char[][] newBoard = move.getBoard();
        char tmpPiece = newBoard[startRow][startCol];
        newBoard[startRow][startCol] = '_';
        newBoard[endRow][endCol] = tmpPiece;
        if((tmpPiece=='r' && endRow==newBoard.length-1)||(tmpPiece=='b'&&endRow==0)){
            newBoard[endRow][endCol] = Character.toUpperCase(newBoard[endRow][endCol]);
        }
        return move;
    }

    private static void removePiece(char[][] board, int i, int j){
        /* provided code. You may call this method, but you should NOT modify it */
        board[i][j] = '_';
    }

    private static boolean validIndex(char[][] board, int i, int j){
        /* provided code. You may call this method, but you should NOT modify it */
        if(i<0 || j<0 || i>=board.length || j>=board[0].length) return false;
        return true;
    }

    private static boolean redCanMoveHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
        //check if index out of bounds
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) {
            return false;
        }

        //check if end row & column are exactly 1 > or < starting row & column
        if(Math.abs(startRow-endRow)!=1 || Math.abs(startCol-endCol)!=1) {
            return false;
        }

        //check if there's no red checker at starting row or column
        //check if there's wrong colored checker(black)
        if((board[startRow][startCol] != 'r') && (board[startRow][startCol] != 'R')) {
            return false;
        }

        //check if checker at starting row & column isn't a king.
        //check if end row > start row --> if not move invalid.
        if (board[startRow][startCol] == 'r' && endRow <= startRow) {
            return false;
        }

        //check if ending row & column is unoccupied
        if(board[endRow][endCol] != '_') {
            return false;
        }
        return true;
    }

    private static boolean redCanJumpHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
        /* provided code. You may call this method, but you should NOT modify it */
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) return false;
        if(Math.abs(startRow-endRow)!=2 || Math.abs(startCol-endCol)!=2) return false;
        if(board[startRow][startCol] == 'r'){
            if(endRow != startRow+2) return false;
            if(board[endRow][endCol] != '_') return false;
            if( (endCol == startCol+2 && (board[startRow+1][startCol+1] == 'b' || board[startRow+1][startCol+1] == 'B')) ||
                (endCol == startCol-2 && (board[startRow+1][startCol-1] == 'b' || board[startRow+1][startCol-1] == 'B'))){
                return true;
            } else {
                return false;
            }
        } else if(board[startRow][startCol] == 'R'){
            if(board[endRow][endCol] != '_') return false;
            if(endRow > startRow && endCol > startCol){
                //down-right
                if(board[startRow+1][startCol+1]=='b' || board[startRow+1][startCol+1]=='B') return true;
                else return false;
            } else if(endRow < startRow && endCol > startCol){
                //up-right
                if(board[startRow-1][startCol+1]=='b' || board[startRow-1][startCol+1]=='B') return true;
                else return false;
            } else if(endRow > startRow && endCol < startCol){
                //down-left
                if(board[startRow+1][startCol-1]=='b' || board[startRow+1][startCol-1]=='B') return true;
                else return false;
            } else if(endRow < startRow && endCol < startCol){
                //up-left
                if(board[startRow-1][startCol-1]=='b' || board[startRow-1][startCol-1]=='B') return true;
                else return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    private static boolean blackCanMoveHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
        //check if index out of bounds
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) {
            return false;
        }

        //check if end row & column are exactly 1 > or < starting row & column
        if(Math.abs(startRow-endRow)!=1 || Math.abs(startCol-endCol)!=1) {
            return false;
        }

        //check if there's no black checker at starting row or column
        //check if there's wrong colored checker(red)
        if((board[startRow][startCol] != 'b') && (board[startRow][startCol] != 'B')) {
            return false;
        }

        //check if checker at starting row & column isn't a king.
        //check if end row < start row --> if not move invalid.
        if (board[startRow][startCol] == 'b' && endRow >= startRow) {
            return false;
        }
            
        //check if ending row & column is unoccupied
        if(board[endRow][endCol] != '_') {
            return false;
        }
        return true;
    }
    
    private static boolean blackCanJumpHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
        /* provided code. You may call this method, but you should NOT modify it */
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) return false;
        if(Math.abs(startRow-endRow)!=2 || Math.abs(startCol-endCol)!=2) return false;
        if(board[startRow][startCol] == 'b'){
            if(endRow != startRow-2) return false;
            if(board[endRow][endCol] != '_') return false;
            if( (endCol == startCol+2 && (board[startRow-1][startCol+1] == 'r' || board[startRow-1][startCol+1] == 'R')) ||
                (endCol == startCol-2 && (board[startRow-1][startCol-1] == 'r' || board[startRow-1][startCol-1] == 'R'))){
                return true;
            } else {
                return false;
            }
        } else if(board[startRow][startCol] == 'B'){
            if(board[endRow][endCol] != '_') return false;
            if(endRow > startRow && endCol > startCol){
                //down-right
                if(board[startRow+1][startCol+1]=='r' || board[startRow+1][startCol+1]=='R') return true;
                else return false;
            } else if(endRow < startRow && endCol > startCol){
                //up-right
                if(board[startRow-1][startCol+1]=='r' || board[startRow-1][startCol+1]=='R') return true;
                else return false;
            } else if(endRow > startRow && endCol < startCol){
                //down-left
                if(board[startRow+1][startCol-1]=='r' || board[startRow+1][startCol-1]=='R') return true;
                else return false;
            } else if(endRow < startRow && endCol < startCol){
                //up-left
                if(board[startRow-1][startCol-1]=='r' || board[startRow-1][startCol-1]=='R') return true;
                else return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ArrayList<MiniCheckers> possibleMoves(Player player) {
        /* provided code. You may call this method, but you should NOT modify it */
        char[][] curBoard = this.getBoard();
        ArrayList<MiniCheckers> rv = new ArrayList<MiniCheckers>();
        if(player == this.red){
            boolean couldJump = false;
            for(int i=0;i<curBoard.length;i++){
                for(int j=0;j<curBoard[i].length;j++){
                    if(board[i][j]=='r' || board[i][j] =='R'){
                        if(redCanJumpHere(board,i,j,i-2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j-2);
                            removePiece(newBoard.board,i-1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(redCanJumpHere(board,i,j,i-2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j+2);
                            removePiece(newBoard.board,i-1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(redCanJumpHere(board,i,j,i+2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j-2);
                            removePiece(newBoard.board,i+1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(redCanJumpHere(board,i,j,i+2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j+2);
                            removePiece(newBoard.board,i+1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                    }
                }
            }
            if(!couldJump){
                for(int i=0;i<curBoard.length;i++){
                    for(int j=0;j<curBoard[i].length;j++){
                        if(board[i][j]=='r' || board[i][j]=='R'){                            
                            if(redCanMoveHere(board,i,j,i-1,j-1)){
                                rv.add(movePiece(i,j,i-1,j-1));
                            }
                            if(redCanMoveHere(board,i,j,i-1,j+1)){
                                rv.add(movePiece(i,j,i-1,j+1));
                            }
                            if(redCanMoveHere(board,i,j,i+1,j-1)){
                                rv.add(movePiece(i,j,i+1,j-1));
                            }
                            if(redCanMoveHere(board,i,j,i+1,j+1)){
                                rv.add(movePiece(i,j,i+1,j+1));
                            }
                        }
                    }
                }
            }
        } else if(player==this.black){
            boolean couldJump = false;
            //check for jumps first
            for(int i=0;i<curBoard.length;i++){
                for(int j=0;j<curBoard[i].length;j++){
                    if(board[i][j]=='b' || board[i][j] =='B'){
                        if(blackCanJumpHere(board,i,j,i-2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j-2);
                            removePiece(newBoard.board,i-1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(blackCanJumpHere(board,i,j,i-2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j+2);
                            removePiece(newBoard.board,i-1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(blackCanJumpHere(board,i,j,i+2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j-2);
                            removePiece(newBoard.board,i+1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(blackCanJumpHere(board,i,j,i+2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j+2);
                            removePiece(newBoard.board,i+1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                    }
                }
            }
            if(!couldJump){
                for(int i=0;i<curBoard.length;i++){
                    for(int j=0;j<curBoard[i].length;j++){
                        if(board[i][j]=='b' || board[i][j]=='B'){
                            if(blackCanMoveHere(board,i,j,i-1,j-1)){
                                rv.add(movePiece(i,j,i-1,j-1));
                            }
                            if(blackCanMoveHere(board,i,j,i-1,j+1)){
                                rv.add(movePiece(i,j,i-1,j+1));
                            }
                            if(blackCanMoveHere(board,i,j,i+1,j-1)){
                                rv.add(movePiece(i,j,i+1,j-1));
                            }
                            if(blackCanMoveHere(board,i,j,i+1,j+1)){
                                rv.add(movePiece(i,j,i+1,j+1));
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("Unrecognized player?!");
        }
        return rv;
    }
}