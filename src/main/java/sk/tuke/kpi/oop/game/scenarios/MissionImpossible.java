package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.LockedDoor;


public class MissionImpossible implements SceneListener {

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        //SceneListener.super.sceneInitialized(scene);

        MovableController movableController = new MovableController(scene.getFirstActorByType(Ripley.class));
        Disposable movable = scene.getInput().registerListener(movableController);


        Ripley ripley = scene.getFirstActorByType(Ripley.class);


        KeeperController keeperController = new KeeperController(ripley);
        Disposable keepe = scene.getInput().registerListener(keeperController);

        Hammer hammer = new Hammer();
        Wrench wrench = new Wrench();
        FireExtinguisher fire = new FireExtinguisher();
        ripley.getBackpack().add(hammer);
        ripley.getBackpack().add(fire);
        ripley.getBackpack().add(wrench);

        scene.getGame().pushActorContainer(ripley.getBackpack());
        ripley.getBackpack().shift();

        //scene.getMessageBus().subscribe(DOOR_OPENED, (Ripley) -> ripley.minusEnergy());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movable.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keepe.dispose());



    }

    public static class Factory implements ActorFactory{
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if(name == null) return null;

            if(name.equals("ellen")){
                return new Ripley();
            }
            if(name.equals("energy")){
                return new Energy();
            }
            if(name.equals("access card")){
                return new AccessCard();
            }
            if(name.equals("locker")){
                return new Locker();
            }
            if(name.equals("ventilator")){
                return new Ventilator();
            }
            if(name.equals("door")){
                return new LockedDoor();
            }

            return null;
        }
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        Ripley ripley = scene.getFirstActorByType(Ripley.class);

        if(ripley != null){
            ripley.showRipleyState(scene);
            scene.follow(ripley);
        }
    }
}
