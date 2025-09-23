package currency_converter;

public abstract class Currency implements Convertable {
    protected String code;
    protected String name;
    protected double exchangeRate;

    public Currency(String code, String name, double exchangeRate) {
        this.code = code;
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public double convertTo(double amount, Convertable targetCurrency) {
        double amountInRub = amount * getExchangeRate();
        return amountInRub / targetCurrency.getExchangeRate();
    }

    @Override
    public double getExchangeRate() {
        return exchangeRate;
    }

    @Override
    public String getCurrencyCode() {
        return code;
    }

    @Override
    public String getCurrencyName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - курс: %.4f RUB", name, code, exchangeRate);
    }
}
