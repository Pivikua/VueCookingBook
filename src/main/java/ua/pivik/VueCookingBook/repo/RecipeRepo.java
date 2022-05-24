package ua.pivik.VueCookingBook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pivik.VueCookingBook.domain.Recipe;

/**
 * @autor Alexander Pivovarov
 */
public interface RecipeRepo extends JpaRepository<Recipe, String> {

}
