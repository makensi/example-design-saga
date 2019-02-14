package com.eitypic.designsaga.domain.coreapi;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
@ToString
public class FileId {

    private final UUID uuid;

    public static FileId generate() {
        return new FileId(UUID.randomUUID());
    }

    public static FileId of(String uuid) {
        Assert.notNull(uuid, "uuid can not be null");
        return new FileId(UUID.fromString(uuid));
    }

}
