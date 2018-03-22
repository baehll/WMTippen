package com.tippen.fama.wmtippen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by DEB681G on 19.03.2018.
 */

public class HandinFragment extends Fragment {
    private ArrayList<Match> matchList;
    private ArrayList<Tipp> tippList;
    private Player player;
    private MatchArrayAdapter matchArrayAdapter;

    public HandinFragment(){
        this.player = new Player();
    }

    private void initList(){
        matchList = new ArrayList<Match>();

        //Später durch die Datenbank abgelöst (matchlist)

        //matchList.add(new Separator("Gruppe A"));
        matchList.add(new Match("A", "Deutschland", "Frankreich", 0));
        matchList.add(new Match("A", "Uno", "Tres", 1));
        matchList.add(new Match("A", "Uno", "Quatro", 2));
        matchList.add(new Match("A", "Dos", "Tres", 3));
        matchList.add(new Match("A", "Dos", "Quatro", 4));
        matchList.add(new Match("A", "Tres", "Quatro", 5));
        //matchList.add(new Separator("Gruppe B"));
        matchList.add(new Match("B", "Uno", "Tres", 1));
        matchList.add(new Match("B", "Uno", "Quatro", 2));
        matchList.add(new Match("B", "Dos", "Tres", 3));
        matchList.add(new Match("B", "Dos", "Quatro", 4));
        matchList.add(new Match("B", "Tres", "Quatro", 5));

        tippList = new ArrayList<Tipp>();

        for (int i=0; i < matchList.size();i++) {
            tippList.add(new Tipp(matchList.get(i), 0, 0));
        }

        player.setTippList(tippList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_handin, viewGroup, false);
        initList();

        matchArrayAdapter = new MatchArrayAdapter(getActivity(), R.layout.list_item_match_name, player.getTippList());

        final ListView groupView = (ListView) rootView.findViewById(R.id.list_view_group);
        groupView.setAdapter(matchArrayAdapter);

        EditText playerNameText = (EditText) rootView.findViewById(R.id.player_name);
        Button buttonSubmit = (Button) rootView.findViewById(R.id.button_submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClickSubmit(view);
            }
        });



        playerNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                player.setPlayerName(editable.toString());
            }
        });

        return rootView;
    }

    public void onClickSubmit(View view){
        Toast.makeText(view.getContext(), tippList.get(0).getTipp1(), Toast.LENGTH_SHORT).show();
    }


}
