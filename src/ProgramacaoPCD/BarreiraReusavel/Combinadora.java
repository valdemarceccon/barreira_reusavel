package ProgramacaoPCD.BarreiraReusavel;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Combinadora extends Thread {

    private int counter = 0;
    private int arquivosRecebidos = 0;
    private Semaphore mutex, barreiraEntrada, barreiraSaida, naoCombinadora;
    private ArrayList<String> listaDeArquivos;

    public Combinadora(ArrayList<String> listaDeArquivos, Semaphore mutex, Semaphore barreiraEntrada,
            Semaphore barreiraSaida, Semaphore naoCombinadora) {
        this.listaDeArquivos = listaDeArquivos;
        this.mutex = mutex;
        this.barreiraEntrada = barreiraEntrada;
        this.barreiraSaida = barreiraSaida;
        this.naoCombinadora = naoCombinadora;
    }

    public void run() {
        try {
            barreiraSaida.acquire();
            List<Integer> todos = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListaInteiros carregarArquivos(ListaInteiros list) throws IOException, ClassNotFoundException {
        for (String nome : listaDeArquivos) {
            System.out.println(this.getName() + " carregando...");
            ListaInteiros arquivo = ManipularArquivo.abrir(nome);
            list.getList().removeAll(arquivo.getList());
            list.getList().addAll(arquivo.getList());
        }
        list.ordenar();
        return list;
    }
}
