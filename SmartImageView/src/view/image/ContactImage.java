package view.image;

import java.io.InputStream;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * 通过手机相册选择的图片
 * 
 * @author lijian-pc
 * @date 2017-7-24 下午6:44:37
 */
public class ContactImage implements SmartImage {
	private long contactId;

	public ContactImage(long contactId) {
		this.contactId = contactId;
	}

	@Override
	public Bitmap getBitmap(Context context) {
		Bitmap bitmap = null;
		ContentResolver contentResolver = context.getContentResolver();
		try {
			Uri uri = ContentUris.withAppendedId(
					ContactsContract.Contacts.CONTENT_URI, contactId);
			InputStream input = ContactsContract.Contacts
					.openContactPhotoInputStream(contentResolver, uri);
			if (input != null) {
				bitmap = BitmapFactory.decodeStream(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
