let fs = require('fs')

module.exports = {
  devServer: {
    host: "localhost",
    port: "8081",
    https: true,
    ca: fs.readFileSync('C:\\Projects\\LicentiaUtilities\\books.p12')
  },
  "transpileDependencies": [
    "vuetify"
  ]
}
