package GoldilocksProd.com.Server.repository;
import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.repository.MusicProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class musicProjectRepositoryTest {
    @Autowired
    private MusicProjectRepository musicProjectRepository;
    @Test
    void testCreate() {
        MusicProject musicProject = new MusicProject(
                null,
                "Music",
                "Kottume",
                "2022",
                "Alex",
                "Kotume is ormoifa song that was produced by Goldilocks producaiton",
                "pic path",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );

        musicProjectRepository.save(musicProject);

        // Check if the object is saved successfully with a generated ID
        assertThat(musicProject.getId()).isNotNull();
    }

    @Test
    void testRead() {
        MusicProject musicProject = new MusicProject(
                null,
                "Music",
                "Kottume",
                "2022",
                "Alex",
                "Kotume is ormoifa song that was produced by Goldilocks producaiton",
                "pic path",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );

        musicProjectRepository.save(musicProject);

        // Find the object by its ID or any other unique attribute
        MusicProject retrievedProject = musicProjectRepository.findById(musicProject.getId()).orElse(null);

        // Check if the retrieved object is not null (i.e., found in the database)
        assertThat(retrievedProject).isNotNull();

        // Additional checks to compare the retrieved object's attributes with the original object's attributes
        assertThat(musicProject.getMusicianName()).isEqualTo(retrievedProject.getMusicianName());
        // Add more assertions for other attributes...
    }

    @Test
    void testUpdate() {
        MusicProject musicProject = new MusicProject(
                null,
                "Music",
                "Kottume",
                "2022",
                "Alex",
                "Kotume is ormoifa song that was produced by Goldilocks producaiton",
                "pic path",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );

        musicProjectRepository.save(musicProject);

        // Modify the object's attributes
        musicProject.setMusicianName("Frezer");
        // Modify other attributes...

        musicProjectRepository.save(musicProject);

        // Retrieve the updated object
        MusicProject updatedProject = musicProjectRepository.findById(musicProject.getId()).orElse(null);

        // Check if the changes were successfully updated in the database
        assertThat(updatedProject).isNotNull();
        assertThat(updatedProject.getMusicianName()).isEqualTo("Frezer");
        // Add more assertions for other updated attributes...
    }

    @Test
    void testDelete() {
        MusicProject musicProject = new MusicProject(
                null,
                "Music",
                "Kottume",
                "2022",
                "Alex",
                "Kotume is ormoifa song that was produced by Goldilocks producaiton",
                "pic path",
                "https://www.youtube.com/watch?v=8weSLMxDczY"
        );

        musicProjectRepository.save(musicProject);

        // Delete the object from the repository
        musicProjectRepository.delete(musicProject);

        // Try to find the object by its ID or any other unique attribute after deletion
        MusicProject deletedProject = musicProjectRepository.findById(musicProject.getId()).orElse(null);

        // Check if the object is not found after deletion
        assertThat(deletedProject).isNull();
    }
}
