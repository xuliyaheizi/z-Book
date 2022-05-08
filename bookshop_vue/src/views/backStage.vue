<template>
    <!--后台管理页面-->
    <div class="back_stage">
        <el-container>
            <el-aside width="auto">
                <el-col :span="24">
                    <el-menu
                        default-active="/backStage/index"
                        class="el-menu-vertical-demo"
                        background-color="rgb(48, 65, 86)"
                        text-color="rgb(191, 203, 217)"
                        active-text-color="#409EFF"
                        :collapse="isFoad"
                        unique-opened
                        router>
                        <el-menu-item index="/backStage/index">
                            <i class="el-icon-location"></i>
                            <span>首页</span>
                        </el-menu-item>
                        <el-submenu index="2">
                            <template slot="title">
                                <i class="el-icon-collection"></i>
                                <span slot="title">图书管理</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="/backStage/admin/showBookLists">查看图书</el-menu-item>
                                <el-menu-item index="/backStage/admin/addNewBook">添加图书</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                        <el-menu-item index="3" disabled>
                            <i class="el-icon-document"></i>
                            <span slot="title">人员管理</span>
                        </el-menu-item>
                        <el-menu-item index="4" disabled>
                            <i class="el-icon-setting"></i>
                            <span slot="title">系统管理</span>
                        </el-menu-item>
                    </el-menu>
                </el-col>
            </el-aside>
            <el-container class="main-container">
                <el-header style="height: 54px;padding: 0px;">
                    <div class="nav-bar">
                        <div class="hambuger-container">
                            <i :class="floadClass" @click="floadSidebar"></i>
                        </div>
                        <div class="el-breadcrumb" role="navigation" aria-label="Breadcrumb">
                            <span class="el-breadcrumb__item" aria-current="page">
                            </span>
                        </div>
                    </div>
                </el-header>
                <el-main style="background: rgb(247, 249, 251);">
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import Footer from "@/components/views/footers";

export default {
    name: "backStage",
    components: {Footer},
    data() {
        return {
            isFoad: false,
            floadClass: 'el-icon-s-fold',
        }
    },
    // 页面初始前
    beforeCreate() {
        document.getElementsByTagName('body')[0].className = 'back_body';
    },
    //页面销毁
    beforeDestroy() {
        document.getElementsByTagName('body')[0].className = '';
    },
    methods: {
        //收起侧边栏
        floadSidebar() {
            if (this.isFoad == true) {
                this.isFoad = false;
                this.floadClass = 'el-icon-s-fold';
                document.querySelector(".back_stage .el-container.main-container.is-vertical").classList.remove("hideSideBar");
            } else {
                this.isFoad = true;
                this.floadClass = 'el-icon-s-unfold';
                document.querySelector(".back_stage .el-container.main-container.is-vertical").classList.add("hideSideBar");
            }
        }
    },
}
</script>

<style scoped>
@import "../assets/styles/backStage.css";
</style>