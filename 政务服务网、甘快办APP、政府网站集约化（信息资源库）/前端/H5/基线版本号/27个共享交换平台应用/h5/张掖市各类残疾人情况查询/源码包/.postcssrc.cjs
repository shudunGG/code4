module.exports = {
  plugins: {
    "postcss-pxtorem": {
      rootValue: 37.5, // 具体值根据项目UI实际情况，默认給37.5
      propList: ["*"],
    },
  },
};