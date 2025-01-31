package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class TimeBomb extends AbstractActor {
    private Animation activateAnimation;
    private Animation explodeAnimation;
    private float time;
    private boolean state;

    public TimeBomb(float time){
        Animation bombAnimation = new Animation("sprites/bomb.png");
        activateAnimation = new Animation("sprites/bomb_activated.png",16,16,0.05f,Animation.PlayMode.LOOP);
        explodeAnimation = new Animation("sprites/small_explosion.png",16,16,0.1f,Animation.PlayMode.ONCE);
        setAnimation(bombAnimation);
        this.time = time;
        state = false;
    }

    public void activate(){
        setAnimation(activateAnimation);
        state = true;
        new ActionSequence<>(new Wait<>(time),new Invoke<>(this::explode)).scheduleFor(this);
        new When<>(
            () -> getAnimation().getCurrentFrameIndex() == 7,
            new Invoke<>(() -> getScene().removeActor(this))
        ).scheduleFor(this);
    }

    public void explode(){
        setAnimation(explodeAnimation);
    }

    public boolean isActivated(){
        return state;
    }

}
