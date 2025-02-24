import java.lang.StringBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Piece{
    public List<char[][]> contents;
    public char identifier;

    public Piece(List<String> rawPiece) {
        identifier = rawPiece.get(0).trim().charAt(0);

        int height = rawPiece.size();
        int width = rawPiece.stream()
                         .mapToInt(String::length)
                         .max()
                         .orElse(0); 
        
        char[][] matrix = new char[height][width];
        for(int i = 0; i < height; i++){
            Arrays.fill(matrix[i], ' ');
            for(int j = 0; j < rawPiece.get(i).length(); j++){
                matrix[i][j] = rawPiece.get(i).charAt(j);
            }
        }

        contents = GenerateTransformation(matrix);
    }

    private static List<char[][]> GenerateTransformation(char[][] matrix) {
        Set<String> seen = new HashSet<>();
        List<char[][]> uniqueTransformations = new ArrayList<>();

        char[][] current = matrix;
        for (int i = 0; i < 4; i++) {
            String key = MatrixToString(current);
            if (seen.add(key)) {
                uniqueTransformations.add(current);
            }
            current = Rotate(current);
        }

        current = Mirror(matrix);
        for (int i = 0; i < 4; i++) {
            String key = MatrixToString(current);
            if (seen.add(key)) {
                uniqueTransformations.add(current);
            }
            current = Rotate(current);
        }

        return uniqueTransformations;
    }

    private static char[][] Rotate(char[][] matrix) {
        int N = matrix.length, M = matrix[0].length;
        char[][] rotated = new char[M][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                rotated[c][N - 1 - r] = matrix[r][c];
            }
        }
        return rotated;
    }

    private static char[][] Mirror(char[][] matrix) {
        int N = matrix.length, M = matrix[0].length;
        char[][] mirrored = new char[N][M];
        for (int r = 0; r < N; r++) {
            mirrored[r] = matrix[N - 1 - r].clone();
        }
        return mirrored;
    }

    private static String MatrixToString(char[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : matrix) {
            sb.append(new String(row)).append("\n");
        }
        return sb.toString();
    }
}