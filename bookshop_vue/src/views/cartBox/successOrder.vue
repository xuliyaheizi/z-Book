<template>
    <div class="success">
        <div id="stepCount" class="flow_step">
            <ul class="cols_3">
                <li class="step_1">1.我的购物车</li>
                <li class="step_2">2.确认订单信息</li>
                <li class="step_3">3.成功提交订单</li>
            </ul>
        </div>
        <div class="success_msg">
            <i class="el-icon-success" style="font-size: 24px;color: rgb(117, 204, 24)"></i>
            <span>恭喜你，订单提交成功</span>
        </div>
    </div>
</template>

<script>
export default {
    name: "successOrder"
}
</script>

<style scoped>
@import "../../assets/styles/cartBox/successOrder.css";
</style>