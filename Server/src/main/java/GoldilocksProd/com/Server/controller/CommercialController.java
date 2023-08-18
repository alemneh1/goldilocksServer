package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.CommercialProject;
import GoldilocksProd.com.Server.services.S3Service.S3Service;
import GoldilocksProd.com.Server.services.commercialService.CommercialProjectServicesImp;
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

public class CommercialController {


    private final CommercialProjectServicesImp commercialProjectServices;



    @Autowired
    private S3Service s3Service;
    @PostMapping(value = "/api/commercial-project/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommercialProject createCommercialProject(@RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
                                                     @RequestParam("projectType") String projectType,
                                                     @RequestParam("title") String title,
                                                     @RequestParam("date") String date,
                                                     @RequestParam("videoPath") String videoPath,
                                                     @RequestParam("commercialDescription") String commercialDescription) {

        CommercialProject commercialProject = new CommercialProject();
        if (file != null) {
            String originalFileName = file.getOriginalFilename();

            // Increase the expiration time to 10 years (315360000 seconds)
            commercialProject.setThumbnailPath(s3Service.getPermanentImageUrl(originalFileName));
        }
        commercialProject.setProjectType(projectType);
        commercialProject.setTitle(title);
        commercialProject.setDate(date);
        commercialProject.setVideoPath(videoPath);
        commercialProject.setCommercialDescription(commercialDescription);

        return commercialProjectServices.createCommercialProject(commercialProject);
    }

    @DeleteMapping("/api/commercial-project/delete/{id}")
    public ResponseEntity<String> deleteCommercialProject(@PathVariable Long id){
        try {
            CommercialProject commercialProject = commercialProjectServices.getCommercialProjectById(id);
            if (commercialProject != null) {
                commercialProjectServices.deleteCommercialProject(commercialProject);
                s3Service.deleteImage(commercialProject.getThumbnailPath());
                return ResponseEntity.ok().body("{\"message\": \"Success\"}");
            } else {
                // Handle the case when the MusicProject doesn't exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MusicProject not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to delete the music project\"}");
        }
    }

    @PutMapping("/api/commercial-project/update/{id}")
    public CommercialProject updateCommercialProject(@PathVariable Long id,
                                           @RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
                                           @RequestParam("projectType") String projectType,
                                           @RequestParam("title") String title,
                                           @RequestParam("date") String date,
                                           @RequestParam("videoPath") String videoPath,
                                           @RequestParam("commercialDescription") String commercialDescription)
                                           {

        // Retrieve the existing music project by ID
        CommercialProject existingCommercialProject = commercialProjectServices.getCommercialProjectById(id);

        // Update the properties of the existing music project
        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            s3Service.addImage(file);
            existingCommercialProject.setThumbnailPath(s3Service.getPermanentImageUrl(originalFileName));
        }

        existingCommercialProject.setProjectType(projectType);
        existingCommercialProject.setTitle(title);
        existingCommercialProject.setDate(date);
        existingCommercialProject.setVideoPath(videoPath);
        existingCommercialProject.setCommercialDescription(commercialDescription);

        // Save the updated music project and return the updated entity
        return commercialProjectServices.updateCommercialProject(existingCommercialProject);
    }

    @GetMapping("/api/commercial-project/list")
    public List<CommercialProject> listAllCommercialProject(){
        return commercialProjectServices.listAllCommercialProject();
    }

}
