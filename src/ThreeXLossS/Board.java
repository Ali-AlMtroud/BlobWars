package ThreeXLossS;

import java.util.Arrays;

public class Board {

    char[][] board = {{'x', '_', 'o'}, {'_', '_', '_'}, {'x', '_', 'o'}};
    char play_value = 'x';
    char computer_value = 'o';
    int level = 0;
    // There is two level moves : 1- is near that copy 2- far and don't copy but replace
    int[][] nearMoves = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };
    int[][] farMoves = {
            {-2, -2}, {-2, -1}, {-2, 0}, {-2, 1}, {-2, 2},
            {-1, -2}, {-1, 2},
            {0, -2}, {0, 2},
            {1, -2}, {1, 2},
            {2, -2}, {2, -1}, {2, 0}, {2, 1}, {2, 2}
    };

    /**
     * Constructors
     */
    public Board() {
        // TODO Auto-generated constructor stub
    }

    public Board(int level) {
        this.level = level;
    }

    public Board(int row, int col) {
        // TODO Auto-generated constructor stub
        this.board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.board[i][j] = '_';
            }
        }
    }

    public Board(int row, int col, int level) {
        // TODO Auto-generated constructor stub
        this.board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.board[i][j] = '_';
            }
        }
        this.board[0][0] = this.play_value;
        this.board[this.board.length - 1][0] = this.play_value;
        this.board[0][this.board[0].length - 1] = this.computer_value;
        this.board[this.board.length - 1][this.board[0].length - 1] = this.computer_value;
        this.level = level;
    }

    /**
     * @param from
     * @param to
     * @return 1 if near 2 if far
     */
    public int isMoveValid(Move from, Move to) {
        // check near move
        for (int i = 0; i < nearMoves.length; i++) {
            int newCol = from.col + nearMoves[i][1];
            int newRow = from.row + nearMoves[i][0];
            if (newCol == to.col && newRow == to.row)
                return 1;
        }
        for (int i = 0; i < farMoves.length; i++) {
            int newCol = from.col + farMoves[i][1];
            int newRow = from.row + farMoves[i][0];
            if (newCol == to.col && newRow == to.row)
                return 2;
        }
        return 0;
    }

    // Insert Value In Empty Place (Needed For Player)
    // when taking the move we should change all neighbors to {value}
    public void insert(char value, Move old, Move pos, int type) {
        if (this.board[pos.row][pos.col] == '_') {
            if (type == 2) { // far then remove the old cell
                this.board[old.row][old.col] = '_';
            }
            // take the move and iterate over near and make them all value
            for (int i = 0; i < nearMoves.length; i++) {
                int newCol = pos.col + nearMoves[i][1];
                int newRow = pos.row + nearMoves[i][0];
                if ((newCol >= 0) && (newCol < this.board[0].length)) {
                    if ((newRow >= 0) && (newRow < this.board.length)) {
                        if (this.board[newRow][newCol] == this.getOppositeMove(value))
                            this.board[newRow][newCol] = value;
                    }
                }
            }
            this.board[pos.row][pos.col] = value;
        }
    }

    char getOppositeMove(char value) {
        switch (value) {
            case 'o':
                return 'x';
            case 'x':
                return 'o';
        }
        return 'o';
    }

    void check_Mate() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length - 2; j++) {
                if ((this.board[i][j] == play_value && this.board[i][j + 1] == computer_value)) {
                    if (this.board[i][j] == this.play_value) {

                    }
                }
            }
        }
    }

    // Check If The Game Is Finished
    int finish() {
        int numOfX = 0;
        int numOfO = 0;
        int numberOfCells = 0;

        for (int row = 0; row < this.board.length; row++) {
            for (int j = 0; j < this.board[0].length; j++) {
                numberOfCells++;
                if (this.board[row][j] == 'x') {
                    numOfX++;
                }
                if (this.board[row][j] == 'o') {
                    numOfO++;
                }
            }
        }

        // check if cells are done
        if (numOfO + numOfX == numberOfCells) {
            if (numOfO > numOfX) {
                return 2; // computer
            } else if (numOfX > numOfO) {
                return 1;
            } else if (numOfX == numOfO) {
                return 0;
            }
        } else {
            if (numOfX == 0) {
                return 2; // computer
            } else if (numOfO == 0) {
                return 1;
            }
        }
        return -1;
    }

    //	Evaluate the Current Board // review
    int evaluate(boolean isMax) {
        int utility = 0;
        int numOfX = 0;
        int numOfO = 0;
        ///////////// Your Code ///////////
        // in this game the bored with more pieces is better to use
        // x for player
        // o is for computer
        // each time the game move the computer move not me so it is o
        // if max return +y else -x
        for (int row = 0; row < this.board.length; row++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[row][j] == this.play_value) {
                    numOfX++;
                }
                if (this.board[row][j] == this.computer_value) {
                    numOfO++;
                }
            }
        }
        if (numOfX == numOfO) {
            utility = 0;
        } else {
            if (isMax) {
                utility = numOfO;
            } else {
                utility = numOfX * -1;
            }
        }
        return utility;
    }

    // Minimax Algorithm
//    int minimax(int depth, boolean isMax) {
//        ///////////// Your Code ///////////
//
//
//        int score=evaluate(isMax);
//        if(finish() ||(depth==level))
//            return score;
//            // If Max Player
//        else if (isMax) {
//            int best = -1000;
//            for (int i = 0; i < this.board.length; i++) {
//                for (int j = 0; j < this.board.length; j++) {
//                    if(board[i][j]=='_')
//                    {
//                        board[i][j]=this.play_value;
//                        int b=minimax(depth+1,false);
//                        board[i][j]='_';
//                        if(b>best) {
//                            best = b;
//                        }
//                    }
//                }
//
//            }
//            return best;
//        }
//
//        // If Min Player
//        else {
//            int best = 1000;
//            for (int i = 0; i < this.board.length; i++) {
//                for (int j = 0; j < this.board.length; j++) {
//                    if(board[i][j]=='_')
//                    {
//                        board[i][j]=this.play_value;
//                        int b=minimax(depth+1,true);
//                        board[i][j]='_';
//                        if(b<best){
//                            best=b;}
//
//                    }
//                }
//
//            }
//            return best;
//        }
//
//    }

    // in this game the bored with more pieces is better to use
    // x for player
    // o is for computer
    // each time the game move the computer move not me so it is o
    // if max return +y else -x
    int minimax(int depth, boolean isMax) {
        int score = evaluate(isMax);
        if (finish() != -1 || (depth == level))
            return score;
            // If Max Player means computer play
        else if (isMax) {
            int best = -1000;
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[0].length; j++) {
                    // try every possible move
                    if (board[i][j] == '_') {
                        board[i][j] = this.computer_value;
                        int b = minimax(depth + 1, false);
                        board[i][j] = '_';
                        if (b > best) {
                            best = b;
                        }
                    }
                }
            }
            return best;
        }

        // If Min Player means human play.
        else {
            int best = 1000;
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[0].length; j++) {
                    // try every possible move
                    if (board[i][j] == '_') {
                        board[i][j] = this.play_value;
                        int b = minimax(depth + 1, true);
                        board[i][j] = '_';
                        if (b < best) {
                            best = b;
                        }
                    }
                }
            }
            return best;
        }
    }

    int minimax(int depth, boolean isMax, int Alpha, int Beta) {
        ///////////// Your Code ///////////
        int score = evaluate(isMax);
        if (finish() != -1 || (depth == level))
            return score;
            // If Max Player
        else if (isMax) {
            int best = -1000;
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board.length; j++) {
                    // check for near moves
                    // check for far moves
                    if (board[i][j] == '_') {
                        board[i][j] = this.computer_value;
                        int b = minimax(depth + 1, false, Alpha, Beta);
                        board[i][j] = '_';
                        if (b > best) {
                            best = b;
                        }
                        if (Beta <= b) {
                            return best;
                            // cut for beta
                        }
                        if (Alpha >= b) {
                            Alpha = b;
                            //update for alpha
                        }
                    }
                }

            }
            return best;
        }

        // If Min Player
        else {
            int best = 1000;
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[0].length; j++) {
                    // check for near moves
                    for (int nearI = 0; nearI < nearMoves.length; nearI++) {
                        int newRow = i + nearMoves[nearI][0];
                        int newCol = j + nearMoves[nearI][1];
                        // check if valid range {x,y} and valid empty move
                        if (newRow >= 0 && newRow < this.board.length &&
                                newCol >= 0 && newCol < this.board[0].length &&
                                board[newRow][newCol] == '_'
                        ) {
                            // move
                            board[newRow][newCol] = this.computer_value;
                            int b = minimax(depth + 1, true, Alpha, Beta);
                            board[newRow][newCol] = '_';
                            if (b < best) {
                                best = b;
                            }
                            if (Alpha >= b) {
                                return best;
                            }
                            if (Beta <= b) {
                                Beta = b;
                            }

                        }
                    }
                    // check for far moves
                    for (int farI = 0; farI < farMoves.length; farI++) {
                        int newRow = i + farMoves[farI][0];
                        int newCol = j + farMoves[farI][1];
                        // check if valid range {x,y} and valid empty move
                        if (newRow >= 0 && newRow < this.board.length &&
                                newCol >= 0 && newCol < this.board[0].length &&
                                board[newRow][newCol] == '_'
                        ) {
                            // move
                            board[i][j] = '_';
                            board[newRow][newCol] = this.computer_value;
                            int b = minimax(depth + 1, true, Alpha, Beta);
                            board[newRow][newCol] = '_';
                            board[i][j] = this.computer_value;
                            if (b < best) {
                                best = b;
                            }
                            if (Alpha >= b) {
                                return best;
                            }
                            if (Beta <= b) {
                                Beta = b;
                            }

                        }

                    }

                }

            }
            return best;
        }

    }

    // Start From Here To Find The Best Move By Calling The Minimax Algorithm For Each Of The Computer Possible Moves
    Move bestMove() {
        int bestVal = -1000;
        Move bestMove = new Move();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                // possible move is from to
                if (board[i][j] == this.computer_value) {
                    // testing near moves
                    for (int nearI = 0; nearI < nearMoves.length; nearI++) {
                        int newRow = i + nearMoves[nearI][0];
                        int newCol = j + nearMoves[nearI][1];
                        // check if valid range {x,y} and valid empty move
                        if (newRow >= 0 && newRow < this.board.length &&
                                newCol >= 0 && newCol < this.board[0].length &&
                                board[newRow][newCol] == '_'
                        ) {
                            // test the move
                            board[newRow][newCol] = this.computer_value;
                            //  int b = minimax(0, false);
                            int b = minimax(0, false, -1000, 1000);
                            board[newRow][newCol] = '_';
                            if (b > bestVal) {
                                bestMove.row = newRow;
                                bestMove.col = newCol;
                                bestVal = b;
                            }
                        }

                    }
                    // testing far moves
                    for (int farI = 0; farI < farMoves.length; farI++) {
                        int newRow = i + farMoves[farI][0];
                        int newCol = j + farMoves[farI][1];
                        // check if valid range {x,y} and valid empty move
                        if (newRow >= 0 && newRow < this.board.length &&
                                newCol >= 0 && newCol < this.board[0].length &&
                                board[newRow][newCol] == '_'
                        ) {
                            // test the move
                            board[i][j] = '_';
                            board[newRow][newCol] = this.computer_value;
                            //  int b = minimax(0, false);
                            int b = minimax(0, false, -1000, 1000);
                            board[newRow][newCol] = '_';
                            board[i][j] = this.computer_value;
                            if (b > bestVal) {
                                bestMove.row = newRow;
                                bestMove.col = newCol;
                                bestMove.oldMove = new Move(i, j);
                                bestMove.type = 2;
                                bestVal = b;
                            }
                        }

                    }
                }
            }
        }
        return bestMove;
    }

    void takeMove() {
        // save the board
        char[][] temp = new char[this.board.length][this.board[0].length];
        for (int i = 0; i < temp.length; i++)
            for (int j = 0; j < temp[0].length; j++)
                temp[i][j] = this.board[i][j];
        // use it
        Move MoveTo = this.bestMove();
        // restore the board
        for (int i = 0; i < temp.length; i++)
            for (int j = 0; j < temp[0].length; j++)
                this.board[i][j] = temp[i][j];
        System.out.println("best row " + (MoveTo.row + 1));
        System.out.println("best col " + (MoveTo.col + 1));
        System.out.println("best move to row " + (MoveTo.oldMove.col + 1));
        System.out.println("best move to col " + (MoveTo.oldMove.col + 1));
        System.out.println("type " + MoveTo.type);
        System.out.println(this.toString());
        this.insert(computer_value, MoveTo.oldMove, MoveTo, MoveTo.type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                sb.append(this.board[i][j]);
                sb.append(" | ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append('\n');
        }
        return sb.toString();
    }
}
