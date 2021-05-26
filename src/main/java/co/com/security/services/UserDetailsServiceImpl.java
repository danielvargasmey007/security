package co.com.security.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.com.security.model.User;
import co.com.security.repositories.UserRepository;

/**
 * The Class UserDetailsServiceImpl.
 * @author AVARGAS
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /** The user repository. */
    private UserRepository userRepository;

    /**
     * Instantiates a new user details service impl.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load user by username.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPass(), getAuthorities());
    }

    /**
     * Gets the authorities.
     *
     * @return the authorities
     */
    private static Set<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        SimpleGrantedAuthority simpleGrantedAuthoritynew = new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(simpleGrantedAuthoritynew);
        return authorities;
    }
}
