package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor{

    private Switchable objekt;

    public PowerSwitch(Switchable objekt){
        Animation controllerAnimation = new Animation("sprites/switch.png");
        setAnimation(controllerAnimation);
        this.objekt = objekt;
    }

    public Switchable getDevice(){
        return this.objekt;
    }

    public void switchOn(){
        if(objekt != null){
            this.objekt.turnOn();
            getAnimation().setTint(Color.WHITE);
        }
    }

    public void switchOff(){
        if(objekt != null){
            this.objekt.turnOff();
            getAnimation().setTint(Color.GRAY);
        }
    }

    /*public void toggle(){
        if(this.reactor == null){
            return;
        }

        if(this.reactor.isOn()){
            this.reactor.turnOff();
        }
        else{
            this.reactor.turnOn();
        }
    }*/


}
