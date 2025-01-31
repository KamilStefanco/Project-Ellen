package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Teleport extends AbstractActor {

    private Teleport destinationTeleport;
    private boolean canTeleport;

    public Teleport(Teleport destinationTeleport){
        Animation teleportAnimation = new Animation("sprites/lift.png");
        setAnimation(teleportAnimation);
        this.destinationTeleport = destinationTeleport;
        this.canTeleport = true;
    }

    public Teleport getDestination(){
        return destinationTeleport;
    }

    public void setDestination(Teleport destinationTeleport){
        if(this != destinationTeleport){
            this.destinationTeleport = destinationTeleport;
        }
    }

    public void teleportPlayer(Player player){
        if(player != null){

            int Mid_x = this.getPosX() + (this.getWidth()/2);
            int Mid_y = this.getPosY() + (this.getHeight()/2);

            player.setPosition(Mid_x- player.getWidth()/2,Mid_y - player.getHeight()/2);
        }
    }

    public boolean isOnTeleport(){
        Player player = Objects.requireNonNull(getScene()).getFirstActorByType(Player.class);
        if(player != null){
            int playerMid_x = player.getPosX() + (player.getWidth()/2);
            int playerMid_y = player.getPosY() + (player.getHeight()/2);

            if ((playerMid_x > this.getPosX()) &&(playerMid_x < this.getPosX()+48) &&  (playerMid_y > this.getPosY()) && (playerMid_y < this.getPosY()+48)){
                return true;
            }
        }
        return false;
    }

    public void teleportLoop(){
        if(destinationTeleport != null && canTeleport && isOnTeleport()){
            Player player = Objects.requireNonNull(getScene()).getFirstActorByType(Player.class);
            destinationTeleport.teleportPlayer(player);
            destinationTeleport.canTeleport = false;
        }
    }

    public void playerGone(){
        Player player = Objects.requireNonNull(getScene()).getFirstActorByType(Player.class);

        if(player != null && !canTeleport && !player.intersects(this)){
                canTeleport = true;
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::teleportLoop)).scheduleFor(Objects.requireNonNull(Objects.requireNonNull(getScene()).getFirstActorByType(Player.class)));
        new Loop<>(new Invoke<>(this::playerGone)).scheduleFor(this);
    }


}
