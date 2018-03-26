package com.spothero.challenge.bo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RateResponse {

    private int rate;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
