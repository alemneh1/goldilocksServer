package GoldilocksProd.com.Server.services.S3Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3ServiceImpl {

    String saveJson(Object jsonObject);

    void addImage(MultipartFile file);

    public ResponseEntity<byte[]> download(String filename);

    byte[] downloadFile(String filename);

    public String deleteImage(String presignedUrl);

    List<String> listAll();
}
