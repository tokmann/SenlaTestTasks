package password_generator;

import java.util.Scanner;
import java.util.Random;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    private Scanner scanner;
    private Random random;

    public PasswordGenerator() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    public void startGenerator() {
        System.out.println("=== ГЕНЕРАТОР БЕЗОПАСНЫХ ПАРОЛЕЙ ===");
        System.out.println("Пароль будет содержать: заглавные и строчные буквы, цифры и специальные символы");

        while (true) {
            try {
                int length = getPasswordLength();
                String password = generatePassword(length);

                System.out.println("\n" + "=".repeat(40));
                System.out.println("ВАШ ПАРОЛЬ: " + password);
                System.out.println("Длина: " + password.length() + " символов");
                System.out.println("=".repeat(40));

            } catch (Exception e) {
                System.out.println("Ошибка ввода, попробуйте снова.");
                scanner.nextLine();
            }
        }

    }

    private int getPasswordLength() {
        while (true) {
            System.out.print("\nВведите длину пароля (от 8 до 12 символов): ");
            int length = scanner.nextInt();

            if (length >= 8 && length <= 12) {
                return length;
            }

            System.out.println("Длина пароля должна быть от 8 до 12 символов!");
        }
    }

    private String generatePassword(int length) {
        char[] password = new char[length];
        boolean[] filledPositions = new boolean[length];

        String[] charSets = {LOWERCASE, UPPERCASE, DIGITS, SYMBOLS};

        int charSetIndex = 0;
        int filledCount = 0;

        while (filledCount < length) {
            String currentCharSet = charSets[charSetIndex];

            char randomChar = getRandomChar(currentCharSet);

            int randomPosition = getRandomEmptyPosition(filledPositions);

            password[randomPosition] = randomChar;
            filledPositions[randomPosition] = true;
            filledCount++;

            charSetIndex = (charSetIndex + 1) % charSets.length;
        }

        return new String(password);
    }

    private int getRandomEmptyPosition(boolean[] filledPositions) {
        while (true) {
            int position = random.nextInt(filledPositions.length);
            if (!filledPositions[position]) {
                return position;
            }
        }
    }

    private char getRandomChar(String characterSet) {
        int charIndex = random.nextInt(characterSet.length());
        return characterSet.charAt(charIndex);
    }
}