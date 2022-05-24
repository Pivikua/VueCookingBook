package ua.pivik.VueCookingBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.pivik.VueCookingBook.repo.RecipeRepo;
import ua.pivik.VueCookingBook.domain.User;

import java.util.HashMap;

/**
 * @autor Alexander Pivovarov
 */
@Controller
@RequestMapping("/")
public class MainController {
    private final RecipeRepo recipeRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("profile", user);
            data.put("recipes", recipeRepo.findAll());
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
