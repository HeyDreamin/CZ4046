package Utils;

import Models.MazeMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

    private String path;

    public FileIO(String path) {
        this.path = path;
    }

    // Function to write file.
    private void writeToFile(String str, Boolean append) {
        try {
            FileWriter fw = new FileWriter(path, append);
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Record utilities after one iteration.
    public void record(Double[][] utilities) {
        StringBuilder printer = new StringBuilder();
        for (int col = 0; col < utilities[0].length; col++) {
            for (int row = 0; row < utilities.length; row++) {
                String temp;
                try {
                    //If the temp is wall.
                    temp = utilities[col][row].toString();
                } catch (NullPointerException e) {
                    temp = "0.0";
                }
                temp = temp.substring(0, Math.min(temp.length(), 6));
                printer.append(temp);
                printer.append(",");
            }
        }
        //Delete last comma.
        printer.replace(printer.lastIndexOf(","),printer.length(),"\n");
        writeToFile(printer.toString(), true);
    }

    public void writeHead(MazeMap mazeMap) {
        // Write position information
        StringBuilder printer = new StringBuilder();
        for (int col = 0; col < mazeMap.cols; col++) {
            for (int row = 0; row < mazeMap.rows; row++) {
                printer.append("\"(").append(col).append(",").append(row).append(")\",");
            }
        }
        printer.replace(printer.lastIndexOf(","),printer.length(),"\n");
        writeToFile(printer.toString(),false);
    }

    public MazeMap readMazeFromFile(){
        MazeMap mazeMap = null;
        try {
            FileReader file = new FileReader(path);
            BufferedReader reader = new BufferedReader(file);
            String[] size = reader.readLine().split(",");
            String[] greenSquares = reader.readLine().split("/");
            String[] brownSquares = reader.readLine().split("/");
            String[] wallSquares = reader.readLine().split("/");
            reader.close();
            mazeMap = new MazeMap(Integer.parseInt(size[0]),Integer.parseInt(size[1]),false);
            mazeMap.setMap(greenSquares,brownSquares,wallSquares);
            mazeMap.printMap(Const.MODE_ORIGIN);
        } catch (Exception e){
            e.printStackTrace();
        }
        return mazeMap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
