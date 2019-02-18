package com.eitypic.designsaga.domain.coreapi;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class DocumentView {
    final DocumentId documentId;
    final String name;
    List<FileId> fileIds = new ArrayList<>();

    public void addFile(FileId fileId) {
        fileIds.add(fileId);
    }

    public void removeFile(FileId fileId) {
        fileIds.remove(fileId);
    }
}
