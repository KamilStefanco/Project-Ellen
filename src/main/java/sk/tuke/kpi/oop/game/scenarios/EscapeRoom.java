package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;


public class EscapeRoom implements SceneListener {

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
            if(name.equals("ammo")){
                return new Ammo();
            }
            if(name.equals("alien")){
                return new Alien();
            }
            if(name.equals("running")){
                return new Alien(100, new RandomlyMoving());
            }
            if(name.equals("front door")){
                return new Door("front door", Door.Orientation.VERTICAL);
            }
            if(name.equals("back door")){
                return new Door("front door", Door.Orientation.HORIZONTAL);
            }
            if(name.equals("alien mother")){
                return new AlienMother(200, new RandomlyMoving());
            }
            if(name.equals("exit door")){
                return null;
            }
            return null;
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        MovableController movableController = new MovableController(scene.getFirstActorByType(Ripley.class));
        Disposable movable = scene.getInput().registerListener(movableController);


        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        ripley.getHealth().drain(20);

        KeeperController keeperController = new KeeperController(ripley);
        Disposable keepe = scene.getInput().registerListener(keeperController);

        ShooterController shooterController = new ShooterController(ripley);
        Disposable shoot = scene.getInput().registerListener(shooterController);

        Hammer hammer = new Hammer();
        Wrench wrench = new Wrench();
        FireExtinguisher fire = new FireExtinguisher();
        ripley.getBackpack().add(hammer);
        ripley.getBackpack().add(fire);
        ripley.getBackpack().add(wrench);

        scene.getGame().pushActorContainer(ripley.getBackpack());
        ripley.getBackpack().shift();

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movable.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keepe.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> shoot.dispose());
        //ripley.check();

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
