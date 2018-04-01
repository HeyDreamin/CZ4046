package Utils;

public class Const {

    // Size of the MazeMap map
    public static int COLS = 6;
    public static int ROWS = 6;

    public static String GREEN = "Green";
    public static String BROWN = "Brown";
    public static String WALL = "Wall";
    public static String WHITE = "White";

    // MazeMap map information in (row, col)
    public static final String GREEN_SQUARES = "0,0/0,2/0,5/1,3/2,4/3,5";
    public static final String BROWN_SQUARES = "1,1/1,5/2,2/3,3/4,4";
    public static final String WALL_SQUARES = "0,1/1,4/4,1/4,2/4,3";

    // Reward of each square
    public static final Double WHITE_REWARD = -0.04;
    public static final Double GREEN_REWARD = +1.0;
    public static final Double BROWN_REWARD = -1.0;
    public static final Double WALL_REWARD = 0.0;

    // Transition model
    static final Double PROB_FORWARD = 0.8;
    static final Double PROB_LEFT = 0.1;
    static final Double PROB_RIGHT = 0.1;

    // Discount factor
    public static final Double DISCOUNT =  0.99;

    // Max reward
    private static final Double MAX_REWARD = 1.0;

    // Constant C
    private static final Double C = 0.1;

    // Epsilon = c * maxReward
    public static final Double EPSILON = C * MAX_REWARD;

    // Constant k for policy iteration
    public static final int K = 10;

    //Map print mode
    public static final String MODE_ORIGIN = "Origin";
    public static final String MODE_UTILITIES = "Utilities";
    public static final String MODE_POLICIES = "Policies";
}
