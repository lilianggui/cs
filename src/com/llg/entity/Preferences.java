package com.llg.entity;

public class Preferences {
    private String account;
    private String contract;
    private String currency;
    private Double trendIdentifier;
    private Integer abrDay;
    private Double trailStop;
    private Double slippage;

    private Double productTickSize;


    private Double stoploss;
    private String marketOpenTime;
    private String marketCloseTime;
    private String oneStPreEntryPlacementTime;
    private String entryCutOffTime;

    private Integer positionExitAverage;
    private String LastPositionExit;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTrendIdentifier() {
        return trendIdentifier;
    }

    public void setTrendIdentifier(Double trendIdentifier) {
        this.trendIdentifier = trendIdentifier;
    }

    public Integer getAbrDay() {
        return abrDay;
    }

    public void setAbrDay(Integer abrDay) {
        this.abrDay = abrDay;
    }

    public Double getTrailStop() {
        return trailStop;
    }

    public void setTrailStop(Double trailStop) {
        this.trailStop = trailStop;
    }

    public Double getSlippage() {
        return slippage;
    }

    public void setSlippage(Double slippage) {
        this.slippage = slippage;
    }

    public Double getProductTickSize() {
        return productTickSize;
    }

    public void setProductTickSize(Double productTickSize) {
        this.productTickSize = productTickSize;
    }

    public Double getStoploss() {
        return stoploss;
    }

    public void setStoploss(Double stoploss) {
        this.stoploss = stoploss;
    }

    public String getMarketOpenTime() {
        return marketOpenTime;
    }

    public void setMarketOpenTime(String marketOpenTime) {
        this.marketOpenTime = marketOpenTime;
    }

    public String getMarketCloseTime() {
        return marketCloseTime;
    }

    public void setMarketCloseTime(String marketCloseTime) {
        this.marketCloseTime = marketCloseTime;
    }

    public String getOneStPreEntryPlacementTime() {
        return oneStPreEntryPlacementTime;
    }

    public void setOneStPreEntryPlacementTime(String oneStPreEntryPlacementTime) {
        this.oneStPreEntryPlacementTime = oneStPreEntryPlacementTime;
    }

    public String getEntryCutOffTime() {
        return entryCutOffTime;
    }

    public void setEntryCutOffTime(String entryCutOffTime) {
        this.entryCutOffTime = entryCutOffTime;
    }

    public Integer getPositionExitAverage() {
        return positionExitAverage;
    }

    public void setPositionExitAverage(Integer positionExitAverage) {
        this.positionExitAverage = positionExitAverage;
    }

    public String getLastPositionExit() {
        return LastPositionExit;
    }

    public void setLastPositionExit(String lastPositionExit) {
        LastPositionExit = lastPositionExit;
    }
}
