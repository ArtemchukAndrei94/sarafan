import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import App from 'pages/App.vue'
import store from 'store/store'
import { connect } from './util/ws'
import 'vuetify/dist/vuetify.min.css'
import * as Sentry from '@sentry/browser'
import * as Integrations from '@sentry/integrations'

Sentry.init({
    dsn: 'https://bc28c06686de4e91b6b9849668f7faa5@sentry.io/2182639',
    integrations: [new Integrations.Vue({Vue, attachProps: true})],
});


Sentry.configureScope(scope =>
    scope.setUser({
        id: profile && profile.id,
        username: profile && profile.name
    }));

if (profile) {
    connect()
}

Vue.use(Vuetify)
new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
});

