import jdk.nashorn.internal.objects.Global;

import java.util.Scanner;

/**
 * Created by israel on 6/13/2017.
 */
public class TicTacToe {

    char[][] myBoard;
    private int currentTurn = 0;
    boolean insertX = true;
    private int emptySpots = 9;
    int selection;

    public static void main(String[] args) {

        String p1 = " ";
        String p2 = " ";

        if(args.length == 0) {
            p1 = "Player 1";
            p2 = "Player2";
        } else if(args.length == 2) {
            p1 = args[0];
            p2 = args[1];
            char player1 = p1.charAt(2);
            char player2 = p2.charAt(1);
            if(player1 == 'h')
                p1 = "Player 1";
            else
                p1 = "Player 1 (computer)";
            if(player2 == 'h')
                p2 = "player 2";
            else
                p2 = "Player 2 (computer)";
        } else {
            System.out.println("***Error! Please enter two command line arguments!\n");
            System.exit(0);
        }

        new TicTacToe().startGame(p1, p2);
    }

    public void startGame(String p1, String p2) {

        myBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                myBoard[i][j] = ' ';
            }
        }

        //Start of loop
        while(emptySpots >= 0) {
            printBoard(myBoard);
            if(checkWinner() == false) {
                if(emptySpots == 0)
                    printTie();
                else
                    nextMove(p1, p2);
            }
            //When checkWinner() returns true
            else
                printBye();
        }
    }

    public boolean checkWinner() {

        char ch;
        //Check rows
        for(int i=0; i<3; i++) {
            if(myBoard[i][0] != ' ') {
                ch = myBoard[i][0];
                if(myBoard[i][1] == ch && myBoard[i][2] == ch) {
                    System.out.println(ch + " wins!");
                    return true;
                }
            }
        }
        //Check columns
        for(int i=0; i<3; i++) {
            if(myBoard[0][i] != ' ') {
                ch = myBoard[0][i];
                if(myBoard[1][i] == ch && myBoard[2][i] == ch) {
                    System.out.println(ch + " wins!");
                    return true;
                }
            }
        }
        //Check diagonal
        if(myBoard[0][0] != ' ') {
            ch = myBoard[0][0];
            if(myBoard[1][1] == ch && myBoard[2][2] == ch) {
                System.out.println(ch + " wins!");
                return true;
            }
        }
        if(myBoard[2][0] != ' ') {
            ch = myBoard[2][0];
            if(myBoard[1][1] == ch && myBoard[0][2] == ch) {
                System.out.println(ch + " wins!");
                return true;
            }
        }
        return false;
    }

    public void nextMove(String p1, String p2) {
        currentTurn++;

        if(currentTurn % 2 == 1) {
            insertX = true;
            if(p1 == "Player 1") {
                System.out.print(p1 + ", please enter a move(1-9): ");
                Scanner input = new Scanner(System.in);
                selection = input.nextInt();
                humanSelect(selection, insertX);
            }
            else
                System.out.println(p1 + " chooses position " + cpuSelect(insertX));
        }
        else {
            insertX = false;
            if(p2 == "Player2") {
                System.out.print(p2 + ", please enter a move(1-9): ");
                Scanner input = new Scanner(System.in);
                int selection = input.nextInt();
                while(selection < 1 || selection > 9) {
                    System.out.println("***Error - position " + selection + " is out of bounds!");
                    System.out.print(p2 + ", please enter a move(1-9): ");
                    input = new Scanner(System.in);
                    selection = input.nextInt();
                    humanSelect(selection, insertX);
                }
            }
            else
                System.out.println(p2 + " chooses position " + cpuSelect(insertX));
        }
        emptySpots--;
    }

    public void humanSelect(int selection, boolean insertX) {
        int row = 0;
        int col = 0;
        int temp = selection;
        while(temp>3) {
            temp = temp-3;
            row++;
        }
        col = temp-1;
        if(myBoard[row][col] == ' ') {
            if(insertX == true)
                myBoard[row][col] = 'X';
            else
                myBoard[row][col] = 'O';
        }
        else {
            System.out.println("***Error - position is already taken!");
            if(insertX == true)
                System.out.print("Player 1, please enter a move(1-9): ");
            else
                System.out.print("Player 2, please enter a move(1-9): ");
            Scanner input = new Scanner(System.in);
            selection = input.nextInt();
            humanSelect(selection, insertX);
        }
    }


    public int cpuSelect(boolean insertX) {
        int row = 0;
        int col = 0;
        boolean inserted = false;
        boolean winningMove = false;
        boolean losingMove = false;
        char ch;

        //Check rows

        for (int i = 0; i < 3; i++) {
            if (myBoard[i][1] != ' ') {
                ch = myBoard[i][1];
                if (myBoard[i][0] == ch && myBoard[i][2] == ' ') {
                    row = i;
                    col = 2;
                    if(currentTurn % 2 == 1) {
                        if(ch == 'X')
                            winningMove = true;
                        else
                            losingMove = true;
                    }
                    else {
                        if(ch == 'X')
                            losingMove = true;
                        else
                            winningMove = true;
                    }
                }
                else if (myBoard[i][0] == ' ' && myBoard[i][2] == ch) {
                    row = i;
                    col = 0;
                    if(currentTurn % 2 == 1) {
                        if(ch == 'X')
                            winningMove = true;
                        else
                            losingMove = true;
                    }
                    else {
                        if(ch == 'X')
                            losingMove = true;
                        else
                            winningMove = true;
                    }
                }
            }
        }

        //Check col's
        for (int i = 0; i < 3; i++) {
            if (myBoard[1][i] != ' ') {
                ch = myBoard[1][i];
                if (myBoard[0][i] == ch && myBoard[2][i] == ' ') {
                    row = 2;
                    col = i;
                    if(currentTurn % 2 == 1) {
                        if(ch == 'X')
                            winningMove = true;
                        else
                            losingMove = true;
                    }
                    else {
                        if(ch == 'X')
                            losingMove = true;
                        else
                            winningMove = true;
                    }
                }
                else if (myBoard[0][i] == ' ' && myBoard[2][i] == ch) {
                    row = 0;
                    col = i;
                    if(currentTurn % 2 == 1) {
                        if(ch == 'X')
                            winningMove = true;
                        else
                            losingMove = true;
                    }
                    else {
                        if(ch == 'X')
                            losingMove = true;
                        else
                            winningMove = true;
                    }
                }
            }
        }


        if(winningMove == true) {
            //take that move
            if(currentTurn % 2 == 1)
                myBoard[row][col] = 'X';
            else
                myBoard[row][col] = 'O';
        }

        else if(losingMove == true) {
            //take that move
            if(currentTurn % 2 == 1)
                myBoard[row][col] = 'X';
            else
                myBoard[row][col] = 'O';
        }

        else if(myBoard[1][1] == ' ') {
            if(insertX == true)
                myBoard[1][1] = 'X';
            else
                myBoard[1][1] = 'O';

            row = 1;
            col = 1;
        }

        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (myBoard[i][j] == ' ' && inserted == false) {
                        if (insertX == true)
                            myBoard[i][j] = 'X';
                        else
                            myBoard[i][j] = 'O';
                        inserted = true;
                        row = i;
                        col = j;
                    }
                }
            }
        }

        int selection = row*3 + col + 1;
        return selection;
    }

    public void printBoard(char[][] board) {
        System.out.println("Game Board:\t\t\t\t\t\t\tPositions:\n");
        System.out.print(" " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("\t\t\t\t\t\t\t 1 | 2 | 3 \n-----------\t\t\t\t\t\t\t-----------");
        System.out.print(" " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("\t\t\t\t\t\t\t 4 | 5 | 6 \n-----------\t\t\t\t\t\t\t-----------");
        System.out.print(" " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
        System.out.println("\t\t\t\t\t\t\t 7 | 8 | 9\n");
    }

    public void printTie() {
        System.out.println("Draw! Better luck next time..");
        System.exit(0);
    }
    public void printBye() {
        System.out.println("Thanks for playing, bye!");
        System.exit(0);
    }
}