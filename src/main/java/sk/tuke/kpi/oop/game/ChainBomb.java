package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Objects;

public class ChainBomb extends TimeBomb{

    public ChainBomb(float time){
        super(time);
    }

    @Override
    public void explode(){
        super.explode();

        Ellipse2D.Float elipsa =  new Ellipse2D.Float(this.getPosX() - 42, this.getPosY() - 42, 108, 108);

        List<Actor> everyActor = Objects.requireNonNull(getScene()).getActors();
        for(Actor bomb : everyActor){
            int x_bomb = bomb.getPosX();
            int y_bomb = bomb.getPosY();

            Rectangle2D.Float stvorec = new Rectangle2D.Float(x_bomb, y_bomb, 16, 16);

            if((bomb instanceof ChainBomb)  && !((ChainBomb) bomb).isActivated() && elipsa.intersects(stvorec)){
                ((ChainBomb)bomb).activate();
            }
        }


    }

}
