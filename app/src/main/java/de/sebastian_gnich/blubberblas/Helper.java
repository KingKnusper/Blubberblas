package de.sebastian_gnich.blubberblas;

import java.util.Random;

/**
 * Created by GnichS on 16.12.2015.
 */
abstract public class Helper {

    static int randomNumber(int first, int second)
    {
        Random r = new Random();
        int bigger = Math.max(first, second);
        int smaller = Math.min(first, second);
        return r.nextInt(bigger - smaller + 1) + smaller;
    }

}
