//app.js
const NET = require("utils/app_server.js")
App({
  onLaunch: function() {
    // 为了避免闪烁，启动时就隐藏tabbar
    // wx.hideTabBar({})
    this.getAndSetNavHight();
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  // 获取设备信息，确定状态栏高度和头部高度
  getAndSetNavHight() {
    !this.globalData.systeminfo && (this.globalData.systeminfo = wx.getSystemInfoSync());
    !this.globalData.headerBtnPosi && (this.globalData.headerBtnPosi = wx.getMenuButtonBoundingClientRect());
    this.globalData.statusBarHeight = this.globalData.systeminfo.statusBarHeight // 状态栏高度
    let headerPosi = this.globalData.headerBtnPosi // 胶囊位置信息
    this.globalData.navbarHeight = headerPosi.bottom + headerPosi.bottom - headerPosi.height - this.globalData.statusBarHeight;
  },
  checkToken() {
    try {
      var reToken = wx.getStorageSync('refresh_token')
      if (reToken) {
        this.login(2, reToken)
      } else {
        wx.redirectTo({
          url: '/pages/login/login',
        })
      }
    } catch (e) {
      wx.redirectTo({
        url: '/pages/login/login',
      })
    }
  },
  loginOut() {
    // 清空缓存refresh_token
    wx.setStorage({
      key: "refresh_token",
      data: ''
    })
    wx.reLaunch({
      url: '/pages/login/login'
    })
  },
  /*** mType=1--普通登陆，else 静默登陆*/
  login(mType, loginDataOrToken) {
    var tempRequest;
    if (mType == 1) {
      tempRequest = {
        grant_type: "password",
        username: loginDataOrToken.tel,
        password: loginDataOrToken.code
      }
    } else {
      tempRequest = {
        grant_type: "refresh_token",
        refresh_token: loginDataOrToken,
      }
    }
    NET.request(`${this.config.server_url}/oauth/token`, tempRequest, (res) => {
      if (res && res.refresh_token) {
        // 本地缓存refresh_token
        wx.setStorage({
          key: "refresh_token",
          data: res.refresh_token
        })
        // 登陆成功展示tabbar
        wx.showTabBar({})
        this.globalData.isLogin = true;
        this.globalData.access_token = res.access_token;
      }
      if (this.loginSuccess) {
        this.loginSuccess()
      }
      //是否注册登陆成功回掉
    }, (e) => {
      if (e.data && e.data.error_description) {
        if (mType == 1) {
          wx.showToast({
            title: e.data.error_description,
            icon: 'none',
            duration: 2000
          })
        }
      }
      var pages = getCurrentPages() //获取加载的页面
      var currentPage = pages[pages.length - 1] //获取当前页面的对象
      var url = currentPage.route //当前页面url
      if (url != "pages/login/login") {
        wx.redirectTo({
          url: '/pages/login/login',
        })
      }
    }, 'post');
  },
  globalData: {
    userInfo: null, //登陆的用户信息
    role: 1, //用户权限
    access_token: null, //header使用
    systeminfo: null, //系统信息
    statusBarHeight: 0, //状态栏高度
    navbarHeight: 0, //导航高度
    business: [{
      id: '1',
      name: "蔬菜"
    }, {
      id: '2',
      name: "水果"
    }, {
      id: '3',
      name: "生鲜"
    }]
  },
  config: {
    server_url: "https://uc.star.dmagico.com",
    server_api: "https://api.star.dmagico.com"
  }
})