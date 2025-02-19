package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class State {
    private int N, M, P;
    private String S;
    private List<String> fileContent; 

    public State() {
        fileContent = new ArrayList<>();
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
            }
        } else {
            System.err.println("File not found: " + filePath);
        }
    }
}
