package hangman_game;

import java.util.Scanner;
import java.util.Random;

public class HangmanGame {

    private static final String[] WORDS = {
            "программирование", "компьютер", "алгоритм",
            "интерфейс", "фреймворк", "переменная",
            "функция", "объект", "класс",
            "абстракция", "паттерн", "библиотека"
    };

    private static final int MAX_LIVES = 6;
    private String secretWord;
    private char[] guessedLetters;
    private int remainingLives;
    private StringBuilder usedLetters;

    private Scanner scanner;

    public HangmanGame() {
        Random random = new Random();
        this.secretWord = WORDS[random.nextInt(WORDS.length)];
        this.guessedLetters = new char[secretWord.length()];
        this.remainingLives = MAX_LIVES;
        this.usedLetters = new StringBuilder();
        this.scanner = new Scanner(System.in);

        for (int i = 0; i < guessedLetters.length; i++) {
            guessedLetters[i] = '_';
        }
    }

    public void startGame() {
        System.out.println("Добро пожаловать в игру 'Виселица'!");
        System.out.println("Угадайте слово по буквам. У вас " + MAX_LIVES + " жизней.");

        while (!isGameOver()) {
            displayGameStatus();
            System.out.print("Введите букву: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Введите одну букву!");
                continue;
            }

            char letter = input.charAt(0);
            processLetter(letter);
        }

        displayFinalResult();
        scanner.close();
    }

    private void processLetter(char letter) {
        if (usedLetters.indexOf(String.valueOf(letter)) != -1) {
            System.out.println("Вы уже вводили эту букву!");
            return;
        }

        usedLetters.append(letter).append(" ");
        boolean found = false;

        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                guessedLetters[i] = letter;
                found = true;
            }
        }

        if (!found) {
            remainingLives--;
            System.out.println("Буквы '" + letter + "' нет в слове!");
        } else {
            System.out.println("Правильно! Буква '" + letter + "' есть в слове.");
        }
    }

    private void displayGameStatus() {
        System.out.println("\n" + "=".repeat(40));
        drawHangman();
        System.out.println("Слово: " + getCurrentWordState());
        System.out.println("Использованные буквы: " + usedLetters.toString());
        System.out.println("Осталось жизней: " + remainingLives);
    }

    private void drawHangman() {
        switch (remainingLives) {
            case 6:
                System.out.println("  ____");
                System.out.println("  |  |");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 5:
                System.out.println("  ____");
                System.out.println("  |  |");
                System.out.println("  |  O");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 4:
                System.out.println("  ____");
                System.out.println("  |  |");
                System.out.println("  |  O");
                System.out.println("  |  |");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 3:
                System.out.println("  ____");
                System.out.println("  |  |");
                System.out.println("  |  O");
                System.out.println("  | /|");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 2:
                System.out.println("  ____");
                System.out.println("  |  |");
                System.out.println("  |  O");
                System.out.println("  | /|\\");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 1:
                System.out.println("  ____");
                System.out.println("  |  |");
                System.out.println("  |  O");
                System.out.println("  | /|\\");
                System.out.println("  | /");
                System.out.println("__|__");
                break;
            case 0:
                System.out.println("  ____");
                System.out.println("  |  |");
                System.out.println("  |  O");
                System.out.println("  | /|\\");
                System.out.println("  | / \\");
                System.out.println("__|__");
                break;
        }
    }

    private String getCurrentWordState() {
        StringBuilder result = new StringBuilder();
        for (char c : guessedLetters) {
            result.append(c).append(" ");
        }
        return result.toString();
    }

    private boolean isGameOver() {
        return isWordGuessed() || remainingLives <= 0;
    }

    private boolean isWordGuessed() {
        for (char c : guessedLetters) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    private void displayFinalResult() {
        System.out.println("\n" + "=".repeat(40));
        if (isWordGuessed()) {
            System.out.println("Поздравляю! Вы угадали слово: " + secretWord);
            System.out.println("У вас осталось " + remainingLives + " жизней.");
        } else {
            drawHangman();
            System.out.println("Игра окончена! Вы проиграли.");
            System.out.println("Загаданное слово было: " + secretWord);
        }
    }
}