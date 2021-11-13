const Admin = {
    data() {
        return {}
    },
    template: `
        <div class="d-flex">
            <div>
                <ul class="list-group">
                    <li class="list-group-item">
                        <router-link :to="{ name: 'adminProduct'}">Продукты</router-link>
                    </li>
                    <li class="list-group-item">
                        <router-link :to="{ name: 'adminCategory'}">Категории</router-link>
                    </li>
                </ul>
            </div>
           
            <router-view :key="$route.path"></router-view>
        </div>
    `,
}