package com.avidly.sdk.account.base.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.avidly.sdk.account.base.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static String MD5 = "MD5";
    private static final String ENCODE_UTF8 = "UTF-8";

    /**
     * md5加密字符串
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static String textOfMd5(String value) {
        byte[] data = new byte[0];
        try {
            data = (value).getBytes(ENCODE_UTF8);
        } catch (UnsupportedEncodingException e) {
            data = (value).getBytes();
        }
        byte[] digest = new byte[0];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.reset();
            messageDigest.update(data);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64Utils.encode(digest);
    }

    public static String getMd5Uuid() {
        UUID uuid = UUID.randomUUID();
        try {
            return Md5Utils.textOfMd5(uuid.toString());
        } catch (Throwable throwable) {
            return AESUtils.getRandomString(16);
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Api方法转换
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int getResId(Context context, String type, String name) {
        int id = context.getResources().getIdentifier(name, type, context.getPackageName());
        return id;
    }

    /**
     * 打开浏览器
     */
    public static void openActionView(Context context, String string) {
        Uri uri = Uri.parse(string);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * notification
     */
    public static boolean isNotificationEnable(Context context) {
        if (context == null) {
            return false;
        }
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        return manager.areNotificationsEnabled();
    }

    /**
     * open setting
     */
    public static void goToNotification(Context context) {
        if (context == null) {
            return;
        }
        // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void hideKeyboard(Context context, IBinder token) {
        if (token != null && context != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static Drawable getDrawable(Context context, int resid) {
        Drawable mDrawable;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mDrawable = ContextCompat.getDrawable(context, resid);
        } else {
            mDrawable = context.getResources().getDrawable(resid);
        }
        return mDrawable;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 24;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static void showToastTip(final Context context, final int textid, final boolean longtime) {
        final String text = context.getString(textid);
        showToastTip(context, text, longtime);
    }

    public static void showToastTip(final Context context, final String text, final boolean longtime) {
        ThreadHelper.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, longtime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmailValid(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static String textOfUrlEncode(String text) {
        String result = "";
        try {
            result = URLEncoder.encode(text, Constants.URL_ENCODER_ENC);
        } catch (Throwable e) {

        }
        return result;
    }
}
