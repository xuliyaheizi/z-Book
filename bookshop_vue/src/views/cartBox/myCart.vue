<template>
    <div class="myCart">
        <div class="flow_step">
            <ul class="cols_1">
                <li class="step_1">1.我的购物车</li>
                <li class="step_2">2.确认订单信息</li>
                <li class="step_3">3.成功提交订单</li>
            </ul>
        </div>
        <div class="flow_step_title">
            <h2 id="mycar">
                购物车</h2>
            <label id="tipTag" style="float: left">
            </label>
            <div class="clear"></div>
        </div>
        <div id="cartbook" class="flow_pro_list">
            <table border="0" cellpadding="0" cellspacing="0" id="tb">
                <tbody>
                <tr>
                    <th width="5%"><label style="padding-left:5px;">
                        <input type="checkbox" checked="checked" id="chkall2"
                               @click="allSelect('chkall2')">
                    </label></th>
                    <th width="47%">商品</th>
                    <th width="8%">定价</th>
                    <th width="11%">您的价格</th>
                    <th width="11%">数量</th>
                    <th width="13%">单品总价</th>
                    <th width="5%" id="tbac">操作</th>
                </tr>
                <tr class="booktr" v-for="(book,bookIndex) in bookList" :key="bookIndex">
                    <td>
                        <input name="idarray" type="checkbox" checked="checked"
                               @click="signalSelect(bookIndex)" :value="bookIndex">
                    </td>
                    <td class="0">
                        <div class="cart_book">
                            <ul>
                                <li style="height:91px;width:5rem;">
                                    <a href="javascript:void(0)"
                                       @click="getBookDetails(book.book.bid,book.book.bname)">
                                        <img style="height: 91px;width: 5rem" :src="book.book.bimg">
                                    </a>
                                </li>
                                <li class="cart_book_name">
                                    <a href="javascript:void(0)"
                                       @click="getBookDetails(book.book.bid,book.book.bname)">
                                        {{ book.book.bname }}
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </td>
                    <td class="book_price">{{ book.book.bprice }}</td>
                    <td class="book_dis">{{ discountPrice(book.book.bprice) }}</td>
                    <td>
                        <el-input-number
                            :value="book.bookNumber"
                            @change="(currentValue, oldValue)=>handleChange(currentValue,book.book.bid)"
                            :min="1"
                            :max="book.book.brepertory"
                        >
                        </el-input-number>
                    </td>
                    <td>￥{{ discountPrice(book.itemPriceId) }}</td>
                    <td>
                        <a href="javascript:void(0)" @click="deleteSignal(book.book.bid)">删除</a>
                    </td>
                </tr>
                <tr v-if="isEmpty">
                    <td colspan="8">
                        <el-empty description="暂无商品，请去选购吧...."></el-empty>
                    </td>
                </tr>
                <tr>
                    <td colspan="8">
                        <div class="pro_clear">共
                            <span id="bookce">{{ cartCount }}</span>
                            件商品，商品金额共计
                            <span id="total_yuanjia">{{ totalPrice }}</span>
                            ，优惠
                            <span id="total_economy">{{ preferentialPrice }}</span>
                            <br>总计（不含运费）
                            <span id="total_account" class="cart_all_p">{{ actualPrice }}</span>
                        </div>
                        <div class="pro_choose">
                            <label>
                                <input type="checkbox" checked="checked" id="chkall"
                                       @click="allSelect('chkall')">
                                <span>全选</span>
                            </label>
                            <a id="pro_quit" class="pro_quit" href="javascript:void(0)"
                               @click="deleteMultiple('idarray')">删 除</a>
                        </div>
                        <div class="clear"></div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <a id="flow_clear" class="flow_clear" href="javascript:void(0)" @click="toChoose">
            ￥去结算 </a>
        <a id="flow_buy_goon" class="flow_buy_goon" href="/"></a>
    </div>
</template>

<script>
export default {
    name: "myCart",
    data() {
        return {
            uid: '',
            bookList: [],
            cartCount: '',
            //总价
            totalPrice: '',
            //优惠金额
            preferentialPrice: '',
            //实际金额
            actualPrice: '',
            isEmpty: true,
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
        //去结算
        toChoose() {
            let buyBookList = [];
            let actualPrice = this.actualPrice;
            let checkBoxes = document.getElementsByName("idarray");
            for (let check of checkBoxes) {
                if (check.checked == true) {
                    buyBookList.push(this.bookList[check.value]);
                }
            }
            let orderBook = {
                uid: this.uid,
                actualPrice: actualPrice,
                buyBookList: buyBookList,
            }
            const loading = this.$loading({
                lock: true,
                text: '正在生成订单，请稍等...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            this.$axios.post("/api/cart/addOrderBook", orderBook).then(orderBook => {
                orderBook = orderBook.data;
                if (orderBook.code != 200) {
                    this.infoTip("结算失败", "error");
                    return;
                }
                setTimeout(() => {
                    loading.close();
                    this.$router.push({
                        path: "/bookCart/trueOrder",
                        name: "trueOrder",
                    });
                }, 2000);
            });
        },
        //删除多个图书
        deleteMultiple(checkName) {
            let bidList = [];
            let checkBoxes = document.getElementsByName(checkName);
            for (let check of checkBoxes) {
                if (check.checked == true) {
                    bidList.push(this.bookList[check.value].book.bid);
                }
            }
            let deleteMultiple = {
                uid: this.uid,
                bidList: bidList,
            }
            this.$axios.post("/api/cart/deleteMultiple", deleteMultiple).then(infos => {
                infos = infos.data;
                if (infos.code != 200) {
                    this.infoTip("删除失败", "error");
                    return;
                }
                this.infoTip("删除成功", "success");
                this.showCartInfo();
            })
        },
        //删除单个图书
        deleteSignal(bookId) {
            let deleteSignalDto = {
                uid: this.uid,
                bid: bookId
            }
            this.$axios.post("/api/cart/deleteSignal", deleteSignalDto).then(deleInfo => {
                deleInfo = deleInfo.data;
                if (deleInfo.code != 200) {
                    this.infoTip("删除失败", "error");
                    return;
                }
                this.infoTip("删除成功", "success");
                this.showCartInfo();
            })
        },
        //图书数量改变
        handleChange(value, bookId) {
            let updateCartDto = {
                uid: this.uid,
                bid: bookId,
                num: value
            }
            this.$axios.post("/api/cart/updateNum", updateCartDto).then(update => {
                update = update.data;
                if (update.code != 200) {
                    this.infoTip("修改失败", "error");
                }
                this.showCartInfo();
            });
        },
        //价格处理
        calculatePrice() {
            let actualPrice = this.totalPrice * 0.8;
            this.actualPrice = this.keepTwo(actualPrice);
            let price = this.totalPrice - this.actualPrice;
            this.preferentialPrice = this.keepTwo(price);
        },
        //数据保留两位数
        keepTwo(num) {
            return num.toFixed(2);
        },
        //单选框 操作
        signalSelect(bookIndex) {
            let allCheck1 = document.getElementById("chkall");
            let allCheck2 = document.getElementById("chkall2");
            let checkBoxes = document.getElementsByName("idarray");
            let checkBoolean = true;
            //循环所有单选框
            for (let check of checkBoxes) {
                if (check.checked == false) {
                    checkBoolean = false;
                }
            }
            if (checkBoolean == true) {
                allCheck1.checked = true;
                allCheck2.checked = true;
            } else {
                allCheck1.checked = false;
                allCheck2.checked = false;
            }
            //单选框 对价格的操作
            let signalCheck = document.getElementsByName("idarray")[bookIndex];
            let totalPrice = null;
            if (signalCheck.checked == true) {
                this.cartCount += this.bookList[bookIndex].bookNumber;
                totalPrice = Number(this.totalPrice) + Number(this.bookList[bookIndex].itemPriceId);
                this.totalPrice = this.keepTwo(totalPrice);
            } else {
                this.cartCount -= this.bookList[bookIndex].bookNumber;
                totalPrice = this.totalPrice - this.bookList[bookIndex].itemPriceId;
                this.totalPrice = this.keepTwo(totalPrice);
            }
            //价格计算
            this.calculatePrice();
        },
        //全选
        allSelect(cid) {
            let check = document.getElementById(cid);
            let allCheck1 = document.getElementById("chkall");
            let allCheck2 = document.getElementById("chkall2");
            let checkBoxes = document.getElementsByName("idarray");
            if (check.checked == true) {
                for (let check of checkBoxes) {
                    check.checked = true;
                }
                allCheck1.checked = true;
                allCheck2.checked = true;
                this.showCartInfo();
            } else {
                for (let check of checkBoxes) {
                    check.checked = false;
                }
                allCheck1.checked = false;
                allCheck2.checked = false;
                this.cartCount = 0;
                this.totalPrice = 0;
                this.calculatePrice();
            }
        },
        //信息提示
        infoTip(message, type) {
            this.$message({
                message: message,
                type: type,
            });
        },
        //获取购物车信息
        showCartInfo() {
            this.$axios.get("/api/cart/show/" + this.uid).then(cartInfo => {
                cartInfo = cartInfo.data;
                if (cartInfo.code != 200) {
                    this.isEmpty = true;
                    this.bookList = [];
                    this.cartCount = 0;
                    this.totalPrice = 0;
                    this.calculatePrice();
                    return;
                }
                this.isEmpty = false;
                this.bookList = cartInfo.data.CartBookList;
                this.cartCount = cartInfo.data.CartCount;
                this.totalPrice = cartInfo.data.CartTotalPrice;
                this.calculatePrice();
            })
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
        if (window.sessionStorage.getItem("isRefresh") == 1) {
            window.sessionStorage.removeItem("isRefresh");
            window.location.reload();
            return;
        }
        this.uid = window.sessionStorage.getItem("uid");
        this.showCartInfo();
    }
}
</script>

<style scoped>
@import "../../assets/styles/cartBox/myCart.css";
</style>