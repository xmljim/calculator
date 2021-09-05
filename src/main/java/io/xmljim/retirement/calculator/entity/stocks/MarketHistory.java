package io.xmljim.retirement.calculator.entity.stocks;

import io.xmljim.retirement.calculator.entity.ServiceResult;

import javax.persistence.*;

@Table(name = "MarketHistory", indexes = {
        @Index(name = "idx_markethistory_year", columnList = "year"),
        @Index(name = "idx_markethistory_year_market", columnList = "year, market")
})
@Entity
public class MarketHistory implements ServiceResult {
    public MarketHistory() {

    }

    public MarketHistory(int year, double yearOpen, double yearClose, String market) {
        this.year = year;
        this.yearOpen = yearOpen;
        this.yearClose = yearClose;
        this.market = market;
    }

    public MarketHistory(int year, double yearOpen, double yearClose, Market market) {
        this(year, yearOpen, yearClose, market.getName());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "yearOpen", nullable = false)
    private Double yearOpen;

    @Column(name = "yearClose", nullable = false)
    private Double yearClose;

    @Column(name = "market", nullable = false)
    private String market;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public Double getYearOpen() {
        return yearOpen;
    }

    public void setYearOpen(final Double yearOpen) {
        this.yearOpen = yearOpen;
    }

    public Double getYearClose() {
        return yearClose;
    }

    public void setYearClose(final Double yearClose) {
        this.yearClose = yearClose;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(final String market) {
        this.market = market;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "year = " + year + ", " +
                "yearOpen = " + yearOpen + ", " +
                "yearClose = " + yearClose + ", " +
                "market = " + market + ")";
    }

    @PostUpdate
    public void postUpdate() {

    }
}
