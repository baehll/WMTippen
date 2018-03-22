package com.tippen.fama.wmtippen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DEB681G on 19.03.2018.
 */

public class MatchArrayAdapter extends ArrayAdapter<Tipp> {
    private int layoutResource;
    private LayoutInflater vi;

    public MatchArrayAdapter(@NonNull Context context, int resource, @NonNull List<Tipp> tipps) {
        super(context, resource, tipps);
        this.layoutResource = resource;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        //Object obj = getItem(position);
        Tipp tipp = getItem(position);

        TextView matchNameView = (TextView) view.findViewById(R.id.list_item_match_name_text_view);
        EditText editText_tip1 = (EditText) view.findViewById(R.id.list_item_match_tip1_edit_text);
        EditText editText_tip2 = (EditText) view.findViewById(R.id.list_item_match_tip2_edit_text);

        matchNameView.setText(tipp.getMatch().getMatchName());
        editText_tip1.setText("");
        editText_tip2.setText("");

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
