package repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import model.Patient;
import model.User;

public interface UserRepository extends JpaRepository<User, Long>  {

	
	User findByUsernameIgnoreCase(String username);
	Optional<User> findById(Long id);
	User findByEmail(String email);
	User save(User user);
	User findByPassword(String password);
	
	
	
}
