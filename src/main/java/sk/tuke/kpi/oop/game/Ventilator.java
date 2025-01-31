package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {

    private Animation ventiAnimation;
    private boolean isReapaired;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);

    public Ventilator(){
        ventiAnimation = new Animation("sprites/ventilator.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(ventiAnimation);
        ventiAnimation.pause();
        isReapaired = false;
    }

    @Override
    public boolean repair() {
        if(!isReapaired){
            ventiAnimation.play();
            isReapaired = true;
            getScene().getMessageBus().publish(VENTILATOR_REPAIRED,this);
            return true;
        }
        return false;
    }
}
