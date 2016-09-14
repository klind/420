package com.mmj.data.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.mmj.data.core.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class BooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Boolean result = Boolean.FALSE;
        JsonToken currentToken = jsonParser.getCurrentToken();
        String text = jsonParser.getText();
        if (currentToken.equals(JsonToken.VALUE_STRING) && StringUtils.isNotBlank(text)) {
            if (Constants.BOOLEAN_TRUE.contains(text.trim().toLowerCase())) {
                result = Boolean.TRUE;
            }
        }
        return result;
    }
}
