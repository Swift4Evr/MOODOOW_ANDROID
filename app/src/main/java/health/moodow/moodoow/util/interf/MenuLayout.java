package health.moodow.moodoow.util.interf;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.zip.Inflater;

import health.moodow.moodoow.R;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class MenuLayout extends LinearLayout {

    public MenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.menu, null);

        addView(view);

    }
}
