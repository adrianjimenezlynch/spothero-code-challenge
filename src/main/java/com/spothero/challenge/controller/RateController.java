package com.spothero.challenge.controller;

import com.codahale.metrics.annotation.Timed;
import com.spothero.challenge.bo.RateResponse;
import com.spothero.challenge.util.RateUtils;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/rest")
public class RateController {

    @GET
    @Timed
    @Path("/rate")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public RateResponse getPrice(@QueryParam("startDate") String startDate,
                                 @QueryParam("endDate") String endDate) {
        if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
            throw new BadRequestException("startDate and endDate are required params");
        }
        RateResponse response = new RateResponse();
        response.setRate(RateUtils.getRateForDates(startDate, endDate));
        return response;
    }

}
