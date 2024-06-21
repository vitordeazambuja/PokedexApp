package com.example.pokedexapp;

public class Pokemon {
    private int id;
    private String nome;
    private String urlSprite;

    public Pokemon(int id, String nome, String urlSprite){
        this.id = id;
        this.nome = nome;
        this.urlSprite = urlSprite;
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUrlSprite() {
        return urlSprite;
    }
}
