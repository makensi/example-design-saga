package com.eitypic.designsaga.infrastructure;

import com.eitypic.designsaga.domain.coreapi.DocumentId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DocumentIdSerializer extends JsonSerializer<DocumentId> {
    @Override
    public void serialize(DocumentId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
