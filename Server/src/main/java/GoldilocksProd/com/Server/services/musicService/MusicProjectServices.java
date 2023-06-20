package GoldilocksProd.com.Server.services.musicService;



import GoldilocksProd.com.Server.projects.MusicProject;

import java.util.List;

public interface MusicProjectServices {

    List<MusicProject> findAllMusicProject();

    public MusicProject createMusicProject(MusicProject musicProject);
}
