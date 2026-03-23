package com.aromasdevina.core.service;

import java.net.URI;
import java.time.Duration;

public interface StorageService {
    public URI presignPut(String key, String contentType, Duration expiry);

    public URI presignGet(String key, Duration expiry);
}
