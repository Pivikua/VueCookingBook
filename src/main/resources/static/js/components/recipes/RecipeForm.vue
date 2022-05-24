<template>
    <form>
        <v-col>
            <v-text-field
                    required
                    :counter="100"
                    label="Recipe name"
                    placeholder="Write something"
                    v-model="recipeName"></v-text-field>
            <v-textarea
                    required
                    solo
                    label="New recipe text"
                    placeholder="Write something"
                    v-model="text"></v-textarea>
            <v-btn
                    v-on:click="save">
                    Save
            </v-btn>
        </v-col>
    </form>
</template>

<script>
    import {mapActions} from 'vuex'

    export default {
        name: "RecipeForm",
        props: ['recipeAttr'],
        data() {  // во всех компонентах data - функция которая возвращвет объект каждого из компонентов
            return {
                recipeName: '',
                text: '',
                id: ''
            }
        },
        // метод наблюдатель watch:
        watch: { // следит за переменными в props: и data: и при измении переменных будет делать вычисления
            recipeAttr(newVal, oldVal) {  // при изменеии messageAttr мы получем новое или старое изначение
                this.recipeName = newVal.recipeName;
                this.text = newVal.text;
                this.id = newVal.id;
            }
        },
        methods: {
            ...mapActions(['addRecipeAction', 'updateRecipeAction']),
            save() {                                 // Создаем функцию записи
                const recipe = {
                    id: this.id,
                    recipeName: this.recipeName,
                    text: this.text
                }; // берем введеный текст

                if (this.id) {  // если
                    this.updateRecipeAction(recipe);
                } else {
                    this.addRecipeAction(recipe);
                }
                this.recipeName = '';                     // После отправки данных очищаем поле ввода
                this.text = '';                     // После отправки данных очищаем поле ввода
                this.id = ''                        // После отправки данных очищаем поле ввода
            }
        }
    }
</script>

<style scoped>

</style>