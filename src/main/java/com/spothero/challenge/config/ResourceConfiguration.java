package com.spothero.challenge.config;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.spothero.challenge.controller.RateController;
import com.spothero.challenge.controller.RateProtoBufController;
import com.spothero.challenge.exception.BadRequestExceptionMapper;
import com.spothero.challenge.util.RateUtils;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration() throws IOException {
        final MetricRegistry metricRegistry = new MetricRegistry();
        register(new InstrumentedResourceMethodApplicationListener(metricRegistry));
        ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build()
                .start(1, TimeUnit.MINUTES);
        register(RateController.class);
        register(RateProtoBufController.class);
        register(BadRequestExceptionMapper.class);
        RateUtils.initializeRates();
    }

}