package ProgramacaoPCD.BarreiraReusavel;

import java.util.Random;

public class GerarInteiro {

    private static Random r = new Random();

    public static int gerar(int bound) {
        return r.nextInt(bound + 1);
    }

}
