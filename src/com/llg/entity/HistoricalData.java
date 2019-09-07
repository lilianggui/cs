package com.llg.entity;

public class HistoricalData {
    private String contract;
    private String date;
    private Double Open;
    private Double High;
    private Double Low;
    private Double Close;
    private Long Volume;
    private Double ABR;

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getOpen() {
        return Open;
    }

    public void setOpen(Double open) {
        Open = open;
    }

    public Double getHigh() {
        return High;
    }

    public void setHigh(Double high) {
        High = high;
    }

    public Double getLow() {
        return Low;
    }

    public void setLow(Double low) {
        Low = low;
    }

    public Double getClose() {
        return Close;
    }

    public void setClose(Double close) {
        Close = close;
    }

    public Long getVolume() {
        return Volume;
    }

    public void setVolume(Long volume) {
        Volume = volume;
    }

    public Double getABR() {
        return ABR;
    }

    public void setABR(Double ABR) {
        this.ABR = ABR;
    }
}
