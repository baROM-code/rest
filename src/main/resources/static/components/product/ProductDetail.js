const ProductDetail = {
    data() {
        return {
            product: {},
            review: {
                rating: 5
            }
        }
    },
    methods: {
        getProductById(productId) {
            axios.get("/api/v1/product/" + productId)
                .then(response => {
                    this.product = response.data;
                })
        },
        sendReview() {
            this.review.productId = this.$route.params.id;
            const userInfo = this.getUserInfo();
            if (userInfo.sub) {
                this.review.user = { username: userInfo.sub }
            }

            axios.post("/api/v1/review", this.review)
                .then(response => {
                    this.getProductById(this.$route.params.id);
                    this.review.comment = "";
                    this.review.rating = 5;
                })
                .catch(error => {
                    console.log(error);
                })
        }
    },
    mounted() {
        const productId = this.$route.params.id;
        this.getProductById(productId);
    },
    template: `
        <div>
            <div class="d-flex mb-5">
                <div>
                    <img :src="product.imageLink" width="500px" class="rounded float-start me-5" alt="">
                </div>
                <div>
                    <h3 class="mb-3">{{ product.title }}</h3>
                    <p class="mb-2">{{ product.description }}</p>
                    <h5 class="mb-5">Цена: {{product.price }} руб.</h5>
                    
                    <button type="button" class="btn btn-success" v-if="isAuthenticated()">Добавить в корзину</button>
                </div>
            </div>
            <div>
                <ul class="nav nav-tabs" id="myTab" role="tablist">
                  <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="reviews-tab" data-bs-toggle="tab" data-bs-target="#reviews" type="button" role="tab" aria-controls="reviews" aria-selected="true">Отзывы</button>
                  </li>
                  <li class="nav-item" role="presentation">
                    <button class="nav-link" id="attributes-tab" data-bs-toggle="tab" data-bs-target="#attributes" type="button" role="tab" aria-controls="attributes" aria-selected="false">Характеристики</button>
                  </li>
                </ul>
                <div class="tab-content" id="myTabContent">
                  <div class="tab-pane fade show active" id="reviews" role="tabpanel" aria-labelledby="reviews-tab">
                     <div>
                        <div v-for="review in product.reviews" :key="review.id" class="card mt-2">
                            <div class="card-body">
                                <h5 class="card-title">Оценка товара: {{ review.rating }}</h5>
                                <p class="card-text">{{ review.comment }}</p>
                            </div>
                            <div class="card-footer">
                                <small class="text-muted">Дата создания: {{ review.created }} </small>
                            </div>
                        </div>
                        
                        <div class="mt-5">
                            <h4> Оставьте отзыв! </h4>
                            <div class="d-flex">
                                <label for="rating">Оценка товара: {{ review.rating }}</label>
                                <input style="width: 30%" class="form-range ms-3 mb-2" type="range" min="0" max="5" id="rating" v-model="review.rating">
                            </div>
                            <div>
                                <label for="comment">Комментарий к отзыву</label>
                                <textarea class="form-control" type="text" id="comment" v-model="review.comment"></textarea>
                            </div>
                            
                            <button type="button" class="btn btn-success mt-3 mb-4" @click="sendReview">
                                Опубликовать!
                            </button>
                        </div>
                     </div>
                  </div>
                  <div class="tab-pane fade" id="attributes" role="tabpanel" aria-labelledby="attributes-tab">
                     Заглушка...
                  </div>
                </div>
            </div>
        </div>
        
    `
}