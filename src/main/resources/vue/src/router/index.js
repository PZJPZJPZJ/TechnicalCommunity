import {createRouter, createWebHistory} from 'vue-router'
import App from "@/App";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import Post from "@/views/Post";
import Detail from "@/views/Detail";
import Info from "@/views/Info";
import Admin from "@/views/Admin";
import News from "@/views/News";
import Tag from "@/views/Tag";
import Chat from "@/views/Chat";
import Search from "@/views/Search";

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
        },
        {
            path: '/post',
            name: 'Post',
            component: Post
        },
        {
            path: '/info',
            name: 'Info',
            component: Info
        },
        {
            path: '/detail',
            name: 'Detail',
            component: Detail
        },
        {
            path: '/admin',
            name: 'Admin',
            component: Admin
        },
        {
            path: '/news',
            name: 'News',
            component: News
        },
        {
            path: '/tag',
            name: 'Tag',
            component: Tag
        },
        {
            path: '/chat',
            name: 'Chat',
            component: Chat
        },
        {
            path: '/search',
            name: 'Search',
            component: Search
        }
    ]
});
export default router;
