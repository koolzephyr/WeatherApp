package com.zephyr.weatherforecast;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zephyr.weatherforecast.model.WeatherForecast;

import java.util.List;

/**
 * Created by Zephyr on 1/3/2015.
 */
public class ListViewAdapter extends ArrayAdapter<WeatherForecast> {
    Context context;
    int resource;
    List<WeatherForecast> objects;

    public ListViewAdapter(Context context, int resource, List<WeatherForecast> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource=resource;
        this.objects=objects;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView dayView;
        TextView descView;
        TextView maxView;
        TextView minView;
        TextView max;
        TextView min;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View row = convertView;


        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            row= inflater.inflate(resource,parent,false);

            holder = new ViewHolder();

            holder.dayView = (TextView) row.findViewById(R.id.dateView);
            holder.descView = (TextView) row.findViewById(R.id.descriptionView);
            holder.maxView = (TextView) row.findViewById(R.id.maxTempView);
            holder.minView = (TextView) row.findViewById(R.id.minTempView);
            holder.imageView = (ImageView) row.findViewById(R.id.weatherIcon);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        WeatherForecast forecast = getItem(position);

        holder.dayView.setText(forecast.getDate());
        holder.descView.setText(forecast.getDescription());
        holder.maxView.setText(forecast.getMaxTemp());
        holder.minView.setText(forecast.getMinTemp());
        holder.imageView.setImageResource(forecast.getIcon());

        return row;
    }
}

