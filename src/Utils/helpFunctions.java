package Utils;

import Models.Actions;
import Models.MazeMap;
import Models.Square;

import java.util.HashMap;
import java.util.Map;

public class helpFunctions {

    public Double calUtilities(Square current, Actions action) {
        //Bellman equations for given square and policy.
        Double result = Const.PROB_FORWARD * current.getNeighbour(action).getUtility();
        result += Const.PROB_RIGHT * current.getNeighbour(Actions.turnTo(true, action)).getUtility();
        result += Const.PROB_LEFT * current.getNeighbour(Actions.turnTo(false, action)).getUtility();
        return result;
    }

    public Map.Entry<Actions, Double> maxUtility(Square current) {
        //Calculate max utility of given square.
        HashMap<Actions, Double> actionUtilities = new HashMap<>();
        actionUtilities.put(Actions.UP, calUtilities(current, Actions.UP));
        actionUtilities.put(Actions.RIGHT, calUtilities(current, Actions.RIGHT));
        actionUtilities.put(Actions.DOWN, calUtilities(current, Actions.DOWN));
        actionUtilities.put(Actions.LEFT, calUtilities(current, Actions.LEFT));

        Map.Entry<Actions, Double> maxEntry = null;
        //Get max (policy, utility) pair as an one entry map.
        for (Map.Entry<Actions, Double> entry : actionUtilities.entrySet()) {
            if (maxEntry == null || entry.getValue()>maxEntry.getValue())
                maxEntry = entry;
        }
        return maxEntry;
    }

    public void updateUtilities(MazeMap mazeMap, Double[][] newUtilities) {
        //Update utilities for whole map.
        for (Square rows[] : mazeMap.getMap()) {
            for (Square square : rows) {
                square.setUtility(newUtilities[square.getRow()][square.getCol()]);
            }
        }
    }

    public Double updateDelta(Square square, Double next, Double delta) {
        //Calculate and update delta.
        Double newDelta = Math.abs(square.getUtility() - next);
        return (newDelta > delta) ? newDelta : delta;
    }
}
