package com.eitypic.designsaga.domain.coreapi;

import com.eitypic.designsaga.infrastructure.FileIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
@EqualsAndHashCode
@JsonSerialize(using = FileIdSerializer.class)
public class FileId {

    private final UUID uuid;

    public static FileId generate() {
        return new FileId(UUID.randomUUID());
    }

    public static FileId of(String uuid) {
        Assert.notNull(uuid, "uuid can not be null");
        return new FileId(UUID.fromString(uuid));
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
