package ProgramacaoPCD.BarreiraReusavel;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {

        public static final int MAX_TRABALHADORAS = 4;
        public static int contador = 0;

        public static void main(String[] args) {

                Semaphore mutexInsercaoLista = new Semaphore(1);
                Semaphore mutexContador = new Semaphore(1);
                Semaphore barreiraEntrada = new Semaphore(0);
                Semaphore barreiraSaida = new Semaphore(1);


                ArrayList<String> listaArquivos = new ArrayList<>();

                Trabalhadora trabalhadora1 =
                        new Trabalhadora(listaArquivos, barreiraEntrada, barreiraSaida,mutexInsercaoLista,mutexContador );

                Trabalhadora trabalhadora2 =
                        new Trabalhadora(listaArquivos, barreiraEntrada,
                                barreiraSaida,mutexInsercaoLista,mutexContador );
                Trabalhadora trabalhadora3 =
                        new Trabalhadora(listaArquivos, barreiraEntrada, barreiraSaida,mutexInsercaoLista,mutexContador );
                Trabalhadora trabalhadora4 =
                        new Trabalhadora(listaArquivos, barreiraEntrada,
                                barreiraSaida,mutexInsercaoLista,mutexContador );

                Combinadora combinadora =
                        new Combinadora(listaArquivos, mutexInsercaoLista, barreiraEntrada, barreiraSaida);

                //-------------------------------------------------------------------

                trabalhadora1.start();
                trabalhadora2.start();
                trabalhadora3.start();
                trabalhadora4.start();
                combinadora.start();
        }
}
