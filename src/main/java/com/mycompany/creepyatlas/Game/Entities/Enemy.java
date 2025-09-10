package com.mycompany.creepyatlas.Game.Entities;

import com.mycompany.creepyatlas.Game.Game;

public class Enemy extends Entity {
    int enemyID;

    public Enemy(int positionX, int positionY, int baseHealth, int mentalHealth, int attack_damage ) {
        super(positionX, positionY, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!isDead)
        {
            return 'E';
        }
        return super.getSymbol();
    }

    @Override
    public void recieveAttack(Entity attacker, int damage)
    {
        if (isDead) { return; }
        super.recieveAttack(attacker, damage);
        if (!isDead)
        {
            attack(attacker.getPositionX(), attackDamage, attacker.getSymbol());
        }
    }

    @Override
    public void attack(int x, int y, char target){
        if (isDead) { return; }
        super.attack(x, y, target);
        Game.EnemyAttacksPosition(this, x, y, target, attackDamage);
    }

    @Override
    public void onDie()
    {
        super.onDie();
        System.out.println(getDefeatMessage());
    }

    @Override
    public void onBeForgiven()
    {
        super.onBeForgiven();
        System.out.println(getForgiveMessage());   
    }
    
    public String getDefeatMessage(){
        return "";
        
    }

    public String getForgiveMessage(){
        return "";
    }

    public void onPlayerRespawn(){

    }
}
