package ProgramacaoPCD.BarreiraReusavel;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Combinadora extends Thread {

    private Semaphore mutex, barreiraEntrada, barreiraSaida;
    private ArrayList<String> listaDeArquivos;

    public Combinadora(ArrayList<String> listaDeArquivos, Semaphore mutex,
                       Semaphore barreiraEntrada, Semaphore barreiraSaida) {
        this.listaDeArquivos = listaDeArquivos;
        this.mutex = mutex;
        this.barreiraEntrada = barreiraEntrada;
        this.barreiraSaida = barreiraSaida;
    }

    public void run() {
        try {
            while (true) {
                ListaInteiros todosDadosArquivos = carregarArquivos();
                criarArquivo(todosDadosArquivos, "MERGE");
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
