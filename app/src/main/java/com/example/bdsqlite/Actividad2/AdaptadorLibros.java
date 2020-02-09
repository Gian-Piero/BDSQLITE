package com.example.bdsqlite.Actividad2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bdsqlite.R;

import java.util.List;

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.myViewHolder>{

    Context context;
    List<Libro> mdata;
    OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onbtnPulsado(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdaptadorLibros(Context context, List<Libro> mdata){
        this.context = context;
        this.mdata = mdata;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.libro, parent, false);
        return new myViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        holder.txtIsbnLibro.setText(mdata.get(position).getIsbn());
        holder.txtNombreLibro.setText(mdata.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombreLibro;
        TextView txtIsbnLibro;
        Button btnBorrarLibro;

        public myViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            txtNombreLibro = itemView.findViewById(R.id.txtIsbnLibro);
            txtIsbnLibro = itemView.findViewById(R.id.txtNombreLibro);
            btnBorrarLibro = itemView.findViewById(R.id.btnBorrarLibro);

            btnBorrarLibro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onbtnPulsado(position);
                        }
                    }
                }
            });

        }
    }
}
