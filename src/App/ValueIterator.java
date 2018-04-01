package App;

import Models.Actions;
import Models.MazeMap;
import Models.Square;
import Utils.Const;
import Utils.FileIO;
import Utils.helpFunctions;

import java.util.Arrays;
import java.util.Map;

public class ValueIterator {

    private MazeMap mazeMap;
    private helpFunctions utils = new helpFunctions();

    public ValueIterator(MazeMap mazeMap) {
        this.mazeMap = mazeMap;
    }

    public void run() {
        //Start the simulation of value iteration.
        final Double ZERO = 0.0;
        //Array to store the utilities calculated in one iteration.
        Double[][] newUtilities = new Double[mazeMap.rows][mazeMap.cols];
        for (int row = 0; row < mazeMap.rows; row++) {
            Arrays.fill(newUtilities[row], ZERO);
        }

        //Prepare csv file to record iteration process.
        FileIO fileIO = new FileIO("result\\valueIteration.csv");
        fileIO.writeHead(mazeMap);
        Double delta;
        //Max is one entry map stores policy and corresponding utility of current square.
        Map.Entry<Actions, Double> max;
        int counter = 0;
        //Value iteration.
        do {
            utils.updateUtilities(mazeMap, newUtilities);
            delta = ZERO;
            for (Square rows[] : mazeMap.getMap()) {
                for (Square square : rows) {
                    if (square.isWall())
                        continue;
                    //Calculate max possible utility of current square.
                    max = utils.maxUtility(square);
                    newUtilities[square.getRow()][square.getCol()] =
                            square.getReward() + Const.DISCOUNT * max.getValue();
                    //Update optimal policy.
                    square.setPolicy(max.getKey());
                    //Calculate and update delta.
                    delta = utils.updateDelta(square, newUtilities[square.getRow()][square.getCol()], delta);
                }
            }
            counter++;
            //Record the new utilities to file.
            fileIO.record(newUtilities);
            //Check utilities after 50 iteration.
            if (counter==78) {
                System.out.println("Counter: "+counter);
                mazeMap.printMap(Const.MODE_POLICIES);
                mazeMap.printMap(Const.MODE_UTILITIES);
            }
            //Stop when update delta < tolerance.
        } while (counter<1000 && delta >= Const.EPSILON * (1 - Const.DISCOUNT)/Const.DISCOUNT);
        System.out.printf("Counter:%d\n",counter);
    }
}
