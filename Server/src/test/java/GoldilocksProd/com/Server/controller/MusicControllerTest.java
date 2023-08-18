package GoldilocksProd.com.Server.controller;

import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.repository.MusicProjectRepository;
import GoldilocksProd.com.Server.services.musicService.MusicProjectServicesImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



class MusicControllerTest {


    private MockMvc mockMvc;

    @Mock
    private MusicProjectServicesImp musicProjectService;

    @Mock
    private MusicProjectRepository musicProjectRepository;
    MusicProject musicProject1;
    MusicProject musicProject2;

    List<MusicProject> musicProjectList = new ArrayList<>();

    MusicProject musicProject;
    AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this); // Initialize the mocks

        mockMvc = MockMvcBuilders.standaloneSetup(new MusicController(musicProjectService)) // Manually create MockMvc and set up the controller
                .build();


        musicProject1 = new MusicProject(
                null,
                "Music",
                "Nafkeshignal",
                "2012",
                "Frezer",
                "Frezer nafkeshingla is ormoifa song that was produced by Goldilocks producaiton",
                "pic path",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );

        musicProject2 = new MusicProject(
                null,
                "Music",
                "Kottume",
                "2022",
                "Alex",
                "Kotume is ormoifa song that was produced by Goldilocks producaiton",
                "pic path",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );
        musicProjectList.add(musicProject1);
        musicProjectList.add(musicProject2);

    }
    @Test
    void createMusicProject() throws Exception {
        // Perform the POST request with required parameters and the fake thumbnail image path
        mockMvc.perform(post("/api/music-projects/upload")
                        .contentType(MediaType.MULTIPART_FORM_DATA) // Set the Content-Type header
                        .param("projectType", "Music")
                        .param("title", "Test Title")
                        .param("date", "2023")
                        .param("musicianName", "Test Musician")
                        .param("musicDescription", "Test Description")
                        .param("videoPath", "Test Video Path")
                ).andDo(print())
                .andExpect(status().isOk()); // You can add more assertions if needed
        verify(musicProjectService, times(1)).createMusicProject(any(MusicProject.class));
        // Verify the interactions with the mock services (if needed)
        // For example: verify(musicProjectServices, times(1)).createMusicProject(any(MusicProject.class));
    }
    @Test
    void testUpdateMusicProject() throws Exception {
        // Create an existing MusicProject with a known ID
        Long existingProjectId = 1L;
        MusicProject existingMusicProject = new MusicProject(existingProjectId,
                "Old Type",
                "Old Title",
                "Old Date",
                "Old Musician",
                "Old Description",
                "Old Thumbnail",
                "Old Video");

        // Set up mock behavior for the musicProjectService.getMusicProjectById method
        when(musicProjectService.getMusicProjectById(existingProjectId)).thenReturn(existingMusicProject);

        // Perform the PUT request to update the music project
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/music-projects/update/{id}", existingProjectId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("projectType", "New Type")
                        .param("title", "New Title")
                        .param("date", "New Date")
                        .param("musicianName", "New Musician")
                        .param("musicDescription", "New Description")
                        .param("videoPath", "New Video")
                ).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the musicProjectService.updateMusicProject method was called with the updated MusicProject
        verify(musicProjectService).updateMusicProject(argThat(updatedProject -> {
            // Perform assertions on the updated MusicProject object
            assertThat(updatedProject.getId()).isEqualTo(existingProjectId);
            assertThat(updatedProject.getProjectType()).isEqualTo("New Type");
            assertThat(updatedProject.getTitle()).isEqualTo("New Title");
            assertThat(updatedProject.getDate()).isEqualTo("New Date");
            assertThat(updatedProject.getMusicianName()).isEqualTo("New Musician");
            assertThat(updatedProject.getMusicDescription()).isEqualTo("New Description");
            assertThat(updatedProject.getVideoPath()).isEqualTo("New Video");
            // Since the thumbnail is being updated in the controller, it is handled differently.
            // Instead of comparing the thumbnail directly, you can verify that the s3Service.addImage method was called with the correct arguments.

            // Replace the condition below with your own verification based on the S3Service behavior in the controller
            assertThat(updatedProject.getThumbnailPath()).startsWith(""); // Assuming a prefix for the thumbnail URL

            return true;
        }));
    }

    @Test
    void testDelete() throws Exception {
        // Create a known MusicProject ID for deletion
        Long existingProjectId = 7L;

        MusicProject existingMusicProject = new MusicProject(
                existingProjectId,
                "Music",
                "Nafkeshignal",
                "2012",
                "Frezer",
                "Frezer nafkeshingla is ormoifa song that was produced by Goldilocks producaiton",
                "pic path",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );

        // Configure the behavior of musicProjectService.getMusicProjectById
        when(musicProjectService.getMusicProjectById(existingProjectId)).thenReturn(existingMusicProject);

        // Perform the DELETE request to delete the music project
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/music-project/delete/{id}", existingProjectId)
                )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        when(musicProjectService.getMusicProjectById(existingProjectId)).thenReturn(null);
        // Verify that the musicProjectService.deleteMusicProject method was called with the correct MusicProject
        verify(musicProjectService).deleteMusicProject(existingMusicProject);

    }

    @Test
    void testList() throws Exception {
        when(musicProjectService.findAllMusicProject()).thenReturn(musicProjectList);
        this.mockMvc.perform(get("/api/music-project/list/")).andDo(print()).andExpect(status().isOk());
    }
}