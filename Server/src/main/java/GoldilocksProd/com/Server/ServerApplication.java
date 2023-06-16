package GoldilocksProd.com.Server;


import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.repository.MusicProjectRepository;
import GoldilocksProd.com.Server.services.S3Service;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private S3Service s3Service;

	@Bean
	CommandLineRunner run(MusicProjectRepository musicRepo) {
		return args -> {
			MusicProject musicProject = new MusicProject(
					null,
					"Music",
					"Kottume",
					2022,
					"Biruk Jane",
					"Kotume is ormoifa song that was produced by Goldilocks producaiton",
					s3Service.getPresignedImageUrl("kottume.jpg", 5000),
					"https://www.youtube.com/watch?v=8weSLMxDczY"
			);

			MusicProject com = new MusicProject(
					null,
					"Music",
					"Tileyaleh",
					2022,
					"Sosi Girma",
					"Kotume is ormoifa song that was produced by Goldilocks producaiton",
					s3Service.getPresignedImageUrl("sos image.JPG", 500),
					"https://www.youtube.com/watch?v=6pJnnIrNxhY"
			);
			musicRepo.save(musicProject);
			musicRepo.save(com);
		};
	}
}