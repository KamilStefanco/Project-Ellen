package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable,Repairable{
    private int temperature;
    private int damage;
    private Animation normalAnimation;
    private Animation brokenAnimation;
    private Animation hotAnimation;
    private Animation hotfasterAnimation;
    private Animation offAnimation;
    private Animation extinguishedAnimation;
    private boolean reactorState;
    private Set<EnergyConsumer> devices;


    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        this.reactorState = false;
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80,80,0.05f,Animation.PlayMode.LOOP_PINGPONG);
        this.hotfasterAnimation = new Animation("sprites/reactor_hot.png", 80,80,0.02f,Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/reactor.png", 80,80,0.02f,Animation.PlayMode.LOOP_PINGPONG);
        this.extinguishedAnimation = new Animation("sprites/reactor_extinguished.png");
        setAnimation(offAnimation);
        this.devices = new HashSet<>();
    }

    public int getDamage(){
        return damage;
    }

    public int getTemperature(){
        return temperature;
    }

    public void increaseTemperature(int increment){
        if (temperature != 6000 && reactorState && increment > 0) {
            int before = damage;

            if (damage >= 33 & damage <= 66) {
                temperature = (int)Math.ceil(temperature + (increment * 1.5));
            } else if (damage > 66) {
                temperature = temperature + increment * 2;
            } else {
                temperature = temperature + increment;
            }

            if (temperature > 2000) {
                damage = (temperature - 2000) / 40;
                if (damage < before) {
                    damage = before;
                }
            }

            updateAnimation();
        }

    }

    public void decreaseTemperature(int decrement) {
        if (damage != 100 && reactorState && decrement > 0) {
            int dec = decrement;
            if (damage >= 50) {
                dec = (int) (decrement * 0.5);
            }
            temperature = temperature - dec;
            if (temperature < 0) {
                temperature = 0;
            }
            updateAnimation();
        }

    }

    private void updateAnimation(){
        if(!reactorState && temperature != 6000){
            setAnimation(offAnimation);
            return;
        }

        if(temperature < 4000 ){
            setAnimation(normalAnimation);
            return;
        }
        if(temperature < 6000){
            if(temperature >=5000){
                setAnimation(hotfasterAnimation);
            }
            else{
                setAnimation(hotAnimation);
            }
        }
        if(damage >= 100) {
            this.damage = 100;
            this.reactorState = false;
            setAnimation(brokenAnimation);
            if(devices != null){
                for(EnergyConsumer everyDevice :devices){
                    everyDevice.setPowered(false);
                }
            }
        }
    }

    public boolean repair(){
        if(this.damage == 100 || !reactorState ){
            return false;
        }
        int beforeDmg = damage -50;
        int tempTemperature;

        if(damage<=50){
            damage = 0;
        }
        else{
            damage = damage - 50;
        }

        tempTemperature = (beforeDmg *40) +2000;
        if(tempTemperature < temperature){
            temperature = tempTemperature;
        }
        updateAnimation();
        return true;
    }

    public void turnOn(){
        this.reactorState = true;
        updateAnimation();
        if(devices != null){
            for(EnergyConsumer everyDevice :devices){
                everyDevice.setPowered(reactorState);
            }
        }
    }
    public void turnOff(){
        this.reactorState = false;
        updateAnimation();
        if(devices != null){
            for(EnergyConsumer everyDevice :devices){
                everyDevice.setPowered(reactorState);
            }
        }
    }

    public boolean isOn(){
        return this.reactorState;
    }

    public void addDevice(EnergyConsumer consumer){
        if(consumer != null) {
            devices.add(consumer);
            consumer.setPowered(reactorState);
        }
    }

    public void removeDevice(EnergyConsumer consumer){
        if(consumer != null){
            devices.remove(consumer);
            consumer.setPowered(false);
        }
    }

    public boolean extinguish(){
        if(damage != 100){
            return false;
        }

        temperature = 4000;
        setAnimation(extinguishedAnimation);
        return true;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}

