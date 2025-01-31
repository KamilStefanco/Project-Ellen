package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {

    private List<ExhaustionEffect> effectsList;
    private int HP;
    private int maxHP;
    private boolean first;

    public Health(int HP, int maxHP){
        this.HP  = HP;
        this.maxHP = maxHP;
        effectsList = new ArrayList<>();
        first = true;
    }

    public Health(int HP){
        this.HP = HP;
        this.maxHP = HP;
        effectsList = new ArrayList<>();
        first = true;
    }

    public int getValue(){
        return HP;
    }

    public void refill(int amount){
        this.HP = HP + amount;
        if(this.HP > this.maxHP){
            this.HP = this.maxHP;
        }
    }

    public void restore(){
        this.HP = this.maxHP;
    }

    public void drain(int amount){
        if(HP > amount){
            HP = HP - amount;
        }
        else{
            exhaust();
        }
    }

    public void exhaust(){
        this.HP = 0;
        if(effectsList != null &&first){
            effectsList.forEach(ExhaustionEffect::apply);
            first = false;
        }
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public void onExhaustion(ExhaustionEffect effect){
        if(effectsList != null){
            effectsList.add(effect);
        }
    }


}
