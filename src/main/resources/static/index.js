const Router = new VueRouter({
    routes: [
        {
            path: '/',
            name: 'productList',
            component: ProductList
        },
        {
            path: '/product/:id',
            name: 'productDetail',
            component: ProductDetail
        },
        {
            path: '/singup',
            name: 'singUp',
            component: SingUp
        },
        {
            path: '/singin',
            name: 'singIn',
            component: SingIn
        },
        {
            path: '/admin',
            name: 'admin',
            component: Admin,
            children: [
                {
                    path: '/',
                    name: 'adminProduct',
                    component: ProductAdminList
                },
                {
                    path: '/product/edit/:id',
                    name: 'productManage',
                    component: ProductAdminManage
                },
                {
                    path: '/categories',
                    name: 'adminCategory',
                    component: CategoryAdminList
                },
                {
                    path: '/category/edit/:id',
                    name: 'categoryManage',
                    component: CategoryAdminManage
                }
            ]
        }],
})

axios.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token')
        if (token) {
            config.headers["Authorization"] = 'Bearer ' + token;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);


const authenticationMixin = {
    methods: {
        getToken() {
          return localStorage.getItem('token');
        },
        isAuthenticated() {
            return this.getToken() != null;
        },
        getUserInfo() {
            if (this.isAuthenticated()) {
                const payload = localStorage.getItem('token').split('.')[1];
                return JSON.parse(atob(payload));
            }

            return {};
        },
        hasRole(roleName) {
            const userInfo = this.getUserInfo();
            if (userInfo.roles) {
                return userInfo.roles.includes(roleName);
            }
        }
    }
}

Vue.mixin(authenticationMixin);



new Vue({
    el: '#app',
    router: Router,
});
