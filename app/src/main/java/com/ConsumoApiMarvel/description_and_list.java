package com.ConsumoApiMarvel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class description_and_list extends AppCompatActivity {


    private TextView listName;
    private TextView titulo;
    private TextView description;
    private TextView subtitle;

    private Button button;


    private ListView listView_ID;

    private ArrayAdapter<String> itensAdapter;
    private ArrayList<String> itens;


    public static final String Reference_File = "ReferenceFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_and_list);




        final SharedPreferences sharedPreferences = getSharedPreferences(Reference_File, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();




        listView_ID      = (ListView) findViewById(R.id.listView_ID);
        button           = (Button) findViewById(R.id.button);
        listName         = (TextView) findViewById(R.id.listName);
        subtitle         = (TextView) findViewById(R.id.subtitle);
        description      = (TextView) findViewById(R.id.description);
        titulo           = (TextView) findViewById(R.id.titulo);

        //recuperando nome e descricao
        String name = sharedPreferences.getString("name","");
        String desc = sharedPreferences.getString("description","");
        //recebendo o json em formato string das comics
        String DataComics =  sharedPreferences.getString("items","");
        subtitle.setText("Dados do personagem "+ name);
        description.setText("Descrição do Personagem :   "+desc);

        list(DataComics);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear memory
                editor.putString("items","");
                editor.putString("manufacturers","");
                editor.putString("description","");
                editor.commit();
                startActivity(new Intent(description_and_list.this, MainActivity.class));
            }////end
        });//end


    }//end oncreate


    private void list (String DATA){

        try {

            JSONArray jsonArray = new JSONArray(DATA);
            JSONObject jsonObject;



            itens = new ArrayList<String>();

            itensAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_2,
                    android.R.id.text2, itens);
            listView_ID.setAdapter(itensAdapter);

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                itens.add("Comic   " + jsonObject.getString("name")
                );
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }//end of list



}//end of java class
