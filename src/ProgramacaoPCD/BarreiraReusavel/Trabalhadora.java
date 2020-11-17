package ProgramacaoPCD.BarreiraReusavel;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Trabalhadora extends MaeDeTodasAsThreads {

    private final ArrayList<String> listaArquivos;
    private final Semaphore barreiraEntrada;
    private final Semaphore barreiraSaida;
    private final  Semaphore mutexInsercaoLista;
    private final  Semaphore mutexContador;
    private final Semaphore mutexCombinadora;
    private Semaphore mutexUUID;

    public Trabalhadora(ArrayList<String> lista,
                        Semaphore barreiraEntrada,
                        Semaphore barreiraSaida,
                        Semaphore mutexInsercaoLista,
                        Semaphore mutexContador,
                        Semaphore mutexCombinadora,
                        Semaphore mutexUUID) {

        this.listaArquivos = lista;

        this.barreiraEntrada = barreiraEntrada;
        this.barreiraSaida = barreiraSaida;
        this.mutexInsercaoLista = mutexInsercaoLista;
        this.mutexContador = mutexContador;
        this.mutexCombinadora = mutexCombinadora;
        this.mutexUUID = mutexUUID;
    }

    public void run() {
        try {
            while (true) {
                barreiraEntrada();

                ListaInteiros listaDesordenada = new ListaInteiros();

                listaDesordenada.popular();

                mutexUUID.acquire();
                Main.contadorUUID++;
                criarArquivo(listaDesordenada, "desordenado[" + Main.contadorUUID + "]");
                listaDesordenada.ordenar();
                String nomeOrdenado = criarArquivo(listaDesordenada, "ordenado[" + Main.contadorUUID + "]");
                mutexUUID.release();

                threadPrint("Arquivo criado");
                inserirNaFilaDeArquivos(nomeOrdenado);

                barreiraSaida();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void inserirNaFilaDeArquivos(String nomeOrdenado) {
        try {
            threadPrint("Esperando para colocar o nome da lista");

            mutexInsercaoLista.acquire();

            listaArquivos.add(nomeOrdenado);

            mutexInsercaoLista.release();
            
            threadPrint("Arquivo inserido na lista");

        } catch (Exception e) {
            threadPrint("Erro no mutex");
        }
    }

    public void barreiraEntrada() {
        try {
            mutexContador.acquire();

            Main.contador++;
            threadPrint("Na entrada");

            if (Main.contador == Main.MAX_TRABALHADORAS) {

                threadPrint("fechou saida, abriu entrada");
                barreiraSaida.acquire(); //fecha
                barreiraEntrada.release(); //abre
            }
            mutexContador.release();
            barreiraEntrada.acquire();
        barreiraEntrada.release();

        } catch (Exception e) {
            threadPrint("Erro no mutex");
        }

    }

    public void barreiraSaida() {
        try {
            mutexContador.acquire();
            Main.contador -= 1;

            threadPrint("Thread na saida");
            mutexCombinadora.release();
            threadPrint(mutexCombinadora.toString());

            if (Main.contador == 0) {
                barreiraEntrada.acquire(); //fecha
                barreiraSaida.release(); //abre
            }
            mutexContador.release();

            barreiraSaida.acquire();
            barreiraSaida.release();

        } catch (Exception e) {
            threadPrint("Erro no mutex");
        }

    }

    private String criarArquivo(ListaInteiros arquivo, String Nome) throws IOException {
        String output = String.format("%s_%s.txt", this.getNome(), Nome);
        ManipularArquivo.salvar(output, arquivo);
        return output;
    }


}
