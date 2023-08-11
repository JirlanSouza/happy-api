package com.dev.happyapi.orphanage.services;

import com.dev.happyapi.orphanage.dtos.CreateOrphanageImageDto;
import com.dev.happyapi.orphanage.dtos.FileDto;
import com.dev.happyapi.shared.storage.FileStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrphanageImageUploadServiceTest {

    @Mock
    FileStorage storage;

    @Mock
    OrphanagesImagesStorageProperties properties;

    @InjectMocks
    OrphanageImageUploadService service;

    @Test
    public void shouldNotStoreEmptyImageFIle() {
        FileDto fileDto = new FileDto(
                "empty file",
                "invalid mimetype",
                0L,
                InputStream.nullInputStream()
        );

        List<CreateOrphanageImageDto> result = service.saveImages(List.of(fileDto));

        verify(storage, never())
                .storeFile(any(), any(), any());
        assertEquals(result.size(), 0);
    }

    @Test
    public void shouldStoreImageFile() {
        FileDto fileDto = new FileDto(
                "image file",
                "png",
                10L,
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                }
        );

        List<CreateOrphanageImageDto> result = service.saveImages(List.of(fileDto));

        verify(storage, times(1)).storeFile(any(), any(), eq(fileDto.data()));
        assertEquals(result.size(), 1);
    }
}