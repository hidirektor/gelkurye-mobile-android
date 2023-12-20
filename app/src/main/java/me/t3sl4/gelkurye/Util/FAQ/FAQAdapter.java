package me.t3sl4.gelkurye.Util.FAQ;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;

public class FAQAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FAQ> faqList;

    public FAQAdapter(Context context, ArrayList<FAQ> faqList) {
        this.context = context;
        this.faqList = faqList;
    }

    @Override
    public int getCount() {
        return faqList.size();
    }

    @Override
    public Object getItem(int position) {
        return faqList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.orders_item_layout, parent, false);
            holder = new ViewHolder();
            holder.questionOrder = convertView.findViewById(R.id.questionOrder);
            holder.question = convertView.findViewById(R.id.questionEditText);
            holder.answer = convertView.findViewById(R.id.answerText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FAQ faq = faqList.get(position);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.faq_first_ikon);
        holder.questionOrder.setImageDrawable(drawable);
        holder.question.setHint(faq.getQuestion());
        holder.answer.setText(faq.getAnswer());

        return convertView;
    }

    private static class ViewHolder {
        ImageView questionOrder;
        EditText question;
        TextView answer;
    }
}
