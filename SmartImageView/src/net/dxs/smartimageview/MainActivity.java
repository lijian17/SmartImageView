package net.dxs.smartimageview;

import view.image.SmartImageTask.OnCompleteListener;
import view.image.SmartImageView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		SmartImageView smartImageView = new SmartImageView(this);
		smartImageView
				.setImageUrl(
						"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",
						new OnCompleteListener() {

							@Override
							public void onComplete() {
								Log.d("TAG", "图片下载成功");
							}
						});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
