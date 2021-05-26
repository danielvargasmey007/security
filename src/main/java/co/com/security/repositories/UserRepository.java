package co.com.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.security.model.User;

/**
 * The Interface UserRepository.
 * @author AVARGAS
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findByUserName(String username);

}
