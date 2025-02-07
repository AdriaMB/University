import java.util.*;
import java.io.*;

public class HM{
    public static void main(String[]args){
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        String[] characters = input.split("x");
        //System.out.println(input.length());

        for(int i = 0; i < characters.length; i++){
            System.out.println(characters[i]);

        }

    }


}
