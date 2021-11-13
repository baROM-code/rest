const CategoryAdminList = {
    data() {
        return {
            categories: [],
        }
    },
    methods: {
        getCategories() {
            axios.get('/api/v1/category').then(response => {
                this.categories = response.data;
            })
        }
    },
    mounted() {
        this.getCategories();
    },
    template: `
        <div class="container">
            <div class="mt-3 mb-3">
                <router-link class="btn btn-success" :to="{ name: 'categoryManage'}">Создать</router-link>
            </div>
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Наименование</th>
                  <th scope="col">Алиас</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="category in categories">
                  <th scope="row">{{ category.id }}</th>
                  <td>{{ category.name }}</td>
                  <td>{{ category.alias }}</td>
                  <td>
                    <router-link class="btn btn-primary" :to="{ name: 'categoryManage', params: { id: category.id }}">Изменить</router-link>
                  </td>
                </tr>
              </tbody>
            </table>
        </div>
    `,
}