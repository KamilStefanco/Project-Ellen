package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable{
    private boolean isRepaired;
    private Disposable disposable;

    public DefectiveLight(){
        super();
    }

    public void blink(){
        int number = (int)(Math.random()*20);
        if (number == 6){
            this.toggle();
        }
        isRepaired = false;
    }

    @Override
    public boolean repair() {
        if(!isRepaired && disposable != null){
            isRepaired = true;
            disposable.dispose();
            disposable = new ActionSequence<>(new Wait<>(10),new Loop<>(new Invoke<>(this::blink))).scheduleFor(this);
            return true;
        }
        return false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        disposable= new Loop<>(new Invoke<>(this::blink)).scheduleFor(this);
    }
}
