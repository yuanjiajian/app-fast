package com.app.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {
    private String path;
    private String pattern;

    public String getPath() {
        return System.getProperty("user.dir")+path;
    }

}
