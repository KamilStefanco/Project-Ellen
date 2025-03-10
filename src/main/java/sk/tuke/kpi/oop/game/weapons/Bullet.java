package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class Bullet extends AbstractActor implements Fireable {

    private int speed;

    public Bullet(){
        Animation bulletAnimation = new Animation("sprites/bullet.png");
        setAnimation(bulletAnimation);
        speed = 4;
    }


    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        if(direction != null){
            getAnimation().setRotation(direction.getAngle());
        }
    }

    @Override
    public void collidedWithWall() {
        Objects.requireNonNull(getScene()).removeActor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
            new Invoke<>(this::checkCollision)).scheduleFor(this);
    }

    private void checkCollision(){
        for(Actor actor : getScene().getActors()){
            if(actor.intersects(this) && actor instanceof Alive && !(actor instanceof Ripley)){
                ((Alive) actor).getHealth().drain(10);
                collidedWithWall();
                getScene().cancelActions(this);

                //if(((Alive) actor).getHealth().getValue() == 0){
                    //getScene().removeActor(actor);
                //}

            }
        }
    }
}
