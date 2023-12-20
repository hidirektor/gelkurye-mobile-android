package me.t3sl4.gelkurye.Util.FAQ;

import android.os.Parcel;
import android.os.Parcelable;

public class FAQ implements Parcelable {

    private int order;
    private String question;
    private String answer;

    public FAQ(int order, String question, String answer) {
        this.order = order;
        this.question = question;
        this.answer = answer;
    }

    protected FAQ(Parcel in) {
        order = in.readInt();
        question = in.readString();
        answer = in.readString();
    }

    public static final Creator<FAQ> CREATOR = new Creator<FAQ>() {
        @Override
        public FAQ createFromParcel(Parcel in) {
            return new FAQ(in);
        }

        @Override
        public FAQ[] newArray(int size) {
            return new FAQ[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order);
        dest.writeString(question);
        dest.writeString(answer);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
