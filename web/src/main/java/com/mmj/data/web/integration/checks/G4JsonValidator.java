package com.mmj.data.web.integration.checks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.File;

public final class G4JsonValidator {

    private G4JsonValidator() { };

    public static boolean isValid(String json, File schemaFile) {
        try {
            JsonNode jsonNode = JsonLoader.fromFile(schemaFile);
            return checkSchema(json, jsonNode);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean isValid(String json, String schemaFileAsString) {
        try {
            JsonNode jsonNode = JsonLoader.fromString(schemaFileAsString);
            return checkSchema(json, jsonNode);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Compare json schema and json.
     *
     * @param json
     * @param jsonNode
     * @return  true if json matches the schema.
     */
    public static boolean checkSchema(String json, JsonNode jsonNode) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode good = mapper.readTree(json);
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = factory.getJsonSchema(jsonNode);
            ProcessingReport report = schema.validate(good);
            System.out.println(report);
            return report.isSuccess();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


}
