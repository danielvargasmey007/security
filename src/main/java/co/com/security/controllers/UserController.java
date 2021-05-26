package co.com.security.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class UserController.
 * @author AVARGAS
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    /**
     * Gets the usuario.
     *
     * @param username the username
     * @return the usuario
     */
    @GetMapping
    public ResponseEntity<String> getUsuario(@RequestParam(required = false) String userName) {
        if(userName ==  null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new ResponseEntity<String>("Hello ".concat(userName), HttpStatus.OK);
    }
}
