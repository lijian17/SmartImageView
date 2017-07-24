package view.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

/**
 * 聪明的图片任务
 * 
 * @author lijian
 * @date 2017-7-23 下午10:27:29
 */
public class SmartImageTask implements Runnable {
	/** Bitmap准备 */
	private static final int BITMAP_READY = 0;

	/** 注销 */
	private boolean cancelled = false;
	private OnCompleteHandler onCompleteHandler;
	private SmartImage image;
	private Context context;

	public static class OnCompleteHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Bitmap bitmap = (Bitmap) msg.obj;
			onComplete(bitmap);
		}

		/**
		 * 完成
		 * 
		 * @param bitmap
		 */
		public void onComplete(Bitmap bitmap) {
		}
	}

	/**
	 * 图片设置到ImageView完成时的监听
	 * 
	 * @author lijian-pc
	 * @date 2017-7-24 下午5:21:22
	 */
	public abstract static class OnCompleteListener {
		/**
		 * 图片设置到ImageView完成时的回调
		 */
		public abstract void onComplete();

		/**
		 * 在图像加载到位图的简便方法。重写此方法以获得位图添加重载实现的句柄，使其向后兼容以前的版本。
		 * 
		 * @param bitmap
		 */
		public void onComplete(Bitmap bitmap) {
			onComplete();
		}
	}

	public SmartImageTask(Context context, SmartImage image) {
		this.context = context;
		this.image = image;
	}

	@Override
	public void run() {
		if (image != null) {
			complete(image.getBitmap(context));
			context = null;
		}
	}

	public void setOnCompleteHandler(OnCompleteHandler handler) {
		this.onCompleteHandler = handler;
	}

	public void cancel() {
		cancelled = true;
	}

	public void complete(Bitmap bitmap) {
		if (onCompleteHandler != null && !cancelled) {
			onCompleteHandler.sendMessage(onCompleteHandler.obtainMessage(
					BITMAP_READY, bitmap));
		}
	}
}
