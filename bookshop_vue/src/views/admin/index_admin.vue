<template>
    <!--首页-->
    <div class="fade-transform-box">
        <link rel="stylesheet" href="https://at.alicdn.com/t/font_3323382_i53adbbe7x.css">
        <div>
            <el-row style="margin-left: -15px; margin-right: -15px;">
                <el-col :span="6" style="padding-left: 15px; padding-right: 15px;">
                    <el-card>
                        <div class="card-icon-container">
                            <i class="iconfont icon-yonghu"
                               style="color: rgb(64, 201, 198);"></i>
                        </div>
                        <div class="card-desc">
                            <div class="card-title">
                                用户量
                            </div>
                            <div class="card-count">
                                108427
                            </div>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="6" style="padding-left: 15px; padding-right: 15px;">
                    <el-card>
                        <div class="card-icon-container">
                            <i class="iconfont icon-kucun"
                               style="color: rgb(64, 201, 198);"></i>
                        </div>
                        <div class="card-desc">
                            <div class="card-title">
                                总库存
                            </div>
                            <div class="card-count">
                                108427
                            </div>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="6" style="padding-left: 15px; padding-right: 15px;">
                    <el-card>
                        <div class="card-icon-container">
                            <i class="iconfont icon-xiaoshou-"
                               style="color: rgb(64, 201, 198);"></i>
                        </div>
                        <div class="card-desc">
                            <div class="card-title">
                                销售量
                            </div>
                            <div class="card-count">
                                108427
                            </div>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="6" style="padding-left: 15px; padding-right: 15px;">
                    <el-card>
                        <div class="card-icon-container">
                            <i class="iconfont icon-liulan"
                               style="color: rgb(64, 201, 198);"></i>
                        </div>
                        <div class="card-desc">
                            <div class="card-title">
                                浏览量
                            </div>
                            <div class="card-count">
                                108427
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <el-card shadow="always" style="margin-top: 1.25rem;">
                <div style="height: 350px">
                    <div id="echart_id" style="width: 100%;height: 350px;"></div>
                </div>
            </el-card>
            <el-card shadow="always" style="margin-top: 1.25rem;">
                <div style="height: 350px">
                    <div id="differentBooks_sales" style="width: 100%;height: 350px;"></div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    import * as echarts from "echarts";

    export default {
        name: "index_admin",
        data() {
            return {}
        },
        methods: {
            //TODO:一周销售量
            myEcharts() {
                let myChart = echarts.init(document.getElementById('echart_id'));
                //配置图表
                myChart.setOption(
                    {
                        title: {
                            text: '一周销售量',
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {type: 'cross'}
                        },
                        legend: {},
                        xAxis: {
                            data: ['A', 'B', 'C', 'D', 'E']
                        }
                        ,
                        yAxis: {
                            type: 'value',
                            name: '销量',
                            axisLabel: {
                                formatter: '{value} 本'
                            }
                        }
                        ,
                        series: [
                            {
                                name: ' ',
                                data: [10, 22, 28, 43, 49],
                                type: 'line',
                                smooth: true,
                            },
                        ]
                    });
            },
            //TODO:各类别图书的销售量
            difBooksSales() {
                let chart = echarts.init(document.getElementById('differentBooks_sales'));
                chart.setOption({
                    title: {
                        text: '各类图书的销售量',
                    },
                    tooltip: {},
                    legend: {
                        data: ['销量']
                    },
                    xAxis: {
                        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                    },
                    yAxis: {},
                    series: [
                        {
                            type: 'bar',
                            name: '销量',
                            data: [23, 24, 18, 25, 27, 28, 25]
                        }
                    ]
                })
            },
        },
        mounted() {
            this.myEcharts();
            this.difBooksSales();
        }
    }
</script>

<style scoped>
    @import "../../assets/styles/admin/index.css";
</style>