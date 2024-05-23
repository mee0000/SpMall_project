import type { RouteRecordRaw } from 'vue-router'
// 모든 유저 접근가능 
export const constantRoute: RouteRecordRaw[] = [
    {
        path: '/',
        name: 'layout', 
        component: () => import('@/layout/index.vue'),
        meta: {
            type: 'menu'
        },
        redirect: '/home',
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('@/views/home/index.vue'),
                meta: {
                    title: 'home', // 2차 메뉴 네임
                    hidden: false
                }
            }
        ]
    },
    {  
        path: '/',
        name: 'user',
        component: () => import('@/layout/index.vue'),
        meta: {
            type: 'loginForm'
        },
        children: [
            {
            path: '/login',
            component: () => import('@/views/auth/login/index.vue'),
            meta: {
                title: 'login', // 1차 메뉴 네임
                hidden: true,
                icon: ''
            }
            },
            {
            path: '/sigin',
            component: () => import('@/views/auth/registration/index.vue'),
            meta: {
                title: 'sigin', // 1차 메뉴 네임
                hidden: true,
                icon: ''
            }
            }
        ]
    },
    {
        path: '/404',
        name:'error',
        component: () => import('@/views/404/index.vue'),
        meta: {
            title: '404', // 1차 메뉴 이름
            hidden: true
        }
    },
    {
        path: '/shop',
        name: 'shop', 
        component: () => import('@/layout/index.vue'),
         meta: {
             type: 'menu',
             title: 'shop'
        },
        redirect: '/shop/product',
        children: [
            {
                path: '/shop/product',
                name: 'product',
                component: () => import('@/views/product/index.vue'),
                meta: {
                    title: 'shop', // 2차 메뉴 네임
                    hidden: false
                },
            },
             {
                path: '/shop/productDetail',
                name: 'productDetail',
                component: () => import('@/views/product/detail/index.vue'),
                meta: {
                    hidden: true
                }
            },
            {
                path: '/cart',
                name: 'cart',
                component: () => import('@/views/cart/index.vue'),
                meta: {
                    title: 'cart', // 2차 메뉴 네임
                    hidden: true
                },
            },
            {
                path: '/shop/best',
                name: '',
                component: () => import('@/views/product/best/index.vue'),
                meta: {
                    title: 'outlet', // 2차 메뉴 네임
                    hidden: false,
                    mode: true
                }
            },
            {
                path: '/payment',
                name: 'payment',
                component: () => import('@/views/pay/index.vue'),
                meta: {
                    title: 'checkout', // 2차 메뉴 네임
                    hidden: true
                }
            }
        ]  
    }
]