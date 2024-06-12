package adnavas.fitlux_backend.security;

import adnavas.fitlux_backend.security.filter.JwtAuthenticationFilter;
import adnavas.fitlux_backend.security.filter.JwtValidationFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(auth ->
                        //USUARIOS
                auth.requestMatchers(HttpMethod.GET,"/api/usuarios", "/api/usuarios/{id}").permitAll()
                        .requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/usuarios/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/usuarios/{id}").hasRole("ADMIN")
                        //SALAS
                        .requestMatchers(HttpMethod.GET,"/api/salas","/api/salas/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/salas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/salas/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/salas/{id}").hasRole("ADMIN")
                        //CLASES
                        .requestMatchers(HttpMethod.GET,"/api/clases","/api/clases/{id}","/api/clases/actividad/{idActividad}", "/api/clases/usuario/{idUsuario}","/api/clases/usuarios/{idClase}").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/clases").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/clases/register/{idClase}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/api/clases/unregister/{idClase}").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,"/api/clases/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/clases/{id}").hasRole("ADMIN")
                        //AVISOS
                        .requestMatchers(HttpMethod.GET,"/api/avisos","/api/avisos/{id}","/api/avisos/usuario/{idUsuario}").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/avisos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/avisos/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/avisos/{id}").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/avisos/{id}").hasAnyRole("USER","ADMIN")
                        //ACTIVIDADES
                        .requestMatchers(HttpMethod.GET,"/api/actividades","/api/actividades/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/actividades").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/actividades/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/actividades/{id}").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
        ).addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Mi API de la aplicación Fitlux")
                        .description("Esta es la API utilizada para la aplicación Fitlux.")
                        .version("1.0").contact(new Contact().name("Adrián Navas")
                                .email("adrian.navasdev@gmail.com").url("adrian.navasdev@gmail.com"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }

}
