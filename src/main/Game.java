package main;

import java.util.Random;
import java.util.Scanner;

public class Game {
    // Festlegen der Größe des Spielbretts.
    private static final int BOARD_SIZE = 3;

    // Erstellen eines 2D-Char-Arrays als Spielbrett.
    private final char[][] board;

    // Definition der aktuellen Spieler instanz.
    private Player currentPlayer;

    // Erstellung einer Scanner instanz zum Lesen der Benutzereingabe.
    private final Scanner scanner;

    // Konstruktor zum Initialisieren des Spielbretts und des Scanners.
    public Game() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = '-';
            }
        }
        scanner = new Scanner(System.in);
    }

    // Hauptspielmethode.
    public void play() {
        // Der Benutzer kann den Gegner auswählen: ein anderer Spieler oder der Computer.
        boolean isComputerOpponent = chooseOpponent();

        // Setzen des Namens für Spieler X.
        System.out.println("Bitte geben Sie den Namen für Spieler X ein: ");
        Player.X.setName(scanner.nextLine());

        if (isComputerOpponent) {
            // Setzen des Namens für den Computer, wenn dieser als Gegner ausgewählt wurde.
            System.out.println("Bitte geben Sie den Namen für den Computer ein: ");
            Player.COMPUTER.setName(scanner.nextLine());
            currentPlayer = Player.X;
        } else {
            // Setzen des Namens für Spieler O, wenn ein menschlicher Gegner ausgewählt wurde.
            System.out.println("Bitte geben Sie den Namen für Spieler O ein: ");
            Player.O.setName(scanner.nextLine());
            currentPlayer = Player.X;
        }

        // Der Spielzyklus beginnt.
        while (true) {
            printBoard();
            if (currentPlayer.isHuman()) {
                makePlayerMove();
            } else {
                makeComputerMove();
            }
            if (checkWinner()) {
                printBoard();
                System.out.println(currentPlayer.getName() + " hat gewonnen!");
                break;
            }
            if (isBoardFull()) {
                printBoard();
                System.out.println("Das Spiel endet unentschieden!");
                break;
            }
            switchPlayer(isComputerOpponent);
        }
    }

    private void makePlayerMove() {

        // Der Benutzer wird aufgefordert, eine Zeile und eine Spalte einzugeben.
        System.out.println("Bitte geben Sie die Zeile ein: ");
        int row = scanner.nextInt();
        System.out.println("Bitte geben Sie die Spalte ein: ");
        int col = scanner.nextInt();

        // Überprüfen, ob der Platz frei ist.
        if (board[row][col] == '-') {
            board[row][col] = currentPlayer.getSymbol();
        } else {
            System.out.println("Dieses Feld ist bereits belegt, bitte wählen Sie ein anderes.");
            makePlayerMove();
        }
    }

    private boolean checkWinner() {
        // Überprüfen, ob der aktuelle Spieler gewonnen hat.
        return checkRows() || checkColumns() || checkDiagonals();

    }

    private boolean checkColumns() {

        // Überprüfen, ob eine der Spalten gewonnen hat.
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRows() {
        // Überprüfen, ob eine der Zeilen gewonnen hat.
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;

    }


    private boolean isBoardFull() {
        // Überprüfen, ob das Spielbrett voll ist.
        return board[0][0] != '-' && board[0][1] != '-' && board[0][2] != '-' &&
                board[1][0] != '-' && board[1][1] != '-' && board[1][2] != '-' &&
                board[2][0] != '-' && board[2][1] != '-' && board[2][2] != '-';
    }


    private void switchPlayer(boolean isComputerOpponent) { // Methode zum Wechseln des Spielers.

        // Wenn der Computer als Gegner ausgewählt wurde, ist der Computer immer Spieler O.
        if (isComputerOpponent) {
            currentPlayer = currentPlayer == Player.X ? Player.COMPUTER : Player.X;
        } else {
            // Wenn ein menschlicher Gegner ausgewählt wurde, wechselt der Spieler zwischen X und O.
            currentPlayer = currentPlayer == Player.X ? Player.O : Player.X;
        }


    }

    // Methode zur Auswahl des Gegners.
    private boolean chooseOpponent() {
        System.out.println("Wählen Sie den Spielmodus:\n1. Spieler gegen Spieler\n2. Spieler gegen Computer");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Verbraucht den übrigen Zeilenumbruch von der vorherigen Eingabe.
        return choice == 2;
    }

    // Methode zur Ausgabe des aktuellen Spielbretts.
    private void printBoard() {
        // Trennlinie zur besseren Übersicht.
        System.out.println("-------------------");

        // Schleife durch alle Felder des Spielbretts.
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print("|  ");

            // Ausgabe der Zeilen des Spielbretts.
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(board[row][col] + "  |  ");
            }

            System.out.println();
            System.out.println("-------------------");
        }
    }

    // Methode zur Eingabe der Zeilen- und Spaltennummer.
    private int getCoordinate(String coordinateName) {
        System.out.print("Bitte geben Sie die " + coordinateName + " (0-" + (BOARD_SIZE - 1) + ") ein: ");
        // Rückgabe der Benutzereingabe.
        return scanner.nextInt();
    }

    // Methode, um einen Computerzug zu machen.
    private void makeComputerMove() {
        System.out.println("Der Computer ist am Zug.");

        // Schleife läuft solange, bis ein gültiger Zug gemacht wurde.
        while (true) {

            // Erzeugung von Zufallszahlen für Zeile und Spalte.
            Random random = new Random();
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);

            // Überprüfung, ob das ausgewählte Feld leer ist.
            if (board[row][col] == '-') {

                // Wenn das Feld leer ist, wird das Symbol des aktuellen Spielers dort eingetragen.
                board[row][col] = currentPlayer.getSymbol();
                break;
            }
        }
    }

    // Methode zur Überprüfung, ob ein Spieler gewonnen hat.
    private boolean checkWin() {

        // Überprüfung aller Zeilen und Spalten auf Siegbedingungen.
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkRow(i) || checkColumn(i)) {
                return true;
            }
        }
        // Überprüfung der Diagonalen auf Siegbedingungen.
        return checkDiagonals();
    }

    // Methode zur Überprüfung einer Zeile auf Siegbedingungen.
    private boolean checkRow(int row) {
        char symbol = currentPlayer.getSymbol();

        // Überprüfung aller Felder der Zeile auf das Symbol des aktuellen Spielers.
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col] != symbol) {
                return false;
            }
        }
        // Wenn alle Felder das Symbol des aktuellen Spielers haben, hat dieser gewonnen.
        return true;
    }

    // Methode zur Überprüfung einer Spalte auf Siegbedingungen.
    private boolean checkColumn(int column) {
        char symbol = currentPlayer.getSymbol();

        // Überprüfung aller Felder der Spalte auf das Symbol des aktuellen Spielers.
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][column] != symbol) {
                return false;
            }
        }
        // Wenn alle Felder das Symbol des aktuellen Spielers haben, hat dieser gewonnen.
        return true;
    }

    // Methode zur Überprüfung der Diagonalen auf Siegbedingungen.
    private boolean checkDiagonals() {

        // Überprüfung beider Diagonalen auf Siegbedingungen.
        return checkMainDiagonal() || checkSecondaryDiagonal();
    }

    // Methode zur Überprüfung der Hauptdiagonale auf Siegbedingungen.
    private boolean checkMainDiagonal() {
        char symbol = currentPlayer.getSymbol();

        // Überprüfung aller Felder der Hauptdiagonale auf das Symbol des aktuellen Spielers.
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][i] != symbol) {
                return false;
            }
        }

        // Wenn alle Felder das Symbol des aktuellen Spielers haben, hat dieser gewonnen.
        return true;
    }

    // Methode zur Überprüfung der Nebendiagonale auf Siegbedingungen.
    private boolean checkSecondaryDiagonal() {
        char symbol = currentPlayer.getSymbol();

        // Überprüfung aller Felder der Nebendiagonale auf das Symbol des aktuellen Spielers.
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][BOARD_SIZE - i - 1] != symbol) {
                return false;
            }
        }
        // Wenn alle Felder das Symbol des aktuellen Spielers haben, hat dieser gewonnen.
        return true;
    }

    // Methode zur Überprüfung auf ein Unentschieden.
    private boolean checkTie() {

        // Überprüfung aller Felder, ob sie leer sind.
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == '-') {

                    // Wenn ein leeres Feld gefunden wird, ist das Spiel noch nicht unentschieden und die Methode gibt false zurück.
                    return false;
                }
            }
        }

        // Wenn alle Felder belegt sind und niemand gewonnen hat, ist das Spiel unentschieden.
        return true;
    }
}