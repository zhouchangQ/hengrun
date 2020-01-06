const removeItem = (arr, value) => {
  var index = arr.indexOf(value);
  if (index > -1) {
    arr.splice(index, 1);
  }
  return arr;
}
/**日期格式化 */
const fmtDate = (obj) => {
  obj = obj.replace(/-/g, "/")
  var date = new Date(obj);
  var y = 1900 + date.getYear();
  var m = "0" + (date.getMonth() + 1);
  var d = "0" + date.getDate();
  var time = y + "." + m.substring(m.length - 2, m.length) + "." + d.substring(d.length - 2, d.length)
  return time;
}
function add0(m) { return m < 10 ? '0' + m : m }
/**日期格式化 */
const fmtDate3 = (shijianchuo) => {
  var time = new Date(shijianchuo);
  var y = time.getFullYear();
  var m = time.getMonth() + 1;
  var d = time.getDate();
  var h = time.getHours();
  var mm = time.getMinutes();
  var s = time.getSeconds();
  return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
}
/** 
 * 时间戳格式化函数 
 * @param  {string} format    格式 
 * @param  {int}    timestamp 要格式化的时间 默认为当前时间 
 * @return {string}           格式化的时间字符串 
 */
const fmtDate2 = (timestamp) => {
  var nowTimestamp = new Date().getTime();
  var difS = (timestamp - nowTimestamp) / 1000;
  if (timestamp <= nowTimestamp) {
    return;
  }
  var difM = difS / 60;
  var difH = difS / 60 / 60;
  var H = parseInt(difM / 60);
  var M = parseInt((difH - H) * 60);
  var S = parseInt(((difH - H) * 60 - M) * 60);
  return ((H + "").length > 1 ? H : "0" + H) + ":" + ((M + "").length > 1 ? M : "0" + M) + ":" + ((S + "").length > 1 ? S : "0" + S)
}
/**当前日期 */
const getToday = () => {
  var date = new Date();
  var y = 1900 + date.getYear();
  var m = "0" + (date.getMonth() + 1);
  var d = "0" + date.getDate();
  var time = y + "-" + m.substring(m.length - 2, m.length) + "-" + d.substring(d.length - 2, d.length)
  return time;
}
/**手机号码校验 */
const isTel = (obj) => {
  var myreg = /^[1][3,4,5,6,7,8][0-9]{9}$/;
  if (!myreg.test(obj)) {
    return false;
  } else {
    return true;
  }
}
//获取当前时间，格式YYYY-MM-DD
const getNowFormatDate = (number) => {
  var date = new Date();
  var seperator1 = "-";
  var year = date.getFullYear() - number;
  var month = date.getMonth() + 1;
  var strDate = date.getDate();
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
  }
  var currentdate = year + seperator1 + month + seperator1 + strDate;
  return currentdate;
}
/**获取n-m随机数 */
const getRandom = (lower, upper) => {
  return Math.floor(Math.random() * (upper - lower + 1)) + lower;
}
/**邮箱校验 */
const isEmail = (obj) => {
  var myreg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
  if (!myreg.test(obj)) {
    return false;
  } else {
    return true;
  }
}
export default {
  removeItem,
  fmtDate,
  fmtDate2,
  fmtDate3,
  isTel,
  isEmail,
  getToday,
  getRandom,
  getNowFormatDate
}