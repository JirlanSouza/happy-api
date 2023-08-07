package com.dev.happyapi.shared.storage.local;

import com.dev.happyapi.shared.storage.FileInfo;
import com.dev.happyapi.shared.storage.FileStorage;
import com.dev.happyapi.shared.storage.StoreFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class LocalFileStorage implements FileStorage {
    @Override
    public String storeFile(String folder, FileInfo info, InputStream stream) throws StoreFileException {
        try {
            Path dir = Path.of(folder);

            if (Files.notExists(dir)) {
                Files.createDirectory(dir);
            }

            Path filePath = dir.resolve(info.name()).toAbsolutePath();
            Files.copy(stream, filePath, StandardCopyOption.REPLACE_EXISTING);

            log.info("Successful stored file to Local file system to path: {}", filePath);
            return filePath.toUri().getPath();

        } catch (IOException e) {
            log.error("Error on store file to Local file system", e);
            throw new StoreFileException(e.getMessage());
        }
    }
}
