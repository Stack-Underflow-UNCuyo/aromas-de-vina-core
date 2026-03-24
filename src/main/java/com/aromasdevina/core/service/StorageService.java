package com.aromasdevina.core.service;

import java.net.URI;
import java.time.Duration;

public interface StorageService {
    URI presignPut(String key, String contentType, Duration expiry);

    URI presignGet(String key, Duration expiry);

    URI getPublicUrl(String key);
}
