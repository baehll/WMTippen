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
    private int[] Tip1Arr = new int[11];
    private int[] Tip2Arr = new int[11];
    private int layoutResource;
    private LayoutInflater vi;
    private static final String LOGTAG = "MatchArrayAdapter";
    private List<Tipp> tipps = new ArrayList<>();
    private List<String> stages = new ArrayList<>();

    public MatchArrayAdapter(@NonNull Context context, int resource, @NonNull List<Tipp> tipps) {
        super(context, resource, tipps);
        this.tipps = tipps;
        this.layoutResource = resource;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        stages = initStages();
    }

    private List<String> initStages(){
        DatabaseHelper db = new DatabaseHelper(getContext());
        return db.getStages();
    }
    public int getCount() {
        return tipps.size();
    }

    @Override
    public Tipp getItem(int position) {
        return tipps.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        View view = convertView;

        if (view == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            holder.matchTitle = (TextView) view.findViewById(R.id.list_item_match_name_text_view);
            holder.tip1 = (EditText) view.findViewById(R.id.list_item_match_tip1_edit_text);
            holder.tip2 = (EditText) view.findViewById(R.id.list_item_match_tip2_edit_text);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tip1.setTag(position);
        holder.tip2.setTag(position);

        /*
        if(position == 0 || getItem(position).getMatch().getGroup() != getItem(position - 1).getMatch().getGroup()){
            Separator sep = new Separator(" debug");
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.list_separator_group, null);
            TextView sepView = (TextView) view.findViewById(R.id.separator_group);;
            int i = getItem(position).getMatch().getStage();
            switch (i){
                case 0:
                    sep.setTitle(stages.get(i) + " " + getItem(position).getMatch().getGroup());
                    break;
                case 1:
                    sep.setTitle(stages.get(i));
                    break;
                case 2:
                    sep.setTitle(stages.get(i));
                    break;
                case 3:
                    sep.setTitle(stages.get(i));
                    break;
                case 4:
                    sep.setTitle(stages.get(i));
                    break;
                case 5:
                    sep.setTitle(stages.get(i));
                    break;
            }
            sepView.setText(sep.getTitle());
        }
        */



        holder.matchTitle.setText(tipps.get(position).getMatch().getMatchName());

        if(tipps.get(position).getTipp1() < 0){
            holder.tip1.setText("");
        } else {
            holder.tip1.setText(String.valueOf(tipps.get(position).getTipp1()));
        }

        if(tipps.get(position).getTipp2() < 0){
            holder.tip2.setText("");
        } else {
            holder.tip2.setText(String.valueOf(tipps.get(position).getTipp2()));
        }

        holder.tip1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                int pos = (Integer) holder.tip1.getTag();
                if(!s.toString().equals("")) tipps.get(pos).setTipp1(Integer.parseInt((s.length() == 0) ? "0": s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.tip2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                int pos = (Integer) holder.tip2.getTag();
                //Log.e(LOGTAG, "tagPosition (nicht textWatcher " + pos + " tip2 tag");
                if(!s.toString().equals("")) tipps.get(pos).setTipp2(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Log.e(LOGTAG, "tipp nr" + position + " tipp1 " + tipps.get(position).getTipp1() + " tipp2 " + tipps.get(position).getTipp2());
        //tipp.setTipp1(Integer.parseInt(editText_tip1.getText().toString()));
        //tipp.setTipp2(Integer.parseInt(editText_tip2.getText().toString()));
        /*if (obj != null){
            if(obj.getClass() == Match.class){
                Match match = (Match) obj;
                view = vi.inflate(R.layout.list_item_match, null);
                TextView matchNameView = (TextView) view.findViewById(R.id.list_item_match_name_text_view);

                if(matchNameView != null){
                    matchNameView.setText(match.getMatchName());
                }
            } else if (obj.getClass() == Separator.class){
                Separator sep = (Separator) obj;
                view = vi.inflate(R.layout.list_separator_group, null);
                TextView sepView = (TextView) view.findViewById(R.id.seperator_group);
                sepView.setText(sep.getTitle());
            }
        }*/

        return view;
    }

    static class ViewHolder { EditText tip1; EditText tip2; TextView matchTitle; }
}

