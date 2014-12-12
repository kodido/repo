package org.ideasforchange.imenno;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ideasforchange.imenno.logic.Contact;
import org.ideasforchange.imenno.logic.NameDay;
import org.ideasforchange.imenno.logic.NameDaysDataHelper;
import org.ideasforchange.imenno.logic.NameDaysNotificator;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Contacts.Photo;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class NameDays extends Activity {

	private BaseAdapter adapter;

	private NameDaysDataHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_days);

		ListView list = (ListView) findViewById(R.id.nameDaysTodayList);

		adapter = new BaseAdapter() {

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				LayoutInflater inflater = getLayoutInflater();
				View itemLayout = inflater.inflate(R.layout.activity_name_day,
						parent, false);

				Contact contact = getContactsWithNameDayToday().get(position);

				TextView nameView = (TextView) itemLayout
						.findViewById(R.id.contactName);
				nameView.setText(contact.getName());

				QuickContactBadge badge = (QuickContactBadge) itemLayout
						.findViewById(R.id.quickbadge);
				Uri contactUri = Contacts.getLookupUri(contact.getId(),
						contact.getLookupKey());
				badge.assignContactUri(contactUri);

				if (contact.getThumbNailUri() != null) {
					/*
					 * Decodes the thumbnail file to a Bitmap.
					 */
					Bitmap thumbnail = loadContactPhotoThumbnail(contact
							.getThumbNailUri());
					/*
					 * Sets the image in the QuickContactBadge QuickContactBadge
					 * inherits from ImageView, so
					 */
					badge.setImageBitmap(thumbnail);
				} else {
					/* TODO: switch to a more adequate resource */
					badge.setImageResource(R.drawable.ic_launcher);
				}

				itemLayout.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// showContact(position);
					}

				});

				return itemLayout;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				return getContactsWithNameDayToday().size();
			}

		};

		list.setAdapter(adapter);

		/* TODO: find a better place to do this */
		scheduleNameDaysCheck();
	}

	protected Bitmap loadContactPhotoThumbnail(String thumbNailUri) {
		// Creates an asset file descriptor for the thumbnail file.
		AssetFileDescriptor afd = null;
		// try-catch block for file not found
		try {
			// Creates a holder for the URI.
			Uri thumbUri = buildThumbNailUri(thumbNailUri);

			/*
			 * Retrieves an AssetFileDescriptor object for the thumbnail URI
			 * using ContentResolver.openAssetFileDescriptor
			 */
			afd = this.getContentResolver().openAssetFileDescriptor(thumbUri,
					"r");
			/*
			 * Gets a file descriptor from the asset file descriptor. This
			 * object can be used across processes.
			 */
			FileDescriptor fileDescriptor = afd.getFileDescriptor();
			// Decode the photo file and return the result as a Bitmap
			// If the file descriptor is valid
			if (fileDescriptor != null) {
				// Decodes the bitmap
				return BitmapFactory.decodeFileDescriptor(fileDescriptor, null,
						null);
			}
			// If the file isn't found
		} catch (FileNotFoundException e) {
			/*
			 * Handle file not found errors
			 */
			// In all cases, close the asset file descriptor
		} finally {
			if (afd != null) {
				try {
					afd.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	private Uri buildThumbNailUri(String thumbNailUri) {
		Uri thumbUri;
		// If Android 3.0 or later
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Sets the URI from the incoming PHOTO_THUMBNAIL_URI
			thumbUri = Uri.parse(thumbNailUri);
		} else {
			// Prior to Android 3.0, constructs a photo Uri using _ID
			/*
			 * Creates a contact URI from the Contacts content URI incoming
			 * photoData (_ID)
			 */
			final Uri contactUri = Uri.withAppendedPath(Contacts.CONTENT_URI,
					thumbNailUri);
			/*
			 * Creates a photo URI by appending the content URI of
			 * Contacts.Photo.
			 */
			thumbUri = Uri
					.withAppendedPath(contactUri, Photo.CONTENT_DIRECTORY);
		}
		return thumbUri;
	}

	private void scheduleNameDaysCheck() {
		/* TODO: enter the time of check as setting */
		//schedule the check at 10:00 every day
		new NameDaysNotificator().scheduleCheck(this, 12, 00, 00);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.name_days, menu);
		return true;
	}

	private List<NameDay> getNameDaysToday() {
		if (helper == null)
			helper = new NameDaysDataHelper(this);
		return helper.getNameDaysToday();
	}

	private List<Contact> getContactsWithNameDayToday() {
		List<NameDay> nameDaysToday = getNameDaysToday();

		List<Contact> result = new ArrayList<Contact>();

		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cur.getCount() > 0)
			while (cur.moveToNext()) {
				long id = cur.getLong(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String lookupKey = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
				int thumbNailColumnIndex = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? cur
						.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)
						: cur.getColumnIndex(ContactsContract.Contacts._ID);
				String thumbNailUri = cur.getString(thumbNailColumnIndex);

				for (NameDay nameDay : nameDaysToday) {
					if (hasNameDay(name, nameDay)) {
						result.add(new Contact(id, name, lookupKey,
								thumbNailUri));
					}
				}
			}

		return result;
	}

	private boolean hasNameDay(String fullName, NameDay nameDay) {
		// \\s+ is regexp for whitespace, this checks if the first name equals the name in the name day
		for (String nameHavingNameDay : nameDay.getNames()) {
			if (fullName.split("\\s+")[0].equals(nameHavingNameDay))
				return true;
		}
		return false;
	}

}
