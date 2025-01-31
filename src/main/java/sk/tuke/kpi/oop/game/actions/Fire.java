package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;


public class Fire<A extends Armed> extends AbstractAction<A> {

    public Fire(){

    }

    @Override
    public void execute(float deltaTime) {
        if(!isDone() && getActor() != null){
            Fireable fireable = getActor().getFirearm().fire();
            if(fireable == null){
                setDone(true);
                return;
            }

            Direction direction = Direction.fromAngle(getActor().getAnimation().getRotation());

            getActor().getScene().addActor(fireable,getActor().getPosX() + 8,getActor().getPosY()+8);
            fireable.startedMoving(direction);

            new Move<Fireable>(direction,999999999).scheduleFor(fireable);

        }
        setDone(true);
    }
}
