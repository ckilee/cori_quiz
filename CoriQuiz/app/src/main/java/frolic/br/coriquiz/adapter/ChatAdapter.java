package frolic.br.coriquiz.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import frolic.br.coriquiz.R;
import frolic.br.coriquiz.model.Message;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Message> chatMessagesList;
    private String loggedUser;
    private int lastPosition = -1;
    private Context context;

    public ChatAdapter(List<Message> historyList, String loggedUser, Context context) {
        this.chatMessagesList = historyList;
        this.loggedUser = loggedUser;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return chatMessagesList.size();
    }

    @Override
    public void onBindViewHolder(final ChatViewHolder holder, int position) {
        Message chatMessage = chatMessagesList.get(position);

        if(chatMessage.getType() == Message.TYPE_MESSAGE) {

            String textToBeDisplayed = chatMessage.getUsername() + ":  " +chatMessage.getMessage();
            holder.tvName.setText(textToBeDisplayed);
            int textLength = textToBeDisplayed.length();
            //36 is the number of characteres per line
            int numberOfLines = textLength/36+2;

            ViewGroup.LayoutParams params = holder.tvName.getLayoutParams();
            final float scale = context.getResources().getDisplayMetrics().density;
            int pixels = (int) (20 * scale + 0.5f);
            //number of pixels needed to fit all message text
            pixels = pixels * numberOfLines;
            params.height = pixels;
            holder.tvName.setLayoutParams(params);
        } else {
            holder.tvName.setText(chatMessage.getMessage());
        }

        if(!loggedUser.equals(chatMessage.getUsername())) {
            holder.tvName.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.tvName.setBackgroundColor(Color.parseColor("#E1FFC7"));
        }

        setAnimation(holder.cardView, position);

    }

    private void setAnimation(CardView cardView, int position) {
        if(position >= lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);

            cardView.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.chat_card, viewGroup, false);

        return new ChatViewHolder(itemView);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        protected CardView cardView;
        protected TextView tvName;

        public ChatViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            tvName =  (TextView) v.findViewById(R.id.tvChat);
        }
    }
}
