package GoldilocksProd.com.Server;


import GoldilocksProd.com.Server.projects.CommercialProject;
import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.repository.CommercialProjectRepository;
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
	CommandLineRunner run(MusicProjectRepository musicRepo, CommercialProjectRepository commercialProjectRepository) {
		return args -> {
			MusicProject musicProject = new MusicProject(
					null,
					"Music",
					"Kottume",
					"2022",
					"Alex",
					"Kotume is ormoifa song that was produced by Goldilocks producaiton",
					s3Service.getPresignedImageUrl("response.jpeg", 5000),
					"https://www.youtube.com/watch?v=8weSLMxDczY"
			);

			MusicProject musicProject1 = new MusicProject(
					null,
					"Music",
					"Kottume",
					"2022",
					"Biruk Jane",
					"Kotume is ormoifa song that was produced by Goldilocks producaiton",
					s3Service.getPresignedImageUrl("ablex-dire.jpg", 5000),
					"https://www.youtube.com/watch?v=8weSLMxDczY"
			);

			CommercialProject commercialProject = new CommercialProject(
					null,
					"Commercial",
					"Expensive Personality\"",
					"2018",
					s3Service.getPresignedImageUrl("806010375_259038.jpg", 5000),
					"https://www.youtube.com/watch?v=8weSLMxDczY",
					"This is one of Goldilocks production commercial that is used by some other people in time oif loerem alex is good"

			);
			CommercialProject commercialProject1 = new CommercialProject(
					null,
					"Commercial",
					"Dega Water",
					"2015",
					s3Service.getPresignedImageUrl("ablex-dire.jpg", 5000),
					"https://www.youtube.com/watch?v=8weSLMxDczY",
					"This is one of Goldilocks production commercial that is used by some other people in time oif loerem alex is good"

			);
			musicRepo.save(musicProject);
			musicRepo.save(musicProject1);
			commercialProjectRepository.save(commercialProject);
			commercialProjectRepository.save(commercialProject1);
		};
	}
}