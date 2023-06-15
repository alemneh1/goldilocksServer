package GoldilocksProd.com.Server.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class S3Service implements  FileServiceImpl{

    @Value("${bucketName}")
    private String bucketName;
    private final AmazonS3 s3;
    private final ObjectMapper objectMapper;

    public S3Service(AmazonS3 s3, ObjectMapper objectMapper) {
        this.s3 = s3;
        this.objectMapper = objectMapper;
    }

    @Override
    public String saveJson(Object jsonObject) {
        try {
            String json = objectMapper.writeValueAsString(jsonObject);

            byte[] bytes = json.getBytes();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType("application/json");

            String objectKey = "jsonObject.json";
            PutObjectResult objectResult = s3.putObject(bucketName, objectKey, inputStream, metadata);

            return objectResult.getContentMd5();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON object to string", e);
        }
    }

    @Override
    public void addImage(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String objectKey = originalFilename; // Set the desired object key for the image

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            s3.putObject(bucketName, objectKey, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add image to S3", e);
        }
    }

    @Override
    public ResponseEntity<byte[]> download(String filename) {

       return null;
    }


    @Override
    public byte[] downloadFile(String filename) {
        S3Object s3Object = s3.getObject(bucketName, filename);
        try (S3ObjectInputStream objectInputStream = s3Object.getObjectContent()) {
            return IOUtils.toByteArray(objectInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file from S3", e);
        }
    }
    public String getPresignedImageUrl(String filename, int expirationTimeInSeconds) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, filename)
                .withMethod(HttpMethod.GET)
                .withExpiration(new Date(System.currentTimeMillis() + expirationTimeInSeconds * 1000));

        URL url = s3.generatePresignedUrl(request);

        // Return XML response with the presigned URL
        return url.toString();
    }
    @Override
    public String deleteFile(String filename) {
        return null;
    }

    @Override
    public List<String> listAll() {
        return null;
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException{
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
