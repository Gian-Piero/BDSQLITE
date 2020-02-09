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

public class AdaptadorPrestamos extends RecyclerView.Adapter<AdaptadorPrestamos.myViewHolder> {

    Context context;
    List<Prestamo> mdata;
    OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onbtnPulsado(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdaptadorPrestamos(Context context, List<Prestamo> mdata){
        this.context = context;
        this.mdata = mdata;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.prestamo, parent, false);
        return new myViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        holder.txtAlumnoPrestamo.setText(mdata.get(position).getNombreAlumno());
        holder.txtTituloLibro.setText(mdata.get(position).getNombreLibro());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView txtAlumnoPrestamo;
        TextView txtTituloLibro;


        public myViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            txtAlumnoPrestamo = itemView.findViewById(R.id.txtAlumnoPrestamo);
            txtTituloLibro = itemView.findViewById(R.id.txtTituloLibro);

        }
    }
}
