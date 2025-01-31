package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.ChainBomb;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.Teleport;
import sk.tuke.kpi.oop.game.items.Hammer;

import java.util.Map;

public class TrainingGameplay extends Scenario{
    @Override
    public void setupPlay(@NotNull Scene scene) {
        Map<String, MapMarker> markers = scene.getMap().getMarkers();

        Reactor reactor = new Reactor();
        MapMarker reactorArea1 = markers.get("reactor-area-1");
        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());
        reactor.turnOn();

        Cooler cooler = new Cooler(reactor);
        MapMarker coolerArea1 = markers.get("cooler-area-1");
        scene.addActor(cooler,coolerArea1.getPosX(),coolerArea1.getPosY());

        ChainBomb bomb1 = new ChainBomb(5);
        scene.addActor(bomb1,200,300);

        ChainBomb bomb2 = new ChainBomb(5);
        scene.addActor(bomb2,250,300);

        ChainBomb bomb3 = new ChainBomb(5);
        scene.addActor(bomb3,200,250);

        ChainBomb bomb4 = new ChainBomb(5);
        scene.addActor(bomb4,200,220);

        Teleport tele = new Teleport(null);
        scene.addActor(tele,40,340);

        Teleport  tele2 = new Teleport(tele);
        scene.addActor(tele2,40,40);

        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(Cooler::turnOn)
        ).scheduleFor(cooler);

        Hammer hammer = new Hammer();
        scene.addActor(hammer,200,200);

        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> reactor.repair())
        ).scheduleFor(reactor);

    }






}
