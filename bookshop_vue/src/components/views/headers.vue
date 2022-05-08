<template>
    <div class="book_headers">
        <!--导航栏-->
        <el-row>
            <el-col :span="24">
                <div class="grid-content bg-purple-dark">
                    <span>欢迎光临z-Book, </span>
                    <span id="loginUser">
                    <span>请 </span>
                    <a href="/login">登录</a>
                    <span> 成为会员  </span>
                </span>
                    <span id="trueLoginUser" style="display: none">
                    <span>尊敬的<span id="user_Name"> {{ login_userName }} </span>先生 </span>
                    <a href="javascript:void(0)" @click="quitUser">退出</a>
                </span>
                    <el-divider direction="vertical"></el-divider>

                    <span class="el-dropdown-link">我的学习</span>

                    <el-divider direction="vertical"></el-divider>
                    <el-dropdown trigger="click">
                            <span class="el-dropdown-link">个人中心
                                <i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>个人信息</el-dropdown-item>
                            <el-dropdown-item><span @click="toBookCart">购物车</span></el-dropdown-item>
                            <el-dropdown-item>我的订单</el-dropdown-item>
                            <el-dropdown-item><span @click="showSites">收获地址</span></el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <el-divider direction="vertical"></el-divider>
                    <span class="el-dropdown-link" style="margin-right: 12rem">帮助中心</span>
                </div>
            </el-col>
        </el-row>
        <!--搜索栏-->
        <div class="search_box">
            <a href="/">
                <img src="/images/book_logo2.png" style="width: 30%;height: 100%">
            </a>
            <div class="search">
                <el-input
                    placeholder="请输入书籍名称或关键字"
                    v-model="input"
                    prefix-icon="el-icon-search"
                    clearable
                    @keyup.enter.native="searchBook(input)">
                </el-input>
                <el-button type="primary" icon="el-icon-search" @click="searchBook(input)">搜索</el-button>
            </div>
        </div>

        <div class="headers_dialog">
            <el-dialog title="收货地址" :visible.sync="outerVisible">
                <!--<el-button type="text" @click="addNewSite" style="float: right">新增地址</el-button>-->
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
                                @click="handleDelete(scope.$index, scope.row)">删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>

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
            </el-dialog>
        </div>

    </div>
</template>

<script>
export default {
    name: "Headers",
    data() {
        return {
            lodding: false,
            colSpan: 7,
            login_userName: '',
            input: '',
            outerVisible: false,
            innerVisible: false,
            gridData: [],
            siteForm: {
                name: '',
                phone: '',
                province: '',
                city: '',
                district: '',
                place: '',
            },
            dataOptions: [],
            pchildsOptions: [],
            cchildsOptions: [],
            //表单验证规则
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
            uname: '',
            uid: '',
        }
    },
    methods: {
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
        //信息提示
        infoTip(message, type) {
            this.$message({
                message: message,
                type: type,
            });
        },
        //用户退出
        quitUser() {
            window.sessionStorage.removeItem("userName");
            window.sessionStorage.removeItem("uid");
            this.uname = '';
            document.getElementById("trueLoginUser").style.display = "none";
            document.getElementById("loginUser").style.display = "inline-block";
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
        //判断用户是否登录
        isUserLogin() {
            if (this.uname != null) {
                document.getElementById("loginUser").style.display = "none";
                document.getElementById("trueLoginUser").style.display = "inline-block";
                this.login_userName = this.uname;
            }
        },
        //收获地址
        showSites() {
            //判断用户是否登录 未登录提醒登录
            if (this.uname == null) {
                this.infoTip("请登录!", "warning");
                return;
            }
            this.outerVisible = true;
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
        //新增地址
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
        //删除地址
        handleDelete(index, row) {
            this.$axios.post("/api/address/delete/" + row.code).then(info => {
                info = info.data;
                if (info.code != 200) {
                    this.infoTip(info.msg, "error");
                    return;
                }
                this.infoTip(info.msg, "success");
                this.showAddress();
            })
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
        //进入购物车
        toBookCart() {
            if (this.uname == null) {
                this.infoTip("请先登录...", "warning");
                return;
            }
            window.sessionStorage.setItem("isRefresh", "1");
            this.$router.push({
                path: "/bookCart/myCart",
                name: "myCart",
            });
        }
    },
    mounted() {
        this.uname = window.sessionStorage.getItem("userName");
        this.uid = window.sessionStorage.getItem("uid");
        this.isUserLogin();
    }
}
</script>

<style scoped>
@import "../css/headers.css";
</style>