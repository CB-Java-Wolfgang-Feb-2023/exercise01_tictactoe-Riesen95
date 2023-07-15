package main;

public class Player {

    // Definiert den menschlichen Spieler X.
    public static final Player X = new Player("X", 'X', true);

    // Definiert den menschlichen Spieler O.
    public static final Player O = new Player("O", 'O', true);

    // Definiert den Computerspieler. Der Computerspieler benutzt dasselbe Symbol wie der menschliche Spieler O.
    public static final Player COMPUTER = new Player("Computer", 'O', false);

    private String name;
    private final char symbol;
    private final boolean isHuman;

    // Konstruktor zum Erstellen eines neuen Spielers.
    public Player(String name, char symbol, boolean isHuman) {
        this.name = name;
        this.symbol = symbol;
        this.isHuman = isHuman;
    }

    // Gibt den Namen des Spielers zurück.
    public String getName() {
        return name;
    }

    // Gibt das Symbol des Spielers zurück.
    public char getSymbol() {
        return symbol;
    }

    // Überprüft, ob der Spieler ein Mensch ist.
    public boolean isHuman() {
        return isHuman;
    }

    // Setzt den Namen des Spielers.
    public void setName(String name) {
        this.name = name;
    }
}
