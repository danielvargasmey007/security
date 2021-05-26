package co.com.security.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * The Class JWTAuthorizationFilter.
 * @author AVARGAS
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    /**
     * Instantiates a new JWT authorization filter.
     *
     * @param authManager the auth manager
     */
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    /**
     * Do filter internal.
     *
     * @param req   the req
     * @param res   the res
     * @param chain the chain
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {
       
        String header = req.getHeader("Authorization");
        
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    /**
     * Gets the authentication.
     *
     * @param request the request
     * @return the authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user;
            try {
                user = Jwts.parserBuilder()
                                           .setSigningKey(Keys.hmacShaKeyFor(JWTAuthenticationFilter.getKey().getEncoded()))
                                           .build()
                                           .parseClaimsJws(token.replace("Bearer ", ""))
                                           .getBody()
                                           .getSubject();
            } catch (Exception e) {
                return null;
            } 

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}