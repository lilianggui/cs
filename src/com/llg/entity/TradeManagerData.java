package com.llg.entity;

public class TradeManagerData {
    private Double dollarPerPoint;
    private Double RiskPerTrade;
    private Double leverageRatio;
    private Double marginRequirement;
    private Double rptDollar;
    private Double volatilityDollar;
    private Double UnitPerTrade;
    private Double uPTRounded;
    private Double startUpEquity;
    private Double adjustment;
    private Double dayMaxLoss;
    private Double maxMarginLevel;
    private Double currentEquity;


    public Double getDollarPerPoint() {
        return dollarPerPoint;
    }

    public void setDollarPerPoint(Double dollarPerPoint) {
        this.dollarPerPoint = dollarPerPoint;
    }

    public Double getRiskPerTrade() {
        return RiskPerTrade;
    }

    public void setRiskPerTrade(Double riskPerTrade) {
        RiskPerTrade = riskPerTrade;
    }

    public Double getLeverageRatio() {
        return leverageRatio;
    }

    public void setLeverageRatio(Double leverageRatio) {
        this.leverageRatio = leverageRatio;
    }

    public Double getMarginRequirement() {
        return marginRequirement;
    }

    public void setMarginRequirement(Double marginRequirement) {
        this.marginRequirement = marginRequirement;
    }

    public Double getRptDollar() {
        return rptDollar;
    }

    public void setRptDollar(Double rptDollar) {
        this.rptDollar = rptDollar;
    }

    public Double getVolatilityDollar() {
        return volatilityDollar;
    }

    public void setVolatilityDollar(Double volatilityDollar) {
        this.volatilityDollar = volatilityDollar;
    }

    public Double getUnitPerTrade() {
        return UnitPerTrade;
    }

    public void setUnitPerTrade(Double unitPerTrade) {
        UnitPerTrade = unitPerTrade;
    }

    public Double getuPTRounded() {
        return uPTRounded;
    }

    public void setuPTRounded(Double uPTRounded) {
        this.uPTRounded = uPTRounded;
    }

    public Double getStartUpEquity() {
        return startUpEquity;
    }

    public void setStartUpEquity(Double startUpEquity) {
        this.startUpEquity = startUpEquity;
    }

    public Double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Double adjustment) {
        this.adjustment = adjustment;
    }

    public Double getDayMaxLoss() {
        return dayMaxLoss;
    }

    public void setDayMaxLoss(Double dayMaxLoss) {
        this.dayMaxLoss = dayMaxLoss;
    }

    public Double getMaxMarginLevel() {
        return maxMarginLevel;
    }

    public void setMaxMarginLevel(Double maxMarginLevel) {
        this.maxMarginLevel = maxMarginLevel;
    }

    public Double getCurrentEquity() {
        return currentEquity;
    }

    public void setCurrentEquity(Double currentEquity) {
        this.currentEquity = currentEquity;
    }
}
