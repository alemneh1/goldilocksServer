package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.MovieProject;
import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.services.S3Service;
import GoldilocksProd.com.Server.services.movieService.MovieProjectServiceImp;
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

public class MovieController {
    private final MovieProjectServiceImp movieProjectService;

    @Autowired
    private S3Service s3Service;


    @PostMapping(value = "/api/movie-project/upload/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MovieProject createMusicProject(@RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
                                           @RequestParam("projectType") String projectType,
                                           @RequestParam("title") String title,
                                           @RequestParam("date") String date,
                                           @RequestParam("videoPath") String videoPath,
                                           @RequestParam("movieLength") String movieLength,
                                           @RequestParam("cast") String cast,
                                           @RequestParam("director") String director,
                                           @RequestParam("movieDescription") String movieDescription)
    {
        // Create a MusicProject instance and set its properties
        MovieProject movieProject = new MovieProject();

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            s3Service.addImage(file);
            //make sure to change this to 5 years
            movieProject.setThumbnailPath(s3Service.getPresignedImageUrl(originalFileName, 1800));
        }

        movieProject.setProjectType(projectType);
        movieProject.setTitle(title);
        movieProject.setDate(date);
        movieProject.setVideoPath(videoPath);
        movieProject.setMovieLength(movieLength);
        movieProject.setCast(cast);
        movieProject.setDirector(director);
        movieProject.setMovieDescription(movieDescription);

        // Save the MusicProject and return the saved entity
        return movieProjectService.createMovieProject(movieProject);
    }

    @PutMapping("/api/movie-project/update/{id}")
    public MovieProject updateMovieProject(@PathVariable Long id,
                                           @RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
                                           @RequestParam("projectType") String projectType,
                                           @RequestParam("title") String title,
                                           @RequestParam("date") String date,
                                           @RequestParam("videoPath") String videoPath,
                                           @RequestParam("movieLength") String movieLength,
                                           @RequestParam("cast") String cast,
                                           @RequestParam("director") String director,
                                           @RequestParam("movieDescription") String movieDescription){

        MovieProject existingMovieProject = movieProjectService.getMovieProjectById(id);

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            s3Service.addImage(file);
            existingMovieProject.setThumbnailPath(s3Service.getPresignedImageUrl(originalFileName, 1800));
        }

        existingMovieProject.setProjectType(projectType);
        existingMovieProject.setTitle(title);
        existingMovieProject.setDate(date);
        existingMovieProject.setVideoPath(videoPath);
        existingMovieProject.setMovieLength(movieLength);
        existingMovieProject.setCast(cast);
        existingMovieProject.setDirector(director);
        existingMovieProject.setMovieDescription(movieDescription);
        return movieProjectService.updateMovieProject(existingMovieProject);

    }

    @DeleteMapping("/api/movie-project/delete/{id}")
    public ResponseEntity<String> deleteMovieProject(@PathVariable Long id){
        try {
            MovieProject movieProject = movieProjectService.getMovieProjectById(id);
            if (movieProject != null) {
                movieProjectService.deleteMovieProject(movieProject);
                s3Service.deleteImage(movieProject.getThumbnailPath());
                return ResponseEntity.ok().body("{\"message\": \"Success\"}");
            } else {
                // Handle the case when the MusicProject doesn't exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("movieProject not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to delete the movie project\"}");
        }
    }



    @GetMapping("/api/movie-project/list/")
    public List<MovieProject> list(){
        return movieProjectService.listAllMovieProject();
    }
}
