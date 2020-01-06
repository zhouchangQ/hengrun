const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    hideBg: true,
    role: app.globalData.role, //角色权限
    // type=1正常进行，2已结束
    listData: [{ id: 1, type: 1 }, { id: 2, type: 2 }, { id: 3, type: 1 }],
    x: app.globalData.systeminfo.windowWidth-130,
    y: app.globalData.systeminfo.windowHeight-120,
    statusBarHeight: app.globalData.statusBarHeight, //状态栏高度
    navbarHeight: app.globalData.navbarHeight //导航高度
  },

  onPageScroll(e) {
    if (e.scrollTop > 20) {
      this.setData({
        hideBg: false
      })
    } else {
      this.setData({
        hideBg: true
      })
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    // this.loading = this.selectComponent("#loading");
    // this.loading.loadingStart();
    setTimeout(() => {
      wx.showTabBar({})
    }, 1000)
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.setData({
      role: app.globalData.role, //角色权限
    })
  },
  ToAdd() {//添加采购
    wx.navigateTo({
      url: `/pages/comen/add/index?type=1`,
    })
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    wx.stopPullDownRefresh() //停止下拉刷新
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})