package com.example.pokedexapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> pokemonList;
    private Context context;
    private FavoritePokemonDatabase favoritePokemonDatabase;

    public PokemonAdapter(List<Pokemon> pokemonList, Context context) {
        this.pokemonList = pokemonList;
        this.context = context;
        this.favoritePokemonDatabase = FavoritePokemonDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.nameTextView.setText(pokemon.getNome());
        Glide.with(context).load(pokemon.getUrlSprite()).into(holder.spriteImageView);

        new Thread(() -> {
            int isFavorite = favoritePokemonDatabase.pokemonDao().isFavorite(pokemon.getId());
            holder.favoriteButton.post(() -> {
                if (isFavorite > 0) {
                    holder.favoriteButton.setText("Remover do time");
                } else {
                    holder.favoriteButton.setText("Adicionar ao time");
                }
            });
        }).start();

        holder.favoriteButton.setOnClickListener(v -> {
            new Thread(() -> {
                if (favoritePokemonDatabase.pokemonDao().isFavorite(pokemon.getId()) == 0) {
                    favoritePokemonDatabase.pokemonDao().insert(new FavoritePokemon(pokemon.getId(), pokemon.getNome(), pokemon.getUrlSprite()));
                    holder.favoriteButton.post(() -> {
                        holder.favoriteButton.setText("Remover do time");
                    });
                    ((Activity) context).runOnUiThread(() ->
                            Toast.makeText(context, "Pokémon adicionado ao time!", Toast.LENGTH_SHORT).show());
                } else {
                    favoritePokemonDatabase.pokemonDao().delete(new FavoritePokemon(pokemon.getId(), pokemon.getNome(), pokemon.getUrlSprite()));
                    holder.favoriteButton.post(() -> {
                        holder.favoriteButton.setText("Adicionar ao time");
                    });
                    ((Activity) context).runOnUiThread(() ->
                            Toast.makeText(context, "Pokémon removido do time!", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });
    }


    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView spriteImageView;
        TextView nameTextView;
        Button favoriteButton;

        PokemonViewHolder(View itemView) {
            super(itemView);
            spriteImageView = itemView.findViewById(R.id.sprite_image_view);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }
}
