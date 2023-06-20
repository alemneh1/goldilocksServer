package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.services.S3Service;
import GoldilocksProd.com.Server.services.musicService.MusicProjectServicesImp;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.logging.Logger;


@RestController
@RequiredArgsConstructor
@Slf4j

public class MusicController {
    private static final Logger logger = Logger.getLogger(MusicController.class.getName());
    private final MusicProjectServicesImp musicProjectServices;
    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "/api/music-projects", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MusicProject createMusicProject(@RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
                                           @RequestParam("projectType") String projectType,
                                           @RequestParam("title") String title,
                                           @RequestParam("year") String year,
                                           @RequestParam("musicianName") String musicianName,
                                           @RequestParam("musicDescription") String musicDescription,
                                           @RequestParam("videoPath") String videoPath) {
        // Create a MusicProject instance and set its properties
        MusicProject musicProject = new MusicProject();

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            //String contentType = file.getContentType();
            //long fileSize = file.getSize();

//            logger.info("Original File Name: " + originalFileName);
//            logger.info("Content Type: " + contentType);
//            logger.info("File Size: " + fileSize);

            s3Service.addImage(file);
            //make sure to change this to 5 years
            musicProject.setThumbnailPath(s3Service.getPresignedImageUrl(originalFileName, 1800));
        }

        musicProject.setProjectType(projectType);
        musicProject.setTitle(title);
        musicProject.setDate(year);
        musicProject.setMusicianName(musicianName);
        musicProject.setMusicDescription(musicDescription);
        musicProject.setVideoPath(videoPath);

        // Save the MusicProject and return the saved entity
        return musicProjectServices.createMusicProject(musicProject);
    }


    @GetMapping("/musicProject/list")
    public List<MusicProject> list() {
        return musicProjectServices.findAllMusicProject();
    }
}

