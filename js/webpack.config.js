module.exports = {
    entry: "./src/site.ts",
    output: {
        filename: "./dist/site.js"
    },
    resolve: {
        extensions: [".js", ".ts", ".tsx"]
    },
    module: {
        loaders: [
            {test: /\.ts(x)?$/, loader: "ts-loader?silent=true"}
        ]
    },
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
