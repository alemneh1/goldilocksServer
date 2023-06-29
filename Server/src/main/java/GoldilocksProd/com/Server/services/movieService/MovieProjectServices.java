package GoldilocksProd.com.Server.services.movieService;

import GoldilocksProd.com.Server.projects.MovieProject;

import java.util.List;

public interface MovieProjectServices {

    public List<MovieProject> listAllMovieProject();

    public MovieProject createMovieProject(MovieProject movieProject);

    public void deleteMovieProject(MovieProject movieProject);

    public MovieProject updateMovieProject(MovieProject movieProject);

    public MovieProject getMovieProjectById(Long id);
}
