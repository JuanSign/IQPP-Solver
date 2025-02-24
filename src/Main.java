import java.util.Scanner;

class Main{
    public static void main(String[] args){
        State state = new State();

        System.out.println("IQPP Solver.");
        System.out.printf("Enter input file name (.txt) : ");

        Scanner scanner = new Scanner(System.in);
        String inputFileName = scanner.nextLine();
        scanner.close();

        if(inputFileName.trim().isEmpty()){
            System.err.println("[ERROR][IO] : Please provide an input file name!");
            return;
        }else if(!inputFileName.endsWith(".txt")){
            System.err.println("[ERROR][IO] : input file must be a .txt file!");
            return;
        }
        
        final String RESET = "\u001B[0m";  // Reset color
        final String[] COLORS = {
            "\u001B[31m", // A - Red
            "\u001B[32m", // B - Green
            "\u001B[33m", // C - Yellow
            "\u001B[34m", // D - Blue
            "\u001B[35m", // E - Magenta
            "\u001B[36m", // F - Cyan
            "\u001B[91m", // G - Bright Red
            "\u001B[92m", // H - Bright Green
            "\u001B[93m", // I - Bright Yellow
            "\u001B[94m", // J - Bright Blue
            "\u001B[95m", // K - Bright Magenta
            "\u001B[96m", // L - Bright Cyan
            "\u001B[97m", // M - White
            "\u001B[90m", // N - Dark Gray
            "\u001B[41m", // O - Red Background
            "\u001B[42m", // P - Green Background
            "\u001B[43m", // Q - Yellow Background
            "\u001B[44m", // R - Blue Background
            "\u001B[45m", // S - Magenta Background
            "\u001B[46m", // T - Cyan Background
            "\u001B[100m", // U - Bright Gray Background
            "\u001B[101m", // V - Bright Red Background
            "\u001B[102m", // W - Bright Green Background
            "\u001B[103m", // X - Bright Yellow Background
            "\u001B[104m", // Y - Bright Blue Background
            "\u001B[105m"  // Z - Bright Magenta Background
        };

        try{
            state.ReadInput(inputFileName);
            long startTime = System.nanoTime(); 
            boolean solved = state.Solve(0);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000;
            if(solved){
                for(int i = 0; i < state.board.length; i++){
                    for(int j = 0; j < state.board[i].length; j++){
                        System.out.printf("%s%c%s|", COLORS[state.board[i][j] - 'A'], state.board[i][j], RESET);
                    }
                    System.out.println();
                }
                System.out.println();
                BoardImageGenerator.generateImage(state.board, String.format("test\\solutions\\%s.png", inputFileName.substring(0, inputFileName.length()-4)));
            }else{
                System.out.println("No solutions.");
            }
            System.out.println("Execution time: " + duration + " ms");
            System.out.println("Combinations tried : " + state.combinations);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}