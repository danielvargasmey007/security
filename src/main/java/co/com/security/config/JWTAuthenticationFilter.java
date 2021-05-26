package co.com.security.config;

import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.security.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * The Class JWTAuthenticationFilter.
 * @author AVARGAS
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /** The authentication manager. */
    private AuthenticationManager authenticationManager;

    /**
     * Instantiates a new JWT authentication filter.
     *
     * @param authenticationManager the authentication manager
     */
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Attempt authentication.
     *
     * @param request  the request
     * @param response the response
     * @return the authentication
     * @throws AuthenticationException the authentication exception
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            User credenciales = new ObjectMapper().readValue(request.getInputStream(), User.class);
            
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credenciales.getUserName(), credenciales.getPass(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Successful authentication.
     *
     * @param request  the request
     * @param response the response
     * @param chain    the chain
     * @param auth     the auth
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication auth)
            throws IOException, ServletException {
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        String token = Jwts.builder().setIssuedAt(new Date())
                .setIssuer("https://www.cidenet.com.co")
                .claim("CLAIM_TOKEN", authorities)
                .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(Keys.hmacShaKeyFor(getKey().getEncoded()))
                .compact();
        
        response.getOutputStream().print(token);
    }

    public static Key getKey() {
        Key signingKey = new SecretKeySpec("zKJZ95M9DoAYiyhDEhyS9W4IaeAd/KnVUbkQsvMLcLI=".getBytes(), SignatureAlgorithm.HS256.name());
        return signingKey;
    }
}