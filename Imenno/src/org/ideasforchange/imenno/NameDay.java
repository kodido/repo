package org.ideasforchange.imenno;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NameDay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_day);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.name_day, menu);
		return true;
	}

}
