package com.spothero.challenge.controller;

import com.spothero.challenge.bo.RateResponse;
import com.spothero.challenge.util.RateUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RateControllerTest extends JerseyTest {

    @Override
    protected Application configure() {
        try {
            RateUtils.initializeRates();
        }catch (IOException e){
            System.out.println("Unable to initialize Rates: "+e.getMessage());
        }
        return new ResourceConfig(RateController.class);
    }


    @Test
    public void getPrice_OK() {
        RateResponse output = target("/rest/rate")
                .queryParam("startDate", "2015-07-01T09:00:00Z")
                .queryParam("endDate", "2015-07-01T12:00:00Z")
                .request()
                .get(RateResponse.class);
        assertEquals("should return a rate of 1750", 1750, output.getRate());
    }

    @Test
    public void getPrice_StartDateMMissing() {
        Response output = target("/rest/rate")
                .queryParam("endDate", "2015-07-01T12:00:00Z")
                .request()
                .get();
        assertEquals("should return status 400", 400, output.getStatus());
    }

    @Test
    public void getPrice_EndDateMMissing() {
        Response output = target("/rest/rate")
                .queryParam("startDate", "2015-07-01T12:00:00Z")
                .request()
                .get();
        assertEquals("should return status 400", 400, output.getStatus());
    }

}
