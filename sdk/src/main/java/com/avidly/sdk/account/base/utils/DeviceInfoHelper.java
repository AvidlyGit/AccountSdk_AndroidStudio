package com.avidly.sdk.account.base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.business.LoginCenter;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Copid by Roy on 2019/2/22.
 */

public class DeviceInfoHelper {
    public static final String KEY_GAID = "key_gaid";
    private static String gaid;
    public DeviceInfoHelper() {
    }

    public static void init(final Context var0) {
       final SpHelper spHelper = new SpHelper(var0,Constants.SP_NAME);
        if(var0 != null) {
            gaid = SpHelper.getString(var0, "key_gaid");
            if(TextUtils.isEmpty(gaid)) {
                ThreadHelper.runOnWorkThread(new Runnable() {
                    public void run() {
                        try {
                            Class var1 = null;

                            try {
                                var1 = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
                            } catch (Throwable var3) {

                            }

                            if(var1 != null) {
                                DeviceInfoHelper.gaid = AdvertisingIdClient.getGaidByClient(var0);
                            }

                            if(TextUtils.isEmpty(DeviceInfoHelper.gaid)) {
                                AdvertisingIdClient.AdInfo var2 = AdvertisingIdClient.getAdvertisingIdInfo(var0);
                                DeviceInfoHelper.gaid = var2.getId();
                            }

                            LogUtils.i("got gaid " + DeviceInfoHelper.gaid);
                            spHelper.putString("key_gaid", DeviceInfoHelper.gaid);
                            LoginCenter.setGaid(DeviceInfoHelper.gaid);
                        } catch (Throwable var4) {
                        }
                    }
                });
            }
        }

    }



    public static String getGaid(Context var0) {
        if(var0 != null) {
            gaid = SpHelper.getString(var0, "key_gaid");
            if(TextUtils.isEmpty(gaid)) {
                init(var0);
            }
        }

        if(gaid == null) {
            gaid = "";
        }

        return gaid;
    }

    public static String getAdsText() {
        return "%@8|{=Go!:[!&!!-R!!-Rl2u`vclu29\"3$>&bgwc7+c;ddx6#2\"26;2j{pHucff|!.\"($tfvghGmy}qg;;o r~}cg5-,*2oyn}f&<]2.'=,5&<,Id-6nitqr}xy;<2VC!80`cjb{J}wxtyzs;;o dv~hqe5#6<!bfwfehaog7+(52yz`|sgvocygx5-,*2qutu``cb\\t|u}v2*!8;dlkrHqbg5-,*2jslzMebu!8%l4;qtgKznzdop2<o5uxh~fb!.i&dgmlpcG}uvuauu68}5tcxvnFuxlg!. ($imnlNopvy6.(-6c`qc$.Ll;gveOmuma$<!cq|w{2<6ufgKicn2<6tv4g`s=sap)??;4%'.-%&#-+0'7Z8!1 ##-2&3 !80e``\\va}:#!($$d\\i}*5bcwcvwa|f29o0vcewc{vt|Otqrxtxv$-k$pr{xyOpxlc0>4*!m{}aFgyr};;%.$vv`g5-B}2bveK|ekc!87p|tor68;`rdY|uy6-5zg=b`s9bqd+:;-'(/&$!\".8&35\"L)% #-2$7&3$0($gedJelu2*%,)1i_{j<$}yc|tcwyw}sh$<x |nl|bOpqf`anr5*}6xyuyOtye}0>7*!cqNl``u6.;c{mr5<$pr{xyOpxlc0>6*!c`ewFs|{g|#.2*5|gmxbm$*!ffffmego 93yfvc6.Bz6c`qOhuzr;<2btn{p&*$bdsNs|i2.6z`9cvg=vau: ?(5 5\"&101:0$\"-E?#&&(0#5>'$$85vf`Owdo6(5>63}Hle52bqcxspgbHfoprx;<k!omxk[qoek7+)52tqxxxKqnxg$.\";;uxlg\\uv&<7/ fywnOqpK}`}nyHdoyrd;<\"3<!y{jYtfutc|Fdyyq;;%7*5q`rd5#]k!qerMjgkf /3y}}ov65#ufYb~o`H~}$*!sb9stv+sww<! (&$\"/5!41.\"7'\"K61\":!7!\"261!.7p~fOd`x;;%:6'm[i;5peomObpa&<}!kv~vFturulm` <l2bq{v`Yckot6(56*!m{}aFgyr};;$.$t|ow|Hmy`f296{jrcqqaxlpq|68;`rdu5*]o5vf`Omqnq0>$ggozs:52qrrFjq{$-2eu:viv=sea9+=>034#%-/')&%*4H-1\"!7# !/6#!<!utbYrwn7+)!  iId|i.$aut6-5jb{\\tfrsqjr\\37l4;}uggxfq <5om6j";
    }

    public static String getBuildModel() {
        return Build.MODEL;
    }

    public static String getBuildBrand() {
        return Build.BRAND;
    }

    public static String getBuildVersion() {
        return Build.VERSION.SDK_INT + "";
    }

    public static String getPackageName(Context var0) {
        try {
            return var0.getPackageName();
        } catch (Throwable var2) {
            return "";
        }
    }

    public static int getVersionCode(Context var0) {
        try {
            return var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0).versionCode;
        } catch (Throwable var2) {
            return 0;
        }
    }

    public static String getVersionName(Context var0) {
        try {
            String var1 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0).versionName;
            return var1;
        } catch (Throwable var2) {
            return "";
        }
    }


    public static String getOrientation(Context var0) {
        try {
            int var1 = var0.getResources().getConfiguration().orientation;
            return var1 == 1?"p":"l";
        } catch (Throwable var2) {
            return "l";
        }
    }

    public static boolean hasGP(Context var0) {
        return pkgExists("com.android.vending", var0);
    }

    public static boolean hasFB(Context var0) {
        return pkgExists("com.facebook.katana", var0);
    }

    private static boolean pkgExists(String var0, Context var1) {
        try {
            PackageManager var2 = var1.getPackageManager();
            var2.getPackageInfo(var0, 1);
            return true;
        } catch (Throwable var3) {
            return false;
        }
    }

    public static String getLocale(Context var0) {
        try {
            Locale var1 = var0.getResources().getConfiguration().locale;
            String var2 = Locale.getDefault().toString();
            return var2;
        } catch (Throwable var3) {
            return "";
        }
    }

    public static DisplayMetrics getDM(Context var0) {
        DisplayMetrics var1 = new DisplayMetrics();

        try {
            ((WindowManager)var0.getSystemService("window")).getDefaultDisplay().getMetrics(var1);
        } catch (Throwable var3) {
            ;
        }

        return var1;
    }

    public static String getDmWidth(Context var0) {
        try {
            return getDM(var0).widthPixels + "";
        } catch (Throwable var2) {
            return "";
        }
    }

    public static String getDmHeight(Context var0) {
        try {
            return getDM(var0).heightPixels + "";
        } catch (Throwable var2) {
            return "";
        }
    }

    public static boolean isNetworkConnected(Context var0) {
        try {
            if(var0 != null) {
                ConnectivityManager var1 = (ConnectivityManager)var0.getSystemService("connectivity");
                NetworkInfo var2 = var1.getActiveNetworkInfo();
                if(var2 != null) {
                    return var2.isAvailable();
                }
            }
        } catch (Throwable var3) {
            ;
        }

        return false;
    }

    public static String getNetWorkType(Context var0) {
        try {
            ConnectivityManager var1 = (ConnectivityManager)var0.getSystemService("connectivity");
            NetworkInfo var2 = var1.getActiveNetworkInfo();
            if(var2 == null) {
                return "none";
            }

            if(var2.getType() == 1) {
                return "wifi";
            }

            if(var2.getType() == 0) {
                return "mobile";
            }
        } catch (Throwable var3) {
            ;
        }

        return "unkown";
    }

    public static boolean isForeground(Context var0) {
        try {
            if(var0 != null) {
                ActivityManager var1 = (ActivityManager)var0.getSystemService("activity");
                List var2 = var1.getRunningAppProcesses();
                Iterator var3 = var2.iterator();

                while(var3.hasNext()) {
                    ActivityManager.RunningAppProcessInfo var4 = (ActivityManager.RunningAppProcessInfo)var3.next();
                    if(var4.processName.equals(var0.getPackageName()) && var4.importance == 100) {
                        return true;
                    }
                }
            }
        } catch (Throwable var5) {
            ;
        }

        return false;
    }
}
