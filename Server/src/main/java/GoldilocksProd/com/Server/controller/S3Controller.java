package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;
    @PostMapping("/upload")
    public void upload(@RequestBody Object json) {
        s3Service.saveJson(json);
    }

    @PostMapping("/upload/image")
    public void addImage(@RequestParam("file") MultipartFile file ) {
        s3Service.addImage(file);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable("filename") String filename) {
        byte[] fileData = s3Service.downloadFile(filename);

        HttpHeaders headers = new HttpHeaders();
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else if (filename.endsWith(".png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else {
            // Handle other image formats if needed
        }

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

}
