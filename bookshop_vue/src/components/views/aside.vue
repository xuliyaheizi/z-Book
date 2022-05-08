<template>
    <div class="book_aside">
        <el-aside width="13rem">
            <h2>图书分类</h2>
            <el-row class="tac">
                <el-col :span="24">
                    <el-menu
                            default-active="2"
                            class="el-menu-vertical-demo">
                        <el-submenu v-for="(cateGory,cIndex) in cateGroyList" :key="cIndex"
                                    :index="cateGory.CateGory.cid+''">
                            <template slot="title">
                                <span>{{cateGory.CateGory.cname}}</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item
                                        v-for="(cateGroySecond,csIndex) in cateGory.cateGoryListSecond"
                                        :key="csIndex"
                                        :index="cateGroySecond.cid+''"
                                        @click="searchBook(cateGroySecond.cname)">
                                    {{cateGroySecond.cname}}
                                </el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                    </el-menu>
                </el-col>
            </el-row>
        </el-aside>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "Aside",
        data() {
            return {
                cateGroyList: [],
            }
        },
        methods: {
            //获取所有分类
            initGetCateGory() {
                return this.$axios.get("/api/cateGory/all");
            },
            //搜索
            searchBook(keyWord) {
                this.input = '';
                if (this.$route.path != "/bookLists") {
                    this.$router.push({
                        path: '/bookLists',
                        name: 'bookLists',
                    });
                    window.sessionStorage.setItem('keyword', keyWord);
                } else {
                    //重新加载当前页面
                    this.$router.go(0);
                }
                window.sessionStorage.setItem('keyword', keyWord);
            },
        },
        mounted() {
            //页面初始化
            axios.all([this.initGetCateGory()])
                .then(axios.spread((cateGorys) => {
                    cateGorys = cateGorys.data;
                    if (cateGorys.code == 200) {
                        this.cateGroyList = cateGorys.data;
                    }
                }));
        }
    }
</script>

<style scoped>
    @import "../css/aside.css";
</style>