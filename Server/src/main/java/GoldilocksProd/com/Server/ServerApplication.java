package GoldilocksProd.com.Server;

import GoldilocksProd.com.Server.security.user.User;
import GoldilocksProd.com.Server.security.user.Role;
import GoldilocksProd.com.Server.security.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
@Import(CorsConfig.class)
public class ServerApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Create a user if it doesn't exist
		if (userRepository.findByEmail("4alexmic@gmail.com").isEmpty()) {
			User user = User.builder()
					.firstname("Alex")
					.lastname("Shiferaw")
					.email("4alexmic@gmail.com")
					.password(passwordEncoder.encode("1"))
					.role(Role.ADMIN)
					.build();
			userRepository.save(user);
		}
//		User user = User.builder()
//				.firstname("Alex")
//				.lastname("Tedla")
//				.email("alex@gmail.com")
//				.password(passwordEncoder.encode("1111"))
//				.role(Role.USER)
//				.build();
//		userRepository.save(user);
	}
}


//	@Autowired
//	private S3Service s3Service;
//
//	@Bean
//	CommandLineRunner run(MusicProjectRepository musicRepo, CommercialProjectRepository commercialProjectRepository, MovieProjectRepository movieProjectRepository, DocumentaryProjectRepository documentaryProjectRepository) {
////		return args -> {
////			MusicProject musicProject = new MusicProject(
////					null,
////					"Music",
////					"Kottume",
////					"2022",
////					"Alex",
////					"Kotume is ormoifa song that was produced by Goldilocks producaiton",
////					s3Service.getPresignedImageUrl("response.jpeg", 5000),
////					"https://www.youtube.com/watch?v=8weSLMxDczY"
////			);
////
////			MusicProject musicProject1 = new MusicProject(
////					null,
////					"Music",
////					"Kottume",
////					"2022",
////					"Biruk Jane",
////					"Kotume is ormoifa song that was produced by Goldilocks producaiton",
////					s3Service.getPresignedImageUrl("photo_2022-11-14_15-25-55.jpg", 5000),
////					"https://www.youtube.com/watch?v=8weSLMxDczY"
////			);
////
////			MovieProject movieProject = new MovieProject(
////					null,
////					"Movie",
////					"Up and Down",
////					"2021",
////					s3Service.getPresignedImageUrl("Abe Kotu.jpg", 5000),
////					"https://www.youtube.com/watch?v=8weSLMxDczY",
////					"2:50",
////					"Napi Wendosen, Selam Tesfaye, Alemayew Chala, Mitiku Kinfe",
////					"Alexander Tedla",
////					"This movie is produced by goldilocks production and other disc ......"
////			);
////			MovieProject movieProject2 = new MovieProject(
////					null,
////					"Documentary",
////					"The Doc is real Hunter",
////					"2013",
////					s3Service.getPresignedImageUrl("Abe Kotu.jpg", 5000),
////					"https://www.youtube.com/watch?v=8weSLMxDczY",
////					"3:50",
////					"Alex Tedla, Mitku, Alemayew Chala, Mitiku Kinfe",
////					"Alexander Tedla",
////					"This movie is produced by goldilocks production and other disc ......"
////
////			);
////
////			DocumentaryProject documentaryProject = new DocumentaryProject(null,
////					"Documentary",
////					"The Java Hunter",
////					"2010",
////					s3Service.getPresignedImageUrl("kottu mef (2).jpg", 5000),
////					"https://www.youtube.com/watch?v=8weSLMxDczY","Alex Ander The Greate", "4:00","Hello doc i am working and then this clear that it will be greate if you do codding " );
////
////			musicRepo.save(musicProject);
////			musicRepo.save(musicProject1);
////			movieProjectRepository.save(movieProject);
////			movieProjectRepository.save(movieProject2);
////			documentaryProjectRepository.save(documentaryProject);
//		};
//	}
//}