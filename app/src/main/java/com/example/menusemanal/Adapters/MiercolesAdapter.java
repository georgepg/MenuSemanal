package com.example.menusemanal.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.menusemanal.Objeto.Comida;
import com.example.menusemanal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MiercolesAdapter extends FirestoreRecyclerAdapter<Comida, MiercolesAdapter.MiercolesHolder> {

    public MiercolesAdapter( FirestoreRecyclerOptions<Comida> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder( MiercolesAdapter.MiercolesHolder holder, int position, Comida model) {
        holder.nombre.setText(model.getNombre());
    }

    @Override
    public MiercolesHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comida_element, parent, false);
        return new MiercolesHolder(v);
    }
    public void borraritem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class MiercolesHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        public MiercolesHolder( View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNomComida);
        }
    }

}
