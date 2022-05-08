const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        host: 'localhost',
        port: 80,
        proxy: {
            "/api": {
                // target: "http://zhulinz.top:8002",
                target: "http://localhost:9002",
                changeOrigin: true, // target是域名的话，需要这个参数
                security: false,    // 设置支持https协议的代理
                pathRewrite: {
                    "^/api": ""
                }
            }
        },
    },
})
