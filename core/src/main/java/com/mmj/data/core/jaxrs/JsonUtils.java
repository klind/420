package com.mmj.data.core.jaxrs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.google.common.collect.Sets;
import com.mmj.data.core.util.DeserializationException;
import com.mmj.data.core.util.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public final class JsonUtils {

    private JsonUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final boolean INDENT_OUTPUT_DEFAULT = false;
    private static final boolean INCLUDE_NULL_VALUES_DEFAULT = true;
    private static final boolean WRAP_ROOT_VALUE_DEFAULT = false;
    private static final boolean FAIL_ON_UNKNOWN_PROPERTY_DEFAULT = true;

    private static final Set<String> DEFAULT_JACKSON_MODULES =
            Sets.newHashSet("GuavaModule", "JodaModule", "JaxbAnnotationModule");

    private static final ObjectMapper MAPPER_NONULL = defaultJacksonObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final ObjectMapper MAPPER_ALWAYS = defaultJacksonObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.ALWAYS);

    @SuppressWarnings("unused")
    public static <T> T deserialize(String json, Class<T> type) throws DeserializationException {
        return deserialize(json, type, FAIL_ON_UNKNOWN_PROPERTY_DEFAULT);
    }

    public static <T> T deserialize(String json, Class<T> type, boolean failOnUnknownProperties) throws DeserializationException {
        checkNotNull(type, "Class type cannot be null");

        ObjectReader reader = MAPPER_ALWAYS.readerFor(type);

        if (failOnUnknownProperties) {
            reader = reader.with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        } else {
            reader = reader.without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }

        T ret;
        try {
            ret = reader.readValue(json);
        } catch (IOException e) {
            throw new DeserializationException(e.getLocalizedMessage(), e);
        }
        return ret;
    }

    @SuppressWarnings("unused")
    public static <T> T deserialize(File file, Class<T> type, boolean failOnUnknownProperties) throws IOException {
        checkNotNull(type, "Class type cannot be null");

        ObjectReader reader = MAPPER_ALWAYS.readerFor(type);

        if (failOnUnknownProperties) {
            reader = reader.with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        } else {
            reader = reader.without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }

        return reader.readValue(file);
    }

    @SuppressWarnings("unused")
    public static String serialize(Object value) throws SerializationException {
        return JsonUtils.serialize(value, INDENT_OUTPUT_DEFAULT, INCLUDE_NULL_VALUES_DEFAULT, WRAP_ROOT_VALUE_DEFAULT);
    }

    @SuppressWarnings("unused")
    public static String serialize(Object value, boolean indentOutput) throws SerializationException {
        return JsonUtils.serialize(value, indentOutput, INCLUDE_NULL_VALUES_DEFAULT, WRAP_ROOT_VALUE_DEFAULT);
    }

    @SuppressWarnings("unused")
    public static String serialize(Object value, boolean indentOutput, boolean includeNullValues) throws SerializationException {
        return JsonUtils.serialize(value, indentOutput, includeNullValues, WRAP_ROOT_VALUE_DEFAULT);
    }

    @SuppressWarnings("unused")
    public static String serialize(Object value, boolean indentOutput, boolean includeNullValues, boolean wrapRootValue) throws SerializationException {
        checkNotNull(value, "Value cannot be null");
        ObjectWriter writer;
        if (includeNullValues) {
            writer = MAPPER_ALWAYS.writer();
        } else {
            writer = MAPPER_NONULL.writer();
        }

        if (indentOutput) {
            writer = writer.with(SerializationFeature.INDENT_OUTPUT);
        } else {
            writer = writer.without(SerializationFeature.INDENT_OUTPUT);
        }

        if (wrapRootValue) {
            writer = writer.withFeatures(SerializationFeature.WRAP_ROOT_VALUE);
        } else {
            writer = writer.without(SerializationFeature.WRAP_ROOT_VALUE);
        }

        String ret;
        try {
            ret = writer.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e.getOriginalMessage(), e);
        }
        return ret;
    }

    public static ObjectMapper defaultJacksonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Value that indicates that property is to be always included, independent of value of the property.
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        // Feature that allows enabling (or disabling) indentation for the underlying generator, using the
        // default pretty printer (see JsonGenerator.useDefaultPrettyPrinter() for details).
        mapper.disable(SerializationFeature.INDENT_OUTPUT);

        // Feature that determines whether java.util.Date values (and Date-based things like Calendars) are to be
        // serialized as numeric timestamps (true; the default), or as something else (usually textual representation).
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // When enabled, will wrap output in a single-property JSON Object. Name of wrapper property is based
        // on class name of the serialized instance (or value type if static typing used or root type specified);
        // or, if using JAXB annotations, name from @XmlRootElement.
        mapper.disable(SerializationFeature.WRAP_ROOT_VALUE);

        // Used to control whether encountering of unknown properties (one for which there is no setter; and
        // there is no fallback "any setter" method defined using @JsonAnySetter annotation) should result in
        // a JsonMappingException (when enabled), or just quietly ignored (when disabled)
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // Add-on module that adds support for JAXB annotations as an alternative to "native" Jackson annotations
        // By default, will use JAXB annotations as the primary and Jackson annotations as secondary source
        mapper.registerModule(new JaxbAnnotationModule());

        // Datatype module support JSON serialization and deserialization of Joda data types
        mapper.registerModule(new JodaModule());

        // Datatype module to support JSON serialization and deserialization of Guava data types
        mapper.registerModule(new GuavaModule());

        List<Module> modules = ObjectMapper.findModules();
        for (Module module : modules) {
            if (DEFAULT_JACKSON_MODULES.contains(module.getModuleName())) {
                logger.debug("Jackson Module {}:{} is already registered", module.getModuleName(), module.version());
            } else {
                logger.debug("Found Jackson Module {}:{}", module.getModuleName(), module.version());
                mapper.registerModule(module);
                logger.debug("Registered Jackson Module {}:{}", module.getModuleName(), module.version());
            }
        }

        // Disable all property accessors from being auto-detected for JSON serialization/de-serialization
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);

        // Enable only field property accessors to be auto-detected for JSON serialization/de-serialization
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        //Enable all properties that have associated non-empty Wrapper name to use that wrapper name instead of property name
        mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);

        return mapper;
    }
}
