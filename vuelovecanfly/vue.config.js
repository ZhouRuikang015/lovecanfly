let proxyObj = {};
proxyObj['/'] = {
    ws: false,
    target: 'http://localhost:8081',
    changeOrigin: true,
    pathRewrite: {
        '^/': ''
    }
};

// const CompressionPlugin = require("compression-webpack-plugin");

module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyObj
    },

    //关闭ESlint无脑检查（太严格
    lintOnSave: false,

    // configureWebpack: config => {
    //     if (process.env.NODE_ENV === 'production') {
    //         return {
    //             plugins: [
    //                 new CompressionPlugin({
    //                     test: /\.js$|\.html$|\.css/,
    //                     threshold: 1024,
    //                     deleteOriginalAssets: false
    //                 })
    //             ]
    //         }
    //     }
    // }
}