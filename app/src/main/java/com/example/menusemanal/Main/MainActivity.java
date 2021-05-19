package com.example.menusemanal.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menusemanal.Adapters.JuevesAdapter;
import com.example.menusemanal.Adapters.LunesAdapter;
import com.example.menusemanal.Adapters.MartesAdapter;
import com.example.menusemanal.Adapters.MiercolesAdapter;
import com.example.menusemanal.Objeto.Comida;
import com.example.menusemanal.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    CollectionReference ComidaRef;
    TextInputEditText edtNombreComida,edtDia,edtCodigo;
    MaterialButton btnañadirComida;
    LunesAdapter Lunesadapter;
    com.example.menusemanal.Adapters.MartesAdapter MartesAdapter;
    com.example.menusemanal.Adapters.MiercolesAdapter MiercolesAdapter;
    com.example.menusemanal.Adapters.JuevesAdapter JuevesAdapter;
    TextView txtNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();
        ComidaRef = firestore.collection("Lunes");
        edtNombreComida = findViewById(R.id.edtNombreComida);
        edtDia = findViewById(R.id.edtDia);
        edtCodigo = findViewById(R.id.edtCodigo);
        txtNom = findViewById(R.id.txtNom);
        btnañadirComida = findViewById(R.id.btnañadirComida);
        btnañadirComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        configRecyclerview();

    }
    private void validate() {

        String NombreComida = edtNombreComida.getText().toString().trim();
        String CodigoAdmin = edtCodigo.getText().toString().trim();
        String Dia = edtDia.getText().toString().trim();

        if (NombreComida.isEmpty()){
            edtNombreComida.setError("El nombre esta vacio");
            return;
        }else{
            if(CodigoAdmin.equalsIgnoreCase("George97")){
                if(Dia.isEmpty()){
                    edtDia.setError("Especifica el dia ejem. 'Lunes' ");
                    return;
                }else if(Dia.equalsIgnoreCase("Lunes")||Dia.equalsIgnoreCase("Miercoles") || Dia.equalsIgnoreCase("Miércoles")){
                    if(Dia.equals("lunes")){
                        Dia = "Lunes";
                    }
                    if(Dia.equals("miercoles") || Dia.equalsIgnoreCase("Miércoles")){
                        Dia = "Miercoles";
                    }
                    AñadriProducto(NombreComida,CodigoAdmin,Dia);
                }else if (Dia.equalsIgnoreCase("Martes")||Dia.equalsIgnoreCase("Jueves")||Dia.equalsIgnoreCase("Viernes")||Dia.equalsIgnoreCase("sabado")||Dia.equalsIgnoreCase("Domingo")){
                    edtDia.setError("No tiene permisos para este dia");
                    return;
                }else {
                    edtDia.setError("El dia no existe");
                    return;
                }
            }else if(CodigoAdmin.equalsIgnoreCase("Enzo97")){
                if(Dia.isEmpty()){
                    edtDia.setError("Especifica el dia ejem. 'Martes' ");
                    return;
                }else if(Dia.equalsIgnoreCase("Martes")||Dia.equalsIgnoreCase("Jueves")){
                    if(Dia.equals("martes")){
                        Dia = "Martes";
                    }
                    if(Dia.equals("jueves")){
                        Dia = "Jueves";
                    }
                    AñadriProducto(NombreComida,CodigoAdmin,Dia);
                }else if (Dia.equalsIgnoreCase("Lunes")||Dia.equalsIgnoreCase("Miercoles")||Dia.equalsIgnoreCase("Viernes")||Dia.equalsIgnoreCase("sabado")||Dia.equalsIgnoreCase("Domingo")){
                    edtDia.setError("No tiene permisos para este dia");
                    return;
                }else {
                    edtDia.setError("El dia no existe");
                    return;
                }
            }else if(CodigoAdmin.isEmpty()){
                edtCodigo.setError("Ingresa el codigo de administrador");
                return;
            }else{
                edtCodigo.setError("El codigo no existe");
                return;
            }
        }

    }
    private void AñadriProducto(String nombre,String CodigoAdmin, String Dia) {
        Map<String, Object> doc = new HashMap<>();
        String id = "COD"+nombre.toUpperCase();
        doc.put("id",id);
        doc.put("Nombre", nombre);

        firestore.collection(Dia).document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                edtNombreComida.setText("");
                edtCodigo.setText("");
                edtDia.setText("");
                Toast.makeText(MainActivity.this,"Elemento añadido", Toast.LENGTH_SHORT).show();
                // notificacionAvisoAdminChannel();
                // notificacionAvisoAdmin();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void configRecyclerview() {
        Query Lunesquery = firestore.collection("Lunes");
        FirestoreRecyclerOptions<Comida> Lunesoptions = new FirestoreRecyclerOptions.Builder<Comida>().setQuery(Lunesquery, Comida.class).build();

        Lunesadapter = new LunesAdapter(Lunesoptions);
        RecyclerView rvLunes = findViewById(R.id.rvLunes);
        rvLunes.setHasFixedSize(true);
        rvLunes.setLayoutManager(new LinearLayoutManager(this));
        rvLunes.setAdapter(Lunesadapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {

            Lunesadapter.borraritem(viewHolder.getAdapterPosition());
            Toast.makeText(MainActivity.this,"Elemento Eliminado", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(rvLunes);

        Query Miercolesquery = firestore.collection("Miercoles");
        FirestoreRecyclerOptions<Comida> Miercolesoptions = new FirestoreRecyclerOptions.Builder<Comida>().setQuery(Miercolesquery, Comida.class).build();

        MiercolesAdapter = new MiercolesAdapter(Miercolesoptions);
        RecyclerView rvMiercoles = findViewById(R.id.rvMiercoles);
        rvMiercoles.setHasFixedSize(true);
        rvMiercoles.setLayoutManager(new LinearLayoutManager(this));
        rvMiercoles.setAdapter(MiercolesAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {

                MiercolesAdapter.borraritem(viewHolder.getAdapterPosition());
                Toast.makeText(MainActivity.this,"Elemento Eliminado", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(rvMiercoles);

        Query Martesquery = firestore.collection("Martes");
        FirestoreRecyclerOptions<Comida> Martesoptions = new FirestoreRecyclerOptions.Builder<Comida>().setQuery(Martesquery, Comida.class).build();

        MartesAdapter = new MartesAdapter(Martesoptions);
        RecyclerView rvMartes = findViewById(R.id.rvMartes);
        rvMartes.setHasFixedSize(true);
        rvMartes.setLayoutManager(new LinearLayoutManager(this));
        rvMartes.setAdapter(MartesAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {

                MartesAdapter.borraritem(viewHolder.getAdapterPosition());
                Toast.makeText(MainActivity.this,"Elemento Eliminado", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(rvMartes);

        Query Juevesquery = firestore.collection("Jueves");
        FirestoreRecyclerOptions<Comida> Juevesoptions = new FirestoreRecyclerOptions.Builder<Comida>().setQuery(Juevesquery, Comida.class).build();

        JuevesAdapter = new JuevesAdapter(Juevesoptions);
        RecyclerView rvJueves = findViewById(R.id.rvJueves);
        rvJueves.setHasFixedSize(true);
        rvJueves.setLayoutManager(new LinearLayoutManager(this));
        rvJueves.setAdapter(JuevesAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {
                /*
                String CodigoAdmin = edtCodigo.getText().toString().trim();
                if (CodigoAdmin.isEmpty() || !CodigoAdmin.equalsIgnoreCase("enzo97")){
                    edtCodigo.setError("Para eliminar un elemento introdusca su codigo");
                    return;
                }*/
               // else {
                    JuevesAdapter.borraritem(viewHolder.getAdapterPosition());
                    Toast.makeText(MainActivity.this,"Elemento Eliminado", Toast.LENGTH_SHORT).show();
                    //edtCodigo.setText("");
                //}

            }
        }).attachToRecyclerView(rvJueves);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Lunesadapter.stopListening();
        MartesAdapter.stopListening();
        MiercolesAdapter.stopListening();
        JuevesAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Lunesadapter.startListening();
        MartesAdapter.startListening();
        MiercolesAdapter.startListening();
        JuevesAdapter.startListening();
    }

}