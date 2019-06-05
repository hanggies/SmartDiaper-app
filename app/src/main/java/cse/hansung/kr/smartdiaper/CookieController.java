package cse.hansung.kr.smartdiaper;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class CookieController {

    public static String TAG = "clearCookies";

    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Log.d(TAG, "Using clearCookies code for API >=" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
            Log.d(TAG, "Using clearCookies code for API <" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    public static void clearAppCache(Context _oContext, java.io.File _oDir)
    {
        java.io.File _oFile = _oDir;

        if(_oFile==null)
            _oFile = _oContext.getCacheDir();
        if(_oFile==null)
            return;

        java.io.File[] _oChildrenFile = _oFile.listFiles();
        try
        {
            for(int i=0;i<_oChildrenFile.length;i++)
            {
                if(_oChildrenFile[i].isDirectory())
                    clearAppCache(_oContext, _oChildrenFile[i]);
                else
                    _oChildrenFile[i].delete();
            }
        }
        catch(Exception e)
        {
        }
    }

}
