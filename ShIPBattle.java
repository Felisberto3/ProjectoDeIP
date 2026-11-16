import java.util.Scanner;

/**
 * Jogo ShIPBattle, uma adaptação do jogo Batalha Naval (Bimaru).
 * Permite validar tabuleiros iniciais e jogar com uma interface textual.
 *
 * @author Felisberto Alberto Kalenhe Mukinda, nº 66880
 * @author Eliana de Oliveira Dinis, nº 66869.
 */
public class ShIPBattle {

    /** Tamanho do maior navio da armada (couraçado). */
    static int MAX_SHIP_SIZE = 4;

    /** Número mínimo de linhas e colunas de um tabuleiro. */
    static int MIN_DIMENSION = 1;

    /** Número máximo de linhas e colunas de um tabuleiro. */
    static int MAX_DIMENSION = 10;

    /**
     * Determina o número de linha a que pertence uma posição do tabuleiro.
     * 
     * @param pos        posicão do tabuleiro, começando em {@code 0}
     * @param numColumns número total de colunas do tabuleiro
     * @requires {@code pos >= 0 && numColums > 0}
     * @ensures {@code \result == pos / numColumns + 1}
     * @return número de linha onde essa posição se encontra
     * 
     */
    public static int rowOfPosition(int pos, int numColumns) {
        return (pos / numColumns) + 1;
    }

    /**
     * Determina o número de coluna a que pertence uma posição do tabuleiro.
     * 
     * @param pos        posicao do tabuleiro, começando em {@code 0}
     * @param numColumns número de colunas do tabuleiro
     * @requires {@code pos >= 0 && numColums > 0}
     * @ensures {@code \result == pos % numColumns + 1}
     * @return número de coluna, começando em {@code 1}
     */
    public static int columnOfPosition(int pos, int numColumns) {
        return (pos % numColumns) + 1;
    }

    /**
     * Converte uma linha e uma coluna na posição correspondente do tabuleiro.
     * 
     * @param row        linha do tabuleiro, começando em {@code 1}
     * @param column     coluna do tabuleiro, começando em {@code 1}
     * @param numColumns número de colunas do tabuleiro
     * @requires {@code row >= 1 && column >= 1 && numColumns > 0}
     * @ensures {@code \result == (row - 1) * numOlumns + (column - 1)}
     * @return posição correspondente, começando em {@code 0}
     */
    public static int positionNumber(int row, int column, int numColumns) {
        return (row - 1) * numColumns + (column - 1);
    }

    /**
     * Calcula o número de linhas de um tabuleiro.
     * 
     * @param size       número total de posições do tabuleiro
     * @param numColumns número de colunas do tabuleiro
     * @requires {@code size > 0 && numColumns > 0 && size % numColumns == 0}
     * @ensures {@code \result == size / numColumns}
     * @return número de linhas do tabuleiro
     */
    public static int numberRows(int size, int numColumns) {
        return size / numColumns;
    }

    /**
     * Verifica se um caracter é uma peça do navio.
     * 
     * @param c caracter a verificar
     * @ensures {@code \result == (c == 'C' || c == 'L' || c == 'R' || c == 'B' || c == 'T' || c == 'M' )}
     * @return {@code true} se for uma peça de navio; caso contrário, {@code false}
     */
    public static boolean isShipPiece(char c) {
        return c == 'C' || c == 'L' || c == 'R' || c == 'B' || c == 'T' || c == 'M';
    }

    /**
     * Verifica se um caracter é uma peça de navio ou {@code 'X'} para preencher as
     * posições vazias
     * 
     * @param c caracter a verificar
     * @ensures {@code \result == (isShipPiece(c) || c == 'X')}
     * @return {@code true} se for uma peca de navio ou {@code 'X'}, caso contrário
     *         {@code false}
     */
    public static boolean isFillingChar(char c) {
        return isShipPiece(c) || c == 'X';
    }

    /**
     * Verifica se uma solução em construção contém apenas caracteres permitidos.
     * 
     * @param stc tabuleiro em construção
     * @return {@code true} se o tabuleiro em construção tiver apenas caracteres
     *         permitidos; caso contrário, {@code false}
     *         falso contrário.
     */
    public static boolean isValidBoardInConstruction(String stc) {
        if (stc == null) {
            return false;
        }
        for (int i = 0; i < stc.length(); i++) {
            char c = stc.charAt(i);
            if (c != '.' && !isFillingChar(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se todas as posições do tabuleiro estão preenchidas com as peças de
     * navios e {@code 'X'}.
     * 
     * @param stc tabuleiro em construção
     * @requires {@code stc != null }
     * @ensures {@code \result == (stc.indexOf('.') == -1)}
     * @return {@code true } se não existir nenhuma posição por preencher
     *         ({@code '.'}); caso contrário, {@code false}
     */
    public static boolean isFilled(String stc) {
        for (int i = 0; i < stc.length(); i++) {
            if (stc.charAt(i) == '.') {
                return false;
            }
        }
        return true;
    }

    /**
     * Conta as peças de navio existentes numa determinada linha.
     * 
     * @param row        linha a verificar, começando em {@code 1}
     * @param stc        tabuleiro em construção
     * @param numColumns número de colunas do tabuleiro
     * @requires {@code stc != null && row >= 1 && numColumn > 0 && stc.length() % numColumn == 0 && row <= stc.length() / numColumns}
     * @return número de peças de navio nessa linha
     */
    public static int countShipPiecesInRow(int row, String stc, int numColumns) {
        int count = 0;
        for (int col = 1; col <= numColumns; col++) {
            if (isShipPiece(stc.charAt(positionNumber(row, col, numColumns)))) {
                count++;
            }
        }
        return count;
    }

    /**
     * Conta as peças de navio existentes numa determinda coluna.
     * 
     * @param col        coluna a verificar, começando em {@code 1}
     * @param stc        tabuleiro em construção
     * @param numColumns número total de colunas no tabuleiro
     * @requires {@code stc != null && numColumn > 0 && stc.length() % numColumn == 0 && col >= 1 && col <= numColumns}
     * @return número de peças de navio nessa coluna
     */
    public static int countShipPiecesInCol(int col, String stc, int numColumns) {
        int count = 0;
        int numRows = numberRows(stc.length(), numColumns);
        for (int row = 1; row <= numRows; row++) {
            if (isShipPiece(stc.charAt(positionNumber(row, col, numColumns)))) {
                count++;
            }
        }
        return count;
    }

    /**
     * Conta as peças de água ({@code 'X'}) existentes numa determinada linha.
     * 
     * @param row        linha a verificar, começando em {@code 1}
     * @param stc        tabuleiro em construção
     * @param numColumns número de colunas do tabuleiro
     * @requires {@code stc != null && row >= 1 && numColumn > 0 && stc.length() % numColumn == 0 && row <= stc.length() / numColumns}
     * @return número de peças de águas nessa linha
     */
    public static int countWaterPiecesInRow(int row, String stc, int numColumns) {
        int count = 0;
        for (int col = 1; col <= numColumns; col++) {
            if (stc.charAt(positionNumber(row, col, numColumns)) == 'X') {
                count++;
            }
        }
        return count;
    }

    /**
     * Conta as peças de água ({@code 'X'}) existentes numa determinada coluna
     * 
     * @param col        coluna a verificar, começando em {@code 1}
     * @param stc        tabuleiro em construção
     * @param numColumns número de colunas do tabuleiro
     * @requires {@code stc != null && numColumn > 0 && stc.length() % numColumn == 0 && col >= 1 && col <= numColumns}
     * @return número de peças de água nessa coluna
     */
    public static int countWaterPiecesInCol(int col, String stc, int numColumns) {
        int count = 0;
        int numRows = numberRows(stc.length(), numColumns);
        for (int row = 1; row <= numRows; row++) {
            if (stc.charAt(positionNumber(row, col, numColumns)) == 'X') {
                count++;
            }
        }
        return count;
    }

    /**
     * Verifica se uma string representa um tabuleiro com dimensões e pistas
     * compatíveis.
     * 
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param stc      tabuleiro a verificar
     * @param rowClues pistas das linhas
     * @param colClues pistas das colunas
     * @requires {@code rows>= 0 0 && cols >= 0 && stc != null && rowClues != null && colClues != null}
     * @return {@code true} se o tabuleiro respeitar as dimensões e as pistas; caso
     *         contrário, {@code false}
     */
    public static boolean isBoard(int rows, int cols, String stc, String rowClues, String colClues) {
        if (stc == null || rowClues == null || colClues == null) {
            return false;
        }
        return stc.length() == rows * cols
                && rowClues.length() == rows && colClues.length() == cols
                && areDigits(rowClues) && areDigits(colClues)
                && rowCluesPossible(rows, cols, stc, rowClues)
                && colCluesPossible(rows, cols, stc, colClues);
    }

    /**
     * Verifica se todos os caracteres de uma String são dígitos.
     *
     * @param s String a verificar
     * @return true se todos os caracteres de s são dígitos
     */
    public static boolean areDigits(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se cada pista de linha é compatível com as peças já preenchidas.
     *
     * @param rows     número de linhas
     * @param cols     número de colunas
     * @param stc      solução-tabuleiro-em-construção
     * @param rowClues pistas de linha
     * @requires rowClues.length() == rows && areDigits(rowClues)
     *           && stc.length() == rows * cols
     * @return true se em cada linha as peças de navio não excedem a pista
     *         e há posições suficientes para a atingir
     */
    public static boolean rowCluesPossible(int rows, int cols, String stc, String rowClues) {
        for (int row = 1; row <= rows; row++) {
            int clue = Character.getNumericValue(rowClues.charAt(row - 1));
            int ships = countShipPiecesInRow(row, stc, cols);
            int water = countWaterPiecesInRow(row, stc, cols);
            if (ships > clue || cols - water < clue) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se cada pista de coluna é compatível com as peças já preenchidas.
     *
     * @param rows     número de linhas
     * @param cols     número de colunas
     * @param stc      solução-tabuleiro-em-construção
     * @param colClues pistas de coluna
     * @requires colClues.length() == cols && areDigits(colClues)
     *           && stc.length() == rows * cols
     * @return true se em cada coluna as peças de navio não excedem a pista
     *         e há posições suficientes para a atingir
     */
    public static boolean colCluesPossible(int rows, int cols, String stc, String colClues) {
        for (int col = 1; col <= cols; col++) {
            int clue = Character.getNumericValue(colClues.charAt(col - 1));
            int ships = countShipPiecesInCol(col, stc, cols);
            int water = countWaterPiecesInCol(col, stc, cols);
            if (ships > clue || rows - water < clue) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se o tabuleiro em construção é uma solução para o problema.
     * 
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param board    tabuleiro inicial, com posições por preencher representadas
     *                 por {@code '.'}
     * @param rowClues pistas das linhas
     * @param colClues pistas das colunas
     * @param stc      tabuleiro em contrução
     * @requires {@ode rows >= 0 && cols >= 0 && board != null && rowClues != null
     *           && colCLues != null && stc != null}
     * @ensures {@code \result == true} se o tabuleiro tiver o tamanho correto,
     *          estiver totalmente preenchido, contiver os navios exigidos,
     *          respeitar as posições fixas do tabuleiro inicial e as pistas; caso
     *          contrário, {@code \result == false}
     * @return {@code true} se o tabuleiro for considerado uma solução; caso
     *         contrário, {@code false}
     */
    public static boolean isSolution(int rows, int cols, String board, String rowClues, String colClues, String stc) {
        return isValidBoardInConstruction(stc)
                && stc.length() == board.length() && stc.length() == rows * cols
                && isFilled(stc)
                && keepsFixedPieces(board, stc)
                && matchesClues(rows, cols, rowClues, colClues, stc)
                && allShipsWellSurrounded(rows, cols, stc)
                && hasAllShips(rows, cols, stc);
    }

    /**
     * Verifica se todas as peças preenchidas em board estão iguais em stc.
     *
     * @param board tabuleiro inicial
     * @param stc   solução-tabuleiro-em-construção
     * @requires board.length() == stc.length()
     * @return true se nenhuma peça fixa de board foi alterada em stc
     */
    public static boolean keepsFixedPieces(String board, String stc) {
        for (int i = 0; i < board.length(); i++) {
            if (board.charAt(i) != '.' && board.charAt(i) != stc.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se o número de peças de navio em cada linha e coluna
     * coincide com as pistas.
     *
     * @param rows     número de linhas
     * @param cols     número de colunas
     * @param rowClues pistas de linha
     * @param colClues pistas de coluna
     * @param stc      solução-tabuleiro-em-construção
     * @requires rowClues.length() == rows && colClues.length() == cols
     *           && stc.length() == rows * cols
     * @return true se todas as contagens coincidem com as pistas
     */
    public static boolean matchesClues(int rows, int cols, String rowClues, String colClues, String stc) {
        for (int row = 1; row <= rows; row++) {
            if (countShipPiecesInRow(row, stc, cols) != Character.getNumericValue(rowClues.charAt(row - 1))) {
                return false;
            }
        }
        for (int col = 1; col <= cols; col++) {
            if (countShipPiecesInCol(col, stc, cols) != Character.getNumericValue(colClues.charAt(col - 1))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Devolve o carácter numa posição (linha, coluna), ou 'X' se estiver
     * fora do tabuleiro.
     *
     * @param rows número de linhas
     * @param cols número de colunas
     * @param stc  solução-tabuleiro-em-construção
     * @param row  linha (a contar de 1), pode estar fora do tabuleiro
     * @param col  coluna (a contar de 1), pode estar fora do tabuleiro
     * @requires stc.length() == rows * cols
     * @return o carácter na posição, ou 'X' se estiver fora do tabuleiro
     */
    public static char cellAt(int rows, int cols, String stc, int row, int col) {
        if (row < 1 || row > rows || col < 1 || col > cols) {
            return 'X';
        }
        return stc.charAt(positionNumber(row, col, cols));
    }

    /**
     * Verifica se uma peça de navio está bem rodeada: as 4 diagonais são água
     * e os vizinhos ortogonais são os que o tipo de peça exige.
     *
     * @param rows número de linhas
     * @param cols número de colunas
     * @param stc  solução-tabuleiro-em-construção
     * @param row  linha da peça (a contar de 1)
     * @param col  coluna da peça (a contar de 1)
     * @requires isShipPiece(stc.charAt(positionNumber(row, col, cols)))
     * @return true se a peça está bem rodeada, false caso contrário
     */
    public static boolean isWellSurrounded(int rows, int cols, String stc, int row, int col) {
        char piece = cellAt(rows, cols, stc, row, col);
        char up = cellAt(rows, cols, stc, row - 1, col);
        char down = cellAt(rows, cols, stc, row + 1, col);
        char left = cellAt(rows, cols, stc, row, col - 1);
        char right = cellAt(rows, cols, stc, row, col + 1);

        boolean diagonalsOk = cellAt(rows, cols, stc, row - 1, col - 1) == 'X'
                && cellAt(rows, cols, stc, row - 1, col + 1) == 'X'
                && cellAt(rows, cols, stc, row + 1, col - 1) == 'X'
                && cellAt(rows, cols, stc, row + 1, col + 1) == 'X';
        if (!diagonalsOk) {
            return false;
        }
        if (piece == 'C') {
            return up == 'X' && down == 'X' && left == 'X' && right == 'X';
        } else if (piece == 'L') {
            return up == 'X' && down == 'X' && left == 'X' && isOneOf(right, "MR");
        } else if (piece == 'R') {
            return up == 'X' && down == 'X' && right == 'X' && isOneOf(left, "LM");
        } else if (piece == 'T') {
            return left == 'X' && right == 'X' && up == 'X' && isOneOf(down, "MB");
        } else if (piece == 'B') {
            return left == 'X' && right == 'X' && down == 'X' && isOneOf(up, "TM");
        }
        boolean horizontal = up == 'X' && down == 'X'
                && isOneOf(left, "LM") && isOneOf(right, "MR");
        boolean vertical = left == 'X' && right == 'X'
                && isOneOf(up, "TM") && isOneOf(down, "MB");
        return horizontal || vertical;
    }

    /**
     * Verifica se um carácter aparece numa String de opções.
     *
     * @param c       carácter a procurar
     * @param options String com os caracteres permitidos
     * @return true se c aparece em options, false caso contrário
     */
    public static boolean isOneOf(char c, String options) {
        return options.indexOf(c) >= 0;
    }

    /**
     * Verifica se todas as peças de navio de stc estão bem rodeadas.
     *
     * @param rows número de linhas
     * @param cols número de colunas
     * @param stc  solução-tabuleiro-em-construção
     * @requires stc.length() == rows * cols && isValidBoardInConstruction(stc)
     * @return true se todas as peças de navio estão bem rodeadas
     */
    public static boolean allShipsWellSurrounded(int rows, int cols, String stc) {
        for (int pos = 0; pos < stc.length(); pos++) {
            if (isShipPiece(stc.charAt(pos))
                    && !isWellSurrounded(rows, cols, stc,
                            rowOfPosition(pos, cols), columnOfPosition(pos, cols))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calcula o comprimento do navio que começa na peça (row, col).
     *
     * @param rows número de linhas
     * @param cols número de colunas
     * @param stc  solução-tabuleiro-em-construção
     * @param row  linha da peça inicial (a contar de 1)
     * @param col  coluna da peça inicial (a contar de 1)
     * @requires a peça em (row, col) é 'C', 'L' ou 'T'
     * @return o comprimento do navio
     */
    public static int shipLength(int rows, int cols, String stc, int row, int col) {
        char piece = cellAt(rows, cols, stc, row, col);
        if (piece == 'C') {
            return 1;
        }
        int dRow = piece == 'T' ? 1 : 0;
        int dCol = piece == 'L' ? 1 : 0;
        int length = 2;
        int r = row + dRow;
        int c = col + dCol;
        while (cellAt(rows, cols, stc, r, c) == 'M') {
            length++;
            r += dRow;
            c += dCol;
        }
        return length;
    }

    /**
     * Conta quantos navios de um dado comprimento existem em stc.
     *
     * @param rows   número de linhas
     * @param cols   número de colunas
     * @param stc    solução-tabuleiro-em-construção
     * @param length comprimento dos navios a contar
     * @requires stc.length() == rows * cols
     * @return o número de navios com esse comprimento
     */
    public static int countShipsOfLength(int rows, int cols, String stc, int length) {
        int count = 0;
        for (int pos = 0; pos < stc.length(); pos++) {
            char c = stc.charAt(pos);
            if ((c == 'C' || c == 'L' || c == 'T')
                    && shipLength(rows, cols, stc, rowOfPosition(pos, cols),
                            columnOfPosition(pos, cols)) == length) {
                count++;
            }
        }
        return count;
    }

    /**
     * Verifica se há exatamente um navio de cada tamanho (1 a 4) e nenhuma
     * peça de navio a mais.
     *
     * @param rows número de linhas
     * @param cols número de colunas
     * @param stc  solução-tabuleiro-em-construção
     * @requires stc.length() == rows * cols
     * @return true se a armada está completa e sem peças a mais
     */
    static boolean hasAllShips(int rows, int cols, String stc) {
        int totalPieces = 0;
        for (int length = 1; length <= MAX_SHIP_SIZE; length++) {
            if (countShipsOfLength(rows, cols, stc, length) != 1) {
                return false;
            }
            totalPieces += length;
        }
        int piecesInBoard = 0;
        for (int i = 0; i < stc.length(); i++) {
            if (isShipPiece(stc.charAt(i))) {
                piecesInBoard++;
            }
        }
        return piecesInBoard == totalPieces;
    }

    /**
     * Imprime o tabuleiro em forma de quadrícula com as pistas das linhas e
     * colunas.
     * 
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param stc      tabuleiro a imprimir
     * @param colClues pistas das colunas
     * @param rowClues Pistas das linhas
     * @requires {@code rows > 0 && cols > 0 && stc != null && stc.length() >= rows * cols && rowClue != null && colClues != null}
     * @ensures o tabuleiro e as respetivas pistas são escritas na consola
     */
    public static void printBoard(int rows, int cols, String stc, String rowClues, String colClues) {
        for (int i = 0; i < rows; i++) {
            System.out.println(stc.substring(i * cols, (i + 1) * cols)
                    + " " + rowClues.charAt(i));
        }
        System.out.println(colClues);
    }

    /**
     * Verifica se uma jogada pode ser realizada numa posição do tabuleiro inicial.
     * 
     * @param stc   tabuleiro em construção
     * @param board tabuleiro inicial
     * @param pos   posição onde se pretende jogar, começando em {@code 0}
     * @param c     caracter que se pretende colocar
     * @requires {@code stc != null && board != null}
     * @return {@code true} se a jogada for válida; caso contrário, {@ode false}
     */
    public static boolean isValidMove(String stc, String board, int pos, char c) {
        if (pos < 0 || pos >= stc.length()) {
            return false;
        }
        if (board.charAt(pos) != '.') {
            return false;
        }
        return c == '.' || isFillingChar(c);
    }

    /**
     * Cria uma nova versão do tabuleiro em construção com uma jogada aplicada.
     * 
     * @param stc tabuleiro em construção
     * @param pos tabuleiro a alterar, começando em {@code 0}
     * @param c   carácter a colocar na posição
     * @requires {@ode stc != null && pos >= 0 && pos < stc.length()}
     * @return novo tabuleiro com jogada aplicada
     */
    public static String playMove(String stc, int pos, char c) {
        return stc.substring(0, pos) + c + stc.substring(pos + 1);
    }

    /**
     * Executa a interação do jogo, permitindo ao utilizador introduzir jogadas.
     * 
     * @param sc       canal de leitura de dados introduzidos pelo utilizador
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param board    tabuleiro inicial
     * @param rowClues pistas das linhas
     * @param colClues pistas das colunas
     * @requires {@code sc != null && rows > 0 && cols > 0 && board != null && rowClues != null && colClues != null}
     * @ensures o jogo termina quando o utilizador introduz {@code -1} ou quando
     *          encontra uma solução
     */
    public static void play(Scanner sc, int rows, int cols, String board, String rowClues, String colClues) {
        String stc = board;
        printBoard(rows, cols, stc, rowClues, colClues);

        boolean finished = false;
        while (!finished) {
            System.out.println("Introduza uma jogada (ou -1 para sair).");
            System.out.print("Posicao: ");
            int pos = sc.nextInt();

            if (pos == -1) {
                System.out.println("Jogo terminado.");
                finished = true;
            } else {
                System.out.print("Jogada: ");
                char c = sc.next().charAt(0);

                if (isValidMove(stc, board, pos, c)) {
                    stc = playMove(stc, pos, c);
                } else {
                    System.out.println("Jogada inválida! Por favor tente novamente, "
                            + "ou indique -1 como posição de jogada para terminar.");
                }
                printBoard(rows, cols, stc, rowClues, colClues);

                if (isSolution(rows, cols, board, rowClues, colClues, stc)) {
                    System.out.println("Parabéns!!! Encontrou todos os navios em ShIPBattle!");
                    finished = true;
                }
            }
        }
    }

    /**
     * Verifica se os dados fornecidos definem um tabuleiro inicial válido.
     *
     * @param rows     número de linhas
     * @param cols     número de colunas
     * @param board    tabuleiro inicial
     * @param rowClues pistas de linha
     * @param colClues pistas de coluna
     * @return true se rows e cols estão entre 1 e 10 e os dados formam
     *         um tabuleiro inicial válido, false caso contrário
     */
    static boolean isValidInput(int rows, int cols, String board, String rowClues, String colClues) {
        return rows >= MIN_DIMENSION && rows <= MAX_DIMENSION
                && cols >= MIN_DIMENSION && cols <= MAX_DIMENSION
                && isValidBoardInConstruction(board)
                && isBoard(rows, cols, board, rowClues, colClues);
    }

    /**
     * Inicia o programa, obtém os dados do jogo do tabuleiro e começa o jogo se
     * forem válidos.
     * 
     * @param args argumentos fornecidos ao programa; podem conter as dimensões, o
     *             tabuleiro e as pistas
     * @ensures os dados do tabuleiro são obtidos e, se forem válidos, o jogo é
     *          iniciado; caso contrário, é apresentada uma mensagem de erro
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int rows;
        int cols;
        String board;
        String rowClues;
        String colClues;

        if (args.length == 5) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
            board = args[2];
            rowClues = args[3];
            colClues = args[4];
        } else {
            System.out.print("Número de linhas: ");
            rows = sc.nextInt();
            System.out.print("Número de colunas: ");
            cols = sc.nextInt();
            System.out.print("Tabuleiro inicial (string de tamanho " + (rows * cols) + "): ");
            board = sc.next();
            System.out.print("Pistas de linha: ");
            rowClues = sc.next();
            System.out.print("Pistas de coluna: ");
            colClues = sc.next();
        }

        if (isValidInput(rows, cols, board, rowClues, colClues)) {
            play(sc, rows, cols, board, rowClues, colClues);
        } else {
            System.out.println("Tabuleiro inicial inválido! Verifique o tamanho, "
                    + "as pistas fornecidas, ou as peças no tabuleiro inicial.");
        }
        sc.close();
    }
}
