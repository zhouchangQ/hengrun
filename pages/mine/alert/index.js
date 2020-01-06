// pages/mine/alert/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgList: [], //相册照片
    imgListId: [], //相册照片上传对象,imageId,showOrder
    checked: false
  },
  OnChange({ detail }) {
    // 需要手动对 checked 状态进行更新
    this.setData({ checked: detail });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },
  HandleSave() {
    wx.navigateBack({})
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
  PreviewImg(e) {
    console.log('开始预览======' + e.currentTarget.dataset.src)
    console.log('开始预览======' + this.data.imgList)
    wx.previewImage({
      current: e.currentTarget.dataset.src+"", // 当前显示图片的http链接
      urls: this.data.imgList // 需要预览的图片http链接列表
    })
  },
  DeleteImg(e) {
    var tempIndex = e.currentTarget.dataset.index;
    this.data.imgList.splice(tempIndex, 1);
    this.data.imgListId.splice(tempIndex, 1);
    this.setData({
      imgList: this.data.imgList,
      imgListId: this.data.imgListId
    })
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