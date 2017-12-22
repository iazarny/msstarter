package com.az.ms.misc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Igor_Azarny on 11/14/2017.
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectWriter PRETTY_WRITER;

    static {
        PRETTY_WRITER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();
    }

    private JsonUtil() {
    }

    /**
     * Checks if the provided string is a valid json.
     *
     * @param json string to check validity
     * @return false if empty or invalid, true otherwise
     */
    public static boolean isValidJson(String json) {
        try {
            OBJECT_MAPPER.readTree(json);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

   

    public static JsonNode makeJsonTreeOf(Object object) {
        return OBJECT_MAPPER.valueToTree(object);
    }

    public static JsonNode readTree(String json) throws IOException {
        return OBJECT_MAPPER.readTree(json);
    }

    public static <T> T readObject(Class<T> type, InputStream input) {
        try {
            return OBJECT_MAPPER.readValue(input, type);
        } catch (IOException e) {
            String errorMessage = "Cannot read JSON";
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T convertValue(Class<T> type, JsonNode node) {
        return OBJECT_MAPPER.convertValue(node, type);
    }

    public static String toJSON(Object object) {
        return toJSON(object, OBJECT_MAPPER::writeValueAsString);
    }

    public static String toPrettyJSON(Object object) {
        return toJSON(object, PRETTY_WRITER::writeValueAsString);
    }

    private static String toJSON(Object object, ToJson toJson) {
        try {
            return toJson.apply(object);
        } catch (JsonProcessingException e) {
            String errorMessage = "Cannot make valid JSON from given object";
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    @FunctionalInterface
    private interface ToJson {
        String apply(Object object) throws JsonProcessingException;
    }



}
