package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {

    private Animation coolerAnimation;
    private Reactor reactor;
    private boolean coolerState;

    public Cooler(Reactor reactor){
        this.reactor = reactor;
        coolerState = false;
        coolerAnimation = new Animation("sprites/fan.png",32,32,0.2f,Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(coolerAnimation);
        coolerAnimation.pause();
    }

    public void coolReactor(){
        if(reactor != null & coolerState) {
            this.reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void turnOn(){
        coolerState = true;
        coolerAnimation.play();
    }

    @Override
    public void turnOff(){
        coolerState = false;
        coolerAnimation.pause();
    }

    public Reactor getReactor(){
        return reactor;
    }

    @Override
    public boolean isOn(){
        return coolerState;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
