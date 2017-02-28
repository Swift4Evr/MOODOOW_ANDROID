package health.moodow.moodoow.util.interf;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import health.moodow.moodoow.R;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class TopBarLayout extends LinearLayout {

    public TopBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.top_bar, null);

        addView(view);

    }
}
