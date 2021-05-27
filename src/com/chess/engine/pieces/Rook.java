package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;
import com.chess.engine.board.Utilities;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Rook extends Piece {

    private static final int[] POSSIBLE_LEGAL_MOVES = {-8, -1, 1, 8};

    Rook(final int piecePosition, final Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> findLegalMove(final Board board) {

        int possibleDestinationPosition;
        final List<Move> legalMoves = new ArrayList<>();

        for (final int possibleDestinationOffset : POSSIBLE_LEGAL_MOVES) {
            possibleDestinationPosition = this.piecePosition + possibleDestinationOffset;

            // same looping as with the bishop but with different offsets
            while (Utilities.isValidSquarePosition(possibleDestinationPosition)) {
                // break the loop if algorithm breaks
                if (firstColumn(this.piecePosition, possibleDestinationOffset) ||
                    eightColumn(this.piecePosition, possibleDestinationOffset)) {
                    break;
                }

                possibleDestinationPosition += possibleDestinationOffset;

                if (Utilities.isValidSquarePosition(possibleDestinationPosition)) {
                    final Square possibleDestinationSquare = board.getSquare(possibleDestinationPosition);

                    // if the destination square is not occupied by an enemy piece
                    if (!possibleDestinationSquare.isSquareFilled()) {
                        legalMoves.add(new BigMove(board, this, possibleDestinationPosition));
                    } else {
                        final Piece pieceAtDestination = possibleDestinationSquare.getPiece();
                        final Color pieceColor = pieceAtDestination.getPieceColor();

                        // checking if the piece is capturing an enemy piece
                        if (this.pieceColor != pieceColor) {
                            legalMoves.add(new BigMove(board, this, possibleDestinationPosition));
                        }
                    }
                    break;
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    // check where the algorithm breaks
    private static boolean firstColumn(final int currentPosition, final int possibleOffset) {
        return Utilities.FIRST_COLUMN[currentPosition] && (possibleOffset == -1);
    }

    private static boolean eightColumn(final int currentPosition, final int possibleOffset) {
        return Utilities.EIGHT_COLUMN[currentPosition] && (possibleOffset == 1);
    }
}