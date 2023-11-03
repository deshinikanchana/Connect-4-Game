package lk.ijse.dep.service;

public class BoardImpl implements Board {

    private final Piece [][] pieces;
    private final BoardUI boardUI;
    public BoardImpl(BoardUI boardUI){
        this.boardUI = boardUI;

        pieces = new Piece [6][5];
        for(int i =0;i<6;i++){
            for(int j =0;j<5;j++){
                pieces [i][j] = Piece.EMPTY;
            }
        }
    }
    @Override
    public BoardUI getBoardUI() {

        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        for(int i =0;i<5;i++){
            if(  pieces [col][i] == Piece.EMPTY){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        int value = findNextAvailableSpot(col);
        return value >= 0;

    }
    @Override
    public boolean existLegalMoves() {
        for (int column = 0; column < 6; column++) {
            boolean ans = isLegalMove(column);
            if (ans) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateMove(int col, Piece move) {
        int spot = findNextAvailableSpot(col);
        pieces[col][spot] = move;
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row] = move;
    }


    @Override
    public Winner findWinner() {
        for (int column = 0; column < 6; column++) {
            int matches = 1;
            int spot = findNextAvailableSpot(column);
            if(spot == -1) {
                for (int row = 1; row < 5 ; row++) {
                    if (pieces[column][row] == pieces[column][row - 1]) {
                        matches++;
                        if (matches == 4) {
                            return new Winner(this.pieces[column][row], column, row - 3, column, row);
                        }
                    } else {
                        matches = 1;
                        if (row >= 2) {
                            break;
                        }
                    }
                }
            } else {
                for (int row = 1; row < spot; row++) {
                    if (pieces[column][row] == pieces[column][row - 1]) {
                        matches++;
                        if (matches == 4) {
                            return new Winner(this.pieces[column][row], column, row - 3, column, row);
                        }
                    } else {
                        matches = 1;
                        if (row >= 2) {
                            break;
                        }
                    }
                }
            }
        }
        for (int row2 = 0; row2 < 5; row2++) {
            int matches = 1;
            for (int col2 = 1; col2 < 6; col2++) {
                if (pieces[col2][row2] ==  pieces[col2 - 1][row2] && pieces[col2][row2] != Piece.EMPTY) {
                    matches++;
                    if (matches == 4) {
                        return new Winner(this.pieces[col2][row2], col2 - 3, row2, col2, row2);
                    }
                } else {
                    matches = 1;
                    if (col2 >= 3) {
                        break;
                    }
                }
            }
        }
        return new Winner(Piece.EMPTY);
    }
}
