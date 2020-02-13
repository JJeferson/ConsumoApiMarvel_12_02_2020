package com.ConsumoApiMarvel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
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

        listView_ID.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

             //   startActivity(new Intent(MainActivity.this, description_and_list.class));


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


                                            String data = response.body().string();

                                            JSONObject json = new JSONObject(data);
                                            JSONObject jsonObjectData = json.getJSONObject("data");
                                            JSONArray  jsonArrayResults = jsonObjectData.getJSONArray("results");
                                            String StringData = String.valueOf(jsonArrayResults);

                                            JSONArray jsonArray = new JSONArray(StringData);
                                            JSONObject jsonObjectResults;
                                            jsonObjectResults = jsonArray.getJSONObject(position);
                                            /*
                                            * ele vai navegarno json até results, então na posicao da lista onde for clicado
                                            * ele pega o campo description grava num string e depois navegará até
                                            * items a chave do json onde se encontra a lista de comics que o personagem participou
                                            * dai grava isso num string para poder se alimentar uma lista depois.
                                            *
                                            * Ao fim ele levará para a outra activity as duas informacoes
                                            * o description, o nome do personagem e o json dentro da string
                                            *
                                             */

                                            JSONObject  jsonObjectComics = jsonObjectResults.getJSONObject("comics");
                                            JSONArray  jsonArrayItems = jsonObjectComics.getJSONArray("items");

                                            String StringDataItems = String.valueOf(jsonArrayItems);
                                            editor.putString("items",StringDataItems);

                                            editor.putString("name",jsonObjectResults.getString("name"));
                                            editor.putString("description",jsonObjectResults.getString("description"));
                                            editor.commit();

                                            startActivity(new Intent(MainActivity.this, description_and_list.class));


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







            }
        });  //End of Onclick Event



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
                                    //recebe o json em string
                                    String data = response.body().string();

                                    JSONObject json = new JSONObject(data);
                                    //entra an chave data
                                    JSONObject datajson = json.getJSONObject("data");
                                    //entra na chave results
                                    JSONArray  results = datajson.getJSONArray("results");
                                     //Uma vez já dentro da chave certa devolvo ao formato string para usar.
                                    String StringData = String.valueOf(results);

                                    JSONArray jsonArray = new JSONArray(StringData);
                                    JSONObject jsonObject;

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
