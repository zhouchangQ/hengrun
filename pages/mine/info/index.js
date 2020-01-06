const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isOpenChoose:false,
    chooseList: app.globalData.business,
    formData:{
      name:null,
      tel:null,
      hw:null,
      business:null,
      compName:null
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  HandleSave(){
    wx.navigateBack({})
  },
  // 选择产品范围
  HandleChooseItem(e) {
    var tempItem = e.currentTarget.dataset.item;
    this.setData({
      "formData.business": tempItem.name
    })
    this.HandleOpenOrCloseChoose();
  },
  HandleOpenOrCloseChoose(){
    this.setData({
      isOpenChoose: !this.data.isOpenChoose
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