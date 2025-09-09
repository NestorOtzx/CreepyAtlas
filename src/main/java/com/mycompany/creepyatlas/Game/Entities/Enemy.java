package com.mycompany.creepyatlas.Game.Entities;

import com.mycompany.creepyatlas.Game.Game;

public class Enemy extends Entity {
    public Enemy(int x, int y, int baseHealth, int mentalHealth, int attack_damage ) {
        super(x, y, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'E';
        }
        return super.getSymbol();
    }

    @Override
    public void RecieveAttack(Entity attacker, int damage)
    {
        if (is_dead) { return; }
        super.RecieveAttack(attacker, damage);
        if (!is_dead)
        {
            Attack(attacker.getX(), attack_damage, attacker.getSymbol());
        }
    }

    @Override
    public void Attack(int x, int y, char target){
        if (is_dead) { return; }
        super.Attack(x, y, target);
        Game.EnemyAttacksPosition(this, x, y, target, attack_damage);
    }
    
    public String getDefeatMessage(){
        return "";
        
    }

    public String getForgiveMessage(){
        return "";
        
    }
}
