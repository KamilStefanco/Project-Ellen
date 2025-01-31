package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;
import java.util.Objects;

public class Take<A extends Keeper> extends AbstractAction<A>  {

    public Take(){

    }

    @Override
    public void execute(float deltaTime) {

        if(getActor() == null || isDone()){
            setDone(true);
            return;
        }

        List<Actor> everyActor = Objects.requireNonNull(Objects.requireNonNull(getActor()).getScene()).getActors();

        for(Actor actor : everyActor){
            if(actor instanceof Collectible && getActor().intersects(actor)){
                try{
                    getActor().getBackpack().add((Collectible) actor);
                    actor.getScene().removeActor(actor);
                } catch(IllegalStateException ex){
                    getActor().getScene().getOverlay().drawText(ex.getMessage(), 100, 100).showFor(2);
                }
            }
        }
        setDone(true);


    }
}
