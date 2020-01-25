package com.example.aplicacion01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Network;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.aplicacion01.R;
import com.example.aplicacion01.models.Contacto;
import java.util.List;
import java.util.Queue;

public class ContactoAdaptador extends ArrayAdapter<Contacto> {
    Context context;
    ImageLoader queue;
    private class ViewHolder {
        TextView phone;
        TextView nickname;
        NetworkImageView image;

        private ViewHolder() {
        }
    }
    public ContactoAdaptador(Context context, List<Contacto> items, ImageLoader _queue){
        super(context, 0, items);
        this.context = context;
        this.queue = _queue;

    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        final Contacto rowItem = (Contacto) getItem(position);
        LayoutInflater mInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.contacto_item, null);
            holder = new ViewHolder();
            holder.image = (NetworkImageView)convertView.findViewById(R.id.image);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.phone.setText(rowItem.phone);
        holder.nickname.setText(rowItem.nickname);
        /*if ( rowItem.getSmallImage() != null ) { //<----- El modelo debe poseer esta function
            holder.image.setImageUrl(rowItem.getSmallImage(), queue);
        }*/
        return convertView;
    }
}