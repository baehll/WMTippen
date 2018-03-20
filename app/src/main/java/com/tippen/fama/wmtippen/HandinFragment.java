package com.tippen.fama.wmtippen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DEB681G on 19.03.2018.
 */

public class HandinFragment extends Fragment {

    public HandinFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_handin, viewGroup, false);

        Match testMatch = new Match("A", "Deutschland", "Frankreich");
        Match testMatch2 = new Match("A", "Uno", "Tres");
        Match testMatch3 = new Match("A", "Uno", "Quatro");
        Match testMatch4 = new Match("A", "Dos", "Tres");
        Match testMatch5 = new Match("A", "Dos", "Quatro");
        Match testMatch6 = new Match("A", "Tres", "Quatro");


        Match[] matchArray = {testMatch, testMatch2, testMatch3, testMatch4, testMatch5, testMatch6};

        List<Match> matchList = new ArrayList<>(Arrays.asList(matchArray));
        MatchArrayAdapter matchArrayAdapter = new MatchArrayAdapter(getActivity(), R.layout.list_item_match_name, matchList);

        ListView groupView = (ListView) rootView.findViewById(R.id.list_view_group);
        groupView.setAdapter(matchArrayAdapter);

        return rootView;
    }
}
