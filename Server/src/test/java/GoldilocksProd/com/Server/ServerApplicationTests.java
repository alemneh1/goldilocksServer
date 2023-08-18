package GoldilocksProd.com.Server;

import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.repository.MusicProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ServerApplicationTests {
	@Autowired
	private MusicProjectRepository musicProjectRepository;
	@Test
	void contextLoads() {
	}

//	@Test
//	void testCreate() {
//		MusicProject musicProject = new MusicProject(
//				null,
//				"Music",
//				"Kottume",
//				"2022",
//				"Alex",
//				"Kotume is ormoifa song that was produced by Goldilocks producaiton",
//				"pic path",
//				"https://www.youtube.com/watch?v=8weSLMxDczY"
//		);
//
//		MusicProject savedProject = musicProjectRepository.save(musicProject);
//
//		// Check if the object is saved successfully with a generated ID
//		assertThat(savedProject.getId()).isNotNull();
//		assertThat(savedProject.getDate()).isEqualTo("2022");
//		assertThat(savedProject.getMusicianName()).isEqualTo("Alex");
//	}

}
