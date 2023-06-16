package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.services.S3Service;
import GoldilocksProd.com.Server.services.musicService.MusicProjectServicesImp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicProjectServicesImp musicProjectServices;
    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "/api/music-projects", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MusicProject createMusicProject(@RequestParam("file") MultipartFile file,
                                           @RequestParam("projectType") String projectType,
                                           @RequestParam("title") String title,
                                           @RequestParam("year") int year,
                                           @RequestParam("musicianName") String musicianName,
                                           @RequestParam("musicDescription") String musicDescription,
                                           @RequestParam("thumbnailImage") String thumbnailImage,
                                           @RequestParam("videoPath") String videoPath) {
        // Create a MusicProject instance and set its properties
        MusicProject musicProject = new MusicProject();
        musicProject.setProjectType(projectType);
        musicProject.setTitle(title);
        musicProject.setDate(year);
        musicProject.setMusicianName(musicianName);
        musicProject.setMusicDescription(musicDescription);
        musicProject.setThumbnailPath(thumbnailImage);
        musicProject.setVideoPath(videoPath);

        // Save the MusicProject and return the saved entity
        return musicProjectServices.createMusicProject(musicProject);
    }


    @GetMapping("/musicProject/list")
    public List<MusicProject> list() {
        return musicProjectServices.findAllMusicProject();
    }
}

