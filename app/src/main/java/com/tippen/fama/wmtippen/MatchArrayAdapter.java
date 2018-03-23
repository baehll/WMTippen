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
import android.widget.Toast;

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

    public int getCount() {
        return tipps.size();
    }

    @Override
    public Tipp getItem(int position) {
        return tipps.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        View view = convertView;

        TextView matchNameView;
        EditText editText_tip1;
        EditText editText_tip2;

        if (view == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            matchNameView = (TextView) view.findViewById(R.id.list_item_match_name_text_view);
            editText_tip1 = (EditText) view.findViewById(R.id.list_item_match_tip1_edit_text);
            editText_tip2 = (EditText) view.findViewById(R.id.list_item_match_tip2_edit_text);

            holder.matchTitle = matchNameView;
            holder.tip1 = editText_tip1;
            holder.tip2 = editText_tip2;

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Log.e(LOGTAG, "tagPosition (nicht textWatcher " + position);

        holder.matchTitle.setText(getItem(position).getMatch().getMatchName());
        holder.tip1.setText(String.valueOf(getItem(position).getTipp1()));
        holder.tip2.setText(String.valueOf(getItem(position).getTipp2()));
        //holder.tip1.setText(getItem(position).getTipp1());
        //holder.tip2.setText(getItem(position).getTipp2());

        holder.tip1.setTag(position);
        holder.tip2.setTag(position);

        holder.tip1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int pos = (Integer) holder.tip1.getTag();
                Log.e(LOGTAG, "tagPosition (nicht textWatcher " + pos + " tip1 tag");
                tipps.get(pos).setTipp1(Integer.parseInt(!editable.toString().equals("") ? editable.toString() : "0"));
            }
        });

        holder.tip2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int pos = (Integer) holder.tip2.getTag();
                Log.e(LOGTAG, "tagPosition (nicht textWatcher " + pos + " tip2 tag");
                tipps.get(pos).setTipp2(Integer.parseInt(!editable.toString().equals("") ? editable.toString() : "0"));
            }
        });

        Log.e(LOGTAG, "tipp nr" + position + " tipp1 " + tipps.get(position).getTipp1() + " tipp2 " + tipps.get(position).getTipp2());
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

    static class ViewHolder { EditText tip1; EditText tip2; TextView matchTitle; }
}

