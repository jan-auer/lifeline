package cyber.lifeline.service;

import android.app.Service;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;

import static android.view.WindowManager.LayoutParams;

/**
 * A Service which hosts a full screen view. The service has full control
 * over the view and can manipulate it's properties.
 *
 * The views are added as system overlays above all other activities and
 * made transparent. It is not possible to capture touch movements.
 *
 * @author Jan Michael Auer <jan.auer@me.com>
 * @version 1.0
 */
public abstract class FullscreenService extends Service {

	private WindowManager windowManager;
	private LayoutParams  layoutParams;

	/**
	 * Adds the given view to the global WindowManager and activates it.
	 *
	 * @param view
	 * 		The view to show.
	 */
	public void showView(View view) {
		getWindowManager().addView(view, getLayoutParams());
	}

	/**
	 * Hides the given view and removes it from the global WindowManager.
	 *
	 * @param view
	 * 		The view to hide.
	 */
	public void hideView(View view) {
		getWindowManager().removeView(view);
	}

	/**
	 * Fetches the global WindowManager as a system service.
	 *
	 * @return The WindowManager instance returned by getSystemService.
	 */
	protected WindowManager getWindowManager() {
		if (windowManager == null) {
			windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		}
		return windowManager;
	}

	/**
	 * Creates layout parameters for displaying the view. The view will be
	 * transparent and fullscreen.
	 *
	 * @return A LayoutParams instance containing all relevant parameters.
	 */
	protected LayoutParams getLayoutParams() {
		if (layoutParams == null) {
			layoutParams = new LayoutParams(
					LayoutParams.TYPE_SYSTEM_OVERLAY,
					LayoutParams.FLAG_LAYOUT_IN_SCREEN |
							LayoutParams.FLAG_NOT_FOCUSABLE |
							LayoutParams.FLAG_NOT_TOUCHABLE,
					PixelFormat.TRANSLUCENT);
		}

		return layoutParams;
	}
}
