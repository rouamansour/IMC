package com.example.imc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText edPoids,edAge, edTaille;
    private Button btnAjouter,btnVider,btnEffacer;
    private ListView lstPersonne;
    private ArrayAdapter<Personne> adpP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        edPoids=findViewById(R.id.edPoids);
        edAge=findViewById(R.id.edAge);
        edTaille=findViewById(R.id.edTaille);
        btnAjouter=findViewById(R.id.btnAjouter);
        btnEffacer=findViewById(R.id.btnEffacer);
        btnVider=findViewById(R.id.btnVider);
        lstPersonne=findViewById(R.id.lstPersonne);
        adpP=new ArrayAdapter<Personne>(this, android.R.layout.simple_list_item_1);
        lstPersonne.setAdapter(adpP);
        ecouteur();
        //effacer();
    }

    public void ecouteur(){
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouter();
            }
        });
        btnVider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vider();
            }
        });
        btnEffacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effacer();
            }
        });
        lstPersonne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                afficher(position);
            }
        });
    }
    public void effacer(){
        edPoids.setText("");
        edAge.setText("");
        edTaille.setText("");
    }

    public void vider(){
        adpP.clear(); //vider tous la liste
    }

    public void afficher(int position){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Homme ou Femme");
        b.setMessage("Vous êtes ???");
        b.setPositiveButton("Homme", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                afficheToast(position,true);
            }
        });
        b.setNegativeButton("Femme", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                afficheToast(position,false);
            }
        });
        AlertDialog alertDialog = b.create();
        alertDialog.show();

    }
    public void ajouter(){
        if(!edAge.getText().toString().isEmpty() && ! edPoids.getText().toString().isEmpty() &&
                ! edTaille.getText().toString().isEmpty()){
            int age = Integer.parseInt(edAge.getText().toString());
            if (age > 18) {
                float poids = Float.parseFloat(edPoids.getText().toString());
                float taille = Float.parseFloat(edTaille.getText().toString());
                Personne p = new Personne(poids, taille, age);
                adpP.add(p);
                effacer();
            } else {
                Toast t = Toast.makeText(this, "Ce calcul n'est interprétable que pour un adulte!",Toast.LENGTH_LONG);
                t.show();
            }
        }else{
            Toast t = Toast.makeText(this, "Vérifier les valeurs SVP!!!!", Toast.LENGTH_LONG);
            t.show();
        }
    }
    public void afficheToast(int position,boolean homme){
        Personne p = adpP.getItem(position);
        DecimalFormat df = new DecimalFormat("0.00");
        String message = "IMC: " + df.format(p.imc()) + "\n";
        message += "Interprétation : ";
        if (p.imc() < 18.5)
            message += "Maigreur \n";
        else if (p.imc() < 30)
            message += "Corpulence normale \n";
        else
            message += "Surpoids \n";
        String type;
        if (homme)
            type = "H";
        else
            type = "F";
        message += "Poids Idéal (" + type + ") : "
                + p.getPoidsIdeal(homme);
        Toast t = Toast.makeText(this, message, Toast.LENGTH_LONG);
        t.show();
    }
    }