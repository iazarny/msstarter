/**
 * Created by Igor_Azarny on 12/1/2017.
 */

//import axios from 'axios';

//const apiHost = "http://172.28.128.3:8081/";
const apiHost = "/";
const config = {headers: {'Content-Type': 'application/json'}};

var products = [];

function findProduct(productId) {
    return products[findProductKey(productId)];
}
;

function findProductKey(productId) {
    for (var key = 0; key < products.length; key++) {
        if (products[key].id == productId) {
            return key;
        }
    }
}
;

var List = Vue.extend({
    template: '#product-list',
    data: function () {
        return {
            products: products,
            searchKey: ''
        };
    },
    computed: {
        filteredProducts: function () {
            var self = this;
            console.log();
            return self.products.filter(function (product) {
                return product.title.indexOf(self.searchKey) !== -1
            })
        }
    }
});

var Product = Vue.extend({
    template: '#product',
    data: function () {
        return {product: findProduct(this.$route.params.art_id)};
    }
});

var ProductEdit = Vue.extend({
    template: '#product-edit',
    data: function () {
        return {product: findProduct(this.$route.params.art_id)};
    },
    methods: {
        updateProduct: function () {
            var prd = this.product;
            console.info(prd);

            axios.put(apiHost + `api/articles/` + prd.id, JSON.stringify(prd), config)
                .then(response => {

                    var newProd = response.data;

                    products[findProductKey(newProd.id)] = {
                        id: newProd.id,
                        title: newProd.title,
                        content: newProd.content,
                        modified: newProd.modified
                    };
                })
                .catch(e => {
                    this.errors.push(e)
                });


            router.push('/');
        }
    }
});


var AddProduct = Vue.extend({
    template: '#add-product',
    data: function () {
        return {
            product: {title: '', content: '', modified: ''}
        }
    },
    methods: {
        createProduct: function () {
            var prd = this.product;

            axios.post(apiHost + `api/articles`, JSON.stringify(prd), config)
                .then(response => {
                    products.push(response.data);
                })
                .catch(e => {
                    this.errors.push(e)
                });


            router.push('/');
        }
    }
});


var ProductDelete = Vue.extend({
    template: '#product-delete',
    data: function () {
        return {product: findProduct(this.$route.params.art_id)};
    },
    methods: {
        deleteProduct: function () {

            var prodToDeleteId = this.$route.params.art_id;


            axios.delete(apiHost + `api/articles/` + prodToDeleteId, config)
                .then(response => {
                    products.splice(findProductKey(prodToDeleteId), 1);
                })
                .catch(e => {
                    this.errors.push(e)
                });


            router.push('/');
        }
    }
});

var router = new VueRouter({
    routes: [{path: '/', component: List},
        {path: '/product/:art_id', component: Product, name: 'product'},
        {path: '/add-product', component: AddProduct},
        {path: '/product/:art_id/edit', component: ProductEdit, name: 'product-edit'},
        {path: '/product/:art_id/delete', component: ProductDelete, name: 'product-delete'}
    ]
});

new Vue({
    el: '#app',
    router: router,
    template: '<router-view></router-view>',
    data: {
        //prds: products
    },
    mounted() {
        this.getAll();
    },
    methods: {

        getAll() {
            axios
                .get(apiHost + "api/articles")
                .then(res => {
                    console.info(res.data);
                    products.push(...res.data);
                })
                .catch(error => {
                    if (error.response) {
                        console.log(error.response);
                    }
                });

        },

        toDateTime(timestamp) {

            return moment.unix(timestamp / 1000).format("DD MMM YYYY hh:mm a")

        }

    }

});
