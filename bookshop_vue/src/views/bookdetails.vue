<template>
    <div class="book">
        <el-container>
            <!--头部导航栏-->
            <el-header style="height: 180px">
                <Headers></Headers>
            </el-header>
            <div class="book_main">
                <el-container>
                    <!--侧边栏-->
                    <Aside></Aside>
                    <!--主要内容-->
                    <el-container>
                        <el-main>
                            <!--书本基本信息-->
                            <div class="pro_book">
                                <div class="pro_book_header">
                                    <h1>{{ book.bname }}</h1>
                                    <div class="pro_name_intr">
                                        <span>{{ book.title }}</span>
                                    </div>
                                </div>
                                <div class="pro_book_info">
                                    <div class="pro_book_img">
                                        <img :src="book.bimg">
                                    </div>
                                    <div class="pro_buy_intr">
                                        <ul>
                                            <li>定价：<span class="pro_buy_pri">￥{{ book.bprice }}</span></li>
                                            <li id="xxjg">会员价：<span
                                                class="pro_buy_sen">￥{{ discountPrice(book.bprice) }}</span></li>
                                            <li class="pro_buy_bg">
                                                <span>我要买：</span>
                                                <el-input-number v-model="num" :min="1"
                                                                 :max="book.brepertory"></el-input-number>
                                                <span id="book_num">(库存:{{ book.brepertory }}本)</span>
                                            </li>
                                            <li>
                                                <el-button id="bugbook" type="primary" plain @click="addBookCart">
                                                    加入购物车
                                                </el-button>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="book_details">
                                <!--基本信息-->
                                <div class="book_basic_info">
                                    <h3>基本信息</h3>
                                    <ul>
                                        <li>作者：{{ book.bauthor }}</li>
                                        <li>出版社：{{ book.bpress }}</li>
                                        <li>ISBN：{{ book.bisbn }}</li>
                                        <li>出版日期：{{ book.bpublicationdate }}</li>
                                        <li>所属分类：{{ book.bclasses }}</li>
                                    </ul>
                                </div>
                                <!--编辑推荐-->
                                <div class="book_abstract" v-if="book.babstract">
                                    <h3>编辑推荐</h3>
                                    <p class="book_desc">{{ book.babstract }}</p>
                                </div>
                                <!--内容简介-->
                                <div class="book_description" v-if="book.bdescription">
                                    <h3>内容简介</h3>
                                    <p class="book_desc">{{ book.bdescription }}</p>
                                </div>
                            </div>

                            <div class="book_dilog">
                                <el-dialog
                                    title="购物车提示"
                                    :visible.sync="dialogVisible"
                                    width="30%">
                                    <div class="shop_pro_pro">
                                        <h4>商品已成功加入购物车！</h4>
                                        <p>购物车中共<span> {{ cartCount }} </span>件商品，合计<span
                                            class="shop_up_dis">￥{{ itemPriceId }}</span>
                                        </p>
                                    </div>
                                    <div style="text-align: center">
                                        <el-button @click="dialogVisible = false"><< 继续购物车</el-button>
                                        <el-button type="primary" @click="toBookCart()">去结算</el-button>
                                    </div>
                                </el-dialog>
                            </div>
                        </el-main>
                    </el-container>
                </el-container>
            </div>
            <el-footer>
                <Footers></Footers>
            </el-footer>
        </el-container>
    </div>
</template>

<script>
import Headers from "@/components/views/headers";
import Aside from "@/components/views/aside";
import Footers from "@/components/views/footers";

export default {
    name: "bookdetails",
    components: {Footers, Aside, Headers},
    data() {
        return {
            num: 1,
            bookname: '',
            bid: '',
            book: '',
            dialogVisible: false,
            cartCount: '',
            itemPriceId: '',
        }
    },
    computed: {
        discountPrice() {
            return function (price) {
                let p = price * 0.8;
                return p.toFixed(2);
            }
        }
    },
    methods: {
        //信息提示
        infoTip(message, type) {
            this.$message({
                message: message,
                type: type,
            });
        },
        //初始化书籍信息
        initBookInfo() {
            //取到路由带过来的参数
            this.bookname = window.sessionStorage.getItem("bookname");
            this.bid = window.sessionStorage.getItem("bid");
            window.document.title = this.bookname;
            return this.$axios.get("/api/book/getBookByID/" + this.bid).then(book => {
                book = book.data;
                if (book.code == 200) {
                    this.book = book.data;
                }
            });
        },
        //添加购物车
        addBookCart() {
            let uid = window.sessionStorage.getItem("uid");
            if (uid == null) {
                this.infoTip("请先登录....", "warning");
                return;
            }
            //获取用户id
            let cartDto = {
                uid: uid,
                bid: this.bid,
                num: this.num,
            }
            this.$axios.post("/api/cart/add", cartDto).then(cart => {
                cart = cart.data;
                if (cart.code != 200) {
                    //添加失败
                    this.infoTip("添加购物车失败", "error");
                    return;
                }
                //添加成功
                this.dialogVisible = true;
                this.showCart();
            })
        },
        //查询购物车信息
        showCart() {
            let uid = window.sessionStorage.getItem("uid");
            this.$axios.get("/api/cart/show/" + uid).then(cart => {
                cart = cart.data;
                if (cart.code != 200) {
                    this.infoTip("购物信息显示失败", "error");
                    return;
                }
                this.cartCount = cart.data.CartCount;
                this.itemPriceId = cart.data.CartTotalPrice;
            })
        },
        //去结算页面
        toBookCart() {
            this.dialogVisible = false;
            let bookUrl = this.$router.resolve({
                name: 'myCart',
            });
            window.open(bookUrl.href);
        }
    },
    watch: {
        '$route': 'initBookInfo'
    },
    mounted() {
        this.initBookInfo();
    }
}
</script>

<style scoped>
@import "../assets/styles/bookdetails.css";
</style>