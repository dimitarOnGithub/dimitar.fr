package fr.dimitar.web.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@Profile("api")
public class AuthController {

    private final LinkAuthenticationProvider authenticationManager;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(LinkAuthenticationProvider authenticationManager, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/send-token")
    public ResponseEntity<?> generateLinkLogin(HttpServletRequest request) {
        this.authenticationService.generateLinkToken(request);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam(name = "token") UUID token, HttpServletRequest request) {
        // TODO: This isn't ideal, review in the future
        var tokenAuthentication = new LinkAuthentication(
                new LinkToken(token),
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
        );
        try {
            Authentication auth = this.authenticationManager.authenticate(tokenAuthentication);

            // Store authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Create session (if not exists)
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return ResponseEntity.ok(Map.of("status", "ok"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/myself")
    public ResponseEntity<?> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        LinkAuthentication authentication = (LinkAuthentication) auth;

        return ResponseEntity.ok(Map.of(
                "ipAddress", authentication.getIpAddress(),
                "userAgent", authentication.getUserAgent()
        ));
    }

}
