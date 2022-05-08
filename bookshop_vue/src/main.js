import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import Element from 'element-ui';
import "element-ui/lib/theme-chalk/index.css";
import axios from "axios";


Vue.use(Element)
Vue.config.productionTip = false
Vue.prototype.$axios = axios
axios.defaults.withCredentials = true

router.beforeEach((to, from, next) => {
    /* 路由发生变化修改页面title */
    if (to.meta.title) {
        document.title = to.meta.title
    }
    next()
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
