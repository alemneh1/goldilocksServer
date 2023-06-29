package GoldilocksProd.com.Server.projects;

import GoldilocksProd.com.Server.model.ProjectModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "movie_project")
public class MovieProject extends ProjectModel {


    private String movieLength;
    private String cast;
    private String director;
    private String movieDescription;

    public MovieProject(Long id, String projectType, String title, String date,
                        String thumbnailPath, String videoPath, String movieLength,
                        String cast,String director, String movieDescription) {
        super(id, projectType, title, date, thumbnailPath, videoPath);
        this.movieLength = movieLength;
        this.cast = cast;
        this.director = director;
        this.movieDescription = movieDescription;
    }





}
