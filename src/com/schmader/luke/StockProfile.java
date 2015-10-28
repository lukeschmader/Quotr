package com.schmader.luke;

import java.math.BigDecimal;

public class StockProfile {

	private BigDecimal price;
	private BigDecimal change;
	private BigDecimal pctChange;
	private BigDecimal ask;
	private BigDecimal bid;
	private BigDecimal changeFromYearLowPct;
	private BigDecimal changeFromYearHighPct;
	private BigDecimal dayLow;
	private BigDecimal dayHigh;
	private BigDecimal open;
	private long volume;
	private long avgVolume;
	private BigDecimal yearLow;
	private BigDecimal yearHigh;
	private BigDecimal eps;
	private BigDecimal peg;
	private String name;
	private String symbol;
	private BigDecimal yieldPct;
	
	
	
	public BigDecimal getAsk() {
		return ask;
	}
	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}
	public BigDecimal getBid() {
		return bid;
	}
	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}
	public BigDecimal getChangeFromYearLowPct() {
		return changeFromYearLowPct;
	}
	public void setChangeFromYearLowPct(BigDecimal changeFromYearLowPct) {
		this.changeFromYearLowPct = changeFromYearLowPct;
	}
	public BigDecimal getChangeFromYearHighPct() {
		return changeFromYearHighPct;
	}
	public void setChangeFromYearHighPct(BigDecimal changeFromYearHighPct) {
		this.changeFromYearHighPct = changeFromYearHighPct;
	}
	public BigDecimal getDayLow() {
		return dayLow;
	}
	public void setDayLow(BigDecimal dayLow) {
		this.dayLow = dayLow;
	}
	public BigDecimal getDayHigh() {
		return dayHigh;
	}
	public void setDayHigh(BigDecimal dayHigh) {
		this.dayHigh = dayHigh;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public long getAvgVolume() {
		return avgVolume;
	}
	public void setAvgVolume(long avgVolume) {
		this.avgVolume = avgVolume;
	}
	public BigDecimal getYearLow() {
		return yearLow;
	}
	public void setYearLow(BigDecimal yearLow) {
		this.yearLow = yearLow;
	}
	public BigDecimal getYearHigh() {
		return yearHigh;
	}
	public void setYearHigh(BigDecimal yearHigh) {
		this.yearHigh = yearHigh;
	}
	public BigDecimal getEps() {
		return eps;
	}
	public void setEps(BigDecimal eps) {
		this.eps = eps;
	}
	public BigDecimal getPeg() {
		return peg;
	}
	public void setPeg(BigDecimal peg) {
		this.peg = peg;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getPctChange() {
		return pctChange;
	}
	public void setPctChange(BigDecimal pctChange) {
		this.pctChange = pctChange;
	}
	public BigDecimal getChange() {
		return change;
	}
	public void setChange(BigDecimal change) {
		this.change = change;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getYieldPct() {
		return yieldPct;
	}
	public void setYieldPct(BigDecimal yieldPct) {
		this.yieldPct = yieldPct;
	}
	

}
