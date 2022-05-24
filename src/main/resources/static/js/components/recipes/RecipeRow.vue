<template>
    <v-card class="my-2">
        <v-card-title class="title">
            {{ recipe.recipeName }}
        </v-card-title>
        <v-card-text class="text-justify">
            {{ recipe.text }}
        </v-card-text>
        <v-card-actions>
            <v-btn icon @click="edit">
                <v-icon>{{ editBtn }}</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn icon @click="del">
                <v-icon>{{ deleteBtn }}</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import {mdiDelete} from '@mdi/js';
    import {mdiSquareEditOutline} from '@mdi/js';
    import {mapActions} from 'vuex'

    export default {
        name: "RecipeRow",
        props: ['recipe', 'editRecipe'],
        data() {
            return {
                deleteBtn: mdiDelete,
                editBtn: mdiSquareEditOutline
            }
        },
        methods: {
            ...mapActions(['removeRecipeAction']),
            edit() {
                this.editRecipe(this.recipe)
            },
            del() {
                this.removeRecipeAction(this.recipe)
            }
        }
    }
</script>

<style scoped>
    .title {
        color: cornflowerblue;
        font-family: Georgia, Times, serif;
        font-weight: bold;
    }

</style>