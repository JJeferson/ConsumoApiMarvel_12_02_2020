package com.ConsumoApiMarvel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    //------------------------------------------------------//
    // Declaring the list component
    private ListView listView_ID;

    private ArrayAdapter<String> itensAdapter;
    private ArrayList<String> itens;

    private TextView titulo;

    public static final String Reference_File = "ReferenceFile";

/*
* Chave prova00321
* */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = getSharedPreferences(Reference_File, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();




        listView_ID = (ListView) findViewById(R.id.listView_ID);
        titulo      = (TextView) findViewById(R.id.titulo);


        // Load a List
        List();

    }//end of oncreate


    public void List() {


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://gateway.marvel.com/v1/public/characters?ts=120220201955&apikey=2c3146623833e4c70f647fa910e4540c&hash=c8fe6f71fbb410984ffaa6ab297816db").newBuilder();

            String url = urlBuilder.build().toString();


            Request request = new Request.Builder()
                    .url(url)
                    .get()//Metodo get
                    .build();




            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                try {
                                   /*
                                    String data = response.body().string();
                                    JSONObject jsonObject= new JSONObject(data);

                                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                                    titulo.setText((CharSequence) jsonArray);
                                    */


                                    //Catch body of Json
                                    String data = response.body().string();

                                    //Catch Json String and convert him to Array
                                    JSONArray jsonArray = new JSONArray(data);
                                    JSONObject jsonObject;




                                 //   String data = response.body().string();
                                 //   JSONObject jsonObject= new JSONObject(data);



                                     //   JSONObject argumentsObject = (JSONObject) jsonArray.get(0);
                                        //Criar adaptador
                                        itens = new ArrayList<String>();

                                        itensAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                                android.R.layout.simple_list_item_2,
                                                android.R.id.text2, itens);
                                        listView_ID.setAdapter(itensAdapter);

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            jsonObject = jsonArray.getJSONObject(i);
                                            itens.add("Nome: " + jsonObject.getString("name")
                                            );
                                        }



                                      /*
                                    //Criar adaptador
                                    itens = new ArrayList<String>();

                                    itensAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_list_item_2,
                                            android.R.id.text2, itens);
                                    listView_ID.setAdapter(itensAdapter);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        jsonObject = jsonArray.getJSONObject(i);
                                        itens.add("Nome: " + jsonObject.getString("name")
                                        );
                                    }
                                      */

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

                ;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }// Fim da função lista

}//end of Java Class
