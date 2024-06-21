package com.example.pokedexapp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoritePokemon.class}, version = 1)
public abstract class FavoritePokemonDatabase extends RoomDatabase {
    private static FavoritePokemonDatabase instance;

    public abstract PokemonDao pokemonDao();

    public static synchronized FavoritePokemonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FavoritePokemonDatabase.class, "favorite_pokemon_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
