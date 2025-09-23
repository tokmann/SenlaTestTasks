package currency_converter;

import currency_converter.currencies.*;
import java.util.Scanner;

public class CurrencyConverter {

    private Convertable[] currencies;
    private Scanner scanner;

    public CurrencyConverter() {
        this.scanner = new Scanner(System.in);
        initializeCurrencies();
    }

    private void initializeCurrencies() {
        this.currencies = new Convertable[] {
                new USDollar(),
                new Euro(),
                new BritishPound(),
                new JapaneseYen(),
                new RussianRouble(),
                new ChineseYuan()
        };
    }

    public void startConverter() {
        System.out.println("=== КОНВЕРТЕР ВАЛЮТ ===");
        displayExchangeRates();

        while (true) {
            try {
                Convertable sourceCurrency = selectCurrency("Выберите исходную валюту:");
                if (sourceCurrency == null) break;

                double amount = getAmount();
                Convertable targetCurrency = selectCurrency("Выберите валюту для конвертации:");
                if (targetCurrency == null) break;

                if (sourceCurrency.getCurrencyCode().equals(targetCurrency.getCurrencyCode())) {
                    System.out.println("Выбрана та же валюта! Конвертация не требуется.");
                    continue;
                }

                double convertedAmount = performConversion(amount, sourceCurrency, targetCurrency);
                displayConversionResult(amount, sourceCurrency, convertedAmount, targetCurrency);

            } catch (Exception e) {
                System.out.println("Ошибка ввода, попробуйте снова.");
                scanner.nextLine();
            }
        }
    }

    private Convertable selectCurrency(String message) {
        System.out.println("\n" + message);
        for (int i = 0; i < currencies.length; i++) {
            System.out.println((i + 1) + ". " + currencies[i]);
        }
        System.out.println("0. Выход");

        while (true) {
            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                return null;
            }

            if (choice >= 1 && choice <= currencies.length) {
                return currencies[choice - 1];
            }

            System.out.println("Неверный выбор. Пожалуйста, введите число от 0 до " + currencies.length);
        }
    }

    private double getAmount() {
        while (true) {
            System.out.print("Введите сумму для конвертации: ");
            double amount = scanner.nextDouble();

            if (amount > 0) {
                return amount;
            }

            System.out.println("Сумма должна быть положительной. Попробуйте снова.");
        }
    }

    private double performConversion(double amount, Convertable fromCurrency, Convertable toCurrency) {
        return fromCurrency.convertTo(amount, toCurrency);
    }

    private void displayConversionResult(double originalAmount, Convertable fromCurrency,
                                         double convertedAmount, Convertable toCurrency) {
        System.out.println("\n" + "=".repeat(60));
        System.out.printf("РЕЗУЛЬТАТ КОНВЕРТАЦИИ:\n");
        System.out.printf("%.2f %s = %.2f %s\n",
                originalAmount, fromCurrency.getCurrencyCode(),
                convertedAmount, toCurrency.getCurrencyCode());
        System.out.printf("(%s → %s)\n",
                fromCurrency.getCurrencyName(),
                toCurrency.getCurrencyName());
        System.out.println("=".repeat(60));
    }

    private void displayExchangeRates() {
        System.out.println("\nТЕКУЩИЕ КУРСЫ ВАЛЮТ (относительно RUB):");
        System.out.println("-".repeat(50));
        for (Convertable currency : currencies) {
            System.out.printf("%-15s: 1 %s = %.4f RUB\n",
                    currency.getCurrencyName(),
                    currency.getCurrencyCode(),
                    currency.getExchangeRate());
        }
        System.out.println("-".repeat(50));
    }
}
