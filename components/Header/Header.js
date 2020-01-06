const app = getApp();
Component({
  properties: {
    title: {
      type: String,
      value: ""
    },
    hideBg: {
      type: Boolean,
      value: true
    }
  },
  data: {
    statusBarHeight: app.globalData.statusBarHeight, // 状态栏高度
    navbarHeight: app.globalData.navbarHeight, // 顶部导航栏高度
  },
  // 微信7.0.0支持wx.getMenuButtonBoundingClientRect()获得胶囊按钮高度
  attached: function () {
  },
  methods: {
  }
})