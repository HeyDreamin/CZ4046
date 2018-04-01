package Models;

//Class stores different actions (policy).
public enum Actions {
    UP, RIGHT, DOWN, LEFT;

    //Return action of turning right/left.
    public static Actions turnTo(Boolean right, Actions direction) {
        switch (direction){
            case UP:
                if (right)
                    return RIGHT;
                else
                    return LEFT;
            case RIGHT:
                if (right)
                    return DOWN;
                else
                    return UP;
            case DOWN:
                if (right)
                    return LEFT;
                else
                    return RIGHT;
            case LEFT:
                if (right)
                    return UP;
                else
                    return DOWN;
        }
        System.out.println("Turn to: Given wrong direction.");
        return UP;
    }
}