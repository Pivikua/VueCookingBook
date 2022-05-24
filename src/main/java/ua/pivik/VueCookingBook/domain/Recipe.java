package ua.pivik.VueCookingBook.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @autor Alexander Pivovarov
 */
@Entity
@Table
public class Recipe {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name= "name", length = 255)
    private String recipeName;
    @Column(name= "text", length = 2048)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullRecipe.class)
    private LocalDateTime creationDate;
/*    private String parentId;
    private String childId;*/

    public Recipe() {
    }

    public Recipe(String recipeName, String text, LocalDateTime creationDate/*, String parentId, String childId*/) {
        this.recipeName = recipeName;
        this.text = text;
        this.creationDate = creationDate;
/*        this.parentId = parentId;
        this.childId = childId;*/
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

/*    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }*/

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
