var time = 3;
const app = getApp();
import util from "../../utils/util.js"
import { getCode, bindUser } from "../../utils/api.js";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tel: null,
    code: null,
    time: 3,
    isShowOnce: true,
    canGetCode: true,
    codeMsg: "获取验证码"
  },
  onLoad: function () {

  },
  toLogin() {
    // if (this.testForm()) {
    //   var tempData = {
    //     tel: this.data.tel,
    //     code: this.data.code
    //   }
    //   app.login(1, tempData);
    //   app.loginSuccess = () => { //登陆成功回掉
    //     this._bindUser();
    console.log('开始跳转')
        wx.switchTab({
          url: '/pages/purchase/index',
        })
    //   };
    // }
  },
  _bindUser() {
    wx.login({
      success: (res) => {
        if (res.code) {
          var tempRequest = {
            code: res.code
          }
          bindUser(tempRequest, (res) => {

          })
        }
      }
    })

  },
  getCode() {
    if (this.data.canGetCode&&this.testForm()) {
      this.waitCode();
      this.sendCode();
    }
  },
  sendCode() {
    var tempRequest = {
      mobile: this.data.tel,
      bizCode: "SIGN_IN"
    }
    getCode(tempRequest, (res) => {
      wx.showToast({
        title: '发送成功',
        icon: 'success',
        duration: 2000
      })
    })
  },
  waitCode() {
    this.setData({
      canGetCode: false,
      time: this.data.time,
      codeMsg: this.data.time + "'后获取"
    })
    setTimeout(() => {
      this.data.time--;
      this.setData({
        time: this.data.time,
        codeMsg: this.data.time + "'后获取"
      })
      if (this.data.time > 0) {
        this.waitCode();
      } else {
        this.setData({
          time: time,
          canGetCode: true,
          codeMsg: "获取验证码"
        })
      }
    }, 1000)
  },
  openLogin() {
    this.setData({
      isShowOnce: false
    })
  },
  getFormData(e) {
    var tempTag = e.currentTarget.dataset.tag;
    var tempValue = null;
    switch (tempTag) {
      case "1":
        tempValue = {
          tel: e.detail.value
        }
        break;
      case "2":
        tempValue = {
          code: e.detail.value
        }
        break;
      default:
        break;
    }
    this.setData(tempValue);
  },
  testForm() {
    if (!util.isTel(this.data.tel)) {
      wx.showToast({
        title: '手机号码有误',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    return true;
  }
})