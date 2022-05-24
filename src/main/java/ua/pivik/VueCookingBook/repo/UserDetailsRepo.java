package ua.pivik.VueCookingBook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pivik.VueCookingBook.domain.User;

/**
 * @autor Alexander Pivovarov
 */
public interface UserDetailsRepo extends JpaRepository<User, String> {
}
