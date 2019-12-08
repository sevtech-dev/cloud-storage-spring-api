package com.github.migangqui.cloud.storage.spring.bean;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetFileRequest {
    private String path;
    private String bucketName;
}
