package com.ipfs.api.entity;

public class StatsBwEntity {

    /** command line:
     ipfs stats bw
     Bandwidth
     TotalIn: 6.7 MB
     TotalOut: 2.1 MB
     RateIn: 2.9 kB/s
     RateOut: 1.8 kB/s
     */

    /**
     * api call
     * http://127.0.0.1:5001/api/v0/stats/bw
     * {"TotalIn":8086241,"TotalOut":2719927,"RateIn":9007.08851460756,"RateOut":4880.935141862984}
     */
    private int TotalIn;
    private int TotalOut;
    private double RateIn;
    private double RateOut;

    public int getTotalIn() {
        return TotalIn;
    }

    public void setTotalIn(int totalIn) {
        TotalIn = totalIn;
    }

    public int getTotalOut() {
        return TotalOut;
    }

    public void setTotalOut(int totalOut) {
        TotalOut = totalOut;
    }

    public double getRateIn() {
        return RateIn;
    }

    public void setRateIn(double rateIn) {
        RateIn = rateIn;
    }

    public double getRateOut() {
        return RateOut;
    }

    public void setRateOut(double rateOut) {
        RateOut = rateOut;
    }
}
