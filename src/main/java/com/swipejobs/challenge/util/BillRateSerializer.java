package com.swipejobs.challenge.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * A helper class to deserialize Bill rate value from Json without currency symbol
 */
@Slf4j
public class BillRateSerializer extends JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String billRate = p.readValueAs(String.class);
        if (!billRate.contains("$")) {
            throw new RuntimeException("Invalid bill rate");
        }
        return Double.valueOf(billRate.split("\\$")[1]);
    }
}
