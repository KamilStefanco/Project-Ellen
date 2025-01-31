package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Drop<A extends Keeper> extends AbstractAction<A> {

    public Drop(){

    }

    @Override
    public void execute(float deltaTime) {
        if(getActor() == null || isDone() || getActor().getBackpack().peek() == null){
            setDone(true);
            return;
        }

        getActor().getScene().addActor(getActor().getBackpack().peek(), getActor().getPosX()+8, getActor().getPosY()+7);
        getActor().getBackpack().remove(getActor().getBackpack().peek());
        setDone(true);

    }
}
