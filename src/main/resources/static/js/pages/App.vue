<template>
    <v-app>
        <v-app-bar color="green" dense app>
            <v-icon>{{chefhat}}</v-icon>
            <v-toolbar-title class="mx-2"><h3>CookingBook</h3></v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile">{{profile.name}}</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>{{logout}}</v-icon>
            </v-btn>
        </v-app-bar>
        <v-main>
            <v-container v-if="!profile">
                Необходимо авторизоваться через
                <a href="/login">Google</a>
            </v-container>
            <v-container v-if="profile">
                <recipes-list/>
            </v-container>
        </v-main>
    </v-app>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    import RecipesList from 'components/recipes/RecipeList.vue'
    import {addHandler} from 'util/ws'
    import {mdiExitToApp} from '@mdi/js'
    import {mdiChefHat} from '@mdi/js';

    export default {
        name: "App",
        components: {
            RecipesList
        },
        computed: mapState(['profile']),
        methods: mapMutations(['addRecipeMutation', 'updateRecipeMutation', 'removeRecipeMutation']),
        data() {
            return {
                logout: mdiExitToApp,
                chefhat: mdiChefHat
            }
        },
        created() {
            addHandler(data => {
                if (data.objectType === 'RECIPE') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addRecipeMutation(data.body);
                            break;
                        case 'UPDATE':
                            this.updateRecipeMutation(data.body);
                            break;
                        case 'REMOVE':
                            this.removeRecipeMutation(data.body);
                            break;
                        default:
                            console.error(`Looks like the event type if unknown "${data.eventType}"`)
                    }
                } else {
                    console.error(`Looks like the object type if unknown "${data.objectType}"`)
                }
            })
        }
    }
</script>

<style scoped>
    h3 {
        color: white;
        font-family: Georgia, Times, serif;
    }
    span {
        color: white;
        font-family: Georgia, Times, serif;
        font-weight: bold;
    }
</style>