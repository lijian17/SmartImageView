package view.image;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

/**
 * 网络图片
 * 
 * @author lijian
 * @date 2017-7-23 下午10:50:16
 */
public class WebImage implements SmartImage {
	/** 连接超时 */
	private static final int TIMEOUT_CONNECT = 5000;
	/** 读取超时 */
	private static final int TIMEOUT_READ = 10000;

	/** 网络图片缓存 */
	private static WebImageCache webImageCache;

	private String url;

	public WebImage(String url) {
		this.url = url;
	}

	@Override
	public Bitmap getBitmap(Context context) {
		// url不可为空
		if (TextUtils.isEmpty(url)) {
			return null;
		}

		// 不要泄漏上下文
		if (webImageCache == null) {
			webImageCache = new WebImageCache(context);
		}

		// 首先尝试从缓存中获取位图
		Bitmap bitmap = webImageCache.get(url);
		if (bitmap == null) {
			bitmap = getBitmapFromUrl(url);
			if (bitmap != null) {
				webImageCache.put(url, bitmap);
			}
		}

		return bitmap;
	}

	/**
	 * 从网络下载图片
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFromUrl(String url) {
		Bitmap bitmap = null;
		try {
			URLConnection conn = new URL(url).openConnection();
			conn.setConnectTimeout(TIMEOUT_CONNECT);
			conn.setReadTimeout(TIMEOUT_READ);
			bitmap = BitmapFactory
					.decodeStream((InputStream) conn.getContent());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 移除缓存中的图片
	 * 
	 * @param url
	 */
	public static void removeFromCache(String url) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		if (webImageCache != null) {
			webImageCache.remove(url);
		}
	}
}
