package Models;

import Utils.Const;
//Class of each state.
public class Square {
    private int row;
    private int col;
    private String color;
    private Double reward;
    private Double utility = 0.0;
    private Double lastUtility = 0.0;
    private Actions policy = null;
    Square upSquare = null;
    Square rightSquare = null;
    Square downSquare = null;
    Square leftSquare = null;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //Return the neighbour square according to action/policy.
    public Square getNeighbour(Actions action) {
        Square neighbour = new Square(-1,-1);
        switch (action) {
            case UP:
                neighbour = upSquare;
                break;
            case RIGHT:
                neighbour = rightSquare;
                break;
            case DOWN:
                neighbour = downSquare;
                break;
            case LEFT:
                neighbour = leftSquare;
                break;
            default:
                System.out.println("Get neighbour error.");
        }
        if (neighbour==null || neighbour.isWall()){
            //If wall or out of bound, return utility of current square.
            neighbour = new Square(-1,-1);
            neighbour.setUtility(this.getUtility());
        }
        return neighbour;
    }

    public boolean isWall() {
        return this.color.equals(Const.WALL);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }

    public Double getUtility() {
        return utility;
    }

    public void setUtility(Double utility) {
        this.utility = utility;
    }

    public Double getLastUtility() {
        return lastUtility;
    }

    public void setLastUtility(Double lastUtility) {
        this.lastUtility = lastUtility;
    }

    public Actions getPolicy() {
        return policy;
    }

    public void setPolicy(Actions policy) {
        this.policy = policy;
    }
}
