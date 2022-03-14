package ThreeXLossS;


public class Move {
    int row, col, type;
    Move oldMove;

    public Move() {
        // TODO Auto-generated constructor stub
        this.row = 0;
        this.col = 0;
        this.oldMove = new Move(-1, -1);
        this.type = 1;
    }

    public Move(int x, int y) {
        this.row = x;
        this.col = y;
    }

    public boolean is_equal(Move pos) {
        if ((this.row == pos.row) && (this.col == pos.col))
            return true;
        return false;
    }

}
