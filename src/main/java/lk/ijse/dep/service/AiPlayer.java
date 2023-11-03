package lk.ijse.dep.service;

import static lk.ijse.dep.service.Piece.EMPTY;

public class AiPlayer extends Player{
    public AiPlayer(Board board) {

        super(board);
    }

    @Override
    public void movePiece(int col) {

            col = this.predictColumn();

        board.updateMove(col, Piece.GREEN);
        board.getBoardUI().update(col, false);
        Winner winner = board.findWinner();
        if (winner.getWinningPiece() != EMPTY) {
            board.getBoardUI().notifyWinner(winner);
        } else if (board.existLegalMoves()) {
            board.getBoardUI().notifyWinner(new Winner(EMPTY));
        }
    }

    private int predictColumn() {
        boolean isUserWinning = false;
        int tiedColumn = 0;
        for (int i = 0; i < 6; ++i) {
            if (this.board.isLegalMove(i)) {
                final int row = this.board.findNextAvailableSpot(i);
                this.board.updateMove(i, Piece.GREEN);
                final int heuristicVal = this.minimax(0, false);
                this.board.updateMove( i, row, Piece.EMPTY);
                if (heuristicVal == 1) {
                    return i;
                }
                if (heuristicVal == -1) {
                    isUserWinning = true;
                }
                else {
                    tiedColumn = i;
                }
            }
        }
        if (isUserWinning && this.board.isLegalMove(tiedColumn)) {
            return tiedColumn;
        }
        int col = 0;
        do {
            col = (int)(Math.random() * 6.0);
        } while (!this.board.isLegalMove(col));
        return col;
    }

    private int minimax(final int depth, final boolean maximizingPlayer) {
        final Winner winner = this.board.findWinner();
        if (winner.getWinningPiece() == Piece.GREEN) {
            return 1;
        }
        if (winner.getWinningPiece() == Piece.BLUE) {
            return -1;
        }
        if (!this.board.existLegalMoves() || depth == 2) {
            return 0;
        }
        if (!maximizingPlayer) {
            for (int i = 0; i < 6; ++i) {
                if (this.board.isLegalMove(i)) {
                    final int row = this.board.findNextAvailableSpot(i);
                    this.board.updateMove(i, Piece.BLUE);
                    final int heuristicVal = this.minimax(depth + 1, true);
                    this.board.updateMove(i, row, EMPTY);
                    if (heuristicVal == -1) {
                        return heuristicVal;
                    }
                }
            }
        }
        else {
            for (int i = 0; i < 6; ++i) {
                if (this.board.isLegalMove(i)) {
                    final int row = this.board.findNextAvailableSpot(i);
                    this.board.updateMove(i, Piece.GREEN);
                    final int heuristicVal = this.minimax(depth + 1, false);
                    this.board.updateMove(i, row, EMPTY);
                    if (heuristicVal == 1) {
                        return heuristicVal;
                    }
                }
            }
        }
        return 0;
    }
}


