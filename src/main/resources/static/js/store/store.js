import Vue from 'vue';
import Vuex from 'vuex';
import recipesApi from 'api/recipes.js'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        recipes: frontendData.recipes,
        profile: frontendData.profile
    },
    getters: {
        sortedRecipes: state => state.recipes.sort(/*(a, b) => -(a.id - b.id)*/)
    },
    mutations: {
        addRecipeMutation(state, recipe) {
            state.recipes = [
                ...state.recipes, recipe
            ]
        }
        ,
        updateRecipeMutation(state, recipe) {
            const updateIndex = state.recipes.findIndex(item => item.id === recipe.id);
            state.recipes = [
                ...state.recipes.slice(0, updateIndex),
                recipe,
                ...state.recipes.slice(updateIndex + 1)
            ]
        },
        removeRecipeMutation(state, recipe) {
            const deletionIndex = state.recipes.findIndex(item => item.id === recipe.id);
            if (deletionIndex > -1) {
                state.recipes = [
                    ...state.recipes.slice(0, deletionIndex),
                    ...state.recipes.slice(deletionIndex + 1) // - 1
                ]
            }
        }
    },
    actions: {
        async addRecipeAction({commit, state}, recipe) {
            const result = await recipesApi.add(recipe);
            const data = await result.json();
            const index = state.recipes.findIndex(item => item.id === data.id);
            console.log(result);
            if (index > -1) {
                commit('updateRecipeMutation', data);
            } else {
                commit('addRecipeMutation', data);
            }
        },
        async updateRecipeAction({commit}, recipe) {
            const result = await recipesApi.update(recipe);
            const data = await result.json();
            commit('updateRecipeMutation', data);
        },
        async removeRecipeAction({commit}, recipe) {
            const result = await recipesApi.remove(recipe.id);
            if (result.ok) {
                commit('removeRecipeMutation', recipe)
            }
        },
    }
});