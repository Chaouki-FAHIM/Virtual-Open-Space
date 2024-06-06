const path = require('path');

module.exports = {
    webpack: {
        configure: (webpackConfig) => {
            webpackConfig.module.rules.push({
                test: /\.m?js$/,
                include: /node_modules/,
                resolve: {
                    fullySpecified: false
                },
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            });

            return webpackConfig;
        }
    }
};
