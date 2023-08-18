package GoldilocksProd.com.Server.security.auth;

import GoldilocksProd.com.Server.projects.MusicProject;
import GoldilocksProd.com.Server.security.user.Role;
import GoldilocksProd.com.Server.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServices services;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response = services.register(request);
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            // Handle the duplicate email exception and return an error response
            String errorMessage = "Email address already exists.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AuthenticationResponse.error(errorMessage));
        } catch (Exception e) {
            // Handle other exceptions if needed
            String errorMessage = "An error occurred.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AuthenticationResponse.error(errorMessage));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(services.authenticate(request));
    }

    @GetMapping("/listUsers")
    public List<User> listUsers(){
        return services.listUsers();
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        try {
            User user = services.getUserById(id);
           // System.out.println(user.getRole() + "==== " + Role.ADMIN);
            if (user != null && user.getRole() != Role.ADMIN) {
                services.deleteUser(id);
                return ResponseEntity.ok().body("{\"message\": \"User Successfully Deleted\"}");

            } else if (user != null && user.getRole() == Role.ADMIN) {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Admin User Can't \"}");
            }
            else {
                // Handle the case when the MusicProject doesn't exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., return error response, log the exception)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to delete the user\"}");
        }
    }

}