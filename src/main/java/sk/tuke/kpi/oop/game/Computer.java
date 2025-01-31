package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer  extends AbstractActor implements EnergyConsumer {

    private boolean powered;

    public Computer(){
        Animation normalAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        normalAnimation.pause();
        this.powered = false;
    }

    public int add(int a, int b){
        if(powered){
            return a+b;
        }
        return 0;
    }

    public float add(float a, float b){
        if(powered){
            return a+b;
        }
        return 0;
    }

    public float sub(float a, float b){
        if(powered){
            return a-b;
        }
        return 0;
    }

    public int sub(int a, int b){
        if(powered){
            return a-b;
        }
        return 0;
    }

    private void updateAnimation(){
        if(!powered){
            this.getAnimation().pause();
        }
        else this.getAnimation().play();
    }

    @Override
    public void setPowered(boolean energy) {
        this.powered = energy;
        updateAnimation();
    }
}
