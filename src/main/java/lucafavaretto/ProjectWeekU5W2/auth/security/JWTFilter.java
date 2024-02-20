package lucafavaretto.ProjectWeekU5W2.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lucafavaretto.ProjectWeekU5W2.employees.Employee;
import lucafavaretto.ProjectWeekU5W2.employees.EmployeeSRV;
import lucafavaretto.ProjectWeekU5W2.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private EmployeeSRV employeeSRV;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
// Il codice di questo metodo verrà eseguito ad ogni richiesta che richieda di essere autenticati
        // Cose da fare:

        // 1. Verifichiamo se la richiesta effettivamente contiene un Authorization Header, se non c'è --> 401
        String authHeader = request.getHeader("Authorization");
        // Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MDgzNTUzNTcsImV4cCI6MTcwODQ0MTc1Nywic3ViIjoiNTRmMmJkY2QtZTJlMS00N2ZiLWI3NjktYzNiYzhmYWI1ODYyIn0.RuKEwVZcna02apIzUNTZ2vFLQBvCSm679FWvgAOHavxmSCKK7Lk9mr6n3b80q1q8_zGqYv_FIl8Qr4twlsIu4w
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Per favore metti il token nell'header");

        // 2. Se c'è estraiamo il token dall'header
        String accessToken = authHeader.substring(7);

        System.out.println("ACCESS TOKEN " + accessToken);

        // 3. Verifichiamo se il token è stato manipolato (verifica signature) e se non è scaduto (verifica Expiration Date)
        jwtTools.verifyToken(accessToken);

        // 4. Se è tutto OK proseguiamo con la catena fino ad arrivare all'endpoint

        // 4.1 Cerco l'utente nel DB (l'id sta nel token..)
        String id = jwtTools.extractIdFromToken(accessToken);
        Employee employee = employeeSRV.findById(UUID.fromString(id));

        // 4.2 Devo informare Spring Security che l'utente è autenticato (se non faccio questo step riceverò 403 come risposta)
        Authentication authentication = new UsernamePasswordAuthenticationToken(employee, null, employee.getAuthorities());
        // Ci servirà domani per l'AUTORIZZAZIONE
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4.3 Proseguo ora col prossimo elemento della catena
        filterChain.doFilter(request, response); // va al prossimo elemento della catena
//        L'interfaccia FilterChain è spesso utilizzata all'interno di un filtro personalizzato per consentire la continuazione
//        dell'esecuzione della catena dei filtri. Quando un filtro ha completato le sue operazioni, può chiamare il metodo doFilter su FilterChain,
//        passando la richiesta e la risposta. Questo invoca il filtro successivo nella catena. Se non ci sono altri filtri nella catena,
//        la richiesta raggiunge l'endpoint desiderato nell'applicazione.

        // 5. Se non è OK --> 401
    }

    // Disabilito il filtro per determinate richieste tipo Login o Register (non devono richiedere token)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Uso questo metodo per specificare in che situazioni il filtro NON DEVE FILTRARE (non deve chiedere il token)
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
        // Se l'URL della richiesta corrente corrisponde a /auth/qualsiasicosa, allora il filtro non entra in azione
    }
}
