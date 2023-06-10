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
	MusicProject commercial = new MusicProject(null,
			"Commecrical",
			"Selam Water",
			2024,
			"Selam Tesfaye",
			"Her ass is amazing",
			"../assets/images/music/kottume.jpg",
			"https://www.youtube.com/watch?v=8weSLMxDczY"
	);

	MusicProject musicProject1 = new MusicProject(
  null,
		  "Commercial",
		  "Selam Water",
		  2024,
		  "Selam Tesfaye",
		  "Her ass is amazing",
		  "../assets/images/music/kottume.jpg",
		  "https://www.youtube.com/watch?v=8weSLMxDczY"
	);

	MusicProject musicProject2 = new MusicProject(
  null,
		  "Commercial",
		  "New Campaign",
		  2023,
		  "Marketing Solutions",
		  "Introducing our new advertising campaign",
			"../assets/images/music/kottume.jpg",
		  "https://www.youtube.com/watch?v=video1"
	);

	MusicProject musicProject3 = new MusicProject(
  null,
		  "Commercial",
		  "Product Demo",
		  2023,
		  "Tech Innovations",
		  "Experience the features of our latest product",
			"../assets/images/music/kottume.jpg",
		  "https://www.youtube.com/watch?v=video2"
	);

	MusicProject musicProject4 = new MusicProject(
  null,
		  "Commercial",
		  "Holiday Sale",
		  2024,
		  "Retail Solutions",
		  "Unbeatable discounts for the holiday season",
			"../assets/images/music/kottume.jpg",
		  "https://www.youtube.com/watch?v=video3"
	);

	MusicProject musicProject5 = new MusicProject(
  null,
		  "Commercial",
		  "Brand Launch",
		  2023,
		  "Brand Management",
		  "Introducing our new brand identity",
			"../assets/images/music/kottume.jpg",
		  "https://www.youtube.com/watch?v=video4"
	);

	@Bean
	CommandLineRunner run(MusicProjectRepository musicRepo){
		return args -> {
			musicRepo.save(musicProject);
			musicRepo.save(commercial);
			musicRepo.save(musicProject1);
			musicRepo.save(musicProject2);
			musicRepo.save(musicProject3);
			musicRepo.save(musicProject4);
			musicRepo.save(musicProject5);
		};
	}
}
