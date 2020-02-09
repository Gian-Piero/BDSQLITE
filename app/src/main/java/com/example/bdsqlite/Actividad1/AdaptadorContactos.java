package com.example.bdsqlite.Actividad1;

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

public class AdaptadorContactos extends RecyclerView.Adapter<AdaptadorContactos.myViewHolder> {

    Context context;
    List<Contacto> mdata;
    OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onbtnPulsado(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdaptadorContactos(Context context, List<Contacto> mdata){
        this.context = context;
        this.mdata = mdata;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.contacto, parent, false);
        return new myViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        holder.txtNombre.setText(mdata.get(position).getNombre());
        holder.txtNumero.setText(mdata.get(position).getNumero());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombre;
        TextView txtNumero;
        Button btnBorrar;

        public myViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtNumero = itemView.findViewById(R.id.txtNumero);
            btnBorrar = itemView.findViewById(R.id.btnBorrar);

            btnBorrar.setOnClickListener(new View.OnClickListener() {
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
