<template>
    <div class="book_lists">
        <el-table
            :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
            v-loading="isloading" style="width: 100%">
            <el-table-column
                label="日期"
                prop="date"
                width="100%"
            >
            </el-table-column>
            <el-table-column
                label="书名"
                prop="name"
                width="300%">
            </el-table-column>
            <el-table-column
                label="价格"
                prop="price">
            </el-table-column>
            <el-table-column
                label="库存"
                prop="num">
            </el-table-column>
            <el-table-column
                label="状态"
                prop="status">
            </el-table-column>
            <el-table-column
                align="right">
                <template slot="header" slot-scope="scope">
                    <el-input
                        v-model="search"
                        size="mini"
                        placeholder="输入关键字搜索"/>
                </template>
                <template slot-scope="scope">
                    <el-button
                        size="mini"
                        @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">
                        {{ scope.row.status == '上架' ? '下架' : '上架' }}
                    </el-button>
                </template>
            </el-table-column>

        </el-table>
        <el-pagination
            @current-change="handleCurrentChange"
            :current-page="pageIndex"
            :page-size="pageSize"
            layout="prev, pager, next"
            :total="pageTotal"
            style="margin-top: 2rem">
        </el-pagination>

        <div class="form_dialog">
            <!-- Form -->
            <el-dialog title="书籍信息" :visible.sync="dialogFormVisible" top="2vh" width="45%">
                <el-form :model="form" :inline="true" label-width="70px" size="mini">
                    <el-form-item label="书名">
                        <el-input autocomplete="off" v-model="form.bname"></el-input>
                    </el-form-item>
                    <el-form-item label="作者">
                        <el-input autocomplete="off" v-model="form.bauthor"></el-input>
                    </el-form-item>
                    <el-form-item label="出版社">
                        <el-input autocomplete="off" v-model="form.bpress"></el-input>
                    </el-form-item>
                    <el-form-item label="类别">
                        <el-select v-model="form.bclasses" placeholder="请选择" style="width: 93%">
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
                    <el-form-item label="单价">
                        <el-input autocomplete="off" v-model="form.bprice"></el-input>
                    </el-form-item>
                    <el-form-item label="库存">
                        <el-input autocomplete="off" v-model="form.brepertory"></el-input>
                    </el-form-item>
                    <el-form-item label="ISBN">
                        <el-input autocomplete="off" v-model="form.bisbn"></el-input>
                    </el-form-item>
                    <el-form-item label="出版日期">
                        <el-input autocomplete="off" v-model="form.bpublicationdate"></el-input>
                    </el-form-item>
                    <div class="book_account">
                        <el-form-item label="简介" style="width: 100%;display: block">
                            <el-input v-model="form.babstract" type="textarea"
                                      style="width: 100%" autosize></el-input>
                        </el-form-item>
                        <el-form-item label="内容" style="width: 100%;display: block">
                            <el-input v-model="form.bdescription" type="textarea" style="width: 100%"
                                      :rows="4" autosize></el-input>
                        </el-form-item>
                    </div>
                </el-form>

                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="changeBookInfo">保 存</el-button>
                </div>
            </el-dialog>
        </div>


    </div>
</template>

<script>
export default {
    name: "showBookLists",
    data() {
        return {
            tableData: [],
            search: '',
            dialogFormVisible: false,
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
            },
            classes: [],
            isloading: true,
            pageIndex: window.sessionStorage.getItem("pageIndex") == null ? 1 : (window.sessionStorage.getItem("pageIndex") - 0),
            pageSize: 10,
            pageTotal: 0,
        }
    },
    methods: {
        //点击页数
        handleCurrentChange(val) {
            window.sessionStorage.setItem("pageIndex", val);
            this.searchBookInfos();
        },
        //信息提示
        infoTip(message, type) {
            this.$message({
                message: message,
                type: type,
            });
        },
        //编辑
        handleEdit(index, row) {
            //域中保存书籍编号
            window.sessionStorage.setItem("bid", row.bid);
            this.dialogFormVisible = true;
            //请求 根据编号查询书籍信息
            this.$axios.get("/api/book/getBookByID/" + row.bid)
                .then(book => {
                    book = book.data;
                    if (book.code != 200) {
                        this.infoTip("接口请求失败！原因:" + book.msg, "error");
                        return;
                    }
                    //请求成功
                    this.form.bname = book.data.bname;
                    this.form.bauthor = book.data.bauthor;
                    this.form.bpress = book.data.bpress;
                    this.form.babstract = book.data.babstract;
                    this.form.bdescription = book.data.bdescription;
                    this.form.bprice = book.data.bprice;
                    this.form.brepertory = book.data.brepertory;
                    this.form.bclasses = book.data.bclasses;
                    this.form.bisbn = book.data.bisbn;
                    this.form.bpublicationdate = book.data.bpublicationdate;
                    if (book.data.bstate == 0) {
                        this.form.bstate = '上架';
                    } else {
                        this.form.bstate = "已下架";
                    }
                })
        },
        //根据图书编号修改状态
        handleDelete(index, row) {
            let state = row.status === '上架' ? 1 : 0;
            this.$confirm('是否修改图书状态', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios.post("/api/book/alertBookState/" + row.bid + "/" + state)
                    .then(state => {
                        if (state.data.code == 200) {
                            this.infoTip(state.data.msg, "success");
                            //TODO:需改进
                            this.initGetBookInfos();
                        } else {
                            this.infoTip(state.data.msg, "error");
                        }
                    })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消修改'
                });
            });

        },
        //获取图书信息
        initGetBookInfos() {
            setTimeout(() => {
                this.isloading = false;
                this.searchBookInfos();
            }, 1000);

        },
        //查询图书信息
        searchBookInfos() {
            let pageIndex = window.sessionStorage.getItem("pageIndex") - 0;
            this.pageIndex = pageIndex == null ? 1 : pageIndex;
            this.$axios.get("/api/book/getBooksRoot/" + this.pageIndex + "/" + this.pageSize).then(infos => {
                infos = infos.data;
                if (infos.code == 200) {
                    //循环所有图书信息
                    this.pageTotal = infos.data.bookAccount;
                    this.tableData.length = 0;
                    for (let book of infos.data.bookList) {
                        this.tableData.push({
                            bid: book.bid,
                            date: book.bpublicationdate,
                            name: book.bname,
                            price: '￥' + book.bprice,
                            num: book.brepertory + ' 本',
                            status: book.bstate == 0 ? '上架' : '已下架',
                        })
                    }
                }
            })
        },
        //获取分类
        initGetCateGory() {
            return this.$axios.get("/api/cateGory/all");
        },
        //信息修改
        changeBookInfo() {
            //获取域中的书籍编号
            let bid = window.sessionStorage.getItem("bid");
            let book = {
                bname: this.form.bname,
                bauthor: this.form.bauthor,
                bpress: this.form.bpress,
                babstract: this.form.babstract,
                bdescription: this.form.bdescription,
                bprice: this.form.bprice,
                brepertory: this.form.brepertory,
                bclasses: this.form.bclasses,
                bisbn: this.form.bisbn,
                bpublicationdate: this.form.bpublicationdate,
            }
            this.$axios.post("/api/book/alterBookInfo", {book, bid})
                .then(bookInfo => {
                    bookInfo = bookInfo.data;
                    if (bookInfo.code != 200) {
                        this.infoTip("信息保存失败！请重试...", "error");
                    }
                    this.infoTip("保存成功...", "success");
                    this.dialogFormVisible = false;
                })
        }
    },
    mounted() {
        this.initGetBookInfos();
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
@import "../../assets/styles/admin/showBookLists.css";
</style>