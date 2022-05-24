import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource.js'
import App from 'pages/App.vue'
import store from 'store/store.js'
import { connect } from './util/ws.js'
import 'vuetify/dist/vuetify.min.css'

if (frontendData.profile) {
    connect()
}

Vue.use(Vuetify
    ,{ iconfont: 'mdiSvg' });

new Vue({
    el: '#app',
    store: store,
    render: a => a(App),
    vuetify: new Vuetify()
});