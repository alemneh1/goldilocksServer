package GoldilocksProd.com.Server.repository;


import GoldilocksProd.com.Server.projects.MusicProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicProjectRepository extends JpaRepository<MusicProject, Long> {

    // Custom query methods can be added here if needed

}