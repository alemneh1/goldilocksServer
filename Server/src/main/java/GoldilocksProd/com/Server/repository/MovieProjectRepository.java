package GoldilocksProd.com.Server.repository;

import GoldilocksProd.com.Server.projects.MovieProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieProjectRepository extends JpaRepository<MovieProject, Long> {
}
