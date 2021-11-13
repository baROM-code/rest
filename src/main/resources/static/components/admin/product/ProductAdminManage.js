const ProductAdminManage = {
    data() {
        return {
            product: {},
            file: null,
            categories: []
        }
    },
    methods: {
        getProductById(productId) {
            axios.get('/api/v1/product/' + productId).then(response => {
                this.product = response.data;
            })
        },
        getCategories() {
            axios.get('/api/v1/category/').then(response => {
                this.categories = response.data;
            })
        },
        saveProduct() {
            const formData = new FormData();
            formData.append("image", this.file);
            formData.append('product', new Blob([JSON.stringify(this.product)], { type: "application/json" }));

            axios.post("/api/v1/product", formData)
                .then(result => {
                    console.log(result);
                    this.$router.push({ name: 'adminProduct' })
                }, function (error) {
                    console.log(error);
                });
        },
        pickProductImage(event) {
            if (event.target.files.length > 0) {
                this.file = event.target.files[0];
            }
        }
    },
    mounted() {
        const productId = this.$route.params.id;
        if (productId) {
            this.getProductById(productId);
        }

        this.getCategories();
    },
    template: `
        <div class="container">
            <div class="form-group">
                <input type="file" @change="pickProductImage" name="image" id="image">
                <input type="text" hidden>
            </div>

            <div class="form-group mb-3">
                <label for="productTitle" class="form-label">Наименование продукта</label>
                <input v-model="product.title" id="productTitle" type="text" class="form-control"  placeholder="Укажите наименование">
            </div>
            
            <div class="form-group">
              <label for="productDescription">Описание продукта</label>
              <textarea v-model="product.description" class="form-control" placeholder="Введите описание продукта" id="productDescription" style="height: 100px"></textarea>
            </div>
            
            <div class="form-group mb-3">
                <label for="productPrice" class="form-label">Цена продукта</label>
                <input v-model="product.price" id="productPrice" type="number" step="0.01" class="form-control" placeholder="Укажите цену" >
            </div>

            <div class="form-group mb-3">
                <label for="categories" class="form-label">Категории</label>
                <select v-model="product.categoryId" id="categories" class="form-select">
                    <option disabled value="">Без категории</option>
                    <option v-for="category in categories" v-bind:value="category.id">
                        {{ category.name }}
                    </option>
                </select>
            </div>

            <button type="submit" @click="saveProduct" class="btn btn-primary">Сохранить</button>
        </div>
    `,
}