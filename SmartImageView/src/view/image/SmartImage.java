package view.image;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * 聪明的Image
 * 
 * @author lijian
 * @date 2017-7-23 下午10:35:01
 */
public interface SmartImage {
	/**
	 * 获取Bitmap
	 * 
	 * @param context
	 *            上下文
	 * @return
	 */
	Bitmap getBitmap(Context context);
}
