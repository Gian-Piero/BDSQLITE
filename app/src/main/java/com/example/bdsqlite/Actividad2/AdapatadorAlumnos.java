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

public class AdapatadorAlumnos extends RecyclerView.Adapter<AdapatadorAlumnos.myViewHolder> {


    Context context;
    List<Alumno> mdata;
    OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onbtnPulsado(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdapatadorAlumnos(Context context, List<Alumno> mdata){
        this.context = context;
        this.mdata = mdata;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.alumno, parent, false);
        return new myViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        holder.txtDniAlumno.setText(mdata.get(position).getDniAlumno());
        holder.txtNombreAlumno.setText(mdata.get(position).getNombreAlumno());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView txtDniAlumno;
        TextView txtNombreAlumno;
        Button btnBorrarAlumno;

        public myViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            txtDniAlumno = itemView.findViewById(R.id.txtDniAlumno);
            txtNombreAlumno = itemView.findViewById(R.id.txtNombreAlumno);
            btnBorrarAlumno = itemView.findViewById(R.id.btnBorrarAlumno);

            btnBorrarAlumno.setOnClickListener(new View.OnClickListener() {
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
