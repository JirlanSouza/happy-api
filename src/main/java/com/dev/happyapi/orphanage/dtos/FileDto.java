package com.dev.happyapi.orphanage.dtos;

import java.io.InputStream;

public record FileDto(String name, String mimeType, Long size, InputStream data) {
}
