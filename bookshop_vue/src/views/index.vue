<template>
    <div class="index">
        <el-container>
            <!--头部导航栏-->
            <el-header style="height: 180px">
                <!--头部组件-->
                <Headers></Headers>
            </el-header>
            <div class="index_main">
                <el-container>
                    <!--侧边栏-->
                    <Aside></Aside>
                    <!--主要内容-->
                    <el-container>
                        <el-main>
                            <div class="main_header">
                                <!--轮播图-->
                                <el-carousel indicator-position="outside">
                                    <el-carousel-item v-for="(item,index) in imgList" :key="index">
                                        <img :src="item" style="width: 100%;height: 270px;"/>
                                    </el-carousel-item>
                                </el-carousel>
                                <div class="informs">
                                    <p id="inform">最新动态</p>
                                    <el-divider></el-divider>
                                    <!--获取新闻-->
                                    <ul>
                                        <li v-for="(newlist,nindex) in newList.slice(0,8)" :key="nindex">
                                            <el-link :underline="false" :href="newlist.url" :title="newlist.keyword">
                                                {{ newlist.keyword }}
                                            </el-link>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <el-divider></el-divider>
                            <!--展示书籍-->
                            <div class="bookMain">
                                <el-row>
                                    <el-col :span="5" v-for="(book,bIndex) in bookList" :key="bIndex">
                                        <el-card :body-style="{ padding: '0px' }">
                                            <a href="javascript:void(0)" @click="getBookDetails(book.bid,book.bname)"
                                               :title="book.bname"><img
                                                :src="book.bimg"></a>
                                            <p id="bookname"><a href="javascript:void(0)"
                                                                @click="getBookDetails(book.bid,book.bname)">{{
                                                    book.bname
                                                }}</a>
                                            </p>
                                            <p id="price">
                                                <span id="vipprice">￥{{ discountPrice(book.bprice) }}</span>
                                                <span id="rawprice">￥{{ book.bprice }}</span>
                                            </p>
                                        </el-card>
                                    </el-col>
                                </el-row>
                                <el-pagination
                                    @size-change="handleSizeChange"
                                    @current-change="handleCurrentChange"
                                    :current-page="pageIndex"
                                    :page-size="pageSize"
                                    layout="prev, pager, next"
                                    :total="pageTotal"
                                    style="margin-top: 2rem">
                                </el-pagination>
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
import axios from 'axios'
import Headers from "@/components/views/headers";
import Aside from "@/components/views/aside";
import Footers from "@/components/views/footers";

export default {
    name: "index",
    components: {Footers, Aside, Headers},
    data() {
        return {
            imgList: [
                '/images/book_lb1.png',
                '/images/book_lb2.png',
                '/images/book_lb3.png',
                '/images/book_lb4.png',
            ],
            newList: [],
            bookList: [],
            pageIndex: 1,
            pageSize: 15,
            pageTotal: 0,
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
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.pageIndex = val;
            this.initGetBooks();
        },
        initGetBooks() {
            //获取书籍信息
            return axios.get("/api/book/getBooks/" + this.pageIndex + "/" + this.pageSize)
                .then(books => {
                    books = books.data;
                    if (books.code == 200) {
                        this.bookList = books.data.bookList;
                        this.pageTotal = books.data.bookAccount;
                    }
                });
        },
        initGetNews() {
            //获取通知内容
            return axios.get("/api/news/getCsdns");
        },
        //获取图书详细信息
        getBookDetails(bid, bookname) {
            let bookUrl = this.$router.resolve({
                name: 'bookdetails',
            });
            window.sessionStorage.setItem('bid', bid);
            window.sessionStorage.setItem('bookname', bookname);
            window.open(bookUrl.href);
        },
    },
    mounted() {
        this.initGetBooks();
        //页面初始化
        axios.all([this.initGetNews()])
            .then(axios.spread((news) => {
                news = news.data;
                if (news.code == 200) {
                    this.newList = news.data;
                }
            }));
    }
};

</script>

<style scoped>
@import "../assets/styles/index.css";
</style>
