<template>
    <div class="trueOrder">
        <div id="stepCount" class="flow_step">
            <ul class="cols_2">
                <li class="step_1">1.我的购物车</li>
                <li class="step_2">2.确认订单信息</li>
                <li class="step_3">3.成功提交订单</li>
            </ul>
        </div>
        <div class="flow_step_title">
            <h2>确认订单信息</h2>
            <span class="login_step"></span>
            <div class="clear">
            </div>
        </div>
        <div class="flow_order_list">
            <table border="0" cellpadding="0" cellspacing="0">
                <tbody>
                <tr>
                    <td class="con_title">
                        商品清单
                        <span>
                            <a href="/bookCart/myCart">返回修改购物车</a>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td id="confirm_bookdiv">
                        <div class="pro_list">
                            <table border="0" cellpadding="0" cellspacing="0" class="mTop10">
                                <tbody>
                                <tr>
                                    <th style="width:750px;color:#fc7215;">纸版书</th>
                                    <th>优惠价</th>
                                    <th style="width:60px">数量</th>
                                    <th>小计</th>
                                </tr>
                                <tr v-for="(buyBook,buyBookIndex) in buyBookList"
                                    :key="buyBookIndex">
                                    <td class="cart_book_n">
                                        <div style="width:730px;">
                                            <a href="javascript:void(0)"
                                               @click="getBookDetails(buyBook.book.bid,buyBook.book.bname)">
                                                {{ buyBook.book.bname }}
                                            </a>
                                        </div>
                                    </td>
                                    <td>￥{{ discountPrice(buyBook.book.bprice) }}(80折)</td>
                                    <td>{{ buyBook.bookNumber }}</td>
                                    <td>￥{{ discountPrice(buyBook.itemPriceId) }}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="con_title">
                        收货地址
                        <a href="javascript:void(0)" @click="updateAddress">[修改]</a>
                    </td>
                </tr>
                <tr>
                    <td id="myinfodiv">
                        <div class="getmsg" v-if="isAddress">
                            <p class="normal_address">
                                <span>{{ userAddress.name }}</span>
                                <span>{{ userAddress.phone }}</span>
                                <span>{{ userAddress.address }}</span>
                            </p>
                        </div>
                        <div class="address_table" v-else>
                            <el-table :data="gridData">
                                <el-table-column property="name" label="姓名" width="100"></el-table-column>
                                <el-table-column property="phone" label="手机号码" width="150"></el-table-column>
                                <el-table-column property="address" label="地址"></el-table-column>
                                <el-table-column
                                    align="right">
                                    <template slot="header" slot-scope="scope">
                                        <el-button type="text" @click="addNewSite" style="float: right">新增地址</el-button>
                                    </template>
                                    <template slot-scope="scope">
                                        <el-button
                                            size="mini"
                                            type="danger"
                                            @click="selectAddress(scope.$index, scope.row)">选择
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                        <span style="display:none;">0</span>
                    </td>
                </tr>
                <tr>
                    <td class="con_title">
                        送货信息<a href="javascript:void(0)"
                               @click="isGetMsg=(isGetMsg==true?false:true)">[展开]</a>
                    </td>
                </tr>
                <tr>
                    <td id="sendmodediv">
                        <div class="getmsg" v-if="isGetMsg">
                            <p class="normal_address">
                                <span>快递：{{ payment }}</span>
                                <span>{{ deliveryTime }}</span>
                            </p>
                        </div>
                        <div v-else>
                            <div class="delive_infor">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tbody>
                                    <tr>
                                        <td class="del_name">
                                            <input type="radio" name="sendtype"
                                                   onclick="sendmode_ck(this);" value="特快专递"
                                                   checked="checked">快递：在线支付
                                        </td>
                                        <td class="del_price">运费：0元</td>
                                        <td class="del_inf" style="width:300px;">
                                            支付成功且订单状态变为“发货”后预计3--5天送达，节假日顺延
                                        </td>
                                        <td class="del_other"></td>
                                    </tr>
                                    <tr id="sendtype_sub_3">
                                        <td colspan="4">
                                            <div id="sendtime_3" class="send_time">
                                                <p>选择送货时间：</p>
                                                <table border="" cellpadding="0" cellspacing="0">
                                                    <tbody>
                                                    <tr>
                                                        <td>
                                                            <el-radio v-model="deliveryTime"
                                                                      :label="'只工作日送货(休息日不用送)'">
                                                                只工作日送货(休息日不用送)
                                                                <em>建议配送至公司地址的用户选择</em>
                                                            </el-radio>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <el-radio v-model="deliveryTime"
                                                                      :label="'只休息日送货(工作日不用送)'">
                                                                只休息日送货(工作日不用送)
                                                                <em>建议配送至家庭地址的用户选择</em>
                                                            </el-radio>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <el-radio v-model="deliveryTime"
                                                                      :label="'休息日、工作日均可送货'">
                                                                休息日、工作日均可送货
                                                            </el-radio>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <el-radio v-model="deliveryTime"
                                                                      :label="'学校地址，课余时间送货'">
                                                                学校地址，课余时间送货
                                                                <em>
                                                                    此项需要特殊安排，可能会超出预计送货天数
                                                                </em>
                                                            </el-radio>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="con_title">
                        支付方式<a href="javascript:void(0)"
                               @click="isPayment=(isPayment==false?true:false)">[展开]</a>
                    </td>
                </tr>
                <tr v-if="isPayment">
                    <div class="pay_type">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <td>
                                    <el-radio v-model="payment" :label="'支付宝支付'">
                                        支付宝支付
                                    </el-radio>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <el-radio v-model="payment" :label="'微信支付'">
                                        微信支付
                                    </el-radio>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </tr>
                <tr>
                    <td class="con_title">
                        结算信息
                    </td>
                </tr>
                <tr>
                    <td id="confirmdiv">
                        <div id="checkout_list" class="checkout_list">
                            <div style="float: right;margin: 20px 20px 0px 0px">
                                <table width="100%" border="0" cellspacing="0"
                                       cellpadding="5" style="_margin-right:30px">
                                    <tbody>
                                    <tr style="height:10px">
                                        <td class="check_l_t" style="height:0px"></td>
                                    </tr>
                                    <tr>
                                        <td class="check_l_c">
                                            <div class="check_result">商品金额总计：￥{{ actualPrice }}<br>
                                                运费总计：￥0.00<br>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="check_l_b"></td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="clear"></div>
                                <h2 style="padding-right:60px">应付总计：
                                    <span id="totalPayAMT">￥{{ actualPrice }}</span>
                                </h2>
                                <p style="padding-right:210px">请确认以上信息无误</p>
                                <a id="check_end" class="check_end"
                                   href="javascript:void(0)" @click="submitOrder">提交订单</a>
                            </div>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <el-dialog
            width="40%"
            title="添加地址"
            :visible.sync="innerVisible"
            append-to-body style="padding: 10px 20px;">
            <el-form :model="siteForm" ref="siteForm" label-position="right" label-width="80px" size="small"
                     :rules="formRule" v-loading="lodding" element-loading-text="正在保存信息">
                <el-form-item label="收货人" prop="name">
                    <el-input v-model="siteForm.name" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item label="手机号码" prop="phone">
                    <el-input v-model="siteForm.phone" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item label="地址" required>
                    <el-col :span="colSpan">
                        <el-form-item prop="province">
                            <!--省份-->
                            <el-select v-model="siteForm.province" placeholder="省" style="width: 100%;"
                                       @change="getAllSite(siteForm.province,'data')">
                                <el-option
                                    v-for="item in dataOptions"
                                    :key="item.code"
                                    :label="item.name"
                                    :value="item.name">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="colSpan">
                        <el-form-item prop="city">
                            <!--市-->
                            <el-select v-model="siteForm.city" placeholder="市"
                                       style="width: 100%;margin-left: 5%;"
                                       @change="getAllSite(siteForm.city,'pchilds')">
                                <el-option
                                    v-for="item in pchildsOptions"
                                    :key="item.code"
                                    :label="item.name"
                                    :value="item.name">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="colSpan">
                        <el-form-item prop="district">
                            <!--区-->
                            <el-select v-model="siteForm.district" placeholder="区"
                                       style="width: 100%;margin-left: 10%;">
                                <el-option
                                    v-for="item in cchildsOptions"
                                    :key="item.code"
                                    :label="item.name"
                                    :value="item.name">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-form-item>
                <el-form-item label="详细地址" prop="place">
                    <el-input v-model="siteForm.place" placeholder="请输入详细地址" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('siteForm')">保存信息</el-button>
                    <el-button @click="innerVisible = false">取 消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "trueOrder",
    data() {
        return {
            uid: '',
            //总价
            totalPrice: '',
            //优惠金额
            preferentialPrice: '',
            //实际金额
            actualPrice: '',
            isGetMsg: true,
            //送货时间
            deliveryTime: '只工作日送货(休息日不用送)',
            //支付方式
            payment: '支付宝支付',
            isPayment: false,
            //选购的图书
            buyBookList: [],
            //用户地址
            userAddress: [],
            gridData: [],
            innerVisible: false,
            isAddress: true,
            siteForm: {
                name: '',
                phone: '',
                province: '',
                city: '',
                district: '',
                place: '',
            },
            formRule: {
                name: [
                    {required: true, message: "请输入姓名", trigger: 'blur'}
                ],
                phone: [
                    {required: true, validator: this.checkPhone(), trigger: 'blur'}
                ],
                province: [
                    {require: true, validator: this.checkPlace("省级行政区不能为空"), trigger: 'change'}
                ],
                city: [
                    {require: true, validator: this.checkPlace("市级行政区不能为空"), trigger: 'change'}
                ],
                district: [
                    {require: true, validator: this.checkPlace("区级行政区不能为空"), trigger: 'change'}
                ],
                place: [
                    {required: true, message: '请输入详细收获地址', trigger: 'blur'}
                ]
            },
            lodding: false,
            colSpan: 7,
            dataOptions: [],
            pchildsOptions: [],
            cchildsOptions: [],
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
        //提交订单
        submitOrder() {
            let orderBook = {
                uid: this.uid,
                actualPrice: this.actualPrice,
                buyBookList: this.buyBookList,
            }
            this.$axios.post("/api/trade/page/pay", orderBook).then(pay => {
                pay = pay.data;
                if (pay.code != 200) {
                    this.infoTip("支付失败", "error");
                    return;
                }
                document.write(pay.data);
            })
        },
        //手机号码校验
        checkPhone() {
            return (rule, value, callback) => {
                if (!value) {
                    return callback(new Error("手机号码不能为空"));
                }
                let mobile_reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
                if (!mobile_reg.test(this.siteForm.phone)) {
                    return callback(new Error("请输入正确的手机号码!"));
                }
                callback();
            };
        },
        //地区校验
        checkPlace(errinfo) {
            return (rule, value, callback) => {
                if (!value) {
                    return callback(new Error(errinfo));
                }
                callback();
            }
        },
        //提交表单
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.lodding = true;
                    setTimeout(() => {
                        this.lodding = false;
                        let uid = this.uid;
                        let address = {
                            aname: this.siteForm.name,
                            aphone: this.siteForm.phone,
                            aprovince: this.siteForm.province,
                            acity: this.siteForm.city,
                            adistrict: this.siteForm.district,
                            aplace: this.siteForm.place
                        }
                        this.$axios.post("/api/address/add", {address, uid})
                            .then(address => {
                                address = address.data;
                                if (address.code != 200) {
                                    this.infoTip("保存失败!", "error");
                                    return;
                                }
                                //关闭窗口
                                this.innerVisible = false;
                                this.$refs[formName].resetFields();
                                this.infoTip("保存成功", "success");
                                //刷新地址信息
                                this.showAddress();
                            })
                    }, 1000)
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        //获取全国城市列表
        getAllSite(siteName, classType) {
            if (classType == 'data') {
                //循环一级
                for (let item of this.dataOptions) {
                    if (item.name == siteName) {
                        this.pchildsOptions = item.pchilds;
                    }
                }
            }
            if (classType == 'pchilds') {
                for (let item of this.pchildsOptions) {
                    if (item.name == siteName) {
                        this.cchildsOptions = item.cchilds;
                    }
                }
            }
        },
        //修改地址
        updateAddress() {
            this.isAddress = (this.isAddress == true ? false : true);
            this.showAddress();
        },
        //显示收获地址
        showAddress() {
            //请求用户的收获地址
            this.$axios.get("/api/address/show/" + this.uid).then(add => {
                add = add.data;
                if (add.code != 200) {
                    this.gridData = [];
                    return;
                }
                this.gridData.length = 0;
                for (let item of add.data) {
                    this.gridData.push({
                        name: item.aname,
                        phone: item.aphone,
                        address: item.aprovince + item.acity + item.adistrict + item.aplace,
                        code: item.aid,
                    })
                }

            })
        },
        //选择地址
        selectAddress(index, row) {
            this.userAddress = this.gridData[index];
            this.isAddress = true;
        },
        //获取全国城市列表
        addNewSite() {
            this.innerVisible = true;
            return this.$axios.get("/api/site").then(site => {
                site = site.data;
                if (site.code != 1) {
                    this.infoTip("获取城市列表接口失败!原因:" + site.msg, "error");
                }
                this.dataOptions = site.data;
            })
        },
        //信息提示
        infoTip(message, type) {
            this.$message({
                message: message,
                type: type,
            });
        },
        //获取需要购买的书籍信息
        initGetOrder() {
            this.$axios.get("/api/cart/getOrderBook/" + this.uid).then(orderBook => {
                orderBook = orderBook.data;
                if (orderBook.code != 200) {
                    this.infoTip("图书清单信息获取失败", "error");
                    return;
                }
                this.actualPrice = orderBook.data.actualPrice;
                this.buyBookList = orderBook.data.buyBookList;
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
        //获取用户的默认地址
        getAddress() {
            this.$axios.get("/api/address/default/" + this.uid).then(address => {
                address = address.data;
                if (address.code != 200) {
                    this.infoTip("获取用户默认地址接口错误", "error");
                    return;
                }
                this.userAddress = {
                    name: address.data.aname,
                    phone: address.data.aphone,
                    address: address.data.aprovince + address.data.acity + address.data.adistrict + address.data.aplace,
                    code: address.data.aid,
                };
            })
        }
    },
    mounted() {
        document.documentElement.scrollTop = 0;
        this.uid = window.sessionStorage.getItem("uid");
        this.initGetOrder();
        this.getAddress();
    }
}
</script>

<style scoped>
@import "../../assets/styles/cartBox/trueOrder.css";
</style>