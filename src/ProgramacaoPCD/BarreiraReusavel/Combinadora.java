package ProgramacaoPCD.BarreiraReusavel;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;
/// Combinadora fora da barreira
public class Combinadora extends Thread {

    private Semaphore mutex;
    private Semaphore barreiraEntrada;
    private Semaphore barreiraSaida;
    private Semaphore mutexContador;
    private ArrayList<String> listaDeArquivos;

    public Combinadora(ArrayList<String> listaDeArquivos, Semaphore mutex,
                       Semaphore barreiraEntrada, Semaphore barreiraSaida,
                       Semaphore mutexContador) {
        this.listaDeArquivos = listaDeArquivos;
        this.mutex = mutex;
        this.barreiraEntrada = barreiraEntrada;
        this.barreiraSaida = barreiraSaida;
        this.mutexContador = mutexContador;
    }

    public void run() {
        try {
            while (true) {

                barreiraEntrada.acquire();
                barreiraEntrada.release();

                // TRABALHO
                System.out.println("Combinadora trabalhando");
                ListaInteiros todosDadosArquivos = carregarArquivos();
                listaDeArquivos.clear();
                criarArquivo(todosDadosArquivos, "MERGE" + Main.contadorUUID);
                // TRABALHO

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ListaInteiros carregarArquivos() throws IOException {
        ListaInteiros total = new ListaInteiros();
        for (String nome : listaDeArquivos) {
            System.out.println(this.getName() + " carregando...");
            ListaInteiros arquivo = ManipularArquivo.abrir(nome);
            total.getList().addAll(arquivo.getList());
        }
        total.ordenar();
        return total;
    }

    private void criarArquivo(ListaInteiros arquivo, String nome) throws IOException {
        String output = String.format("%s.txt",  nome);
        ManipularArquivo.salvar(output, arquivo);
    }
}
