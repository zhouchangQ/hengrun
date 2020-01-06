const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    isOpenChoose:false,
    role: app.globalData.role,
    roleName:"恒润",
    userInfo: app.globalData.userInfo,
    userRole:[{id:1,name:"恒润"},{id:2,name:"供应商"}]
  },
  HandleJump(e) {
    wx.navigateTo({
      url: e.currentTarget.dataset.url
    })
  },
  getUserInfo(e) {
    wx.getUserInfo({
      success: (res) => {
        console.log('res====' + JSON.stringify(res.userInfo))
        app.globalData.userInfo = res.userInfo;
        this.setData({
          userInfo: res.userInfo
        })
        this.HandleJump(e)
      }
    })
  },
  // 选择权限
  HandleChooseItem(e) {
    var tempItem = e.currentTarget.dataset.item;
    app.globalData.role = tempItem.id;
    this.setData({
      role: app.globalData.role,
      roleName: tempItem.name,
      isOpenChoose : false
    })
  },
  HandleOpenOrCloseChoose() {
    this.setData({
      isOpenChoose: !this.data.isOpenChoose
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

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