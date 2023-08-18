package GoldilocksProd.com.Server.security.auth;

import GoldilocksProd.com.Server.security.user.User;
import GoldilocksProd.com.Server.security.user.Role;
import GoldilocksProd.com.Server.security.config.JwtService;
import GoldilocksProd.com.Server.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServices {
    private  final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
//        if (repository.findByEmail(request.getEmail()) != null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email address already exists.");
//        }
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public  AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        String role = user.getRole().name();

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .role(role)
                .build();
    }

    public List<User> listUsers(){
        return repository.findAll();
    }


    public boolean deleteUser(Integer userId){
        User user = getUserById(userId);
        if(user != null){
            repository.delete(user);
            return true;
        }else{
            return false;
        }
    }

    public User getUserById(int userID){
        Optional<User> optionalUser = repository.findById(userID);
        return  optionalUser.orElse(null);
    }


}
