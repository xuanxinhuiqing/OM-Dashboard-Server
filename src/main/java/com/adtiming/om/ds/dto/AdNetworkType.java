package com.adtiming.om.ds.dto;

public enum AdNetworkType {
    NONE(0),
    Adtiming(1),
    AdMob(2),
    Facebook(3),
    Unity(4),
    Vungle(5),
    TencentAd(6),
    AdColony(7),
    AppLovin(8),
    Mopub(9),
    GoogleAd(10),
    Tapjoy(11),
    Chartboost(12),
    TikTok(13),
    Mintegral(14),
    IronSource(15),
    MyTarget(16),
    ChartboostBid(17),
    Mint(18),
    
    // ADDCODE aaron.song
    TC(19),
    Sigmob(20),
    ZYT(21),
    KS(22),
    Alion(23),
    //
    
    Fyber(30),
	
	// ADDCODE aaron.song
	Agsdk(32),
	AgsdkPlugin(33),
	AlionPlugin(34),
	TiktokPlugin(35),
	KsPlugin(36),
	Plugin6(37),
	MintgralPlugin(38),
	Plugin8(39),
	Plugin9(40),
	Plugin10(41),
	Plugin11(42),
	Plugin12(43),
	Plugin13(44),
	Plugin14(45),
	Plugin15(46),
	Plugin16(47),
	Plugin17(48),
	Plugin18(49),
	Plugin19(50),
	Plugin20(51),
	Plugin21(52),
	Plugin22(53),
	Inmobi(54),
	Baidu(55),
	Smaato(56);
	//

    private int value;

    AdNetworkType(int value) {
        this.value = value;
    }

    public static AdNetworkType getAdNetworkType(int adNetworkType) {
        switch (adNetworkType) {
            case 1:
                return Adtiming;
            case 2:
                return AdMob;
            case 3:
                return Facebook;
            case 4:
                return Unity;
            case 5:
                return Vungle;
            case 6:
                return TencentAd;
            case 7:
                return AdColony;
            case 8:
                return AppLovin;
            case 9:
                return Mopub;
            case 10:
                return GoogleAd;
            case 11:
                return Tapjoy;
            case 12:
                return Chartboost;
            case 13:
                return TikTok;
            case 14:
                return Mintegral;
            case 15:
                return IronSource;
            case 16:
                return MyTarget;
            case 17:
                return ChartboostBid;
            case 18:
                return Mint;
                
            // ADDCODE aaron.song
            case 19:
            	return TC;
            case 20:
            	return Sigmob;
            case 21:
            	return ZYT;
            case 22:
            	return KS;
            case 23:
            	return Alion;
            //
            
            case 30:
                return Fyber;
            
            // ADDCODE aaron.song
            case 32:
            	return Agsdk;
            case 33:
            	return AgsdkPlugin;
            case 34:
            	return AlionPlugin;
            case 35:
            	return TiktokPlugin;
            case 36:
            	return KsPlugin;
            case 37:
            	return Plugin6;
            case 38:
            	return MintgralPlugin;
            case 39:
            	return Plugin8;
            case 40:
            	return Plugin9;
            case 41:
            	return Plugin10;
            case 42:
            	return Plugin11;
            case 43:
            	return Plugin12;
            case 44:
            	return Plugin13;
            case 45:
            	return Plugin14;
            case 46:
            	return Plugin15;
            case 47:
            	return Plugin16;
            case 48:
            	return Plugin17;
            case 49:
            	return Plugin18;
            case 50:
            	return Plugin19;
            case 51:
            	return Plugin20;
            case 52:
            	return Plugin21;
            case 53:
            	return Plugin22;
            case 54:
            	return Inmobi;
            case 55:
            	return Baidu;
            case 56:
            	return Smaato;
            //
                
            default:
                throw new RuntimeException("Not support ad network type:" + adNetworkType);
        }
    }
}
