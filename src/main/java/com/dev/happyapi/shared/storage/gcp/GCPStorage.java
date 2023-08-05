package com.dev.happyapi.shared.storage.gcp;

import com.dev.happyapi.shared.storage.FileInfo;
import com.dev.happyapi.shared.storage.FileStorage;
import com.dev.happyapi.shared.storage.StoreFileException;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

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
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

            Storage.BlobWriteOption blobPrecondition;

            if (storage.get(folder, info.name()) == null) {
                blobPrecondition = Storage.BlobWriteOption.doesNotExist();
            } else {
                blobPrecondition = Storage.BlobWriteOption.generationMatch(
                        storage.get(folder, info.name()).getGeneration()
                );
            }

            storage.createFrom(blobInfo, stream, blobPrecondition);
        } catch (IOException e) {
            throw new StoreFileException(e.getMessage());
        }

        return null;
    }
}
