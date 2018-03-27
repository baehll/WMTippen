package com.tippen.fama.wmtippen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DEB681J on 23.03.2018.
 */

public class OverviewFragment extends Fragment {

    private OverviewArrayAdapter mOverviewAdapter;

    public OverviewFragment() {
    }

    private List<Player> initPlayerList(){
        DatabaseHelper db = new DatabaseHelper(getContext());
        return db.parsePlayers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);

        List<Player> overview_list = initPlayerList(); // liste aus db besorgen;
        mOverviewAdapter = new OverviewArrayAdapter(getActivity(), R.layout.list_item_overview, overview_list);

        TextView textViewRank = (TextView) rootView.findViewById(R.id.text_view_rank);
        TextView textViewPlayerName = (TextView) rootView.findViewById(R.id.text_view_name);
        TextView textViewScore = (TextView) rootView.findViewById(R.id.text_view_score);

        ListView overviewListView = (ListView) rootView.findViewById(R.id.list_view_overview);
        overviewListView.setAdapter(mOverviewAdapter);

        return rootView;
    }
}
