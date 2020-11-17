package ProgramacaoPCD.BarreiraReusavel;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class Main {

    public static final int MAX_TRABALHADORAS = 4;
    public static int contador = 0;
    public static int contadorUUID = 0;

    public static void main(String[] args) {

        Semaphore mutexInsercaoLista = new Semaphore(1);
        Semaphore mutexContador = new Semaphore(1);
        Semaphore barreiraEntrada = new Semaphore(0);
        Semaphore barreiraSaida = new Semaphore(1);
        Semaphore semaforoCombinadora = new Semaphore(0);


        ArrayList<String> listaArquivos = new ArrayList<>();

        Trabalhadora trabalhadora1 =
                new Trabalhadora(listaArquivos, barreiraEntrada, barreiraSaida, mutexInsercaoLista, mutexContador, semaforoCombinadora);
        Trabalhadora trabalhadora2 =
                new Trabalhadora(listaArquivos, barreiraEntrada, barreiraSaida, mutexInsercaoLista, mutexContador, semaforoCombinadora);
        Trabalhadora trabalhadora3 =
                new Trabalhadora(listaArquivos, barreiraEntrada, barreiraSaida, mutexInsercaoLista, mutexContador, semaforoCombinadora);
        Trabalhadora trabalhadora4 =
                new Trabalhadora(listaArquivos, barreiraEntrada, barreiraSaida, mutexInsercaoLista, mutexContador, semaforoCombinadora);

        Combinadora combinadora = new Combinadora(listaArquivos, semaforoCombinadora);

        trabalhadora1.start();
        trabalhadora2.start();
        trabalhadora3.start();
        trabalhadora4.start();
        combinadora.start();
    }

}
