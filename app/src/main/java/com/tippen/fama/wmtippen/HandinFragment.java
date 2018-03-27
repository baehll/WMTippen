package com.tippen.fama.wmtippen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by DEB681G on 19.03.2018.
 */

public class HandinFragment extends Fragment {
    private ArrayList<Match> matchList;
    private ArrayList<Tipp> tippList;
    private Player player;
    private MatchArrayAdapter matchArrayAdapter;
    private DatabaseHelper db;
    private static String LOGTAG = "HandinFragment";

    public HandinFragment(){
        this.player = new Player();
    }

    private ArrayList<Tipp> initList(){
        db = new DatabaseHelper(getContext());
        matchList = (ArrayList<Match>) db.parseMatches(2,1);

        tippList = new ArrayList<>();

        for (int i=0; i < matchList.size();i++) {
            tippList.add(new Tipp(matchList.get(i)));
        }

        return tippList;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_handin, viewGroup, false);
        player.setTippList(initList());

        matchArrayAdapter = new MatchArrayAdapter(getActivity(), R.layout.list_item_match, player.getTippList());

        final ListView groupView = (ListView) rootView.findViewById(R.id.list_view_group);
        groupView.setDivider(getActivity().getDrawable(R.drawable.list_view_divider));
        groupView.setAdapter(matchArrayAdapter);
        groupView.setItemsCanFocus(true);

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
        DatabaseHelper db = new DatabaseHelper(getContext());
        //Prüfung, ob der Spieler schon in TABLE_PLAYERS schon vorhanden ist,
        int playerID = db.getPlayerID(player.getPlayerName());
        if ( playerID == -1){
            playerID = db.insertPlayer(player.getPlayerName());
        }
        player.setPlayerID(playerID);
        Log.e(LOGTAG, "playerID = " + player.getPlayerID());
        //Überführt die Spielertipps in die TABLE_PLAYER_RESULTS
        db.insertOrUpdatePlayerTipps(player);

        //Spielertipps und tatsächliche Tore in Arrays für die Berechnung des Spieler Scores
        int[][] playerResults = new int[player.getTippList().size()][2];
        int[][] matchResults = new int[player.getTippList().size()][2];

        //Iteriert durch alle Spielertipps
        for (int i = 0; i < player.getTippList().size(); i++) {
            playerResults[i] = db.parsePlayerTipps(player.getTippList().get(i).getMatch().getMatchId(), player.getPlayerID());
            matchResults[i] = db.parseMatchResults(player.getTippList().get(i).getMatch().getMatchId());
        }

        //Tatsächliche Berechnung des Spielerergebnisses wird an die TABLE_PLAYERS zurückgegeben.
        ScoreCalculator sc = new ScoreCalculator(playerResults, matchResults);
        db.updatePlayerScore(player.getPlayerID(), sc.start());
    }


}
