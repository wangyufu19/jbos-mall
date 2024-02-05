package com.mall.admin.common.sensitive;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * SensitiveJsonSerializer
 *
 * @author youfu.wang
 * @date 2024/2/5 14:23
 */
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {
    /**
     * strategy
     */
    private SensitiveStrategy strategy;

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (!ObjectUtils.isEmpty(strategy)) {
            jsonGenerator.writeString(strategy.getDesensitizer().apply(s));
        } else {
            jsonGenerator.writeString(s);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Sensitive sensitive = beanProperty.getAnnotation(Sensitive.class);
        if (!ObjectUtils.isEmpty(sensitive) && Objects.equals(String.class, beanProperty.getType().getRawClass())) {
            this.strategy = sensitive.strategy();
            return this;
        } else {
            this.strategy = null;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }

}
