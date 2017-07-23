package view.image;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
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

	/**
	 * 通过URL设置图像助手
	 * 
	 * @param url
	 */
	public void setImageUrl(String url) {
		setImage(new WebImage(url));
	}

}
