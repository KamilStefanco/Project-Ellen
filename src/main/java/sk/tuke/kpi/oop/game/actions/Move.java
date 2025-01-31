package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;

public class Move <A extends Movable>implements Action<A> {

    private A hrac;
    private boolean first;
    private Direction direction;
    private float duration;
    private boolean done;

    public Move(Direction direction){
        this.direction = direction;
        this.done = false;
        this.first = true;
    }

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        this.done = false;
        this.first = true;
    }

    @Override
    public A getActor() {
        return hrac;
    }

    @Override
    public void setActor(@Nullable A actor) {
        this.hrac = actor;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void reset() {
        hrac.stoppedMoving();
        done = false;
        first = true;
    }

    public void stop(){
        done = true;
        first = true;
        if(hrac != null){
            hrac.stoppedMoving();
        }
    }

    @Override
    public void execute(float deltaTime) {
        duration = duration - deltaTime;
        if(hrac != null && !done){
            if(duration<=0){
                stop();
            }
            if(first){
                hrac.startedMoving(direction);
                first = false;
            }
            int speed = hrac.getSpeed();
            hrac.setPosition(hrac.getPosX()+(direction.getDx() * speed),hrac.getPosY()+(direction.getDy() * speed));

            if(hrac.getScene().getMap().intersectsWithWall(hrac)){
                hrac.setPosition(hrac.getPosX()-(direction.getDx() * speed),hrac.getPosY()-(direction.getDy() * speed));
                hrac.collidedWithWall();
            }

        }
    }
}
