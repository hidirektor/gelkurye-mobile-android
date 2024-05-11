package me.t3sl4.gelkurye.Util.Model.Earning;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;

public class EarningAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Earning> earningList;

    public EarningAdapter(Context context, ArrayList<Earning> earningList) {
        this.context = context;
        this.earningList = earningList;
    }

    @Override
    public int getCount() {
        return earningList.size();
    }

    @Override
    public Object getItem(int position) {
        return earningList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.earning_item_layout, parent, false);
            holder = new ViewHolder();
            holder.transactionIDTextView = convertView.findViewById(R.id.transactionIDTextView);
            holder.transactionDateTextView = convertView.findViewById(R.id.transactionDateTextView);
            holder.transactionAmountButton = convertView.findViewById(R.id.transactionAmountButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Earning earning = earningList.get(position);
        holder.transactionIDTextView.setText(earning.getTransactionID());
        holder.transactionDateTextView.setText(earning.getTransactionDate());
        int textColor = 0;
        Drawable backgroundDrawable = null;
        String amountText;
        if(earning.isTransactionType()) {
            //Başarılı İşlem Demek
            backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.earning_success);
            textColor = ContextCompat.getColor(context, R.color.acceptStartColor);
            amountText = "+ " + String.valueOf(earning.getTransactionAmount()) + " ₺";
        } else {
            //Başarısız İşlem Demek
            backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.earning_failed);
            textColor = ContextCompat.getColor(context, R.color.declineStartColor);
            amountText = "- " + String.valueOf(earning.getTransactionAmount()) + " ₺";
        }
        holder.transactionAmountButton.setBackground(backgroundDrawable);
        holder.transactionAmountButton.setTextColor(textColor);
        holder.transactionAmountButton.setText(amountText);

        return convertView;
    }

    private static class ViewHolder {
        TextView transactionIDTextView;
        TextView transactionDateTextView;
        Button transactionAmountButton;
    }
}
