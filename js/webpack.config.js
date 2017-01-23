module.exports = {
    entry: "./src/site.ts",
    output: {
        filename: "./dist/site.js"
    },
    resolve: {
        extensions: ["", ".webpack.js", ".web.js", ".ts", ".js"]
    },
    module: {
        loaders: [
            {test: /\.ts$/, loader: "ts-loader"}
        ]
    },
    externals: {
        "jquery": "$",
        "parsleyjs": "window.Parsley",
        "autolinker": "window.Autolinker"
    }
};
