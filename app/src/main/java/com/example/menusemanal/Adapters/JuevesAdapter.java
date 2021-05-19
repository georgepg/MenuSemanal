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

public class JuevesAdapter extends FirestoreRecyclerAdapter<Comida, JuevesAdapter.JuevesHolder> {

    public JuevesAdapter( FirestoreRecyclerOptions<Comida> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder( JuevesAdapter.JuevesHolder holder, int position, Comida model) {
        holder.nombre.setText(model.getNombre());
    }

    @Override
    public JuevesHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comida_element, viewGroup, false);
        return new JuevesHolder(v);
    }

    public void borraritem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class JuevesHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        public JuevesHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNomComida);
        }
    }
}
