package com.eitypic.designsaga.domain.coreapi;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
public class DocumentId {

    @ToString.Include
    private final UUID uuid;

    public static DocumentId generate() {
        return new DocumentId(UUID.randomUUID());
    }

    public static DocumentId of(String uuid) {
        Assert.notNull(uuid, "uuid can not be null");
        return new DocumentId(UUID.fromString(uuid));
    }

}
