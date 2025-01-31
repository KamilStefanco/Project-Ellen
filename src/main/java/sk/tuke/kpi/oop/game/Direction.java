package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH (0, 1),
    EAST (1, 0),
    SOUTH (0, -1),
    WEST (-1, 0),
    NONE(0,0),
    NORTHWEST(-1,1),
    NORTHEAST(1,1),
    SOUTHWEST(-1,-1),
    SOUTHEAST(1,-1)
    ;

    private float angle = -1;
    private int dy;
    private int dx;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx(){
        return dx;
    }

    public int getDy(){
        return dy;
    }

    public float getAngle(){
        if(dx == 0 && dy == 1) angle = 0;
        if(dx == 1 && dy == 0) angle = 270;
        if(dx == 0 && dy == -1) angle = 180;
        if(dx == -1 && dy == 0) angle = 90;

        angle = getAngle2();

        return angle;
    }

    private float getAngle2(){

        if(dx == -1 && dy == 1) angle = 45;
        if(dx == 1 && dy == 1) angle = 315;
        if(dx == -1 && dy == -1) angle = 135;
        if(dx == 1 && dy == -1) angle = 225;

        return angle;
    }

    public Direction combine(Direction other){
        if(this == other){
            return other;
        }

        Direction result = Direction.NONE;

        int x = getDx() + other.getDx();
        int y = getDy() + other.getDy();

        x = calculate(x);
        y = calculate(y);

        for(Direction direct : Direction.values()){
            if((direct.getDx() == x) && (direct.getDy() == y)){
               result = direct;
            }
        }

        return result;
    }

    private int calculate(int value){
        int result = value;
        if(value > 1){
            result = 1;
        }

        if(value< -1){
            result = -1;
        }

        return result;
    }

    public static Direction fromAngle(float angle){
        Direction result;
        for(Direction direction : Direction.values()){
            if(direction.getAngle() == angle){
                result = direction;
                return result;
            }
        }
        return Direction.NONE;
    }
}
