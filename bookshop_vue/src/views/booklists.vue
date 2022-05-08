<template>
    <div class="bookLists">
        <el-container>
            <!--头部导航栏-->
            <el-header style="height: 180px">
                <headers></headers>
            </el-header>
            <div class="bookLists_main">
                <el-container>
                    <!--侧边栏-->
                    <Aside></Aside>
                    <!--主要内容-->
                    <el-container>
                        <div class="book_resources">
                            <el-main>
                                <div class="resources">
                                    <table border="0" cellspacing="0" cellpadding="0" v-for="(book,bIndex) in bookList"
                                           :key="bIndex">
                                        <tbody>
                                        <tr>
                                            <td width="100px">
                                                <a href="javascript:void(0)"
                                                   @click="getBookDetails(book.bid,book.bname)">
                                                    <img :src="book.bimg" :title="book.bname" width="90px">
                                                </a>
                                            </td>
                                            <td valign="top">
                                                <ul>
                                                    <li>
                                                        <a href="javascript:void(0)"
                                                           @click="getBookDetails(book.bid,book.bname)">
                                                            <span class="">{{ book.bclasses }}</span>
                                                            |&nbsp;&nbsp;&nbsp;&nbsp;{{ book.bname }}
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            (著)
                                                            {{ book.bauthor }}
                                                            <el-divider direction="vertical"></el-divider>
                                                            {{ book.bpress }}
                                                            <el-divider direction="vertical"></el-divider>
                                                            {{ book.bisbn }}
                                                            <el-divider direction="vertical"></el-divider>
                                                            {{ book.bpublicationdate }}
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <div class="block">
                                                            <el-rate
                                                                v-model="value2"
                                                                :colors="colors">
                                                            </el-rate>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <ul>
                                                            <li class="book_icon"><i class="el-icon-s-finance"></i></li>
                                                            <li class="book_dis">会员价：￥{{ discountPrice(book.bprice) }}
                                                            </li>
                                                            <li class="book_price">￥{{ book.bprice }}</li>
                                                            <li class="book_opera">
                                                                <a href="javascript:void(0);" @click=""
                                                                   class="book_fav">
                                                                    <i class="el-icon-shopping-bag-2">购买</i>
                                                                </a>
                                                                <a href="javascript:void(0)" @click=""
                                                                   class="book_buy">
                                                                    <i class="el-icon-shopping-cart-full">加入购物车</i>
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <el-pagination
                                    @current-change="handleCurrentChange"
                                    :current-page="pageIndex"
                                    :page-size="pageSize"
                                    layout="prev, pager, next"
                                    :total="pageTotal"
                                    style="margin-top: 2rem">
                                </el-pagination>
                                <div id="empty" style="display: none">
                                    <el-empty :image-size="200"></el-empty>
                                </div>

                            </el-main>
                        </div>
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
    name: "booklists",
    components: {Footers, Aside, Headers},
    data() {
        return {
            keyword: '',
            bookList: [],
            value1: null,
            value2: null,
            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
            pageIndex: window.sessionStorage.getItem("pageIndex") == null ? 1 : (window.sessionStorage.getItem("pageIndex") - 0),
            pageSize: 10,
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
        //点击页数
        handleCurrentChange(val) {
            window.sessionStorage.setItem("pageIndex", val);
            this.initSearch();
            document.documentElement.scrollTop = 0;
        },
        //获取搜索结果
        initSearch() {
            this.keyword = window.sessionStorage.getItem("keyword");
            let pageIndex = window.sessionStorage.getItem("pageIndex") - 0;
            this.pageIndex = pageIndex == null ? 1 : pageIndex;
            return this.$axios.get("/api/book/searchBook/" + this.keyword + "/" + this.pageIndex + "/" + this.pageSize)
                .then((searchBooks) => {
                    searchBooks = searchBooks.data;
                    if (searchBooks.code == 200) {
                        document.getElementById("empty").style.display = "none";
                    } else {
                        document.getElementById("empty").style.display = "block";
                    }
                    this.bookList = searchBooks.data.bookList;
                    this.pageTotal = searchBooks.data.bookAccount;
                });
        },
        //获取图书信息
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
        this.initSearch();
    }
}
</script>
<style scoped>
@import "../assets/styles/booklists.css";
</style>