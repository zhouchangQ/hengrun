/**app.js调用的接口请求 */
const base64 = require("base64.js")
const app = getApp();

function request(url, params, success, fail, methos) {
  wx.request({
    url: url,
    data: params,
    header: {
      'Content-Type': 'application/x-www-form-urlencoded',
      "Accept-Language": "zh-cn",
      "Authorization": "Basic " + base64.CusBASE64.encoder("default:default")
    },
    method: methos,
    success: function(res) {
      if (res.statusCode == 200) {
        success(res.data)
      } else {
        fail(res)
        if (res.statusCode == 401) {
          app.checkToken();
        } else if (res.data && res.data.error_description) {
          wx.showToast({
            title: res.data.error_description,
            icon: 'none',
            duration: 2000
          })
        } else {
          wx.showToast({
            title: "请求失败",
            icon: 'none',
            duration: 2000
          })
        }
      }
    },
    fail: function(res) {
      fail(res)
    },
    complete: function(res) {
      wx.hideNavigationBarLoading()
    },
  })
}
module.exports = {
  request: request
}