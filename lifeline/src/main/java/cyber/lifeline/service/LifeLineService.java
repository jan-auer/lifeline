package cyber.lifeline.service;

import android.content.*;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import cyber.lifeline.ui.LifeLinePreferences;
import cyber.lifeline.ui.LineView;

/**
 * The LifeLine service implementation.
 *
 * @author Jan Michael Auer <jan.auer@me.com>
 * @author Tobias Curth <tobiascurth@gmail.com>
 * @version 1.1
 */
public class LifeLineService extends FullscreenService
		implements SharedPreferences.OnSharedPreferenceChangeListener {

	private SharedPreferences preferences;
	private IBinder           serviceBinder;
	private BroadcastReceiver batteryReceiver;
	private LineView          lineView;

	//
	// Service Lifecycle   ----------------------------------------------------
	//

	@Override
	public void onCreate() {
		super.onCreate();

		Context application = getApplicationContext();
		preferences = PreferenceManager.getDefaultSharedPreferences(application);
		lineView = new LineView(this);

		loadPreferences();

		preferences.registerOnSharedPreferenceChangeListener(this);
		registerReceiver(getReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		showView(lineView);
    }


    @Override
	public void onDestroy() {
		hideView(lineView);
		unregisterReceiver(getReceiver());
		preferences.unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }



    //
	// Service Binder   -------------------------------------------------------
	//

	public class LifeLineBinder extends Binder {

		public LifeLineService getService() {
			return LifeLineService.this;
		}

	}

	public IBinder onBind(Intent intent) {
		return getBinder();
	}

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private IBinder getBinder() {
		if (serviceBinder == null) serviceBinder = new LifeLineBinder();
		return serviceBinder;
	}

	//
	// Battery Receiver   -----------------------------------------------------
	//

	/** Listens to changes of the battery status and updates the view. */
	private class BatteryReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
			lineView.setPercentage(level / (float) scale);
		}
	}

	/**
	 * Creates a singleton BatteryReceiver instance.
	 *
	 * @return The battery receiver.
	 */
	private BroadcastReceiver getReceiver() {
		if (batteryReceiver == null) batteryReceiver = new BatteryReceiver();
		return batteryReceiver;
	}

	//
	// Preferences & Event handling   -----------------------------------------
	//

	/** Loads all preferences and injects them to the view. */
	private void loadPreferences() {
		loadColorPreference();
	}

	/** Loads the line color and in */
	private void loadColorPreference() {
		String colorHash = preferences.getString(LifeLinePreferences.PREF_COLOR, "#000000");
		lineView.setColor(Color.parseColor(colorHash));
	}

	/**
	 * Updates properties of the view.
	 *
	 * @param preferences
	 * 		The application wide LifeLine preferences.
	 * @param key
	 * 		The Preference which has changed it's value.
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
		if (key.equals(LifeLinePreferences.PREF_COLOR)) {
			loadColorPreference();
		}
	}

}
