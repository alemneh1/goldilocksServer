package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.CommercialProject;
import GoldilocksProd.com.Server.services.S3Service;
import GoldilocksProd.com.Server.services.commercialService.CommercialProjectServicesImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
            //String contentType = file.getContentType();
            //long fileSize = file.getSize();

//            logger.info("Original File Name: " + originalFileName);
//            logger.info("Content Type: " + contentType);
//            logger.info("File Size: " + fileSize);

            s3Service.addImage(file);
            //make sure to change this to 5 years
            commercialProject.setThumbnailPath(s3Service.getPresignedImageUrl(originalFileName, 1800));
        }
        commercialProject.setProjectType(projectType);
        commercialProject.setTitle(title);
        commercialProject.setDate(date);
        commercialProject.setVideoPath(videoPath);
        commercialProject.setCommercialDescription(commercialDescription);

        return commercialProjectServices.createCommercialProject(commercialProject);
    }

    @GetMapping("/api/commercial-project/list")
    public List<CommercialProject> listAllCommercialProject(){
        return commercialProjectServices.listAllCommercialProject();
    }

}
