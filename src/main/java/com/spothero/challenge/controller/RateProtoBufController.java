package com.spothero.challenge.controller;

import com.codahale.metrics.annotation.Timed;
import com.spothero.challenge.bo.RateResponseProtoc;
import com.spothero.challenge.util.RateUtils;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/protobuf")
public class RateProtoBufController {


    @GET
    @Timed(name = "getPrice")
    @Path("/rate")
    @Produces("application/protobuf")
    public Response getPrice(@QueryParam("startDate") String startDate,
                             @QueryParam("endDate") String endDate) {
        if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
            throw new BadRequestException("startDate and endDate are requiered params");
        }
        int rate = RateUtils.getRateForDates(startDate, endDate);
        RateResponseProtoc.RateResponse rateResponse =
                RateResponseProtoc.RateResponse.newBuilder().setRate(rate).build();
        return Response.ok(rateResponse).build();
    }

}
