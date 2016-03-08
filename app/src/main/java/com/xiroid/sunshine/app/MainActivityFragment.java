package com.xiroid.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        List<String> data = new ArrayList<String>();
        data.add("Today-Sunny-88/63");
        data.add("Tomorrow-Sunny-88/63");
        data.add("Weds-Sunny-88/63");
        data.add("Thurs-Sunny-88/63");
        data.add("Fri-Sunny-88/63");
        data.add("Sat-Sunny-88/63");
        data.add("Sun-Sunny-88/63");
        
        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.list_item_forecast,
                R.id.list_item_forecast_textview, data
        );
        
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);

        return rootView;
    }
}
