import { createRouter, createWebHistory } from 'vue-router'
import SignIn from "@/views/Login.vue";

 const router = createRouter({
     history: createWebHistory(import.meta.env.BASE_URL),
     routes: [
         {
             path:'/',
             name:'home',
             component:SignIn
         },
     ]
 })
