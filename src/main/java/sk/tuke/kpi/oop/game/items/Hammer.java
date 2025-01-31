package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

import java.util.Objects;

public class Hammer extends BreakableTool<Repairable> implements Collectible{

    private int remainingUses;

    public Hammer() {
        this(1);
    }

    public Hammer(int remainingUses) {
        super(remainingUses);
        this.remainingUses = remainingUses;
        Animation hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    public int getUses() {
        return this.remainingUses;
    }

    @Override
    public void useWith(Repairable repairable) {
        if (repairable != null && repairable.repair()) {
            super.useWith(repairable);

            this.remainingUses--;
            if(this.remainingUses <=0){
                Objects.requireNonNull(getScene()).removeActor(this);
            }
        }

    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}
