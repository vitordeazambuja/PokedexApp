package com.example.pokedexapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokemonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;
    private List<Pokemon> pokemonList;
    private RequestQueue requestQueue;
    private Button meuTimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        EdgeToEdge.enable(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pokemonList = new ArrayList<>();
        pokemonAdapter = new PokemonAdapter(pokemonList, this);
        recyclerView.setAdapter(pokemonAdapter);

        meuTimeButton = findViewById(R.id.meu_time);
        meuTimeButton.setOnClickListener(v -> {
            Intent intent = new Intent(PokemonActivity.this, MeuTime.class);
            startActivity(intent);
        });

        requestQueue = Volley.newRequestQueue(this);
        fetchPokemonData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchPokemonData() {
        String url = "https://pokeapi.co/api/v2/pokemon?limit=150";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject pokemon = results.getJSONObject(i);
                            String name = pokemon.getString("name");
                            String urlDetails = pokemon.getString("url");
                            fetchPokemonDetails(name, urlDetails);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );
        requestQueue.add(request);
    }

    private void fetchPokemonDetails(String name, String urlDetails) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlDetails, null,
                response -> {
                    try {
                        int id = response.getInt("id");
                        String spriteUrl = response.getJSONObject("sprites").getString("front_default");
                        Pokemon pokemon = new Pokemon(id, name, spriteUrl);
                        pokemonList.add(pokemon);
                        pokemonAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );
        requestQueue.add(request);
    }
}
