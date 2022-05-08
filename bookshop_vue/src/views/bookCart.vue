<template>
    <div class="book_cart">
        <el-container>
            <el-main>
                <div class="main">
                    <div class="header">
                        <a href="/">
                            <img src="/images/book_logo2.png" style="width: 20%;height: 100%">
                        </a>
                    </div>
                    <div class="cart_box">
                        <router-view :key="this.$route.fullPath"></router-view>
                    </div>
                </div>
            </el-main>
            <el-footer>
                <Footers></Footers>
            </el-footer>
        </el-container>
    </div>
</template>

<script>
import Footers from "@/components/views/footers";
import Headers from "@/components/views/headers";

export default {
    name: "bookCart",
    components: {Headers, Footers},
}
</script>

<style scoped>
@import "../assets/styles/bookCart.css";
</style>