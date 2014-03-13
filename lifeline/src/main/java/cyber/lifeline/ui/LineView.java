package cyber.lifeline.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Draws a line with the specified properties at the very top of the view.
 *
 * @author Jan Michael Auer <jan.auer@me.com>
 * @version 1.0
 */
public class LineView extends View {

    private Paint paint;
    private float percentage;
    private boolean isCharging;
    private boolean advancedColors;
    private int color;

    public LineView(Context context) {
        super(context);

        setBackgroundColor(Color.argb(0, 0, 0, 0));
        setHapticFeedbackEnabled(false);

        paint = new Paint();
    }


    //
    // Drawing and Properties   -----------------------------------------------
    //

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();

        paint.setColor(color);

        if (advancedColors) {
            if (isCharging)
                paint.setColor(Color.parseColor("#669900"));
            else {
                if (percentage < .2)
                    paint.setColor(Color.parseColor("#CC0000"));
                else if (percentage < .35)
                    paint.setColor(Color.parseColor("#669900"));
            }
        }

        canvas.drawLine(0, 0, width * percentage, 0, paint);
    }


    /**
     * Sets the width of the line as a percentage.
     *
     * @param percentage A float from 0 to 1 specifying the width of the line.
     */
    public void setPercentage(float percentage) {
        if (percentage < 0) percentage = 0;
        if (percentage > 1) percentage = 1;
        this.percentage = percentage;

        invalidate();
    }

    /**
     * Sets the current battery status.
     * @param isCharging The new status.
     */
    public void setCharging(boolean isCharging) {
        this.isCharging = isCharging;
        invalidate();
    }

    /**
     * Sets the preference option of advancedColors.
     * @param advancedColors The preference option.
     */
    public void setAdvancedColors(boolean advancedColors) {
        this.advancedColors = advancedColors;
        invalidate();
    }

    /**
     * Sets the color of the line.
     *
     * @param color The new color in hexadecimal representation.
     */
    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

}
