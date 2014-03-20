package cyber.lifeline.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.Toast;
import cyber.lifeline.R;
import cyber.lifeline.service.LifeLineService;

/**
 * Shows the settings screen and starts or stops the LifeLine service.
 *
 * @author Jan Michael Auer <jan.auer@me.com>
 * @author Tobias Curth <tobiascurth@gmail.com>
 * @version 1.1
 */
public class SettingsActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

	private SharedPreferences preferences;
	private Intent            serviceIntent;

	//
	// Activity Lifecycle & User Interface   ----------------------------------
	//

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);

		// Display Settings

		preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		preferences.registerOnSharedPreferenceChangeListener(this);
		getFragmentManager()
				.beginTransaction()
				.replace(android.R.id.content, new LifeLinePreferences())
				.commit();

		// Display the Action Bar

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.settings_menu, menu);
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (preferences.getBoolean(LifeLinePreferences.PREF_ACTIVE, false)) {
			startService(getServiceIntent());
		}
	}

	//
	// Service Control   ------------------------------------------------------
	//

	/**
	 * Creates a service intent for starting and stopping the LifeLine service.
	 * While the service is running, the intent stays the same. Each time the
	 * service service is stopped a new intent is created.
	 *
	 * @return An android.content.Intent instance to start the LifeLineService.
	 */
	private Intent getServiceIntent() {
		if (serviceIntent == null) {
			serviceIntent = new Intent(getApplicationContext(), LifeLineService.class);
		}
		return serviceIntent;
	}

	/**
	 * Starts the LifeLine service. If the service is already running, nothing
	 * happens.
	 */
	public void startLifeLineService() {
		if (serviceIntent != null) return;
		startService(getServiceIntent());
	}

	/** Stops the LifeLine service, if it is running. */
	public void stopLifeLineService() {
		if (serviceIntent == null) return;
		stopService(getServiceIntent());
		serviceIntent = null;
	}

	/**
	 * Listens for changes of the `active` preference and starts or stops the
	 * LifeLine service when it has changed.
	 *
	 * @param preferences
	 * 		The application wide LifeLine preferences.
	 * @param key
	 * 		The Preference which has changed it's value.
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
		if (key.equals(LifeLinePreferences.PREF_ACTIVE)) {
			if (preferences.getBoolean(key, false)) {
				startLifeLineService();
			} else {
				stopLifeLineService();
			}
		}
	}

	//
	// Debugging   ------------------------------------------------------------
	//

	/**
	 * Displays a debug message visible on the target device.
	 *
	 * @param resourceId
	 * 		A string resource id to display.
	 */
	private void debug(int resourceId) {
		Toast.makeText(getApplicationContext(), resourceId, Toast.LENGTH_SHORT).show();
	}

}
