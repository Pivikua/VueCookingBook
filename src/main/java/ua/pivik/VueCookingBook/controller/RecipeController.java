package ua.pivik.VueCookingBook.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.pivik.VueCookingBook.domain.Recipe;
import ua.pivik.VueCookingBook.domain.Views;
import ua.pivik.VueCookingBook.dto.ObjectType;
import ua.pivik.VueCookingBook.dto.EventType;
import ua.pivik.VueCookingBook.repo.RecipeRepo;
import ua.pivik.VueCookingBook.util.WsSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @autor Alexander Pivovarov
 */

@RestController
@RequestMapping("recipe")
public class RecipeController {
    private final RecipeRepo recipeRepo;
    private final BiConsumer<EventType, Recipe> wsSender; // используем BiConsumer а не WsSender

    @Autowired
    public RecipeController(RecipeRepo recipeRepo, WsSender wsSender) {
        this.recipeRepo = recipeRepo;
        this.wsSender = wsSender.getSender(ObjectType.RECIPE, Views.IdName.class);
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Recipe> list() {
        return recipeRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullRecipe.class)
    public Recipe getOne(@PathVariable("id") Recipe recipe) {
        return recipe;
    }

    @PostMapping
    public Recipe create(@RequestBody Recipe recipe) {
        recipe.setCreationDate(LocalDateTime.now());
        Recipe updatedRecipe = recipeRepo.save(recipe);
        // accept - метод передачи даных в Consumer или BiConsumer
        // передаем первым аргументом тип эвента и функционал эвента
        wsSender.accept(EventType.CREATE, updatedRecipe);
        return updatedRecipe;
    }

    @PutMapping("{id}")
    public Recipe update(@PathVariable("id") Recipe recipeFromDb,    // этот месседж получается из БД по id
                          @RequestBody Recipe recipe) {               // этот месседж получаем из формы пользователя
        // копируем данные из месседжа формы в месеседж базы данных кроме поля id
        BeanUtils.copyProperties(recipe, recipeFromDb, "id");
        Recipe updatedRecipe = recipeRepo.save(recipeFromDb);
        wsSender.accept(EventType.UPDATE, updatedRecipe);
        return updatedRecipe;                         // записываем в репо БД
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Recipe recipe) {
        recipeRepo.delete(recipe);
        wsSender.accept(EventType.REMOVE, recipe);
    }
}
