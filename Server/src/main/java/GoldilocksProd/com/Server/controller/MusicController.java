package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.services.musicService.MusicProjectServicesImp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/musicProject")
@RequiredArgsConstructor
public class MusicController {

    private final MusicProjectServicesImp musicRepo;

    @GetMapping("/list")
    public List<MusicProject> list() {
        return musicRepo.findAllMusicProject();
    }
}

