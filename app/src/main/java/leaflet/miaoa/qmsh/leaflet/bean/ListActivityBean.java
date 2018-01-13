package leaflet.miaoa.qmsh.leaflet.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */

public class ListActivityBean {


    public  List<Head_adv> headdata;
        /**
         *
         */

        public  class Head_adv{
            public String  hpId;
            public String  hpNum;


            public String  hpArea;
            public int  hpDays;
            public String  hpPerson;
            public String  hpImgs;
            public int  hpMark;
            public String  lianjie;

            public String getHpImgs() {
                return hpImgs;
            }

            public void setHpImgs(String hpImgs) {
                this.hpImgs = hpImgs;
            }

            public String getLianjie() {
                return lianjie;
            }

            public void setLianjie(String lianjie) {
                this.lianjie = lianjie;
            }
        }

    public  class Fans_increate{
        public String  id;
        public String  fanCount;
        public Long  increaseTime;


        public String  storeName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFanCount() {
            return fanCount;
        }

        public void setFanCount(String fanCount) {
            this.fanCount = fanCount;
        }

        public Long getIncreaseTime() {
            return increaseTime;
        }

        public void setIncreaseTime(Long increaseTime) {
            this.increaseTime = increaseTime;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }
    }
    public  class SellerNews extends Entity{
        public String  isRead;
        public String  newsTheme;
        public String  newsContent;
        public Long  newsTime;

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getNewsTheme() {
            return newsTheme;
        }

        public void setNewsTheme(String newsTheme) {
            this.newsTheme = newsTheme;
        }

        public String getNewsContent() {
            return newsContent;
        }

        public void setNewsContent(String newsContent) {
            this.newsContent = newsContent;
        }

        public Long getNewsTime() {
            return newsTime;
        }

        public void setNewsTime(Long newsTime) {
            this.newsTime = newsTime;
        }
    }
    public  class Advadver{
        public String  userlike;

        public String getUserlike() {
            return userlike;
        }

        public void setUserlike(String userlike) {
            this.userlike = userlike;
        }
    }
    public  class GoodRules{
        public String  rules;

        public String getRules() {
            return rules;
        }

        public void setRules(String rules) {
            this.rules = rules;
        }
    }
    public  class Product{
        public String  cId;
        public String  cName;
        public String  cIntro;
        public String  cNowPrice;
        public String  cFormerPrice;
        public String  cCover;
        public String  cSales;

        public String getcId() {
            return cId;
        }

        public void setcId(String cId) {
            this.cId = cId;
        }

        public String getcName() {
            return cName;
        }

        public void setcName(String cName) {
            this.cName = cName;
        }

        public String getcIntro() {
            return cIntro;
        }

        public void setcIntro(String cIntro) {
            this.cIntro = cIntro;
        }

        public String getcNowPrice() {
            return cNowPrice;
        }

        public void setcNowPrice(String cNowPrice) {
            this.cNowPrice = cNowPrice;
        }

        public String getcFormerPrice() {
            return cFormerPrice;
        }

        public void setcFormerPrice(String cFormerPrice) {
            this.cFormerPrice = cFormerPrice;
        }

        public String getcCover() {
            return cCover;
        }

        public void setcCover(String cCover) {
            this.cCover = cCover;
        }

        public String getcSales() {
            return cSales;
        }

        public void setcSales(String cSales) {
            this.cSales = cSales;
        }
    }
    public  class Shop{
        public String  sName;
        public Long  beginTime;
        public Long  endTime;
        public String  distance;
        public String  sLogo;
        public String  sAddress;

        public String getsName() {
            return sName;
        }

        public void setsName(String sName) {
            this.sName = sName;
        }

        public Long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Long beginTime) {
            this.beginTime = beginTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getsLogo() {
            return sLogo;
        }

        public void setsLogo(String sLogo) {
            this.sLogo = sLogo;
        }

        public String getsAddress() {
            return sAddress;
        }

        public void setsAddress(String sAddress) {
            this.sAddress = sAddress;
        }
    }
    public  class Goods_shopping_mall{
        public String  cName;
        public Long  beginTime;
        public Long  endTime;
        public Long  cUploadTime;
        public Long  cCheckTime;
        public String  cIntro;
        public String  cNowPrice;
        public String  cFormerPrice;
        public String  cImgs;
        public String  sName;
        public String  sAddress;
        public String  sLegalPhone;

        public String getcName() {
            return cName;
        }

        public void setcName(String cName) {
            this.cName = cName;
        }

        public Long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Long beginTime) {
            this.beginTime = beginTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public Long getcUploadTime() {
            return cUploadTime;
        }

        public void setcUploadTime(Long cUploadTime) {
            this.cUploadTime = cUploadTime;
        }

        public Long getcCheckTime() {
            return cCheckTime;
        }

        public void setcCheckTime(Long cCheckTime) {
            this.cCheckTime = cCheckTime;
        }

        public String getcIntro() {
            return cIntro;
        }

        public void setcIntro(String cIntro) {
            this.cIntro = cIntro;
        }

        public String getcNowPrice() {
            return cNowPrice;
        }

        public void setcNowPrice(String cNowPrice) {
            this.cNowPrice = cNowPrice;
        }

        public String getcFormerPrice() {
            return cFormerPrice;
        }

        public void setcFormerPrice(String cFormerPrice) {
            this.cFormerPrice = cFormerPrice;
        }

        public String getcImgs() {
            return cImgs;
        }

        public void setcImgs(String cImgs) {
            this.cImgs = cImgs;
        }

        public String getsName() {
            return sName;
        }

        public void setsName(String sName) {
            this.sName = sName;
        }

        public String getsAddress() {
            return sAddress;
        }

        public void setsAddress(String sAddress) {
            this.sAddress = sAddress;
        }

        public String getsLegalPhone() {
            return sLegalPhone;
        }

        public void setsLegalPhone(String sLegalPhone) {
            this.sLegalPhone = sLegalPhone;
        }
    }
    }
