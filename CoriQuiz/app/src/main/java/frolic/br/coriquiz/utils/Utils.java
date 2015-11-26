package frolic.br.coriquiz.utils;

import java.util.Random;

/**
 * Created by ckilee on 26/11/15.
 */
public class Utils {
    public static int randInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min)+1)+min;
        return randomNum;
    }
}
