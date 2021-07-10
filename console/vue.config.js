module.exports = {
  productionSourceMap: false,
  pages: {
    index: {
      entry: 'src/main.js',
      template: 'public/index.html'
    },
    login: {
      entry: 'src/login.js',
      template: 'public/login.html'
    }
  },
  devServer: {
    port: 9000,
    disableHostCheck: true,
    proxy: {
      '/api': {
        changeOrigin: false,
        target: 'http://localhost:8080',
        pathRewrite: {'^/api': ''}
      }
    }
  },
  css: {
    loaderOptions: {
      sass: {
        prependData: `@import "@/scss/_variables.scss";`
      },
    }
  }
};
