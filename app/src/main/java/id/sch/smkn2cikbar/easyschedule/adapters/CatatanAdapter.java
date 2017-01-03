package id.sch.smkn2cikbar.easyschedule.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import id.sch.smkn2cikbar.easyschedule.R;
import id.sch.smkn2cikbar.easyschedule.models.DaftarCatatanModel;

/**
 * Created by Robby Akbar on 30/12/16.
 */

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private ArrayList<DaftarCatatanModel> daftarCatatanModels;

    public CatatanAdapter(ArrayList<DaftarCatatanModel>catatanModels, OnItemClickListener onItemClickListener) {
        this.daftarCatatanModels = catatanModels;
        this.listener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catatan_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.click(daftarCatatanModels.get(position), listener);
        holder.tvId.setText(String.valueOf(daftarCatatanModels.get(position).getId()));
        holder.pelajaran.setText(daftarCatatanModels.get(position).getPelajaran());
        holder.tugas.setText(daftarCatatanModels.get(position).getTugas());
        holder.deadline.setText(daftarCatatanModels.get(position).getDeadline());
    }

    @Override
    public int getItemCount() {
        return daftarCatatanModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, pelajaran, tugas, deadline;
        public ViewHolder(View view){
            super(view);
            tvId = (TextView)view.findViewById(R.id.tv4);
            pelajaran = (TextView)view.findViewById(R.id.tv1);
            tugas = (TextView)view.findViewById(R.id.tv2);
            deadline = (TextView)view.findViewById(R.id.tv3);
        }
        public void click(final DaftarCatatanModel catatanModel, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(catatanModel);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(DaftarCatatanModel item);
    }

}
