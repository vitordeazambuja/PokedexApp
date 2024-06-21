package com.example.pokedexapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_pokemon")
public class FavoritePokemon {
    @PrimaryKey
    private int id;
    private String name;
    private String spriteUrl;

    public FavoritePokemon(int id, String name, String spriteUrl) {
        this.id = id;
        this.name = name;
        this.spriteUrl = spriteUrl;
    }

    // Getters e setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getSpriteUrl() { return spriteUrl; }
}
