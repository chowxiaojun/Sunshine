package com.xiroid.sunshine.app.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiroid.sunshine.app.fragment.ForecastFragment;
import com.xiroid.sunshine.app.R;
import com.xiroid.sunshine.app.Utility;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {

    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FUTURE_DAY = 1;
    private boolean mUseTodayLayout = true;

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;
    }
    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = (viewType == VIEW_TYPE_TODAY ? R.layout.list_item_forecast_today : R.layout.list_item_forecast);
        View rootView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        rootView.setTag(new ViewHolder(rootView));
        return rootView;
    }

    /**
     * 关于ViewHolder模式：http://developer.android.com/training/improving-layouts/smooth-scrolling.html
     * 设置列表项
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Read weather icon ID from cursor
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID);
        int viewType = getItemViewType(cursor.getPosition());
        if (viewType == VIEW_TYPE_TODAY) {
            int resId = Utility.getArtResourceForWeatherCondition(weatherId);
            viewHolder.iconView.setImageResource(resId);
        } else if (viewType == VIEW_TYPE_FUTURE_DAY) {
            int resId = Utility.getIconResourceForWeatherCondition(weatherId);
            viewHolder.iconView.setImageResource(resId);
        }

        long dateInMillis = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        viewHolder.dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        viewHolder.descriptionView.setText(description);

        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        viewHolder.highView.setText(Utility.formatTemperature(context, high));

        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        viewHolder.lowView.setText(Utility.formatTemperature(context, low));
    }

    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView highView;
        public final TextView lowView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            highView = (TextView) view.findViewById(R.id.list_item_high_textview);
            lowView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }
}
