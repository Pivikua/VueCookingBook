import Vue from 'vue';

const recipes = Vue.resource('/recipe{/id}');

export default {
    add: recipe => recipes.save({}, recipe),
    update: recipe => recipes.update({id: recipe.id}, recipe),
    remove: id => recipes.remove({id: id}) // для удаления используем только id
}