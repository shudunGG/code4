# 移动前端VUE开发平台

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Run your tests
```
npm run test
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

history 模式下 

nginx 设置
  
location /pub/jmas/jmasbucket/new/dist { 
    root /data/;
    try_files $uri @rewrites;
}
location @rewrites { 
    rewrite ^(.+)$ /pub/jmas/jmasbucket/new/dist/index.html last;
}

vue路由 设置 

mode 改为 history 即
mode: "history"

base设置为项目部署路径 例

base: "/pubcs/jmas/jmasbucket/new/dist/"

vue.config.js publicPath 设置 例

publicPath: "/pubcs/jmas/jmasbucket/new/dist"


部署示例 ： http://wfw.isdapp.shandong.gov.cn/pubcs/jmas/jmasbucket/new/dist/vueui

设置页面缓存参考文档

https://www.cnblogs.com/xfcao/p/11473946.html

eslint 代码走查

npm run lint