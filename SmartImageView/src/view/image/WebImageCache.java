package view.image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

/**
 * 网络图片缓存到本地（本地磁盘，本地内存(软引用)）
 * 
 * @author lijian
 * @date 2017-7-23 下午11:14:09
 */
public class WebImageCache {
	private static final String DISK_CACHE_PATH = "/web_image_cache/";

	/** 内存缓存 */
	private ConcurrentHashMap<String, SoftReference<Bitmap>> memoryCache;
	/** 磁盘缓存路径 */
	private String diskCachePath;
	/** 磁盘缓存是否可用 */
	private boolean diskCacheEnabled = false;
	/** 写线程 */
	private ExecutorService writeThread;

	public WebImageCache(Context context) {
		// 创建内存缓存仓库
		memoryCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

		// 创建磁盘缓存仓库
		Context appContext = context.getApplicationContext();
		diskCachePath = appContext.getCacheDir().getAbsolutePath()
				+ DISK_CACHE_PATH;

		// 设置图像的读取任务线程池
		writeThread = Executors.newSingleThreadExecutor();
	}

	/**
	 * 从缓存中获取图片(先读取内存缓存，否则磁盘缓存)
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap get(final String url) {
		Bitmap bitmap = null;

		// 检查内存中的image
		bitmap = getBitmapFromMemory(url);

		// 检查本地缓存磁盘中的image
		if (bitmap == null) {
			bitmap = getBitmapFromDisk(url);

			// 顺便将Bitmap写入内存缓存(方便下次快速读取)
			if (bitmap != null) {
				cacheBitmapToMemory(url, bitmap);
			}
		}

		return bitmap;
	}

	/**
	 * 将图片添加到缓存仓库中(内存缓存仓库，本地磁盘缓存仓库)
	 * 
	 * @param url
	 * @param bitmap
	 */
	public void put(String url, Bitmap bitmap) {
		cacheBitmapToMemory(url, bitmap);
		cacheBitmapToDisk(url, bitmap);
	}

	/**
	 * 从缓存中移除图片
	 * 
	 * @param url
	 */
	public void remove(String url) {
		// 从内存中移除
		memoryCache.remove(getCacheKey(url));

		// 从本地磁盘中移除
		File f = new File(diskCachePath, getCacheKey(url));
		if (f.exists() && f.isFile()) {
			f.delete();
		}
	}

	/**
	 * 清空本地缓存
	 */
	public void clear() {
		// 从内存缓存中删除所有内容
		memoryCache.clear();

		// 从文件缓存中删除所有内容
		File cachedFileDir = new File(diskCachePath);
		if (cachedFileDir.exists() && cachedFileDir.isDirectory()) {
			File[] cachedFiles = cachedFileDir.listFiles();
			for (File f : cachedFiles) {
				if (f.exists() && f.isFile()) {
					f.delete();
				}
			}
		}
	}

	private void cacheBitmapToMemory(final String url, final Bitmap bitmap) {
		memoryCache.put(getCacheKey(url), new SoftReference<Bitmap>(bitmap));
	}

	private void cacheBitmapToDisk(final String url, final Bitmap bitmap) {
		writeThread.execute(new Runnable() {
			@Override
			public void run() {
				if (diskCacheEnabled) {
					BufferedOutputStream ostream = null;
					try {
						ostream = new BufferedOutputStream(
								new FileOutputStream(new File(diskCachePath,
										getCacheKey(url))), 2 * 1024);
						bitmap.compress(CompressFormat.PNG, 100, ostream);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally {
						if (ostream != null) {
							try {
								ostream.flush();
								ostream.close();
								ostream = null;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
	}

	private Bitmap getBitmapFromMemory(String url) {
		Bitmap bitmap = null;
		SoftReference<Bitmap> softRef = memoryCache.get(getCacheKey(url));
		if (softRef != null) {
			bitmap = softRef.get();
		}

		return bitmap;
	}

	private Bitmap getBitmapFromDisk(String url) {
		Bitmap bitmap = null;
		if (diskCacheEnabled) {
			String filePath = getFilePath(url);
			File file = new File(filePath);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(filePath);
			}
		}
		return bitmap;
	}

	private String getFilePath(String url) {
		return diskCachePath + getCacheKey(url);
	}

	private String getCacheKey(String url) {
		if (url == null) {
			throw new RuntimeException("Null url passed in");
		} else {
			return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
		}
	}
}
