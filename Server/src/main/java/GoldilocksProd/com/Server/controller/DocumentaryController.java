package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.DocumentaryProject;
import GoldilocksProd.com.Server.services.S3Service.S3Service;
import GoldilocksProd.com.Server.services.documentaryService.DocumentaryProjectServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DocumentaryController {

    private final DocumentaryProjectServices documentaryProjectServices;

    @Autowired
    private S3Service s3Service;


    @PostMapping(value = "/api/documentary-project/upload/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DocumentaryProject createDocumentaryProject(@RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
                                           @RequestParam("projectType") String projectType,
                                           @RequestParam("title") String title,
                                           @RequestParam("date") String date,
                                           @RequestParam("videoPath") String videoPath,
                                           @RequestParam("documentaryDirector") String documentaryDirector,
                                           @RequestParam("documentaryLength") String documentaryLength,
                                           @RequestParam("synopsis") String synopsis)

    {

        // Create a MusicProject instance and set its properties
        DocumentaryProject documentaryProject = new DocumentaryProject();

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            s3Service.addImage(file);
            //make sure to change this to 5 years
            documentaryProject.setThumbnailPath(s3Service.getPermanentImageUrl(originalFileName));
        }

        documentaryProject.setProjectType(projectType);
        documentaryProject.setTitle(title);
        documentaryProject.setDate(date);
        documentaryProject.setVideoPath(videoPath);
        documentaryProject.setDocumentaryDirector(documentaryDirector);
        documentaryProject.setDocumentaryLength(documentaryLength);
        documentaryProject.setSynopsis(synopsis);

        // Save the DocumentaryProject and return the saved entity
        return documentaryProjectServices.createDocumentaryProject(documentaryProject);
    }

    @PutMapping(value = "/api/documentary-project/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DocumentaryProject updateDocumentaryProject(@PathVariable Long id,
                                                       @RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
                                                       @RequestParam("projectType") String projectType,
                                                       @RequestParam("title") String title,
                                                       @RequestParam("date") String date,
                                                       @RequestParam("videoPath") String videoPath,
                                                       @RequestParam("documentaryDirector") String documentaryDirector,
                                                       @RequestParam("documentaryLength") String documentaryLength,
                                                       @RequestParam("synopsis") String synopsis)
    {
        DocumentaryProject existingDocumentaryProject = documentaryProjectServices.getDocumentaryProjectById(id);

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            s3Service.addImage(file);
            existingDocumentaryProject.setThumbnailPath(s3Service.getPermanentImageUrl(originalFileName));
        }
        existingDocumentaryProject.setProjectType(projectType);
        existingDocumentaryProject.setTitle(title);
        existingDocumentaryProject.setDate(date);
        existingDocumentaryProject.setVideoPath(videoPath);
        existingDocumentaryProject.setDocumentaryDirector(documentaryDirector);
        existingDocumentaryProject.setDocumentaryLength(documentaryLength);
        existingDocumentaryProject.setSynopsis(synopsis);
        return documentaryProjectServices.updateDocumentaryProject(existingDocumentaryProject);
    }

    @DeleteMapping("/api/documentary-project/delete/{id}")
    public ResponseEntity<String> deleteDocumentaryProject(@PathVariable Long id){
        try {
            DocumentaryProject documentaryProject = documentaryProjectServices.getDocumentaryProjectById(id);
            if (documentaryProject != null) {
                documentaryProjectServices.deleteDocumentaryProject(documentaryProject);
                s3Service.deleteImage(documentaryProject.getThumbnailPath());
                return ResponseEntity.ok().body("{\"message\": \"Success\"}");
            } else {
                // Handle the case when the documentaryProject doesn't exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("documentaryProject not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to delete the documentary project\"}");
        }
    }

    @GetMapping("/api/documentary-project/list/")
    public List<DocumentaryProject> getDocumentaryProjects(){
        return documentaryProjectServices.listAllDocumentaryProjects();
    }

}
