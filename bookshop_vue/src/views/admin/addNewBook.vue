<template>
    <div class="book_addNewBook">
        <el-form :model="form" :inline="true" ref="form" label-width="80px" :rules="formRule" status-icon inmes>
            <el-form-item label="图片" prop="bimg">
                <el-upload
                        class="avatar-uploader"
                        action="/api/file/upload"
                        list-type="picture"
                        :show-file-list="false"
                        :before-upload="beforeUpload"
                        :on-success="handleUploadSuccess"
                        name="file">
                    <img v-if="form.bimg" :src="form.bimg" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item>
            <div class="book_name">
                <el-form-item label="书名" prop="bname">
                    <el-input autocomplete="off" v-model="form.bname" placeholder="请输入书名"></el-input>
                </el-form-item>
                <el-form-item label="作者" prop="bauthor">
                    <el-input autocomplete="off" v-model="form.bauthor" placeholder="请输入作者名"></el-input>
                </el-form-item>
            </div>
            <el-form-item label="出版社">
                <el-input autocomplete="off" v-model="form.bpress" placeholder="请输入出版社"></el-input>
            </el-form-item>
            <el-form-item label="类别" prop="bclasses">
                <el-select v-model="form.bclasses" placeholder="请选择类别" style="width: 100%">
                    <el-option-group
                            v-for="group in classes"
                            :key="group.label"
                            :label="group.label">
                        <el-option
                                v-for="item in group.secondClasses"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-option-group>
                </el-select>
            </el-form-item>
            <el-form-item label="单价" prop="bprice">
                <el-input autocomplete="off" v-model="form.bprice" placeholder="请输入价格"></el-input>
            </el-form-item>
            <el-form-item label="库存" prop="brepertory">
                <el-input autocomplete="off" v-model="form.brepertory" placeholder="请输入库存数量"></el-input>
            </el-form-item>
            <el-form-item label="ISBN" prop="bisbn">
                <el-input autocomplete="off" v-model="form.bisbn" placeholder="请输入ISBN码"></el-input>
            </el-form-item>
            <el-form-item label="出版日期" prop="bpublicationdate">
                <el-date-picker type="date" placeholder="选择日期" v-model="form.bpublicationdate"
                                style="width: 100%;" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
            <div class="book_account">
                <el-form-item label="简介" style="width: 100%;display: block">
                    <el-input v-model="form.babstract" type="textarea"
                              style="width: 85%;" :autosize="{minRows:2}" placeholder="请输入简介内容"></el-input>
                </el-form-item>
                <el-form-item label="内容" style="width: 100%;display: block">
                    <el-input v-model="form.bdescription" type="textarea" style="width: 85%"
                              :autosize="{minRows:4}" placeholder="请输入详细内容"></el-input>
                </el-form-item>
            </div>
            <el-form-item style="float: right">
                <el-button type="primary" @click="submitForm('form')">立即创建</el-button>
                <el-button @click="resetForm('form')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "addNewBook",
        data() {
            return {
                form: {
                    bname: '',
                    bauthor: '',
                    bpress: '',
                    babstract: '',
                    bdescription: '',
                    bprice: '',
                    brepertory: '',
                    bclasses: '',
                    bisbn: '',
                    bpublicationdate: '',
                    bimg: '',
                },
                classes: [],
                formRule: {
                    bname: [
                        {required: true, message: "请输入书名", trigger: 'blur'},
                    ],
                    bauthor: [
                        {required: true, message: "请输入作者名", trigger: 'blur'}
                    ],
                    bclasses: [
                        {required: true, message: '请选择该书的分类', trigger: 'change'}
                    ],
                    bprice: [
                        {required: true, validator: this.verifyInfos("价格不能为空", "价格不能为负数"), trigger: 'blur'}
                    ],
                    brepertory: [
                        {required: true, validator: this.verifyInfos("库存不能为空", "库存不能为负数"), trigger: 'blur'},
                    ],
                    bisbn: [
                        {required: true, message: "请输入ISBN码", trigger: 'blur'}
                    ],
                    bpublicationdate: [
                        {required: true, message: "选择出版日期", trigger: 'change'}
                    ],
                    bimg: [
                        {required: true, message: "请上传书本图片", trigger: 'blur'}
                    ]
                },

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
            //校验回调信息
            verifyInfos(ainfo, binfo) {
                return (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error(ainfo));
                    }
                    setTimeout(() => {
                        if (value < 0) {
                            callback(new Error(binfo));
                        } else {
                            callback();
                        }
                    }, 1000);
                };
            },
            //获取分类信息
            initGetCateGory() {
                return this.$axios.get("/api/cateGory/all");
            },
            /*对整个表单进行校验的方法，参数为一个回调函数。
            该回调函数会在校验结束后被调用，并传入两个参数：是否校验成功和未通过校验的字段。
            若不传入回调函数，则会返回一个 promise
             */
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$confirm('请确认信息无误?', '信息确认', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            //向后端发送请求保存信息
                            let book = {
                                bname: this.form.bname,
                                bauthor: this.form.bauthor,
                                bpress: this.form.bpress,
                                babstract: this.form.babstract,
                                bdescription: this.form.bdescription,
                                bprice: this.form.bprice,
                                brepertory: this.form.brepertory,
                                bclasses: this.form.bclasses,
                                bimg: this.form.bimg,
                                bisbn: this.form.bisbn,
                                bpublicationdate: this.form.bpublicationdate
                            }
                            this.$axios.post("/api/book/addNewBook", book)
                                .then(book => {
                                    book = book.data;
                                    if (book.code != 200) {
                                        this.infoTip("信息提交失败", "error");
                                    }
                                    //信息清空
                                    this.$refs[formName].resetFields();
                                    this.form.bname = '';
                                    this.form.bauthor = '';
                                    this.form.bpress = '';
                                    this.form.babstract = '';
                                    this.form.bdescription = '';
                                    this.form.bprice = '';
                                    this.form.brepertory = '';
                                    this.form.bclasses = '';
                                    this.form.bimg = '';
                                    this.form.bisbn = '';
                                    this.form.bpublicationdate = '';
                                    this.$refs[formName].resetFields();
                                    this.infoTip('信息提交成功!', 'success');
                                })
                        }).catch(() => {
                            this.infoTip('已取消提交', 'info');
                        });
                    } else {
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

            //文件进行上传期间对文件进行的操作
            beforeUpload(file) {
                const isJPGOrPNG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPGOrPNG) {
                    this.$message.error('上传头像图片只能是 JPG/PNGs 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            //res表示后端的返回值，其中应包含文件上传后的url路径
            handleUploadSuccess(res, file) {
                if (res.code == 200) {
                    this.infoTip("上传成功", "success");
                    //返回Oss存储图片的路径
                    // this.form.bimg = URL.createObjectURL(file.raw);
                    this.form.bimg = res.data;
                }
            }
        },
        mounted() {
            this.$axios.all([this.initGetCateGory()])
                .then(this.$axios.spread(gorys => {
                    gorys = gorys.data;
                    if (gorys.code == 200) {
                        //循环所有分类
                        for (var gory of gorys.data) {
                            let sort = [];
                            //循环该一级分类下的二级分类
                            for (let secondGory of gory.cateGoryListSecond) {
                                sort.push({
                                    value: secondGory.cname,
                                    label: secondGory.cname
                                })
                            }
                            this.classes.push({
                                label: gory.CateGory.cname,
                                secondClasses: sort,
                            })

                        }
                    }
                }));
        }
    }
</script>

<style scoped>
    @import "../../assets/styles/admin/addNewBook.css";
</style>