package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler{
    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    public void smartCool(){
        if(getReactor() != null){
            if(getReactor().getTemperature() >= 2500){
                turnOn();
            }
            else if(getReactor().getTemperature() <= 1500){
                turnOff();
            }
            if(isOn()){
                coolReactor();
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::smartCool)).scheduleFor(this);
    }
}
