package view.image;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Bitmap图片
 * 
 * @author lijian-pc
 * @date 2017-7-25 上午10:17:03
 */
public class BitmapImage implements SmartImage {
	private Bitmap bitmap;

	public BitmapImage(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public Bitmap getBitmap(Context context) {
		return bitmap;
	}
}
