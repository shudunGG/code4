module.exports = {
  presets: ["@babel/preset-env", "@vue/app"],
  plugins: [
    [
      "import",
      {
        libraryName: "vant",
        libraryDirectory: "es",
        style: true,
      },
      "vant",
    ],
    "@babel/plugin-transform-runtime",
    "@babel/plugin-proposal-class-properties",
  ],
};
