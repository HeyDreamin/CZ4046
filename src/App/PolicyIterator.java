package App;

import Models.Actions;
import Models.MazeMap;
import Models.Square;
import Utils.Const;
import Utils.FileIO;
import Utils.helpFunctions;
import java.util.Map;

public class PolicyIterator {

    private MazeMap mazeMap;
    private helpFunctions utils = new helpFunctions();

    public PolicyIterator(MazeMap mazeMap) {
        this.mazeMap = mazeMap;
    }

    public void run(Boolean custom) {
        //Start simulation of policy iteration
        Boolean change;
        int counter = 0;
        //Max is one entry map stores policy and corresponding utility of current square.
        Map.Entry<Actions, Double> max;
        //Prepare csv file to record iteration process.
        FileIO fileIO;
        if (custom){
             fileIO = new FileIO("result\\customIteration.csv");
        }
        else{
            fileIO = new FileIO("result\\policyIteration.csv");
        }
        fileIO.writeHead(mazeMap);
        do {
            //Policy iteration.
            policyEvaluation(mazeMap, fileIO);
            change = false;
            for (Square rows[] : mazeMap.getMap()) {
                for (Square square : rows) {
                    if (square.isWall())
                        continue;
                    //Calculate max possible utility of current square.
                    max = utils.maxUtility(square);
                    if (max.getValue() > utils.calUtilities(square, square.getPolicy())){
                        //Update optimal policy.
                        square.setPolicy(max.getKey());
                        change = true;
                    }
                }
            }
            if (change)
                mazeMap.backUp();
            counter++;
        } while (change && counter<100);
        mazeMap.rollBackUtilities();
        System.out.printf("Counter:%d\n",counter);
    }

    private void policyEvaluation(MazeMap mazeMap, FileIO fileIO) {
        // Number(K) of simplified value iteration steps with fixed policy
        // to give a reasonably good approximation of the utilities.
        Double[][] newUtilities = new Double[mazeMap.rows][mazeMap.cols];
        for (int k = 0; k < Const.K; k++) {
            for (int row = 0; row < mazeMap.rows; row++) {
                for (int col = 0; col < mazeMap.cols; col++) {
                    Square square = mazeMap.getMap()[row][col];
                    if (!square.isWall())
                        newUtilities[row][col] = square.getReward() +
                                Const.DISCOUNT * utils.calUtilities(square, square.getPolicy());
                }
            }
            //Update utilities after each step.
            utils.updateUtilities(mazeMap, newUtilities);
        }
        //Record utilities after every time of evaluation.
        fileIO.record(newUtilities);
    }
}
