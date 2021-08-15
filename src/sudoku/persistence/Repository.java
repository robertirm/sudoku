package sudoku.persistence;

import sudoku.domain.SudokuGame;

import java.io.*;


public class Repository implements IRepository{
    private static final File GAME_DATA = new File(System.getProperty("persistence","gamedata.txt"));

    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try{
            FileOutputStream fileOut = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(game);
            objectOut.close();

        }catch (IOException e){
            throw new IOException("Can't access game data");
        }
    }

    @Override
    public SudokuGame getGameData() throws IOException {
        FileInputStream fileIn = new FileInputStream(GAME_DATA);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        try{
            SudokuGame gameState = (SudokuGame) objectIn.readObject();
            objectIn.close();
            return gameState;
        }catch(ClassNotFoundException  e) {
            throw new IOException("File not found");
        }
    }
}
