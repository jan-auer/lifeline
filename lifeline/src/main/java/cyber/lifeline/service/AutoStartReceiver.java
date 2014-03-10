package cyber.lifeline.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import cyber.lifeline.ui.LifeLinePreferences;

/**
 * Designed to listen to android.intent.action.BOOT_COMPLETED and start the
 * LifeLine service, if configured.
 *
 * @author Jan Michael Auer <jan.auer@me.com>
 * @version 1.0
 */
public class AutoStartReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		Context application = context.getApplicationContext();
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(application);

		boolean active = preferences.getBoolean(LifeLinePreferences.PREF_ACTIVE, false);
		boolean autoStart = preferences.getBoolean(LifeLinePreferences.PREF_AUTO_START, false);

		if (active && autoStart) {
			application.startService(new Intent(application, LifeLineService.class));
		}
	}

}
