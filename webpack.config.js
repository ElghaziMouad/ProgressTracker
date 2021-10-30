const path = require('path');
//const CopyPlugin = require('copy-webpack-plugin');

module.exports = {
    entry: {
        context: './src/main/js/index.js'
    },
    devtool: 'source-map',
    cache: true,    
    mode: 'development',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/bundle.js'
    },
    // plugins: [
    //     new CopyPlugin([
    //         {from: '../src/main/resources/static/js/bundle.js', to: '../target/classes/static/js'},
    //     ]),
    // ],
    devServer: {
        historyApiFallback: true,
        contentBase: './',
        hot: true
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                include: path.resolve(__dirname, 'src'),
                exclude: /(node_modules|bower_components|build)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"],
                        plugins: [
                            ["@babel/plugin-proposal-class-properties"],
                            ["@babel/plugin-transform-runtime"]
                        ],
                    }
                }]
            },
            {
                test: /\.(css)$/,
                use: ["style-loader", "css-loader"]
            },
            {
                test: /\.scss$/,
                use: [
                    // Creates `style` nodes from JS strings
                    "style-loader",
                    // Translates CSS into CommonJS
                    "css-loader",
                    // Compiles Sass to CSS
                    "sass-loader",
                ],
            },
            {
                test: /\.svg$/,
                use: [
                    {
                        loader: 'svg-url-loader',
                        options: {
                            limit: 10000,
                        },
                    },
                ],
            },
            {
                test: /\.(png|svg|jpg|gif)$/,
                use: [
                    'file-loader',
                ],
            }
            // s
        ]
    }
};
