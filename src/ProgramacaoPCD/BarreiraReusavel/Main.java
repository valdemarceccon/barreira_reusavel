package ProgramacaoPCD.BarreiraReusavel;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {

        public static final int MAX_TRABALHADORAS = 4;
        public static int cont = 0;

        public static void main(String[] args) {

                Semaphore mutex = new Semaphore(1);
                Semaphore barreiraEntrada = new Semaphore(0);
                Semaphore barreiraSaida = new Semaphore(1);
                Semaphore naoCombinadora = new Semaphore(0);

                //-------------------------------------------------------------------

                ArrayList<String> listaArquivos = new ArrayList<>();

                Trabalhadora trabalhadora1 = new Trabalhadora(listaArquivos, mutex,
                                barreiraEntrada, barreiraSaida, naoCombinadora);

                Trabalhadora trabalhadora2 = new Trabalhadora(listaArquivos, mutex,
                                barreiraEntrada, barreiraSaida, naoCombinadora);
                Trabalhadora trabalhadora3 = new Trabalhadora(listaArquivos, mutex,
                                barreiraEntrada, barreiraSaida, naoCombinadora);
                Trabalhadora trabalhadora4 = new Trabalhadora(listaArquivos, mutex,
                                barreiraEntrada, barreiraSaida, naoCombinadora);

                Combinadora combinadora = new Combinadora(listaArquivos, mutex, barreiraEntrada,
                                barreiraSaida, naoCombinadora);

                //-------------------------------------------------------------------

                trabalhadora1.start();
                trabalhadora2.start();
                trabalhadora3.start();
                trabalhadora4.start();
                combinadora.start();
        }
}
