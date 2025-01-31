package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> implements Collectible{

    public Wrench(){
        super(2);
        Animation wrenchAnimation = new Animation("sprites/wrench.png");
        setAnimation(wrenchAnimation);
    }

    @Override
    public void useWith(DefectiveLight Actor) {
        if(Actor != null && Actor.repair()){
            super.useWith(Actor);
        }
    }

    @Override
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }
}
