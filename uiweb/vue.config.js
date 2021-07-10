module.exports = {
  devServer:{
    port:8013,
    disableHostCheck: true,
    open:true,
    proxy:{
      '/api': {
        // 目标 API 地址
        target: 'http://127.0.0.1:8083',//真实的api地址
        // 如果要代理 websockets
        // ws: true,
        // 将主机标头的原点更改为目标URL
        changeOrigin: false,
        pathRewrite: {
            '^/api': ''
        }
    }
    }
  }
}