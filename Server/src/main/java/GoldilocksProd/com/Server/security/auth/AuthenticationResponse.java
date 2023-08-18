package GoldilocksProd.com.Server.security.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String role;
    private String errorMessage; // Add an error message field

    // Add a static method to create a successful response
    public static AuthenticationResponse success(String token, String role) {
        return AuthenticationResponse.builder()
                .token(token)
                .role(role)
                .build();
    }

    // Add a static method to create an error response
    public static AuthenticationResponse error(String errorMessage) {
        return AuthenticationResponse.builder()
                .errorMessage(errorMessage)
                .build();
    }
}
