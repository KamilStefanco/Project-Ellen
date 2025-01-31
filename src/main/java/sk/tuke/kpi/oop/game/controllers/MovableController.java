package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable hrac;
    private Set<Input.Key> keyList = new HashSet<>();
    private Move<Movable> move;

    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN,Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT,Direction.EAST)
    );

    public MovableController(Movable actor){
        this.hrac = actor;
        this.move = null;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if(keyDirectionMap.containsKey(key)){
            keyList.add(key);
            update();
        }

    }

    @Override
    public void keyReleased(Input.Key key) {
        if(keyDirectionMap.containsKey(key)){
            keyList.remove(key);

            if(keyList.isEmpty()){
                move.stop();
                move = null;
            }
            else{
                update();
            }

        }
    }

    public void update(){

        Direction temp = Direction.NONE;

        for (Input.Key key : keyList){
            temp = temp.combine(keyDirectionMap.get(key));
        }

        if(move != null){
            move.stop();
            move = null;
        }


        move = new Move<Movable>(temp,999999999);
        move.scheduleFor(hrac);
    }
}
