package com.tippen.fama.wmtippen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DEB681G on 19.03.2018.
 */

public class OverviewArrayAdapter extends ArrayAdapter<Player> {

    private int layoutResource;
    private static final String LOGTAG = "OverviewArrayAdapter";

    public OverviewArrayAdapter(@NonNull Context context, int resource, List<Player> players ) {
        super(context, resource, players);
        this.layoutResource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        View view = convertView;

        if (view == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        //Log.e(LOGTAG, "getView " + position + " convertView " + view.toString());

        final Player player = getItem(position);

        TextView playerRank = (TextView) view.findViewById(R.id.list_item_player_rank);
        TextView playerName = (TextView) view.findViewById(R.id.list_item_player_name);
        TextView playerScore = (TextView) view.findViewById(R.id.list_item_player_score);

        playerName.setText(player.getPlayerName());
        playerScore.setText(String.valueOf(player.getScore()));
        playerRank.setText(String.valueOf(position + 1));

        return view;
    }
}
