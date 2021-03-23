module.exports = {
  // configureWebpack: {
  //   devServer: {
  //       headers: { "Access-Control-Allow-Origin": "*" }
  //   }
  // },
  devServer: {
    port: 9000,
    proxy: {
      '^/api': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '^/actuator/health': {
        target: 'http://localhost:8080'
      }
    }
  }
}