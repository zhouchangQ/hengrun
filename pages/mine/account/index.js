// pages/mine/account/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    listData:[1]
  },
  HandleJump(e) {
    wx.navigateTo({
      url: "/pages/mine/account/add/index"
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  HandleDelete(e) {
    let index = e.currentTarget.dataset.index;
    wx.showModal({
      title: '提示',
      content: '是否确认删除此账号的权限？',
      success: (res) => {
        if (res.confirm) {
          this.data.listData.splice(index, 1);
          this.setData({
            listData: this.data.listData
          })
          wx.showToast({
            icon: "none",
            title: '删除成功'
          })
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

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