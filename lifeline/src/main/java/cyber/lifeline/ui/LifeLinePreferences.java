package cyber.lifeline.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import cyber.lifeline.R;

/**
 * A preference fragment which hosts all LifeLine preferences.
 *
 * @author Jan Michael Auer <jan.auer@me.com>
 * @version 1.0
 */
public class LifeLinePreferences extends PreferenceFragment {

    public static final String PREF_ACTIVE = "active";
    public static final String PREF_AUTO_START = "autostart";
    public static final String PREF_COLOR = "color";
    public static final String PREF_ADVANCED_COLORS = "advanced_colors";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

    }

}
