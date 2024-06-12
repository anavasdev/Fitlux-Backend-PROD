package adnavas.fitlux_backend;

import adnavas.fitlux_backend.entity.Usuario;
import adnavas.fitlux_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FitluxBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitluxBackendApplication.class, args);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner runner(UsuarioRepository repository) {
        return args -> {
            Usuario usuario = new Usuario(
                    "anavas",
                    "adrian.navasdev@gmail.com",
                    passwordEncoder.encode("anavas"),
                    "49505472L",
                    "Adrián Navas",
                    "ROLE_ADMIN",
                    true
            );
            if (repository.findByUsername("anavas") == null) {
                repository.save(usuario);
            }
        };
    }


}
