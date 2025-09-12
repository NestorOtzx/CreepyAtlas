package com.mycompany.creepyatlas.Game.Data;

public class EntityInformation {
    String id;
    String name;
    int health;
    int attackDamage;
    int mentalHealth;
    String description;

    public EntityInformation(String id, String name, int health, int attackDamage, int mentalHealth, String description) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;
        this.mentalHealth = mentalHealth;
        this.description = description;
    }

    public int getHealth(){
        return health;
    }

    public int getAttackDamage()
    {
        return attackDamage;
    }

    public int getMentalHealth(){
        return mentalHealth;
    }
}