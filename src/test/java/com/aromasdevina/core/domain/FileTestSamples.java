package com.aromasdevina.core.domain;

import java.util.UUID;

public class FileTestSamples {

    public static File getFileSample1() {
        return new File().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static File getFileSample2() {
        return new File().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static File getFileRandomSampleGenerator() {
        return new File().id(UUID.randomUUID());
    }
}
