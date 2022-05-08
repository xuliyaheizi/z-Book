import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)
const routes = [
    {
        path: '/',
        name: 'index',
        //懒加载
        component: () => import('../views/index'),
        meta: {
            title: "z-Book",
            keepAlive: true
        }
    },
    {
        path: '/details',
        name: 'bookdetails',
        //懒加载
        component: () => import('../views/bookdetails'),
    },
    {
        path: '/bookLists',
        name: 'bookLists',
        component: () => import('../views/booklists'),
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login'),
        meta: {
            title: '用户登录',
            keepAlive: true  //需要缓存页面
        }
    },
    {
        path: '/backStage',
        name: 'backStage',
        component: () => import('../views/backStage'),

        children: [
            {
                path: 'index',
                name: 'index_admin',
                component: () => import('../views/admin/index_admin'),
                meta: {
                    title: '后台管理系统',
                },
            },
            {
                //子路由前不能加斜杠/
                path: 'admin/showBookLists',
                name: 'showBookLists',
                component: () => import('../views/admin/showBookLists'),
                meta: {
                    title: '后台管理系统',
                },
            },
            {
                path: 'admin/addNewBook',
                name: 'addNewBook',
                component: () => import('../views/admin/addNewBook'),
                meta: {
                    title: '后台管理系统',
                },
            }
        ]
    },
    {
        path: "/bookCart",
        name: "bookCart",
        component: () => import('../views/bookCart'),
        children: [
            {
                path: 'myCart',
                name: 'myCart',
                component: () => import('../views/cartBox/myCart'),
                meta: {
                    title: '购物车',
                }
            },
            {
                path: 'trueOrder',
                name: 'trueOrder',
                component: () => import('../views/cartBox/trueOrder'),
                meta: {
                    title: '确认订单'
                }
            },
            {
                path: 'successOrder',
                name: 'successOrder',
                component: () => import('../views/cartBox/successOrder'),
                meta: {
                    title: '支付成功',
                }
            }
        ]
    }
]
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
