package hu.isakots.martosgym.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "image")
public class ImageProperties {
    private String[] supportedExtensions;
    private String uploadDirectory;

    public String[] getSupportedExtensions() {
        return supportedExtensions;
    }

    public void setSupportedExtensions(String[] supportedExtensions) {
        this.supportedExtensions = supportedExtensions;
    }

    public String getUploadDirectory() {
        return uploadDirectory;
    }

    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }
}
