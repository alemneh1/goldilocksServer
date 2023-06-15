package GoldilocksProd.com.Server.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileServiceImpl {

    String saveJson(Object jsonObject);

    void addImage(MultipartFile file);

    public ResponseEntity<byte[]> download(String filename);

    byte[] downloadFile(String filename);

    String deleteFile(String filename);

    List<String> listAll();
}
