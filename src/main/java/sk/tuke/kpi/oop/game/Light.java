package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable,EnergyConsumer{

    private boolean lightState;
    private boolean electricityState;
    private Animation lightOn;
    private Animation lightOff;

    public Light(){
        this.lightOff = new Animation("sprites/light_off.png",16,16,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.lightOn = new Animation("sprites/light_on.png",16,16,0.1f,Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(lightOff);
        this.lightState = false;
        this.electricityState = false;
    }

    @Override
    public void setPowered(boolean energy) {
        this.electricityState = energy;
        updateLight();
    }

    @Override
    public void turnOn(){
        lightState = true;
        updateLight();
    }

    @Override
    public void turnOff(){
        lightState = false;
        updateLight();
    }

    @Override
    public boolean isOn(){
        return this.lightState;
    }

    public void toggle(){
        if(lightState){
            lightState = false;
         }
        else{
            lightState = true;
        }
        updateLight();
    }

    public void setElectricityFlow(boolean electricity){
        electricityState = electricity;
        updateLight();
    }

    private void updateLight(){
        if(this.electricityState & this.lightState){
            setAnimation(lightOn);
        }
        else{
            setAnimation(lightOff);
        }
    }

}
