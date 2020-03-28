package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.properties.ImageProperties;
import hu.isakots.martosgym.configuration.properties.MultiPartProperties;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.FileUploadException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import liquibase.util.file.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class StorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class.getName());

    private final AccountService accountService;
    private final ImageProperties imageProperties;
    private final MultiPartProperties multiPartProperties;
    private final Path rootLocation;

    public StorageService(AccountService accountService, ImageProperties imageProperties, MultiPartProperties multiPartProperties) {
        this.accountService = accountService;
        this.imageProperties = imageProperties;
        this.multiPartProperties = multiPartProperties;
        this.rootLocation = Paths.get(imageProperties.getUploadDirectory());
    }

    @PostConstruct
    public void init() {
        File uploadDirectory = new File(imageProperties.getUploadDirectory());
        if (!uploadDirectory.exists() && !uploadDirectory.mkdirs()) {
            throw new IllegalStateException("Directory to store uploaded images, cannot be created!");
        }
        File tempDirectory = new File(multiPartProperties.getLocation());
        if (!tempDirectory.exists() && !tempDirectory.mkdirs()) {
            throw new IllegalStateException("Directory to store uploaded images temporarly, cannot be created!");
        }
    }

    public void store(MultipartFile file) throws FileUploadException {
        validateUploadedFile(file);
        deleteOutDatedFile();
        saveImage(file);
    }

    public String loadFile() throws ResourceNotFoundException {
        User user = accountService.getAuthenticatedUserWithData();
        Optional<String> fileName = Optional.ofNullable(user.getImagePath());
        try {
            Path file = rootLocation.resolve(fileName
                    .orElseThrow(() -> new ResourceNotFoundException("User does not have uploaded image."))
            );
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return encodeImage(file);
            } else {
                throw new ResourceNotFoundException(MessageFormat.format("File not found with name: {0}", fileName));
            }
        } catch (IOException e) {
            throw new ResourceNotFoundException(MessageFormat.format("Error occured during reading file with name: {0}", fileName));
        }
    }


    private void deleteOutDatedFile() {
        String imagePath = accountService.getAuthenticatedUserWithData().getImagePath();
        if (!StringUtils.isEmpty(imagePath)) {
            Path path = rootLocation.resolve(imagePath);
            try {
                if (Files.deleteIfExists(path)) {
                    LOGGER.debug("File deletion was successful");
                } else {
                    LOGGER.error("File deletion cannot be performed");
                }
            } catch (IOException e) {
                // Image uploading process should not be interrupted because of this..
                LOGGER.error("Error occured during deleting previous imageFile with path: {}", imagePath);
            }
        }
    }

    private void validateUploadedFile(MultipartFile file) throws FileUploadException {
        validateFilePresence(file);
        validateExtension(file.getOriginalFilename());
    }

    private void validateFilePresence(MultipartFile file) throws FileUploadException {
        if (file.isEmpty()) {
            throw new FileUploadException("There is no uploaded file");
        }
    }

    private void validateExtension(String originalFileName) throws FileUploadException {
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        String[] supportedExtensions = imageProperties.getSupportedExtensions();
        AtomicBoolean isExtensionValid = new AtomicBoolean(false);
        Arrays.stream(supportedExtensions)
                .forEach(extension -> {
                    if (extension.equals(fileExtension)) {
                        isExtensionValid.set(true);
                    }
                });
        if (!isExtensionValid.get()) {
            throw new FileUploadException("The uploaded file extension is not valid");
        }
    }

    private void saveImage(MultipartFile file) throws FileUploadException {
        String fileNameToSave = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileNameToSave));
        } catch (Exception e) {
            throw new FileUploadException("Uploaded file cannot be saved.");
        }
        accountService.saveUserImage(fileNameToSave);
    }

    private String encodeImage(Path path) throws IOException {
        String extension = FilenameUtils.getExtension(path.toString());
        LOGGER.debug("File extension is: {}", extension);
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("data:image/")
                .append(extension)
                .append(";base64,")
                .append(Base64.getEncoder()
                        .withoutPadding()
                        .encodeToString(Files.readAllBytes(path)))
                .toString();
    }

}
