import {
  server
} from "server"; //引用网络组件

const app = getApp();
var server_url = app.config.server_url;
var server_api = app.config.server_api;

export default {
  //获取验证码
  getCode(data) {
    return server({
      url: `${server_url}/api/sendSmsCode`,
      method: 'post',
      data: data,
      contenType: 'application/x-www-form-urlencoded'
    })
  },
  //绑定用户
  bindUser(data) {
    return server({
      url: `${server_api}/star-card/wechat/miniprogram/bindOpenId`,
      method: 'post',
      data: data,
      contenType: 'application/x-www-form-urlencoded'
    })
  },
  //查询交换成功的卡片
  successedCard() {
    return server({
      url: `${server_api}/star-card/exchange/successed`,
      method: 'get'
    })
  },
  //上传头像
  upLoadHeader(data) {
    return server({
      url: `${server_api}/star-card/my/avatar`,
      method: 'post',
      data: data,
      contenType: 'application/x-www-form-urlencoded'
    })
  }
}