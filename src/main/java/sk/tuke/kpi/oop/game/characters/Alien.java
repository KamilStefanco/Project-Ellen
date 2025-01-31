package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;
import java.util.Objects;

public class Alien extends AbstractActor implements Movable,Enemy,Alive {

    private int speed;
    private Health HP;
    private Behaviour<? super Alien> behaviour;

    public Alien(int health, Behaviour<? super Alien> behaviour){
        Animation alienAnimation = new Animation("sprites/alien.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(alienAnimation);
        speed = 2;
        HP = new Health(health);
        this.behaviour = behaviour;

        HP.onExhaustion(() -> {
            getScene().cancelActions(this);
            getScene().removeActor(this);
        });
    }

    public Alien(){
        Animation alienAnimation = new Animation("sprites/alien.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(alienAnimation);
        speed = 2;
        HP = new Health(30);

        HP.onExhaustion(() -> {
            getScene().cancelActions(this);
            getScene().removeActor(this);
        });
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public Health getHealth() {
        return HP;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        if(behaviour != null){
            behaviour.setUp(this);
        }

        new Loop<>( new Invoke<>(this::attack)).scheduleFor(this);

    }

    private void attack(){
        List<Actor> everyActor = Objects.requireNonNull(getScene()).getActors();

        for(Actor actor : everyActor){
            if(actor instanceof Alive && this.intersects(actor) && !(actor instanceof Enemy)){
                ((Alive) actor).getHealth().drain(1);
            }
        }
    }
}
