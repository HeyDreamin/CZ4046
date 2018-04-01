package Main;

import App.PolicyIterator;
import App.ValueIterator;
import Models.MazeMap;
import Utils.Const;

public class Main {

    public static void main(String[] args) {
        System.out.println("Value iteration running");
        MazeMap valueMazeMap = new MazeMap(Const.ROWS,Const.COLS,true);
        ValueIterator valueIterator = new ValueIterator(valueMazeMap);
        valueIterator.run();
        valueMazeMap.printMap(Const.MODE_POLICIES);
        valueMazeMap.printMap(Const.MODE_UTILITIES);

        System.out.println("==============================\n" +
                            "Policy iteration running");
        MazeMap policyMazeMap = new MazeMap(Const.ROWS,Const.COLS,true);
        PolicyIterator policyIterator = new PolicyIterator(policyMazeMap);
        policyIterator.run(false);
        policyMazeMap.printMap(Const.MODE_POLICIES);
        policyMazeMap.printMap(Const.MODE_UTILITIES);

        System.out.println("==============================\n" +
                "Custom maze with policy iteration running");
        MazeMap custom = new Utils.FileIO("data\\custom.txt").readMazeFromFile();
        PolicyIterator customIterator = new PolicyIterator(custom);
        custom.getMap()[8][8].setReward(2.0);
        customIterator.run(true);
        custom.printMap(Const.MODE_POLICIES);
        custom.printMap(Const.MODE_UTILITIES);
    }
}
