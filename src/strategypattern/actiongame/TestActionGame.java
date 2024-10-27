package strategypattern.actiongame;

class TestActionGame {
    static void main(String[] args) {
        final var CHARACTERS = new Character[4];
        CHARACTERS[0] = new King();
        CHARACTERS[1] = new Queen();
        CHARACTERS[2] = new Knight();
        CHARACTERS[3] = new Troll();

        for (var character : CHARACTERS) {
            System.out.println(character);
        }
    }

}
