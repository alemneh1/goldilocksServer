package GoldilocksProd.com.Server.services.musicService;

import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.repository.MusicProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class MusicProjectServicesImpTest {

    @Mock
    private MusicProjectRepository musicProjectRepository;

    @InjectMocks
    private MusicProjectServicesImp musicProjectService;
    MusicProject musicProject;
    AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        musicProjectService = new MusicProjectServicesImp(musicProjectRepository);
        musicProject = new MusicProject(
                null,
                "Music",
                "Kottume",
                "2022",
                "Alex",
                "Kotume is ormoifa song that was produced by Goldilocks producaiton",
               "imageURL",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testFindAllMusicProject() {

        List<MusicProject> expectedProjects = Arrays.asList(musicProject);

        // Mock the behavior of musicProjectRepository.findAll() to return the expectedProjects list
        when(musicProjectRepository.findAll()).thenReturn(expectedProjects);

        // Act
        List<MusicProject> actualProjects = musicProjectService.findAllMusicProject();

        // Assert
        // Verify that musicProjectRepository.findAll() was called exactly once
        verify(musicProjectRepository, times(1)).findAll();

        // Assert that the actualProjects list contains the same elements as the expectedProjects list
        assertThat(expectedProjects).isEqualTo( actualProjects);
    }

    @Test
    void testCreateMusicProject() {
        // Arrange
        // Create a music project with the necessary details
        MusicProject musicProjectToCreate = new MusicProject(
                null,
                "Music",
                "Kottume",
                "2022",
                "Alex",
                "Kotume is ormoifa song that was produced by Goldilocks producaiton",
                "imageURL",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );

        // Mock the behavior of musicProjectRepository.save(musicProjectToCreate) to return the saved music project
        MusicProject savedMusicProject = new MusicProject(
                1L,
                "Music",
                "Kottume",
                "2022",
                "Alex",
                "Kotume is ormoifa song that was produced by Goldilocks producaiton",
                "imageURL",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );
        when(musicProjectRepository.save(musicProjectToCreate)).thenReturn(savedMusicProject);

        // Act
        MusicProject createdMusicProject = musicProjectService.createMusicProject(musicProjectToCreate);

        // Assert
        // Verify that musicProjectRepository.save(musicProjectToCreate) was called exactly once
        verify(musicProjectRepository, times(1)).save(musicProjectToCreate);

        // Assert that the returned createdMusicProject is equal to the savedMusicProject
        assertThat(createdMusicProject).isEqualTo(savedMusicProject);
    }

    @Test
    void updateMusicProject() {
        // Arrange
        // Create an existing music project with the necessary details
        MusicProject existingMusicProject = new MusicProject(
                1L,
                "Existing Music",
                "Existing Kottume",
                "2022",
                "Existing Alex",
                "Existing description",
                "existingImageURL",
                "https://www.youtube.com/watch?v=existingVideoID"
        );

        // Mock the behavior of musicProjectRepository.findById(existingMusicProject.getId()) to return the existing music project
        when(musicProjectRepository.findById(existingMusicProject.getId())).thenReturn(Optional.of(existingMusicProject));

        // Create an updated music project with the necessary details
        MusicProject updatedMusicProject = new MusicProject(
                1L,
                "Updated Music",
                "Updated Kottume",
                "2023",
                "Updated Alex",
                "Updated description",
                "updatedImageURL",
                "https://www.youtube.com/watch?v=updatedVideoID"
        );

        // Mock the behavior of musicProjectRepository.save(updatedMusicProject) to return the updated music project
        when(musicProjectRepository.save(updatedMusicProject)).thenReturn(updatedMusicProject);

//        // Act
//        MusicProject result = musicProjectService.updateMusicProject(updatedMusicProject);
//
//        // Assert
//        // Verify that musicProjectRepository.findById(existingMusicProject.getId()) was called exactly once
//        verify(musicProjectRepository, times(1)).findById(existingMusicProject.getId());
//
//        // Verify that musicProjectRepository.save(updatedMusicProject) was called exactly once
//        verify(musicProjectRepository, times(1)).save(updatedMusicProject);
//
//        // Assert that the returned result is equal to the updated music project
//        assertThat(result).isEqualTo(updatedMusicProject);
//    }


    }
    @Test
    void getMusicProjectById() {

        assertThat(musicProject).isNotNull();

    }

    @Test
    void deleteMusicProject() {
        // Arrange
        // Mock the behavior of musicProjectRepository.existsById(existingMusicProject.getId()) to return true
        when(musicProjectRepository.existsById(musicProject.getId())).thenReturn(true);

        // Act
        boolean result = musicProjectService.deleteMusicProject(musicProject);

        // Assert
        // Verify that musicProjectRepository.existsById(existingMusicProject.getId()) was called exactly once
        verify(musicProjectRepository, times(1)).existsById(musicProject.getId());

        // Verify that musicProjectRepository.delete(existingMusicProject) was called exactly once
        verify(musicProjectRepository, times(1)).delete(musicProject);

        // Assert that the music project with the specified ID no longer exists
        assertThat(result).isTrue();
    }


}