const ExtractTextPlugin = require("extract-text-webpack-plugin")
const path = require('path');

var distDir = path.resolve(__dirname, './dist')

module.exports = {
    entry: "./src/site.ts",
    output: {
        path: distDir,
        filename: "site.js"
    },
    resolve: {
        extensions: [".js", ".ts", ".tsx"]
    },
    module: {
        loaders: [
            {test: /\.ts(x)?$/, loader: "ts-loader?silent=true", exclude: /node_modules/},
            {test: /\.css$/, loader: ExtractTextPlugin.extract('css-loader')}
        ]
    },
    plugins: [
        new ExtractTextPlugin({filename: 'style.css', allChunks: true})
    ],
    stats: {children: false},
    externals: {
        "jquery": "$",
        "parsleyjs": "window.Parsley",
        "react": "React",
        "react-dom": "ReactDOM",
        "redux": "Redux",
        "react-redux": "ReactRedux",
        "store": "store"
    }
};
