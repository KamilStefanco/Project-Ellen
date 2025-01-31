package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;


public class KeeperController implements KeyboardListener {
    private final Keeper keeper;

    public KeeperController(Keeper keeper){
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(Input.Key key) {
        switch(key){
            case ENTER:
                new Take<>().scheduleFor(keeper);
                break;
            case S:
                new Shift<>().scheduleFor(keeper);
                break;
            case BACKSPACE:
                new Drop<>().scheduleFor(keeper);
                break;
            case U:
                uCase();
                break;
            case B:
                bCase();
                break;
            default:
        }
    }

    private void uCase(){
        Usable<?> usable = keeper.getScene().getActors().stream()
            .filter(Usable.class::isInstance)
            .filter(keeper::intersects)
            .map(Usable.class::cast)
            .findFirst()
            .orElse(null);

        if(usable != null){
            new Use<>(usable).scheduleForIntersectingWith(keeper);
        }
    }

    private void bCase(){
        if(keeper.getBackpack().getSize() == 0 || keeper.getBackpack().peek() == null){
            return;
        }

        if(keeper.getBackpack().peek() instanceof Usable){
            Usable<?> usable = (Usable<?>) keeper.getBackpack().peek();
            new Use<>(usable).scheduleForIntersectingWith(keeper);
        }
    }
}
