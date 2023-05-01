package com.example.healthcare;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.healthcare.models.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchComentsAdapter extends BaseAdapter {
    int i=0;
    Context mContext;
    LayoutInflater inflater;
    List<Comment> commentList;
    ArrayList<Comment> commentArrayList;

    public SearchComentsAdapter(Context context, List<Comment> commentList) {
        mContext = context;
        this.commentList = commentList;
        inflater = LayoutInflater.from(mContext);
        this.commentArrayList = new ArrayList();
        this.commentArrayList.addAll(commentList);
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.commentrow,null);

        TextView commenttxt = convertView.findViewById(R.id.comment);
        TextView fullName = convertView.findViewById(R.id.sickness);
        TextView ID = convertView.findViewById(R.id.stdname);

        Comment comment = commentList.get(position);

        fullName.setText(comment.getSickness().trim()  );
        commenttxt.setText(comment.getComment() );
        ID.setText(comment.getStdName());
        return convertView;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        commentList.clear();
        if (charText.length()==0){
            commentList.addAll(commentArrayList);
        }
        else {
            for (Comment p : commentArrayList){
                if (p.getStdName().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    commentList.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }

}

