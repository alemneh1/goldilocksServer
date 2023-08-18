package GoldilocksProd.com.Server.services.musicService;


import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.repository.MusicProjectRepository;
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
public class MusicProjectServicesImp implements MusicProjectServices {

    private final MusicProjectRepository musicProjectRepository;

    @Override
    public List<MusicProject> findAllMusicProject() {
        return musicProjectRepository.findAll();
    }

    @Override
    public MusicProject createMusicProject(MusicProject musicProject) {
        {
            return musicProjectRepository.save(musicProject);
        }
    }
    @Override
    public MusicProject updateMusicProject(MusicProject musicProject) {
        try {
            if (musicProjectRepository.existsById(musicProject.getId())) {
                return musicProjectRepository.save(musicProject);
            } else {
                // Handle the case when the MusicProject doesn't exist
                throw new IllegalArgumentException("MusicProject not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            // You can customize this based on your error handling strategy
            throw new RuntimeException("Failed to update the MusicProject: " + e.getMessage());
        }
    }

    public boolean deleteMusicProject(MusicProject musicProject) {
        if (musicProjectRepository.existsById(musicProject.getId())) {
            musicProjectRepository.delete(musicProject);
            return true;
        } else {
            return false;
        }
    }

    public MusicProject getMusicProjectById(Long id){
        Optional<MusicProject> optionalMusicProject = musicProjectRepository.findById(id);
        return optionalMusicProject.orElse(null);
    }

}
