package com.eitypic.designsaga.infrastructure;

import com.eitypic.designsaga.domain.coreapi.FileId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class FileIdSerializer extends JsonSerializer<FileId> {
    @Override
    public void serialize(FileId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
