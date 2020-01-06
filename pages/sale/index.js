const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    hideBg:true,
    listData:[1,2],
    role: app.globalData.role, //角色权限
    x: app.globalData.systeminfo.windowWidth - 120,
    y: app.globalData.systeminfo.windowHeight - 120,
    statusBarHeight: app.globalData.statusBarHeight,//状态栏高度
    navbarHeight: app.globalData.navbarHeight//导航高度
  },
  ToAdd() {
    wx.navigateTo({
      url: `/pages/comen/add/index?type=2`,
    })
  },
  onPageScroll(e) {
    if (e.scrollTop>20){
      this.setData({
        hideBg: false
      })
    }else{
      this.setData({
        hideBg: true
      })
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      role: app.globalData.role, //角色权限
    })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh() //停止下拉刷新
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})