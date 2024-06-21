package com.example.pokedexapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MeuTime extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoritePokemonAdapter adapter;
    private FavoritePokemonDatabase favoritePokemonDatabase;
    private List<FavoritePokemon> favoritePokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_time);
        EdgeToEdge.enable(this);

        recyclerView = findViewById(R.id.recycler_view_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoritePokemonDatabase = FavoritePokemonDatabase.getInstance(this);

        new Thread(() -> {
            favoritePokemonList = favoritePokemonDatabase.pokemonDao().getAllFavoritePokemon();
            runOnUiThread(() -> {
                adapter = new FavoritePokemonAdapter(favoritePokemonList, this, (favoritePokemon, position) -> {
                    new Thread(() -> {
                        favoritePokemonDatabase.pokemonDao().delete(favoritePokemon);
                        runOnUiThread(() -> {
                            favoritePokemonList.remove(position);
                            adapter.notifyItemRemoved(position);
                            Toast.makeText(this, "Pokémon removido do time", Toast.LENGTH_SHORT).show();
                        });
                    }).start();
                });
                recyclerView.setAdapter(adapter);
            });
        }).start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
