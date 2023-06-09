package GoldilocksProd.com.Server.services;



import GoldilocksProd.com.Server.projects.MusicProject;

import java.util.List;

public interface musicProjectServices {
    MusicProject addMusicProject(MusicProject musicProject);
    List<MusicProject> findAllMusicProject();
}
