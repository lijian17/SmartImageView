package view.image;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 聪明的ImageView
 * 
 * @author lijian
 * @date 2017-7-23 下午10:20:15
 */
public class SmartImageView extends ImageView {
	/** 加载线程数 */
	private static final int LOADING_THREADS = 4;
	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(LOADING_THREADS);

	/** 当前任务 */
	private SmartImageTask currentTask;

	public SmartImageView(Context context) {
		super(context);
	}

	public SmartImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SmartImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// ---通过网络URL设置Image---------------------------------------------
	/**
	 * 通过URL设置图像助手
	 * 
	 * @param url
	 *            网络图片地址
	 */
	public void setImageUrl(String url) {
		setImage(new WebImage(url));
	}

	/**
	 * 通过URL设置图片
	 * 
	 * @param url
	 *            网络图片地址
	 * @param completeListener
	 *            图片下载完成的监听器
	 */
	public void setImageUrl(String url,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), completeListener);
	}

	/**
	 * 通过URL设置图片
	 * 
	 * @param url
	 *            网络图片地址
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 */
	public void setImageUrl(String url, final Integer fallbackResource) {
		setImage(new WebImage(url), fallbackResource);
	}

	/**
	 * 通过URL设置图片
	 * 
	 * @param url
	 *            网络图片地址
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 * @param completeListener
	 *            图片下载完成的监听器
	 */
	public void setImageUrl(String url, final Integer fallbackResource,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), fallbackResource, completeListener);
	}

	/**
	 * 通过URL设置图片
	 * 
	 * @param url
	 *            网络图片地址
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 * @param loadingResource
	 *            通过本地图片资源ID设置（网络图片下载前，先显示该默认图片）
	 */
	public void setImageUrl(String url, final Integer fallbackResource,
			final Integer loadingResource) {
		setImage(new WebImage(url), fallbackResource, loadingResource);
	}

	/**
	 * 通过URL设置图片
	 * 
	 * @param url
	 *            网络图片地址
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 * @param loadingResource
	 *            通过本地图片资源ID设置（网络图片下载前，先显示该默认图片）
	 * @param completeListener
	 *            图片下载完成的监听器
	 */
	public void setImageUrl(String url, final Integer fallbackResource,
			final Integer loadingResource,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), fallbackResource, loadingResource,
				completeListener);
	}

	// ---通过手机相册图片ID设置Image---------------------------------------------
	/**
	 * 通过手机相册图片ID设置Image
	 * 
	 * @param contactId
	 *            相册图片
	 */
	public void setImageContact(long contactId) {
		setImage(new ContactImage(contactId));
	}

	/**
	 * 通过手机相册图片ID设置Image
	 * 
	 * @param contactId
	 *            相册图片
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 */
	public void setImageContact(long contactId, final Integer fallbackResource) {
		setImage(new ContactImage(contactId), fallbackResource);
	}

	/**
	 * 通过手机相册图片ID设置Image
	 * 
	 * @param contactId
	 *            相册图片
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 * @param loadingResource
	 *            通过本地图片资源ID设置（网络图片下载前，先显示该默认图片）
	 */
	public void setImageContact(long contactId, final Integer fallbackResource,
			final Integer loadingResource) {
		setImage(new ContactImage(contactId), fallbackResource,
				fallbackResource);
	}

	// ---使用SmartImage设置图片到ImageView---------------------------------------------
	/**
	 * 使用聪明的Image设置图像
	 * 
	 * @param image
	 *            SmartImage对象
	 */
	public void setImage(final SmartImage image) {
		setImage(image, null, null, null);
	}

	/**
	 * 使用聪明的Image设置图像
	 * 
	 * @param image
	 *            SmartImage对象
	 * @param completeListener
	 *            图片下载完成的监听器
	 */
	public void setImage(final SmartImage image,
			final SmartImageTask.OnCompleteListener completeListener) {
		setImage(image, null, null, completeListener);
	}

	/**
	 * 使用聪明的Image设置图像
	 * 
	 * @param image
	 *            SmartImage对象
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 */
	public void setImage(final SmartImage image, final Integer fallbackResource) {
		setImage(image, fallbackResource, fallbackResource, null);
	}

	/**
	 * 使用聪明的Image设置图像
	 * 
	 * @param image
	 *            SmartImage对象
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 * @param completeListener
	 *            图片下载完成的监听器
	 */
	public void setImage(final SmartImage image,
			final Integer fallbackResource,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(image, fallbackResource, fallbackResource, completeListener);
	}

	/**
	 * 使用聪明的Image设置图像
	 * 
	 * @param image
	 *            SmartImage对象
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 * @param loadingResource
	 *            通过本地图片资源ID设置（网络图片下载前，先显示该默认图片）
	 */
	public void setImage(final SmartImage image,
			final Integer fallbackResource, final Integer loadingResource) {
		setImage(image, fallbackResource, loadingResource, null);
	}

	/**
	 * 使用聪明的Image设置图像
	 * 
	 * @param image
	 *            SmartImage对象
	 * @param fallbackResource
	 *            设置备选图片（当网络图片加载失败时，显示默认图片）
	 * @param loadingResource
	 *            通过本地图片资源ID设置（网络图片下载前，先显示该默认图片）
	 * @param completeListener
	 *            图片下载完成的监听器
	 */
	public void setImage(final SmartImage image,
			final Integer fallbackResource, final Integer loadingResource,
			final SmartImageTask.OnCompleteListener completeListener) {
		// 通过本地图片资源ID设置（网络图片下载前，先显示该默认图片）
		if (loadingResource != null) {
			setImageResource(loadingResource);
		}

		// 取消此ImageView的所有现有任务
		if (currentTask != null) {
			currentTask.cancel();
			currentTask = null;
		}

		// 启动一个新任务
		currentTask = new SmartImageTask(getContext(), image);
		currentTask
				.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
					@Override
					public void onComplete(Bitmap bitmap) {
						if (bitmap != null) {
							setImageBitmap(bitmap);
						} else {
							// 设置备选图片（当网络图片加载失败时，显示默认图片）
							if (fallbackResource != null) {
								setImageResource(fallbackResource);
							}
						}

						if (completeListener != null) {
							completeListener.onComplete(bitmap);
						}
					}
				});

		// 执行网络图片下载任务
		threadPool.execute(currentTask);
	}

	/**
	 * 关闭线程池中所有运行任务
	 */
	public static void cancelAllTasks() {
		threadPool.shutdownNow();
		threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
	}
}
