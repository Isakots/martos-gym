package hu.isakots.martosgym.rest;

import hu.isakots.martosgym.exception.FileUploadException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@Controller
@RequestMapping(value = API_CONTEXT)
public class FileController {
    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/profile/image/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws FileUploadException {
        storageService.store(file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/profile/image/download")
    public ResponseEntity<Map<String, String>> downloadFile() throws ResourceNotFoundException {
        String encodedImage = storageService.loadFile();
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("content", encodedImage);
        return ResponseEntity.ok(jsonMap);
    }

}