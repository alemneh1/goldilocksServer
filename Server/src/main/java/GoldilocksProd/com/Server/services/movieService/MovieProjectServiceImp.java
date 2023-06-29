package GoldilocksProd.com.Server.services.movieService;

import GoldilocksProd.com.Server.projects.MovieProject;
import GoldilocksProd.com.Server.repository.MovieProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MovieProjectServiceImp implements MovieProjectServices {
    private final MovieProjectRepository movieProjectRepository;
    @Override
    public List<MovieProject> listAllMovieProject() {
        return movieProjectRepository.findAll();
    }

    @Override
    public MovieProject createMovieProject(MovieProject movieProject) {
        return movieProjectRepository.save(movieProject);
    }

    @Override
    public void deleteMovieProject(MovieProject movieProject) {
        try {
            if (movieProjectRepository.existsById(movieProject.getId())) {
                movieProjectRepository.delete(movieProject);
            } else {
                // Handle the case when the MovieProject doesn't exist
                throw new IllegalArgumentException("MovieProject not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            // You can customize this based on your error handling strategy
            throw new RuntimeException("Failed to delete the MovieProject: " + e.getMessage());
        }
    }

    @Override
    public MovieProject updateMovieProject(MovieProject movieProject) {
        try {
            if (movieProjectRepository.existsById(movieProject.getId())) {
                return movieProjectRepository.save(movieProject);
            } else {
                // Handle the case when the MusicProject doesn't exist
                throw new IllegalArgumentException("Movie Project not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            // You can customize this based on your error handling strategy
            throw new RuntimeException("Failed to update the Movie Project: " + e.getMessage());
        }
    }

    @Override
    public MovieProject getMovieProjectById(Long id) {
        Optional<MovieProject> optionalMovieProject = movieProjectRepository.findById(id);
        return optionalMovieProject.orElse(null);
    }
}
