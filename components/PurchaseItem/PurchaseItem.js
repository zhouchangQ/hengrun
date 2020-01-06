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
    showType: { //是否仅展示不操作
      type: Boolean,
      value: false
    },
    mType: { //列表标识，1甩卖，2报价
      type: String,
      value: 1
    }
  },
  data: {
    role: app.globalData.role, //角色权限
  },
  pageLifetimes: {
    show: function () {
      // 页面被展示
      this.setData({
        role: app.globalData.role, //角色权限
      })
    }
  },
  // 微信7.0.0支持wx.getMenuButtonBoundingClientRect()获得胶囊按钮高度
  attached: function() {
    
  },
  methods: {
    ToEnd(){//查看已结束活动
      wx.navigateTo({
        url: `/pages/alertDetail/index`
      })
    },
    ToAddBaojia() {
      wx.navigateTo({
        url: `/pages/purchase/addBaoJia/index`
      })
    },
    HandleCallPhone(e) {
      wx.makePhoneCall({
        phoneNumber: e.currentTarget.dataset.tel + ""
      })
    },
    ToDetail() {
      wx.navigateTo({
        url: '/pages/purchase/detail/index',
      })
    },
    ToListBaojia() {
      wx.navigateTo({
        url: '/pages/purchase/baojia/index',
      })
    },
    HandleEnd(e) {
      let index = e.currentTarget.dataset.index;
      wx.showModal({
        title: '提示',
        content: '是否立即结束此次采购？',
        success: (res) => {
          if (res.confirm) {
            this.data.listData[index].type = 2;
            this.setData({
              listData: this.data.listData
            })
          }
        }
      })
    },
    HandleDelete(e) {
      let index = e.currentTarget.dataset.index;
      wx.showModal({
        title: '提示',
        content: '是否确认删除此次采购？',
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