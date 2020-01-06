const base64 = require("base64.js")
const app = getApp();
export function server(params) {
  return new Promise((resolve, reject) => {
    wx.request({
      url: params.url,
      data: params.data,
      header: {
        'Content-Type': params.contenType ? params.contenType : 'application/json',
        "Accept-Language": "zh-cn",
        "Authorization": app.globalData.access_token ? ("bearer " + app.globalData.access_token) : ("Basic " + base64.CusBASE64.encoder("default:default"))
      },
      method: params.method,
      success: function(res) {
        if (res.statusCode == 200) {
          resolve(res.data)
        } else {
          if (res.statusCode == 401) {
            app.checkToken();
          } else if (res.data && res.data.error_description) {
            reject(res)
            wx.showToast({
              title: res.data.error_description,
              icon: 'none',
              duration: 2000
            })
          } else {
            reject(res)
            wx.showToast({
              title: "请求失败",
              icon: 'none',
              duration: 2000
            })
          }
        }
      },
      fail: function(res) {
        reject(res)
      },
      complete: function(res) {
        wx.stopPullDownRefresh();
      }
    })
  })
}
