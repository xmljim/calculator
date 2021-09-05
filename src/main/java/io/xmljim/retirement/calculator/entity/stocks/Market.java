package io.xmljim.retirement.calculator.entity.stocks;

public enum Market {
    SP("GSPC"),
    DOW("DJI"),
    NASDAQ("IXIC")
    ;
    private String name;

    Market(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
