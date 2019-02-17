package com.eitypic.designsaga.domain.coreapi;

import com.eitypic.designsaga.infrastructure.DocumentIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
@EqualsAndHashCode
@JsonSerialize(using = DocumentIdSerializer.class)
public class DocumentId {

    private final UUID uuid;

    public static DocumentId generate() {
        return new DocumentId(UUID.randomUUID());
    }

    public static DocumentId of(String uuid) {
        Assert.notNull(uuid, "uuid can not be null");
        return new DocumentId(UUID.fromString(uuid));
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
