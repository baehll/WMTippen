package com.tippen.fama.wmtippen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

public class MatchArrayAdapter extends ArrayAdapter<Tipp> {

    private int layoutResource;
    private LayoutInflater vi;
    private static final String LOGTAG = "MatchArrayAdapter";
    private ArrayList<Tipp> tipps;

    public MatchArrayAdapter(@NonNull Context context, int resource, @NonNull List<Tipp> tipps) {
        super(context, resource, tipps);
        this.tipps = (ArrayList<Tipp>) tipps;
        this.layoutResource = resource;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        View view = convertView;

        if (view == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        //Log.e(LOGTAG, "getView " + position + " convertView " + view.toString());

        final Tipp tipp = getItem(position);

        TextView matchNameView = (TextView) view.findViewById(R.id.list_item_match_name_text_view);
        EditText editText_tip1 = (EditText) view.findViewById(R.id.list_item_match_tip1_edit_text);
        EditText editText_tip2 = (EditText) view.findViewById(R.id.list_item_match_tip2_edit_text);

        matchNameView.setText(tipp.getMatch().getMatchName());
        editText_tip1.setText(String.valueOf(tipp.getTipp1()));
        editText_tip2.setText(String.valueOf(tipp.getTipp2()));

        editText_tip1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tipps.get(position).setTipp1(!editable.toString().equals("") ? Integer.parseInt(editable.toString()): 0);
            }
        });


        editText_tip2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tipps.get(position).setTipp2(!editable.toString().equals("") ? Integer.parseInt(editable.toString()): 0);
            }
        });
        //Log.e(LOGTAG, "tipp nr" + position + " tipp1 " + tipp.getTipp1() + " tipp2 " + tipp.getTipp2());
        //tipp.setTipp1(Integer.parseInt(editText_tip1.getText().toString()));
        //tipp.setTipp2(Integer.parseInt(editText_tip2.getText().toString()));
        /*if (obj != null){
            if(obj.getClass() == Match.class){
                Match match = (Match) obj;
                view = vi.inflate(R.layout.list_item_match_name, null);
                TextView matchNameView = (TextView) view.findViewById(R.id.list_item_match_name_text_view);

                if(matchNameView != null){
                    matchNameView.setText(match.getMatchName());
                }
            } else if (obj.getClass() == Separator.class){
                Separator sep = (Separator) obj;
                view = vi.inflate(R.layout.list_seperator_group, null);
                TextView sepView = (TextView) view.findViewById(R.id.seperator_group);
                sepView.setText(sep.getTitle());
            }
        }*/

        return view;
    }
}
