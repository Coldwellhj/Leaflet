package leaflet.miaoa.qmsh.leaflet.bean;

/**
 * Created by hj on 2018/1/6.
 */

public class Https {


    public static String basehttp="http://10.10.24.9:8080/";//本地ip
    public static String basehttp_yun="http://118.31.38.161/";//远程服务器
    public static String picture_yun="http://oxyiwa6jf.bkt.clouddn.com/";//图片远程服务器

    public static String loginpsd=basehttp+"MiaoA/checkloginaction_checkuserlogin.action";//登录
    public static String passwordGet=basehttp+"MiaoA/passwordGet.action";//记住密码
    public static String getCode=basehttp+"MiaoA/servlet/SamplerServlet";//获取验证码
    public static String isExist=basehttp+"MiaoA/checkRegister.action";//查询账号是否存在
    public static String register=basehttp+"MiaoA/addUsers.action";//注册账号
    public static String resetPassword=basehttp+"MiaoA/forgetPassword.action";//重置密码
    public static String loginMsg=basehttp+"MiaoA/checkUserShortLogin.action";//短信登录


    public static String slideShow=basehttp+"MiaoA/getNewHomePageAppCarouselAdver.action";//首页轮播图
    public static String slideShow_error=basehttp+"MiaoA/images/0104.png";//无网络首页轮播图
    public static String slideShow1=basehttp+"MiaoA/getNewHomePageAppSpreadAdver.action";//启动页



    public static String vermicelli_chart=basehttp+"MiaoA/getFanIncreaseByNum.action";//粉丝折线图
    public static String sellerNews=basehttp+"MiaoA/getAllMerchanNews.action";//商家消息
    public static String toaddadver=basehttp+"MiaoA/toaddadver.action";//得到发布广告需要的所有人群特性
    public static String toaddCoin=basehttp+"MiaoA/toaddCoin.action";//查找商家的所有店铺
    public static String coinIndex=basehttp+"MiaoA/coinIndex.action";//查找首页10条商品
    public static String storeIndex=basehttp+"MiaoA/storeIndex.action";//查找十个附近店铺
    public static String getCoinById=basehttp+"MiaoA/getCoinById.action";//商城里的商品详情

}
