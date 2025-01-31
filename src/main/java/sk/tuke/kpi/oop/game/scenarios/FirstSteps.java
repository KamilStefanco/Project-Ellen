package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;


public class FirstSteps implements SceneListener {

    private Ripley player = new Ripley();
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        //SceneListener.super.sceneInitialized(scene);

        scene.addActor(player,0,0);


        MovableController movableController = new MovableController(player);
        scene.getInput().registerListener(movableController);

        Energy energy = new Energy();
        scene.addActor(energy,50,50);

        Ammo ammo = new Ammo();
        scene.addActor(ammo,-50,50);

        new When<>(
            () -> player.intersects(ammo),
            new Invoke<>(() -> ammo.useWith(player))
        ).scheduleFor(player);


        new When<>(
            () -> player.intersects(energy),
            new Invoke<>(() -> energy.useWith(player))
        ).scheduleFor(player);



        //player.startedMoving(Direction.WEST);
        //Move<Ripley> abc = new Move<Ripley>(Direction.SOUTH,5);

        //abc.setActor(player);
        //abc.execute(4);

    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        //scene.getGame().getOverlay().drawText("Energy: " +player.getEnergy(), 120, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: " +player.getAmmo(), 300, yTextPos);

    }

}
