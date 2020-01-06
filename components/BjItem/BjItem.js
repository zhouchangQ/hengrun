const app = getApp();
Component({
  options: {
    addGlobalClass: true,
  },
  properties: {
    listData: { //列表数据
      type: Array,
      value: []
    },
    mType: { //列表标识，1甩卖，2报价
      type: String,
      value: 1
    }
  },
  data: {
    role: app.globalData.role, //角色权限
  },
  // 微信7.0.0支持wx.getMenuButtonBoundingClientRect()获得胶囊按钮高度
  attached: function() {

  },
  pageLifetimes: {
    show: function () {
      // 页面被展示
      this.setData({
        role: app.globalData.role, //角色权限
      })
    }
  },
  methods: {
    HandleCallPhone(e) {
      wx.makePhoneCall({
        phoneNumber: e.currentTarget.dataset.tel + ""
      })
    },
    HandleToDetail() {
      wx.navigateTo({
        url: `/pages/comen/saleAndBjDetail/index?mType=${this.data.mType}`,
      })
    },
    HandleDelete(e) {
      let index = e.currentTarget.dataset.index;
      wx.showModal({
        title: '提示',
        content: '是否确认删除此次甩卖？',
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
    }
  }
})