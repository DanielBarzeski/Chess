package Logic;

public class BitBoard {
    public static final int WP = 0, WN = 1, WB = 2, WR = 3, WQ = 4, WK = 5;
    public static final int BP = 6, BN = 7, BB = 8, BR = 9, BQ = 10, BK = 11;

    private long[] bitboards = new long[12];

    public BitBoard() {
        this.bitboards = new long[12];
        this.bitboards[BP] = 0x000000000000FF00L;
        this.bitboards[BQ] = 0x0000000000000008L;
        this.bitboards[BK] = 0x0000000000000010L;
        this.bitboards[BB] = 0x0000000000000024L;
        this.bitboards[BR] = 0x0000000000000081L;
        this.bitboards[BN] = 0x0000000000000042L;

        this.bitboards[WP] = 0x00FF000000000000L;
        this.bitboards[WQ] = 0x0800000000000000L;
        this.bitboards[WK] = 0x1000000000000000L;
        this.bitboards[WB] = 0x2400000000000000L;
        this.bitboards[WR] = 0x8100000000000000L;
        this.bitboards[WN] = 0x4200000000000000L;
    }

    public BitBoard(BitBoard other) {
        this.bitboards[WP] = other.bitboards[WP];
        this.bitboards[WQ] = other.bitboards[WQ];
        this.bitboards[WK] = other.bitboards[WK];
        this.bitboards[WB] = other.bitboards[WB];
        this.bitboards[WR] = other.bitboards[WR];
        this.bitboards[WN] = other.bitboards[WN];
        this.bitboards[BP] = other.bitboards[BP];
        this.bitboards[BQ] = other.bitboards[BQ];
        this.bitboards[BK] = other.bitboards[BK];
        this.bitboards[BB] = other.bitboards[BB];
        this.bitboards[BR] = other.bitboards[BR];
        this.bitboards[BN] = other.bitboards[BN];
    }

    public PieceType getPieceType(int col, int row) {
        int index = row * 8 + col;
        long mask = 1L << index;

        for (int i = 0; i < 12; i++) {
            if ((bitboards[i] & mask) != 0) {
                switch (i) {
                    case WP:
                        return PieceType.WHITE_PAWN;
                    case WN:
                        return PieceType.WHITE_KNIGHT;
                    case WB:
                        return PieceType.WHITE_BISHOP;
                    case WR:
                        return PieceType.WHITE_ROOK;
                    case WQ:
                        return PieceType.WHITE_QUEEN;
                    case WK:
                        return PieceType.WHITE_KING;
                    case BP:
                        return PieceType.BLACK_PAWN;
                    case BN:
                        return PieceType.BLACK_KNIGHT;
                    case BB:
                        return PieceType.BLACK_BISHOP;
                    case BR:
                        return PieceType.BLACK_ROOK;
                    case BQ:
                        return PieceType.BLACK_QUEEN;
                    case BK:
                        return PieceType.BLACK_KING;
                }
            }
        }

        return null;
    }


    public void movePiece(int currCol, int currRow, int nextCol, int nextRow) {
        int fromSquare = currRow * 8 + currCol;
        int toSquare = nextCol + nextRow * 8;

        long fromMask = 1L << fromSquare;
        long toMask = 1L << toSquare;

        int piece = -1;

        for (int i = 0; i < 12; i++) {
            if ((bitboards[i] & fromMask) != 0) {
                piece = i;
                break;
            }
        }

        if (piece == -1) {
            System.out.println(" אין כלי בנקודת המוצא!");
            return;
        }

        bitboards[piece] &= ~fromMask;
        bitboards[piece] |= toMask;

    }

    public boolean removePiece(int col, int row, boolean white) {
        int square = row * 8 + col;
        long mask = 1L << square;

        int start = white ? 0 : 6;
        int end   = white ? 6 : 12;

        for (int i = start; i < end; i++) {
            if ((bitboards[i] & mask) != 0) {
                bitboards[i] &= ~mask;
                return true;
            }
        }
        return false;
    }



    public long getWhitePawns() {
        return bitboards[WP];
    }

    public long getWhiteKnights() {
        return bitboards[WN];
    }

    public long getWhiteBishops() {
        return bitboards[WB];
    }

    public long getWhiteRooks() {
        return bitboards[WR];
    }

    public long getWhiteQueens() {
        return bitboards[WQ];
    }

    public long getWhiteKing() {
        return bitboards[WK];
    }

    public long getBlackPawns() {
        return bitboards[BP];
    }

    public long getBlackKnights() {
        return bitboards[BN];
    }

    public long getBlackBishops() {
        return bitboards[BB];
    }

    public long getBlackRooks() {
        return bitboards[BR];
    }

    public long getBlackQueens() {
        return bitboards[BQ];
    }

    public long getBlackKing() {
        return bitboards[BK];
    }

}
