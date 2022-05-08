<template>
    <div class="container">
        <!-- 注册页面 -->
        <div class="container-form container-signup">
            <form action="" class="form">
                <h2 class="form-title">Sign Up</h2>
                <el-input type="text" placeholder="User  字母开头,5~16个字母" class="input" v-model="username"></el-input>
                <el-input type="password" placeholder="Password  5~17个字母" class="input" v-model="pwd"
                          show-password></el-input>
                <el-input placeholder="Phone" class="input" v-model="mobile" @keyup.enter.native="postCode"></el-input>
                <div id="code">
                    <el-input type="captcha" placeholder="Captcha" class="input" style="width: 60%"
                              v-model="code"></el-input>
                    <el-button :plain="true" id="singUp_Code" @click="postCode" :disabled="isCode">{{ mation }}
                    </el-button>
                </div>
                <el-button :plain="true" class="submit" @click="signUpUser">sign up</el-button>
            </form>
        </div>
        <!-- 登录页面 -->
        <div class="container-form container-signin">
            <form action="javascript:void(0);" class="form">
                <h2 class="form-title">Sign In</h2>
                <el-input type="text" placeholder="User  字母开头,5~16个字母" class="input" v-model="username"></el-input>
                <el-input type="password" placeholder="Password  5~17个字母" class="input" v-model="pwd"
                          @keyup.enter.native="isLogin" show-password></el-input>
                <a href="javascript:void(0);">forgot your password?</a>
                <el-button :plain="true" class="submit" @click="isLogin">sign in</el-button>
            </form>
        </div>
        <!-- 覆盖层 -->
        <div class="container-overlay">
            <div class="overlay">
                <div class="overlay-slide overlay-left">
                    <button class="submit" id="goSignIn" @click="goSignIn">sign in</button>
                </div>
                <div class="overlay-slide overlay-right">
                    <button class="submit" id="goSignUp" @click="goSignUp">sign up</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import md5 from "js-md5"

export default {
    name: "login",
    data() {
        return {
            username: '',
            pwd: '',
            mobile: '',
            code: '',
            isCode: false,
            mation: '发送',
        }
    },
    // //页面初始前
    beforeCreate() {
        document.getElementsByTagName('body')[0].className = 'body-bg';
    },
    methods: {
        //信息提示
        infoTip(message, type) {
            this.$message({
                message: message,
                type: type,
            });
        },
        //数据校验
        dataValidation() {

            if (this.username == '' || this.pwd == '') {
                this.infoTip("用户名或密码不能为空", "error");
                return true;
            }

            let userName_reg = /^[a-zA-Z][a-zA-Z0-9_]{4,15}$/;
            if (!userName_reg.test(this.username)) {
                this.infoTip("请检查用户名输入格式", "error");
                return true;
            }

            let pwd_reg = /\w{5,17}$/;
            if (!pwd_reg.test(this.pwd)) {
                this.infoTip("请检查密码输入格式", "error");
                return true;
            }
        },
        //登录
        isLogin() {
            //数据校验
            if (this.dataValidation()) {
                return;
            }
            let user = {
                uname: this.username,
                upassword: this.pwd
            };
            this.$axios.get("/api/user/getRandomId/" + this.username)
                .then(infos => {
                    infos = infos.data;
                    if (infos.code == 200) {
                        let randomId = infos.data;
                        //将随机数和密码组合进行加密
                        user.upassword = md5(randomId + this.pwd);
                        //请求登录方法
                        this.$axios.post("/api/user/login", user)
                            .then(user_info => {
                                user_info = user_info.data;
                                if (user_info.code == 200) {
                                    //登录成功 保存用户的用户名
                                    this.infoTip(user_info.msg, "success");
                                    if (user_info.data.ustatus == 0) {
                                        //进入后台管理系统
                                        setTimeout(() => {
                                            this.$router.push({
                                                path: '/backStage/index',
                                                name: 'index_admin',
                                            })
                                        }, 2000);
                                    } else {
                                        //返回首页 并保存用户名和用户id进session域
                                        window.sessionStorage.setItem("userName", this.username);
                                        window.sessionStorage.setItem("uid", user_info.data.uid);
                                        setTimeout(() => {
                                            this.$router.back();
                                        }, 2000);
                                    }
                                } else {
                                    this.infoTip(user_info.msg, "error");
                                }
                            })
                    } else {
                        this.infoTip(infos.msg, "error");
                    }
                })
        },
        //发送验证码
        postCode() {

            let mobile_reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
            if (!mobile_reg.test(this.mobile)) {
                this.infoTip("错误的手机号码！", "error");
                return;
            }
            this.$axios.post("/api/code/getCaptcha/" + this.mobile).then(code => {
                code = code.data;
                if (code.code != 0) {
                    this.infoTip("验证码发送失败! 原因：" + code.msg, "error");
                    return;
                }
                this.infoTip("验证码发送成功!", "success");

                let count = 60;
                this.isCode = true;
                this.mation = count + "s 后重新发送";
                let time = setInterval(() => {
                    count--;

                    this.mation = count + "s 后重新发送";
                    // 判断如果时间到了 0 ，就要停止定时器
                    if (count <= 0) {
                        //停止定时器
                        clearInterval(time)
                        // 5s 结束后，需要让文字恢复 发送 恢复按钮状态
                        this.isCode = false;
                        this.mation = "重新发送";
                    }
                }, 1000)
            })
        },
        //清楚数据
        clearnData() {
            this.username = '';
            this.pwd = '';
            this.mobile = '';
            this.code = '';
        },
        goSignIn() {
            document.querySelector('.container').classList.remove("switch");
            this.clearnData();
        },
        goSignUp() {
            document.querySelector('.container').classList.add('switch');
            this.clearnData();
        },
        //用户注册
        signUpUser() {
            //数据校验
            if (this.dataValidation()) {
                return;
            }

            if (this.code == '') {
                this.infoTip("验证码不能为空", "error");
                return;
            }
            let user = {
                uname: this.username,
                upassword: this.pwd,
                uphone: this.mobile,
            }
            let code = this.code;
            this.$axios.post("/api/user/signUp", {user, code})
                .then(up => {
                    up = up.data;
                    if (up.code != 200) {
                        this.infoTip("注册失败! 原因：" + up.msg, "error");
                        return;
                    }
                    this.infoTip("注册成功!! 2s 后自动切换登录界面!", "success");
                    setTimeout(() => {
                        this.goSignIn();
                    }, 2000);
                })

        }
    }
}
</script>
<style src="../assets/styles/login.css" scoped></style>
<style>
/* 清除所有元素内外边距 转换为c3盒子模型 */
* {
    /*margin: 0 auto;*/
    padding: 0;
    box-sizing: border-box;
}

/* 清除input、button默认边框以及点击高亮 */
input,
button {
    outline: none;
    border: none;
}

/* 清除a标签默认下划线 颜色设置为#333 */
a {
    text-decoration: none;
    color: #333;
    margin: 1.25rem 0;
}

/* 页面整体样式 */
.body-bg {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background: url('../../public/images/background_05.png') no-repeat fixed center;
    background-size: cover;
}
</style>