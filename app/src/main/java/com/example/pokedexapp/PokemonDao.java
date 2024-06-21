package com.example.pokedexapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavoritePokemon favoritePokemon);

    @Query("SELECT * FROM favorite_pokemon")
    List<FavoritePokemon> getAllFavoritePokemon();

    @Query("SELECT COUNT(*) FROM favorite_pokemon WHERE id = :pokemonId")
    int isFavorite(int pokemonId);

    @Delete
    void delete(FavoritePokemon favoritePokemon);
}
