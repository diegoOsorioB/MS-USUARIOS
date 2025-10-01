package idmusic.modulo_jwt.auth;

import idmusic.modulo_jwt.exception.Excepcion;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import idmusic.modulo_jwt.JWT.JwtService;
import idmusic.modulo_jwt.user.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new Excepcion.UserNotFound(request.getCorreo()));

        String jwtToken = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new Excepcion.AuthException("Correo ya registrado");
        }

        User user = User.builder()
                .nombre(request.getNombre())
                .apellido_paterno(request.getApellido_paterno())
                .correo(request.getCorreo())
                .Password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public User updateUser(int id, RegisterRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Excepcion.UserNotFound("ID: " + id));

        user.setNombre(request.getNombre());
        user.setApellido_paterno(request.getApellido_paterno());
        user.setCorreo(request.getCorreo());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new Excepcion.UserNotFound("ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
