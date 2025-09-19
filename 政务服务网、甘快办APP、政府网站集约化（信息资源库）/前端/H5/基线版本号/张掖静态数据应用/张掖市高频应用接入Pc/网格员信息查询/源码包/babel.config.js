// babel.config.js
module.exports = {
    presets: [
        ['@vue/app', {
            useBuiltIns: 'entry',
        }]
    ],
    plugins: [
        [
            'component',
            {
                libraryName: 'element-ui',
                styleLibraryName: 'theme-chalk'
            }
        ]
    ]
}
