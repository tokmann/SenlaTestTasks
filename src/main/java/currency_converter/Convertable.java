package currency_converter;

interface Convertable {
    double convertTo(double amount, Convertable targetCurrency);
    double getExchangeRate();
    String getCurrencyCode();
    String getCurrencyName();
}
