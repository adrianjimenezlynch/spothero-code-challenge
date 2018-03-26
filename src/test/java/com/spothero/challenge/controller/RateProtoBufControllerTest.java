package com.spothero.challenge.controller;

import com.spothero.challenge.bo.RateResponseProtoc;
import com.spothero.challenge.config.RateResponseProtocMessageBodyProvider;
import com.spothero.challenge.util.RateUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RateProtoBufControllerTest extends JerseyTest {

    @Override
    protected Application configure() {
        try {
            RateUtils.initializeRates();
        }catch (IOException e){
            System.out.println("Unable to initialize Rates: "+e.getMessage());
        }
        ResourceConfig rc = new ResourceConfig(RateProtoBufController.class);
        rc.register(RateResponseProtocMessageBodyProvider.class);
        return rc;
    }

    @Test
    public void getPrice_OK() {
        RateResponseProtoc.RateResponse output = target("/protobuf/rate")
                .queryParam("startDate", "2015-07-01T09:00:00Z")
                .queryParam("endDate", "2015-07-01T12:00:00Z")
                .register(RateResponseProtocMessageBodyProvider.class)
                .request()
                .header("Accept", "application/protobuf")
                .get(RateResponseProtoc.RateResponse.class);
        assertEquals("should return a rate of 1750", 1750, output.getRate());
    }

    @Test
    public void getPrice_StartDateMMissing() {
        Response output = target("/protobuf/rate")
                .queryParam("endDate", "2015-07-01T12:00:00Z")
                .register(RateResponseProtocMessageBodyProvider.class)
                .request()
                .header("Accept", "application/protobuf")
                .get();
        assertEquals("should return status 400", 400, output.getStatus());
    }

    @Test
    public void getPrice_EndDateMMissing() {
        Response output = target("/protobuf/rate")
                .queryParam("startDate", "2015-07-01T12:00:00Z")
                .register(RateResponseProtocMessageBodyProvider.class)
                .request()
                .header("Accept", "application/protobuf")
                .get();
        assertEquals("should return status 400", 400, output.getStatus());
    }

}
