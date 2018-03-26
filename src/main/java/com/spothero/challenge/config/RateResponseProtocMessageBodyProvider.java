package com.spothero.challenge.config;//import java.io.IOException;

import com.spothero.challenge.bo.RateResponseProtoc;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces("application/protobuf")
@Consumes("application/protobuf")
public class RateResponseProtocMessageBodyProvider
        implements MessageBodyReader, MessageBodyWriter {

    @Override
    public boolean isReadable(Class type, Type type1,
                              Annotation[] antns, MediaType mt) {
        return RateResponseProtoc.RateResponse.class.isAssignableFrom(type)
                || RateResponseProtoc.RateResponse.class.isAssignableFrom(type);
    }

    @Override
    public Object readFrom(Class type, Type type1, Annotation[] antns,
                           MediaType mt, MultivaluedMap mm, InputStream in)
            throws IOException, WebApplicationException {
        if (RateResponseProtoc.RateResponse.class.isAssignableFrom(type)) {
            return RateResponseProtoc.RateResponse.parseFrom(in);
        } else if (RateResponseProtoc.RateResponse.class.isAssignableFrom(type)) {
            return RateResponseProtoc.RateResponse.parseFrom(in);
        } else {
            throw new BadRequestException("Can't Deserailize");
        }
    }

    @Override
    public boolean isWriteable(Class type, Type type1,
                               Annotation[] antns, MediaType mt) {
        return RateResponseProtoc.RateResponse.class.isAssignableFrom(type)
                || RateResponseProtoc.RateResponse.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Object t, Class type, Type type1,
                        Annotation[] antns, MediaType mt) {
        return -1;
    }

    @Override
    public void writeTo(Object t, Class type, Type type1,
                        Annotation[] antns, MediaType mt,
                        MultivaluedMap mm, OutputStream out)
            throws IOException, WebApplicationException {
        if (t instanceof RateResponseProtoc.RateResponse) {
            RateResponseProtoc.RateResponse widget = (RateResponseProtoc.RateResponse) t;
            widget.writeTo(out);
        } else if (t instanceof RateResponseProtoc.RateResponse) {
            RateResponseProtoc.RateResponse list = (RateResponseProtoc.RateResponse) t;
            list.writeTo(out);
        }
    }
}