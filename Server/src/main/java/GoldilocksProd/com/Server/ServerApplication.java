package GoldilocksProd.com.Server;


import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.repository.MusicProjectRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@NoArgsConstructor
@AllArgsConstructor
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	MusicProject musicProject = new MusicProject(null,
			"Music",
			"Alex Tedla",
			2022,
			"Biruk Jane",
			"Kotume is ormoifa song that was produced by Goldilocks producaiton",
			"../assets/images/music/kottume.jpg",
			"https://www.youtube.com/watch?v=8weSLMxDczY"
	);

	@Bean
	CommandLineRunner run(MusicProjectRepository musicRepo){
		return args -> {
			musicRepo.save(musicProject);
		};
	}
}
