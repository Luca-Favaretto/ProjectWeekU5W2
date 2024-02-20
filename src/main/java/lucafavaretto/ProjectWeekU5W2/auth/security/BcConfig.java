package lucafavaretto.ProjectWeekU5W2.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//utilizzata per abilitare la sicurezza basata su web in un'applicazione Spring Boot
@EnableMethodSecurity
//Quando si utilizza @EnableMethodSecurity, si indica l'intenzione di proteggere i propri metodi
// mediante annotazioni come @PreAuthorize, @Secured o @RolesAllowed.
public class BcConfig {
    @Bean
    PasswordEncoder getBCrypt() {
        return new BCryptPasswordEncoder(11);
        // 11 è il numero di ROUNDS, ovvero quante volte viene eseguito l'algoritmo. Questo valore
        // è utile per impostare la velocità di esecuzione di BCrypt. Più veloce è BCrypt, meno sicure saranno
        // le password, e ovviamente viceversa. Di contro però più lento è l'algoritmo, peggiore sarà la User Experience.
        // Bisogna trovare il giusto bilanciamento tra le 2.
        // 11 significa che l'algoritmo verrà eseguito 2^11 volte cioè 2048 volte. Su un computer di prestazioni medie
        // vuol dire circa 100ms
    }
}
