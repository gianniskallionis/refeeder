package com.example.refeeder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refeeder.data.entity.User;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private final List<User> users;

    public ProfileAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setPadding(32, 24, 32, 24);
        textView.setTextSize(18);
        return new ProfileViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        User user = users.get(position);

        String text = user.name + "\n"
                + user.weight + "kg · "
                + user.height + "cm · "
                + user.age;

        holder.textView.setText(text);

        holder.textView.setOnClickListener(v -> {
            if (onProfileClick != null) {
                onProfileClick.onProfileSelected(user);
            }
        });
    }

    public interface OnProfileClickListener {
        void onProfileSelected(User user);
    }

    private OnProfileClickListener onProfileClick;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        this.onProfileClick = listener;
    }





    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}