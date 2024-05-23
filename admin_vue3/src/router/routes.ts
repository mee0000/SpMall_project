import type { RouteRecordRaw } from 'vue-router'
// 对外暴露配置路由（常量路由）：모든 유저 접근가능
export const constantRoute: RouteRecordRaw[] =  [
    {
       path: '/login',
       component: () => import('@/views/login/index.vue'),
       name: 'login',
       meta: {
         title: 'login', // 좌측 1차 메뉴 이름 
         hidden: true, // 좌측메뉴 노출할지 판단
         icon: "Promotion" //element-plus icon
       }
    },
    {
       path: '/',
       component: () => import('@/layout/index.vue'),
       name: 'layout',
       meta: {
          icon: "UserFilled"
       },
       redirect: '/home',
       children: [
         {
            path: '/home',
            name: 'home',
            component: () => import('@/views/home/index.vue'),
            meta: {
               title: 'home', // 2차메뉴 이름
               hidden: false,
               icon: "HomeFilled"
            }
         }
       ]
    },
    {
      path: '/404',
      component: () => import('@/views/404/index.vue'),
      name: 'error',
      meta: {
         title: '404', // 1차 메뉴 이름
         hidden: true
      }
   }
]
  
// 异步路由 (권한관리, 상품관리)
export const asyncRoute = [
      {
       path: '/acl',
       component: () => import('@/layout/index.vue'),
       name: 'Acl',
       redirect: '/acl/user',
       meta: {
         title: '권리제한',
         icon: 'Lock'
       },
       children: [
         {
            path:'/acl/user',
            component: () => import('@/views/acl/user/index.vue'),
            name: 'User',
            meta: {
               title: '사용자 관리',
               icon: 'User'
            }
         },
         {
            path:'/acl/role',
            component: () => import('@/views/acl/role/index.vue'),
            name: 'Role',
            meta: {
               title: '사용자 권한',
               icon: 'UserFilled'
            }
         },
         {
            path: '/acl/permission',
            component: () => import('@/views/acl/permission/index.vue'),
            name: 'Permission',
            meta: {
               title: '메뉴 권한',
               icon: 'Monitor'
            }
         }
       ]
    },
    {
         path: '/product',
         component: () => import('@/layout/index.vue'),
         name: 'Product',
         redirect: '/product/attr',
         meta: {
            title: '상품관리',
            icon: 'Goods'
         },
         children: [
            {
               path: '/product/attr',
               component: () => import('@/views/product/attr/index.vue'),
               name: 'Attr',
               meta: {
                  title: 'Attr',
                  icon: 'ChromeFilled'
               }
            },
            {
               path: '/product/sku',
               component: () => import('@/views/product/sku/index.vue'),
               name: 'Sku',
               meta: {
                  title: 'SKU관리',
                  icon: 'Orange'
               }
            },
            {
               path: '/product/spu',
               component: () => import('@/views/product/spu/index.vue'),
               name: 'Spu',
               meta: {
                  title: 'SPU관리',
                  icon: 'Calendar'
               }
            },
            {
               path: '/product/trademark',
               component: () => import('@/views/product/trademark/index.vue'),
               name: 'Trademark',
               meta: {
                  title: '브랜드관리',
                  icon: 'Calendar'
               }
            }
         ]
    },
]

// 任意路由 （any)
export const anyRoute = {
       path: '/:pathMatch(.*)*',
       redirect: '/404',
       name: 'Any',
       meta: {
         title: 'anyPage',
         hidden: true
       }
    }
