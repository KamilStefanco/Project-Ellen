package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Actor> {

    private boolean first;
    public Locker(){
        Animation lockerAnimation = new Animation("sprites/locker.png");
        setAnimation(lockerAnimation);
        first = true;
    }

    @Override
    public void useWith(Actor actor) {
        if(first){
            Hammer hammer = new Hammer();
            actor.getScene().addActor(hammer,this.getPosX(),this.getPosY());
            first = false;
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
