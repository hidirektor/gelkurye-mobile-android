package me.t3sl4.gelkurye.Model.Order;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Order> orderList;

    public OrderAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.orders_item, parent, false);
            holder = new ViewHolder();
            holder.orderImage = convertView.findViewById(R.id.orderImage);
            holder.foodNameTextView = convertView.findViewById(R.id.foodNameTextView);
            holder.shopNameTextView = convertView.findViewById(R.id.shopNameTextView);
            holder.orderDateTextView = convertView.findViewById(R.id.orderDateTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Order order = orderList.get(position);
        //Glide.with(context).load(imageUrl).override(100, 100).into(holder.profilePhoto);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.testdata_yemek);
        holder.orderImage.setImageDrawable(drawable);
        holder.foodNameTextView.setText(order.getFoodName());
        holder.shopNameTextView.setText(order.getShopName());
        holder.orderDateTextView.setText(order.getOrderDate());

        return convertView;
    }

    private static class ViewHolder {
        ImageView orderImage;
        TextView foodNameTextView;
        TextView shopNameTextView;
        TextView orderDateTextView;
    }
}
