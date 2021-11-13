const ProductAdminList = {
    data() {
        return {
            products: [],
        }
    },
    methods: {
        getProducts() {
            axios.get('/api/v1/product').then(response => {
                this.products = response.data;
            })
        }
    },
    mounted() {
        this.getProducts();
    },
    template: `
        <div class="container">
            <div class="mt-3 mb-3">
                <router-link class="btn btn-success" :to="{ name: 'productManage'}">Создать</router-link>
            </div>
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Наименование</th>
                  <th scope="col">Цена</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="product in products">
                  <th scope="row">{{ product.id }}</th>
                  <td>{{ product.title }}</td>
                  <td>{{ product.price }}</td>
                  <td>
                    <router-link class="btn btn-primary" :to="{ name: 'productManage', params: { id: product.id }}">Изменить</router-link>
                  </td>
                </tr>
              </tbody>
            </table>
        </div>
    `,
}