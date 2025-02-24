import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class State {
    private int N, M, P;
    private String S;

    private List<Piece> pieces;
    public char[][] board;
    public int combinations = 0;

    public void ReadInput(String inputFileName) throws Exception {
        Path inputFilePath = Paths.get("test/inputs/", inputFileName);

        if (!Files.exists(inputFilePath)) {
            throw new IOException("[ERROR][IO] : Input file not found!");
        }

        List<String> inputFileContent;
        try {
            inputFileContent = Files.readAllLines(inputFilePath);
        } catch (IOException e) {
            throw new IOException("[ERROR][IO] : ERROR Reading file", e);
        }

        try {
            ParseInput(inputFileContent);
        } catch (Exception e){
            throw e;
        }
    }

    private void ParseInput(List<String> inputFileContent) throws Exception {
        if(inputFileContent.size() < 2){
            throw new Exception("[ERROR][INPUT] : Input must be at least two lines.");
        }

        String[] config = inputFileContent.get(0).trim().split("\\s+");
        if(config.length != 3){
            throw new Exception(String.format("[ERROR][INPUT] : Invalid input at line 1 [%s]", inputFileContent.get(0)));
        } 

        try{
            N = Integer.parseInt(config[0]);
            M = Integer.parseInt(config[1]);
            P = Integer.parseInt(config[2]);

            board = new char[N][M];
            for(int i = 0; i < N; i++){
                for(int j = 0; j < M; j++){
                    board[i][j] = ' ';
                }
            }
        }catch(NumberFormatException e){
            throw new Exception("[ERROR][INPUT] : N, M, P must be an integer!");
        }

        S = inputFileContent.get(1).trim();
        // TO DO : implement different cases
        // if(!Arrays.asList("DEFAULT", "CUSTOM", "PYRAMID").contains(SS)){
        //     throw new Exception("[ERROR][INPUT] : S must be one of [DEFAULT/CUSTOM/PYRAMID]");
        // }
        if(!S.equals("DEFAULT")){
            throw new Exception("[ERROR][INPUT] : S must be DEFAULT");
        }

        List<List<String>> rawPieces = new ArrayList<>();
        Set<Character> charSet = new HashSet<>();
        char curID = '\0';
        for(int i = 2; i < inputFileContent.size(); i++){
            String inputLine = inputFileContent.get(i);
            Set<Character> uniqueChar = new HashSet<>();
            for(char c : inputLine.toCharArray()){
                if(!Character.isWhitespace(c)){
                    uniqueChar.add(c);
                }
            }
            if(uniqueChar.size() != 1){
                throw new Exception(String.format("[ERROR][INPUT] : Invalid input at line %d [%s]", i+1, inputLine));
            }else{
                char id = uniqueChar.iterator().next();
                if(id == curID){
                    rawPieces.get(rawPieces.size()-1).add(inputLine);
                }else{
                    rawPieces.add(new ArrayList<>(List.of(inputLine)));
                    if(charSet.contains(id)){
                        throw new Exception(String.format("[ERROR][INPUT] : Duplicate piece identifier [%c]", id));
                    }else{
                        curID = id;
                        charSet.add(curID);
                    }
                }
            }
        }
        if(rawPieces.size() != P){
            throw new Exception(String.format("[ERROR][INPUT] : Given %d pieces but P is %d", rawPieces.size(), P));
        }

        pieces = new ArrayList<>();
        for(List<String> rawPiece : rawPieces){
            pieces.add(new Piece(rawPiece));
        }
    }

    public boolean Solve(int id){
        if(id == P) return true;
        for(char[][] p : pieces.get(id).contents){
            for(int i = 0; i <= N - p.length; i++){
                for(int j = 0; j <= M - p[0].length; j++){
                    if(Check(p, i, j)){
                        Put(p, i, j);
                        combinations++;
                        if(Solve(id+1)){
                            return true;
                        }
                        UnPut(p, i, j);
                    }
                }
            }
        }
        return false;
    }

    private boolean Check(char[][] p, int i, int j){
        for(int ii = 0; ii < p.length; ii++){
            for(int jj = 0; jj < p[ii].length; jj++){
                if(board[i+ii][j+jj] != ' ' && p[ii][jj] != ' '){
                    return false;
                }
            }
        }
        return true;
    }

    private void Put(char[][] p, int i, int j){
        for(int ii = 0; ii < p.length; ii++){
            for(int jj = 0; jj < p[ii].length; jj++){
                if(p[ii][jj] != ' '){
                    board[i+ii][j+jj] = p[ii][jj];
                }
            }
        }
    }

    private void UnPut(char[][] p, int i, int j){
        for(int ii = 0; ii < p.length; ii++){
            for(int jj = 0; jj < p[ii].length; jj++){
                if(p[ii][jj] != ' '){
                    board[i+ii][j+jj] = ' ';
                }
            }
        }
    }
   
}
