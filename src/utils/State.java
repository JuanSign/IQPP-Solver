package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class State {
    private int N, M, P;
    private String S;

    private List<String> fileContent; 
    private List<String> errors;

    public State() {
        fileContent = new ArrayList<>();
        errors = new ArrayList<>();
    }

    public void ReadInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter file name (with extension): ");
        String inputPath = scanner.nextLine();
        scanner.close();

        Path filePath = Paths.get("test/cases", inputPath);

        if (Files.exists(filePath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line); 
                }
                System.out.println("File successfully read!");
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
                return;
            }
        } else {
            System.err.println("File not found: " + filePath);
            return;
        }

        ValidateConfig();
        ValidatePieces();
        
        if(errors.size() == 0){
            System.out.println("Input is valid.");
        }else{
            for(String error : errors){
                System.out.println(error);
            }
        }
    }

    private void ValidateConfig(){
        String configLine;
        try{
            configLine = fileContent.get(0);
        }catch (IndexOutOfBoundsException e){
            errors.add("ERROR - CONFIG : No N, M, P provided");
            return;
        }
        String[] configs = configLine.split("\\s+");
        
        if(configs.length != 3){
            errors.add("ERROR - CONFIG : Wrong format of configurations");
            return;
        }

        try{
            N = Integer.parseInt(configs[0]);
        } catch (NumberFormatException e) {
            errors.add("ERROR - CONFIG [N] : " + e.getMessage());
        }

        try{
            M = Integer.parseInt(configs[1]);
        } catch (NumberFormatException e) {
            errors.add("ERROR - CONFIG [M] : " + e.getMessage());
        }

        try{
            P = Integer.parseInt(configs[2]);
        } catch (NumberFormatException e) {
            errors.add("ERROR - CONFIG [P] : " + e.getMessage());
        }

        try{
            S = fileContent.get(1);
        }catch (IndexOutOfBoundsException e){
            errors.add("ERROR - CONFIG : No S provided");
            return;
        }
        if (!Arrays.asList("DEFAULT", "CUSTOM", "PYRAMID").contains(S)) {
            errors.add("ERROR - CONFIG [S] : Invalid cases " + S);
            return;
        } 
    }

    private void ValidatePieces(){
        if(fileContent.size() - 2 == 0){
            errors.add("ERROR - PIECES : No pieces provided.");
            return;
        }

        List<List<String>> rawPieces = new ArrayList<>();

        char id = '\0';

        for(int i = 2; i < fileContent.size(); i++){
            String pieceLine = fileContent.get(i);
            Set<Character> chars = new HashSet<>();
            for(char c : pieceLine.toCharArray()){
                if(!Character.isWhitespace(c)){
                    chars.add(c);
                }
            }
            if(chars.size() != 1){
                errors.add(String.format("ERROR - PIECES : Invalid piece format [%s] [Line %d]", pieceLine, i+1));
                return;
            }

            char curID = chars.iterator().next(); 
            if (id == curID) {
                rawPieces.get(rawPieces.size() - 1).add(pieceLine);
            } else {
                rawPieces.add(new ArrayList<>(List.of(pieceLine)));
                id = curID;
            }
        }

        if(rawPieces.size() != P){
            errors.add(String.format("ERROR - PIECES : P is %d, but given %d pieces.", P, rawPieces.size()));
        }
    }
}
