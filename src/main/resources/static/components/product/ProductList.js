const ProductList = {
    data() {
        return {
            products: [],
        }
    },
    methods: {
        getProducts() {
            axios.get('/api/v1/product/').then(response => {
                this.products = response.data;
            })
        }
    },
    mounted() {
        this.getProducts();
    },
    template: `
        <div>
            <h1>Products:</h1>
            <div class="row row-cols-auto mt-5 mb-5">
                <div v-for="product in products" :key="product.id" class="card col px-0" style="width: 18rem;">
                    <router-link class="text-decoration-none text-dark" :to="{ name: 'productDetail', params: { id: product.id } }">
                        <img :src="product.imageLink" class="card-img-top" alt="product image">
                        <div class="card-body">
                            <h5 class="card-title">{{ product.title }}</h5>
                            <p class="card-text">{{ product.description }}</p>
                            <div class="justify-content"></div>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">Цена: {{ product.price }} руб.</small>
                        </div>
                    </router-link>
                </div>
            </div>
        </div>
    `,
}