package org.example.domain;

public enum HeaderMenuItem {
    BANK("Банк", "https://www.tinkoff.ru/"),
    BUSINESS("Бизнес", "https://www.tinkoff.ru/business/"),
    CASH_REGISTER("Касса", "https://www.tinkoff.ru/kassa/"),
    INVESTMENTS("Инвестиции", "https://www.tinkoff.ru/invest/"),
    SIM_CARD("Сим-карта", "https://www.tinkoff.ru/mobile-operator/"),
    INSURANCE("Страхование", "https://www.tinkoff.ru/insurance/"),
    TRIPS("Путешествия", "https://www.tinkoff.ru/travel/flights/"),
    ENTERTAINMENT("Развлечения", "https://www.tinkoff.ru/entertainment/.*"),
    SHARES("Долями", "https://www.tinkoff.ru/dolyame/");

    public String name;
    public String url;

    private HeaderMenuItem(String name, String url) {
        this.name = name;
        this.url = url;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
