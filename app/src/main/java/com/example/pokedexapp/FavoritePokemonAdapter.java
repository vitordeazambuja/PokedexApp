package com.example.pokedexapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavoritePokemonAdapter extends RecyclerView.Adapter<FavoritePokemonAdapter.FavoritePokemonViewHolder> {

    private List<FavoritePokemon> favoritePokemonList;
    private Context context;
    private OnPokemonRemovedListener onPokemonRemovedListener;

    public FavoritePokemonAdapter(List<FavoritePokemon> favoritePokemonList, Context context, OnPokemonRemovedListener onPokemonRemovedListener) {
        this.favoritePokemonList = favoritePokemonList;
        this.context = context;
        this.onPokemonRemovedListener = onPokemonRemovedListener;
    }

    @NonNull
    @Override
    public FavoritePokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_pokemon, parent, false);
        return new FavoritePokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePokemonViewHolder holder, int position) {
        FavoritePokemon favoritePokemon = favoritePokemonList.get(position);
        holder.nameTextView.setText(favoritePokemon.getName());
        Glide.with(context).load(favoritePokemon.getSpriteUrl()).into(holder.spriteImageView);

        holder.removeButton.setOnClickListener(v -> {
            if (onPokemonRemovedListener != null) {
                onPokemonRemovedListener.onPokemonRemoved(favoritePokemon, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritePokemonList.size();
    }

    static class FavoritePokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView spriteImageView;
        TextView nameTextView;
        Button removeButton;

        FavoritePokemonViewHolder(View itemView) {
            super(itemView);
            spriteImageView = itemView.findViewById(R.id.sprite_image_view);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            removeButton = itemView.findViewById(R.id.remove_button);
        }
    }

    public interface OnPokemonRemovedListener {
        void onPokemonRemoved(FavoritePokemon favoritePokemon, int position);
    }
}
