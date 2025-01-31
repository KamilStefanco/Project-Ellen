package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

import java.util.Objects;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible{

    private int remainingUses;

    public FireExtinguisher(){
        super(1);
        this.remainingUses = 1;
        Animation normalAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(normalAnimation);
    }

    public int getUses(){
        return remainingUses;
    }

    @Override
    public void useWith(Reactor reactor){
        if (reactor != null && reactor.extinguish()) {
            super.useWith(reactor);

            this.remainingUses--;
            if(this.remainingUses <=0){
                Objects.requireNonNull(getScene()).removeActor(this);
            }
        }
    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }
}
