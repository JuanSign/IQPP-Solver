package structs;

import java.util.ArrayList;
import java.util.List;

public class Piece{
    char id;
    List<List<Integer>> content;

    public Piece(List<String> rawPiece){
        id = rawPiece.get(0).charAt(0);
        content = Compress(rawPiece);
        System.out.println("Identifier : " + id);
        for(List<Integer> li : content){
            for(Integer i : li){
                System.out.printf("%d ", i);
            }
            System.out.println();
        }
    }

    private List<List<Integer>> Compress(List<String> rawPiece){
        List<List<Integer>> compressed = new ArrayList<>();
        int trailingSpace = Integer.MAX_VALUE;

        for(int i = 0; i < rawPiece.size(); i++){
            compressed.add(new ArrayList<>());
            
            String curString = rawPiece.get(i);
            int curLength = curString.length();
            int cur = 0;
            boolean space = true;

            for(int j = 0; j < curLength; j++){
                if(space){
                    if(curString.charAt(j) == ' '){
                        cur++;
                    }else{
                        compressed.get(i).add(cur);
                        cur = 1;
                        space = false;
                    }
                }else{
                    if(curString.charAt(j) != ' '){
                        cur++;
                    }else{
                        compressed.get(i).add(cur);
                        cur = 1;
                        space = true;
                    }
                }
            }
            if(!space){
                compressed.get(i).add(cur);
            }
            trailingSpace = Math.min(trailingSpace, compressed.get(i).get(0));
        }
        for(int i = 0; i < rawPiece.size(); i++){
            compressed.get(i).set(0, compressed.get(i).get(0)-trailingSpace);
        }

        return compressed;
    }
}