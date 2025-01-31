package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {

    public Helicopter(){
        Animation heliAnimation = new Animation("sprites/heli.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(heliAnimation);
    }

    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::search)).scheduleFor(this);
    }

    public void search(){
        Player player = (Player) getScene().getFirstActorByName("Player");

        if(player == null){
            return;
        }

        if(this.getPosX() < player.getPosX() ){
            this.setPosition(getPosX()+1,getPosY());
        }
        else{
            this.setPosition(getPosX()-1,getPosY());
        }

        if(this.getPosY() < player.getPosY()){
            this.setPosition(getPosX(),getPosY()+1);
        }
        else{
            this.setPosition(getPosX(),getPosY()-1);
        }

        if(intersects(player)){
            player.setEnergy(player.getEnergy() - 1);
        }
    }

}
