import {createRouter, createWebHistory} from 'vue-router'
import App from "@/App";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";

const router = createRouter({
    history: createWebHistory(),
    routes : [
        {
            path: '/',
            name: 'App',
            component: App
        },
        {
            path: '/home',
            name: 'Home',
            component: Home
        },
        {
            path: '/login',
            name: 'Login',
            component: Login
        }
    ]
});
export default router;
