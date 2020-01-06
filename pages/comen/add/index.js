const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    fromWay: 0, //1,添加采购，2添加甩卖
    imgList: [], //相册照片
    imgListId: [], //相册照片上传对象,imageId,showOrder
    isOpenChoose: false,
    chooseList: app.globalData.business,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(e) {
    this.data.fromWay = e.type;
    this.setData({
      fromWay: e.type
    })
    if (e.type == 1) {
      wx.setNavigationBarTitle({
        title: '添加采购',
      })
    } else if (e.type == 2){
      wx.setNavigationBarTitle({
        title: '添加甩卖',
      })
    }
  },
  HandleOpenOrCloseChoose() {
    this.setData({
      isOpenChoose: !this.data.isOpenChoose
    })
  },
  // 选择产品范围
  HandleChooseItem(e) {
    var tempItem = e.currentTarget.dataset.item;
    this.setData({
      "formData.business": tempItem.name
    })
    this.HandleOpenOrCloseChoose();
  },
  ChooseImg() {
    var that = this;
    var temList = this.data.imgList
    wx.chooseImage({
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: (res) => {
        // tempFilePath可以作为img标签的src属性显示图片
        const tempFilePaths = res.tempFilePaths;
        temList.push(tempFilePaths)
        this.setData({
          imgList: temList
        })
        // tempFilePaths.forEach(item => {
        //   that.upLoadImg(item, 2)
        // })
        // console.log(temList)
      }
    })
  },
  HandleSave() {
    wx.navigateBack({})
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