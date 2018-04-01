package Models;

import Utils.Const;
import Utils.helpFunctions;

/*
  Mazemap class. Store the map with squares.
 */
public class MazeMap {

    private Square[][] map;
    public int rows;
    public int cols;

    public MazeMap(int rows, int cols, Boolean defaultMode) {
        map = new Square[rows][cols];
        this.rows = rows;
        this.cols = cols;
        //Initialize the map with white square and UP policy.
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                map[row][col] = new Square(row, col);
                setSquare(row+","+col, Const.WHITE, Const.WHITE_REWARD);
                map[row][col].setPolicy(Actions.UP);
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                //Set up neighbours for each square.
                try {
                    map[row][col].upSquare = row > 0 ? map[row - 1][col] : null;
                    map[row][col].rightSquare = col < cols - 1 ? map[row][col + 1] : null;
                    map[row][col].downSquare = row < rows - 1 ? map[row + 1][col] : null;
                    map[row][col].leftSquare = col > 0 ? map[row][col - 1] : null;
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
        //Load default map.
        if (defaultMode) {
            setMap(Const.GREEN_SQUARES.split("/"),
                    Const.BROWN_SQUARES.split("/"),
                    Const.WALL_SQUARES.split("/"));
            printMap(Const.MODE_ORIGIN);
        }
    }


    public void backUp() {
        helpFunctions utils = new helpFunctions();
        for (Square rows[] : map) {
            for (Square square : rows) {
                if (!square.isWall())
                    square.setLastUtility(utils.calUtilities(square, square.getPolicy()));
            }
        }
    }

    public void rollBackUtilities() {
        for (Square rows[] : map) {
            for (Square square : rows) {
                square.setUtility(square.getLastUtility());
            }
        }
    }

    public Square[][] getMap() {
        return map;
    }

    public void setMap(String[] greenSquares, String[] brownSquares, String[] wallSquares) {
        //Set reward for each type of squares.
        for (String square: greenSquares) {
            setSquare(square, Const.GREEN, Const.GREEN_REWARD);
        }
        for (String square: brownSquares) {
            setSquare(square, Const.BROWN, Const.BROWN_REWARD);
        }
        for (String square: wallSquares) {
            setSquare(square, Const.WALL, Const.WALL_REWARD);
        }
    }

    private void setSquare(String position, String color, Double reward) {
        //Set color and reward for one square.
        int row = Integer.parseInt(position.substring(0,1));
        int col = Integer.parseInt(position.substring(2));
        map[row][col].setColor(color);
        map[row][col].setReward(reward);
        if (color.equals(Const.WALL))
            map[row][col].setPolicy(null);
    }

    public void printMap(String mode) {
        //Print map with different mode.
        StringBuilder printer = new StringBuilder();
        printer.append("Print map Mode: ").append(mode).append("\n");
        switch (mode) {
            case Const.MODE_ORIGIN:
                //Print original map
                for (int m = 0; m < cols; m++) {
                    printer.append("-----");
                }
                printer.append("-\n");
                for (int i = 0; i < cols; i++) {
                    printer.append("|");
                    for (int j = 0; j < rows; j++) {
                        if (map[i][j].getColor().equals(Const.WALL)){
                            printer.append(String.format(" %-2s |", "WW"));
                        }
                        else if (map[i][j].getColor().equals(Const.WHITE)){
                            printer.append(String.format(" %-2s |", "  "));
                        }
                        else{
                            printer.append(String.format(" %+1.0f |", map[i][j].getReward()));
                        }
                    }
                    printer.append("\n");
                    for (int m = 0; m < cols; m++) {
                        printer.append("-----");
                    }
                    printer.append("-\n");
                }
                break;
            case Const.MODE_UTILITIES:
                //Print map with current utilities.
                for (int m = 0; m < cols; m++) {
                    printer.append("---------");
                }
                printer.append("-\n");
                for (int i = 0; i < cols; i++) {
                    printer.append("|");
                    for (int j = 0; j < rows; j++) {
                        if (map[i][j].getColor().equals(Const.WALL))
                            printer.append(String.format(" %-6s |", " Wall"));
                        else
                            printer.append(String.format(" %-6.3f |", map[i][j].getUtility()));

                    }
                    printer.append("\n");
                    for (int m = 0; m < cols; m++) {
                        printer.append("---------");
                    }
                    printer.append("-\n");
                }
                break;
            case Const.MODE_POLICIES:
                //Print map with current policies.
                for (int m = 0; m < cols; m++) {
                    printer.append("--------");
                }
                printer.append("-\n");
                for (int i = 0; i < cols; i++) {
                    printer.append("|");
                    for (int j = 0; j < rows; j++) {
                        if (map[i][j].getColor().equals(Const.WALL))
                            printer.append(String.format(" %-5s |", "Wall"));
                        else
                            printer.append(String.format(" %-5s |", map[i][j].getPolicy()));
                    }
                    printer.append("\n");
                    for (int m = 0; m < cols; m++) {
                        printer.append("--------");
                    }
                    printer.append("-\n");
                }
                break;
            default:
                printer.append("Wrong mode.\n");
        }
        System.out.print(printer.toString());
    }
}
