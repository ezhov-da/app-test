package ru.ezhov.test.help;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class ParseJson {
    public static void main(String[] args) {
        String json = "{\n" +
                "   \"date\":\"01.12.2014\",\n" +
                "   \"bank\":\"PB\",\n" +
                "   \"baseCurrency\":980,\n" +
                "   \"baseCurrencyLit\":\"UAH\",\n" +
                "   \"exchangeRate\":[\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"AUD\",\n" +
                "         \"saleRateNB\":12.8319250,\n" +
                "         \"purchaseRateNB\":12.8319250\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"CAD\",\n" +
                "         \"saleRateNB\":13.2107400,\n" +
                "         \"purchaseRateNB\":13.2107400,\n" +
                "         \"saleRate\":15.0000000,\n" +
                "         \"purchaseRate\":13.0000000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"CZK\",\n" +
                "         \"saleRateNB\":0.6796950,\n" +
                "         \"purchaseRateNB\":0.6796950,\n" +
                "         \"saleRate\":0.8000000,\n" +
                "         \"purchaseRate\":0.6000000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"DKK\",\n" +
                "         \"saleRateNB\":2.5258930,\n" +
                "         \"purchaseRateNB\":2.5258930\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"HUF\",\n" +
                "         \"saleRateNB\":0.0612592,\n" +
                "         \"purchaseRateNB\":0.0612592\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"ILS\",\n" +
                "         \"saleRateNB\":3.8627380,\n" +
                "         \"purchaseRateNB\":3.8627380,\n" +
                "         \"saleRate\":4.5000000,\n" +
                "         \"purchaseRate\":3.7000000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"JPY\",\n" +
                "         \"saleRateNB\":0.1272593,\n" +
                "         \"purchaseRateNB\":0.1272593,\n" +
                "         \"saleRate\":0.1500000,\n" +
                "         \"purchaseRate\":0.1200000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"LVL\",\n" +
                "         \"saleRateNB\":0.1272593,\n" +
                "         \"purchaseRateNB\":0.1272593\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"LTL\",\n" +
                "         \"saleRateNB\":5.4433850,\n" +
                "         \"purchaseRateNB\":5.4433850\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"NOK\",\n" +
                "         \"saleRateNB\":2.1609570,\n" +
                "         \"purchaseRateNB\":2.1609570,\n" +
                "         \"saleRate\":2.6000000,\n" +
                "         \"purchaseRate\":2.1000000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"SKK\",\n" +
                "         \"saleRateNB\":2.1609570,\n" +
                "         \"purchaseRateNB\":2.1609570\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"SEK\",\n" +
                "         \"saleRateNB\":2.0283750,\n" +
                "         \"purchaseRateNB\":2.0283750\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"CHF\",\n" +
                "         \"saleRateNB\":15.6389750,\n" +
                "         \"purchaseRateNB\":15.6389750,\n" +
                "         \"saleRate\":17.0000000,\n" +
                "         \"purchaseRate\":15.5000000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"RUB\",\n" +
                "         \"saleRateNB\":0.3052700,\n" +
                "         \"purchaseRateNB\":0.3052700,\n" +
                "         \"saleRate\":0.3200000,\n" +
                "         \"purchaseRate\":0.2800000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"GBP\",\n" +
                "         \"saleRateNB\":23.6324910,\n" +
                "         \"purchaseRateNB\":23.6324910,\n" +
                "         \"saleRate\":25.8000000,\n" +
                "         \"purchaseRate\":24.0000000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"USD\",\n" +
                "         \"saleRateNB\":15.0564130,\n" +
                "         \"purchaseRateNB\":15.0564130,\n" +
                "         \"saleRate\":15.7000000,\n" +
                "         \"purchaseRate\":15.3500000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"BYR\",\n" +
                "         \"saleRateNB\":0.0013900,\n" +
                "         \"purchaseRateNB\":0.0013900\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"EUR\",\n" +
                "         \"saleRateNB\":18.7949200,\n" +
                "         \"purchaseRateNB\":18.7949200,\n" +
                "         \"saleRate\":20.0000000,\n" +
                "         \"purchaseRate\":19.2000000\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"GEL\",\n" +
                "         \"saleRateNB\":8.1500890,\n" +
                "         \"purchaseRateNB\":8.1500890\n" +
                "      },\n" +
                "      {\n" +
                "         \"baseCurrency\":\"UAH\",\n" +
                "         \"currency\":\"PLZ\",\n" +
                "         \"saleRateNB\":4.4922010,\n" +
                "         \"purchaseRateNB\":4.4922010,\n" +
                "         \"saleRate\":5.0000000,\n" +
                "         \"purchaseRate\":4.2000000\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        Basic basic = new Gson().fromJson(json, Basic.class);
        System.out.println(basic);
    }
}

        class Basic {
            private String date;
            private String bank;
            private String baseCurrency;
            private String baseCurrencyLit;
            private List<Inner> exchangeRate;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getBaseCurrency() {
                return baseCurrency;
            }

            public void setBaseCurrency(String baseCurrency) {
                this.baseCurrency = baseCurrency;
            }

            public String getBaseCurrencyLit() {
                return baseCurrencyLit;
            }

            public void setBaseCurrencyLit(String baseCurrencyLit) {
                this.baseCurrencyLit = baseCurrencyLit;
            }

            public List<Inner> getExchangeRate() {
                return exchangeRate;
            }

            public void setExchangeRate(List<Inner> exchangeRate) {
                this.exchangeRate = exchangeRate;
            }

            @Override
            public String toString() {
                return "Basic{" +
                        "date='" + date + '\'' +
                        ", bank='" + bank + '\'' +
                        ", baseCurrency='" + baseCurrency + '\'' +
                        ", baseCurrencyLit='" + baseCurrencyLit + '\'' +
                        ", exchangeRate=" + Arrays.toString(exchangeRate.toArray()) +
                        '}';
            }
        }

        class Inner {
            private String baseCurrency;
            private String currency;
            private String saleRateNB;
            private String purchaseRateNB;
            private String saleRate;
            private String purchaseRate;

            public String getBaseCurrency() {
                return baseCurrency;
            }

            public void setBaseCurrency(String baseCurrency) {
                this.baseCurrency = baseCurrency;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getSaleRateNB() {
                return saleRateNB;
            }

            public void setSaleRateNB(String saleRateNB) {
                this.saleRateNB = saleRateNB;
            }

            public String getPurchaseRateNB() {
                return purchaseRateNB;
            }

            public void setPurchaseRateNB(String purchaseRateNB) {
                this.purchaseRateNB = purchaseRateNB;
            }

            public String getSaleRate() {
                return saleRate;
            }

            public void setSaleRate(String saleRate) {
                this.saleRate = saleRate;
            }

            public String getPurchaseRate() {
                return purchaseRate;
            }

            public void setPurchaseRate(String purchaseRate) {
                this.purchaseRate = purchaseRate;
            }

            @Override
            public String toString() {
                return "Inner{" +
                        "baseCurrency='" + baseCurrency + '\'' +
                        ", currency='" + currency + '\'' +
                        ", saleRateNB='" + saleRateNB + '\'' +
                        ", purchaseRateNB='" + purchaseRateNB + '\'' +
                        ", saleRate='" + saleRate + '\'' +
                        ", purchaseRate='" + purchaseRate + '\'' +
                        '}';
            }
        }
