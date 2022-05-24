<template>
    <v-layout align-space-around justify-start column>
        <recipe-form :recipeAttr="recipe"/>
        <recipe-row v-for="recipe in sortedRecipes"
                     :key="recipe.id"
                     :recipe="recipe"
                     :editRecipe="editRecipe"/>
    </v-layout>
</template>

<script>
    import { mapGetters } from 'vuex'
    import RecipeRow from 'components/recipes/RecipeRow.vue'
    import RecipeForm from 'components/recipes/RecipeForm.vue'

    export default {
        name: "RecipeList",
        //props: ['recipes'],
        components: {
            RecipeRow,
            RecipeForm
        },
        data() {
            return {
                recipe: null
            }
        },
        computed: mapGetters(['sortedRecipes']),
        methods: {
            editRecipe(recipe) {
                this.recipe = recipe
            },
        }
    }
</script>

<style scoped>

</style>