package com.tippen.fama.wmtippen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DEB681G on 19.03.2018.
 */

public class MatchArrayAdapter extends ArrayAdapter<Object> {
    private int layoutResource;
    private LayoutInflater vi;

    public MatchArrayAdapter(@NonNull Context context, int resource, @NonNull List<Object> objects) {
        super(context, resource, objects);
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

        Object obj = getItem(position);

        if (obj != null){
            if(obj.getClass() == Match.class){
                Match match = (Match) obj;
                view = vi.inflate(R.layout.list_item_match_name, null);
                TextView matchNameView = (TextView) view.findViewById(R.id.list_item_match_name_text_view);

                if(matchNameView != null){
                    matchNameView.setText(match.getMatch());
                }
            } else if (obj.getClass() == Separator.class){
                Separator sep = (Separator) obj;
                view = vi.inflate(R.layout.list_seperator_group, null);
                TextView sepView = (TextView) view.findViewById(R.id.seperator_group);
                sepView.setText(sep.getTitle());
            }
        }

        return view;
    }

}
