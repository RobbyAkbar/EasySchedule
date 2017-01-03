package id.sch.smkn2cikbar.easyschedule.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.sch.smkn2cikbar.easyschedule.R;
import id.sch.smkn2cikbar.easyschedule.models.CardItemModel;

/**
 * Created by Robby Akbar on 14/12/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public List<CardItemModel> cardItems;
    CustomItemClickListener listener;

    public RecyclerAdapter(List<CardItemModel> cardItems, CustomItemClickListener listener){
        this.cardItems = cardItems;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, content;
        public ViewHolder (View itemView){
            super(itemView);
            this.title = (TextView)itemView.findViewById(R.id.card_title);
            this.content = (TextView)itemView.findViewById(R.id.card_content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, viewHolder.getPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(cardItems.get(position).title);
        holder.content.setText(cardItems.get(position).content);
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }
}
