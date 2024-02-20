package lucafavaretto.ProjectWeekU5W2.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//utilizzata per abilitare la sicurezza basata su web in un'applicazione Spring Boot
@EnableMethodSecurity
//Quando si utilizza @EnableMethodSecurity, si indica l'intenzione di proteggere i propri metodi
// mediante annotazioni come @PreAuthorize, @Secured o @RolesAllowed.
public class AuthConfig {
    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Disabilitare alcuni comportamenti di default
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);// Disabilitiamo il form di default di login
        httpSecurity.csrf(AbstractHttpConfigurer::disable); // Non vogliamo la protezione da CSRF, l'attacco CSRF coinvolge
        // la creazione di una richiesta HTTP valida (ad esempio, una richiesta di modifica delle impostazioni dell'account)
        // e l'invio di questa richiesta dal browser dell'utente autenticato senza il suo consenso.
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // Non vogliamo le sessioni in caso l'applicazione non richieda sistemi di protezione ulteriori
        //che potrebbero inficiare sull'esperienza dell'utente

        // Aggiungere filtri custom prima del filtro generale (importante ordine)
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // Aggiungere/rimuovere determinate regole di protezione per gli endpoint
        // Qui possiamo determinare se debba essere necessaria un'autenticazione per accedervi
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll());

        return httpSecurity.build();
    }


}
