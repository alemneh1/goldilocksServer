package GoldilocksProd.com.Server.services.musicService;


import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.repository.MusicProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


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
}
