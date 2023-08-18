package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.services.S3Service.S3Service;
import GoldilocksProd.com.Server.services.musicService.MusicProjectServicesImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j

public class MusicController {

    private final MusicProjectServicesImp musicProjectServices;
    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "/api/music-projects/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MusicProject createMusicProject(@RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
                                           @RequestParam("projectType") String projectType,
                                           @RequestParam("title") String title,
                                           @RequestParam("date") String date,
                                           @RequestParam("musicianName") String musicianName,
                                           @RequestParam("musicDescription") String musicDescription,
                                           @RequestParam("videoPath") String videoPath) {
        // Create a MusicProject instance and set its properties
        MusicProject musicProject = new MusicProject();

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            s3Service.addImage(file);
            //make sure to change this to 5 years
            musicProject.setThumbnailPath(s3Service.getPermanentImageUrl(originalFileName));
        }

        musicProject.setProjectType(projectType);
        musicProject.setTitle(title);
        musicProject.setDate(date);
        musicProject.setMusicianName(musicianName);
        musicProject.setMusicDescription(musicDescription);
        musicProject.setVideoPath(videoPath);

        // Save the MusicProject and return the saved entity
        return musicProjectServices.createMusicProject(musicProject);
    }

    @PutMapping("/api/music-projects/update/{id}")
    public MusicProject updateMusicProject(@PathVariable Long id,
                                           @RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
                                           @RequestParam("projectType") String projectType,
                                           @RequestParam("title") String title,
                                           @RequestParam("date") String date,
                                           @RequestParam("musicianName") String musicianName,
                                           @RequestParam("musicDescription") String musicDescription,
                                           @RequestParam("videoPath") String videoPath) {

        // Retrieve the existing music project by ID
        MusicProject existingMusicProject = musicProjectServices.getMusicProjectById(id);

        // Update the properties of the existing music project
        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            s3Service.addImage(file);
            existingMusicProject.setThumbnailPath(s3Service.getPermanentImageUrl(originalFileName));
        }

        existingMusicProject.setProjectType(projectType);
        existingMusicProject.setTitle(title);
        existingMusicProject.setDate(date);
        existingMusicProject.setMusicianName(musicianName);
        existingMusicProject.setMusicDescription(musicDescription);
        existingMusicProject.setVideoPath(videoPath);

        // Save the updated music project and return the updated entity
        return musicProjectServices.updateMusicProject(existingMusicProject);
    }


    @DeleteMapping("/api/music-project/delete/{id}")
    public ResponseEntity<String> deleteMusicProject(@PathVariable Long id) {
        try {
            MusicProject musicProject = musicProjectServices.getMusicProjectById(id);
            if (musicProject != null) {
                musicProjectServices.deleteMusicProject(musicProject);
                s3Service.deleteImage(musicProject.getThumbnailPath());
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

    @GetMapping("/api/music-project/list/")
    public List<MusicProject> list() {
        return musicProjectServices.findAllMusicProject();
    }
}

