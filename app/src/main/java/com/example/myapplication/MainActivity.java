package com.example.gestionnotes;

import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // declaration des variables
    ListView markListView;
    protected EditText note;
    protected Button add_btn;
    protected EditText moy;
    protected double moyenne_ = 0;
    static double noteSomme = 0;
    protected ArrayList<String> listViewItems;
    static ArrayList<String> insertMark;
    ArrayAdapter<String> marksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ajouter les elements du dropdown
        Spinner spinner = findViewById(R.id.spinner);
        String[] modules = new String[]{"Statistique", "Dev Mobile", "Data Mining"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modules);
        spinner.setAdapter(adapter);

        // initialiser les components
        markListView = findViewById(R.id.listedesnotes);
        note = findViewById(R.id.note);
        note.setInputType(InputType.TYPE_CLASS_NUMBER);
        moy = findViewById(R.id.moy);
        moy.setEnabled(false);
        add_btn = findViewById(R.id.add_btn);

        insertMark = new ArrayList<>();
        listViewItems = new ArrayList<>();
        marksAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listViewItems);
        markListView.setAdapter(marksAdapter);

        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mark = note.getText().toString();
                String subject = spinner.getSelectedItem().toString();

                //verification de redoublement de saisie
                if(mark == null){
                    show_toast("Veuillez saisir une note");
                    hideKeyBoard();
                }else{

                    if (insertMark.contains(subject)){
                        show_toast("La note du module "+ subject +" existe deja");
                        hideKeyBoard();
                    }else{
                        addNote(mark, subject);
                        noteSomme = noteSomme + Double.parseDouble(mark);
                        // update moyenne
                        moyenne_ = noteSomme / listViewItems.size();
                        moy.setText(moyenne_+"");

                        hideKeyBoard();
                    }
                }

            }
        });

    }

    // cacher le clavier
    public void hideKeyBoard(){
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // afficher message
    public void show_toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    public void addNote(String mark, String subject){
        int n = listViewItems.size() + 1;
        listViewItems.add(n+"          "+subject+"         "+note);
        insertMark.add(subject);
        note.setText("");
        markListView.setAdapter(marksAdapter);
    }

}