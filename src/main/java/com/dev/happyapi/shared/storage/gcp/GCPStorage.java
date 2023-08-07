package com.dev.happyapi.shared.storage.gcp;

import com.dev.happyapi.shared.storage.FileInfo;
import com.dev.happyapi.shared.storage.FileStorage;
import com.dev.happyapi.shared.storage.StoreFileException;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Slf4j
@Primary
@Component
public class GCPStorage implements FileStorage {
    private final Storage storage;
    private final String storageBucket;

    public GCPStorage(Storage storage, GCPStorageProperties storageProperties) {
        this.storage = storage;
        this.storageBucket = storageProperties.getBucket();
    }

    @Override
    public String storeFile(String folder, FileInfo info, InputStream stream) throws StoreFileException {
        try {
            BlobId blobId = BlobId.of(storageBucket, folder + "/" + info.name());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setAcl(Collections.nCopies(1, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
                    .build();

            Storage.BlobWriteOption blobPrecondition;

            if (storage.get(folder, info.name()) == null) {
                blobPrecondition = Storage.BlobWriteOption.doesNotExist();
            } else {
                blobPrecondition = Storage.BlobWriteOption.generationMatch(
                        storage.get(folder, info.name()).getGeneration()
                );
            }

            Blob fileBlob = storage.createFrom(blobInfo, stream, blobPrecondition);
            log.info(
                    "Successful stored file to Google Cloud Storage to bucket: {} with name: {}",
                    blobId.getBucket(),
                    blobId.getName()
            );

            return fileBlob.getMediaLink();

        } catch (IOException e) {
            log.error("Error on store file to Google Cloud Storage", e);
            throw new StoreFileException(e.getMessage());
        }

    }
}
