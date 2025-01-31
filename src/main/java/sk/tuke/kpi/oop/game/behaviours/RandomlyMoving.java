package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;


public class RandomlyMoving implements Behaviour<Movable> {
    @Override
    public void setUp(Movable actor) {
        if(actor != null){
            new Loop<>( new ActionSequence<>( new Invoke<>(this::random), new Wait<>(1))).scheduleFor(actor);
        }

    }

    private void random(Movable actor){
        int dir = getRandomNumber(0,4);
        dir = dir *90;

        Direction direction = Direction.fromAngle(dir);

        if(direction != Direction.NONE){
            new Move<>(direction,1).scheduleFor(actor);
        }

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}


