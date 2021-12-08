// index.js
// 获取应用实例
const app = getApp()

Page({
  data: {
    //motto: 'Lee MP demo',
    openid:'',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserProfile'),
    canIUseGetUserProfile: true,
    canIUseOpenData: true
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad() {
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
  },

  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认，开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    wx.getUserProfile({
      desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        console.log("getUserProfile" + res)
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    })
  },

  getUserInfo(e) {
    // 不推荐使用getUserInfo获取用户信息，预计自2021年4月13日起，getUserInfo将不再弹出弹窗，并直接返回匿名的用户个人信息
    console.log(e)
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },

  requestSubscribeMessage(e){
    const that = this;
    wx.requestSubscribeMessage({
      tmplIds: ['eVfNqzbacMOyAgFY0mFtpCm5x7lKEpoU5tlSg-rLDYc'],
      success(res) {
        if (res['eVfNqzbacMOyAgFY0mFtpCm5x7lKEpoU5tlSg-rLDYc'] === 'accept') {
          wx.showToast({
            title: '订阅OK！',
            duration: 1000,
            success(data) {
              //订阅成功，发送模板消息
              that.sendMessage();
            }
          })
        }
      },
    })
  },
  // 发送消息
  sendMessage(){
    wx.request({
      url: "http://1750d6n614.iask.in/sendSubscribeMessage",
      method: 'get',
      header: { 'Content-Type': 'application/json' },
      success(res) {
        console.log("res",res)
      },
      fail(error){
        console.log("error",error)
      }
    })
  },

  open:function(){
    wx.login({
      success:function(res){
        var that = this;
        var header = {
          'content-type':'application/x-www-form-urlencoded',
          'token': wx.getStorageSync('token')//读取cookie 拿到登录之后异步保存的token值
        };
        if (res.code) {
          console.log(res.code);
          wx.request({//getOpenid
            url: 'https://api.weixin.qq.com/sns/jscode2session',
              data: {
                appid: 'wx188c3d530bb43375', //AppID
                secret: 'f1a7242bb688b9ed2944c11390bc453b', //secret密钥
                grant_type: 'authorization_code',
                js_code: res.code
              },
           method: 'GET',
          header: header,
            success: function (res) {
              var openid = res.data.openid; //登录之后返回的openid
              // this.setData({
              //   openid: res.data.openid
              // });
              console.log(openid+'   我的openid')
              wx.setStorageSync('openid', openid) //储存openid
              if (openid != null & openid != undefined) {
                wx.getUserInfo({
                  success: function (res) {
                   
                  },
                  fail: function (res) {
                    //console.info('用户拒绝授权');
                  }
                }); 
              }else{
                console.info('获取用户openid失败');
              }
            },
            fail: function (res) {
              console.info('获取用户openid失败');
              console.log(error);
            }
          })
        }
      }
    })
  }
})