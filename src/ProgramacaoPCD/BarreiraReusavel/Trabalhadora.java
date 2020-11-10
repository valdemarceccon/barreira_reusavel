package ProgramacaoPCD.BarreiraReusavel;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Trabalhadora extends Thread {

    private ArrayList<String> listaArquivos;
    private Semaphore mutex;
    private Semaphore barreiraEntrada;
    private Semaphore barreiraSaida;
    private Semaphore naoCombinadora;

    public Trabalhadora(ArrayList<String> lista, Semaphore mutex, Semaphore barreiraEntrada, Semaphore barreiraSaida,
            Semaphore naoCombinadora) {
        this.listaArquivos = lista;
        this.mutex = mutex;
        this.barreiraEntrada = barreiraEntrada;
        this.barreiraSaida = barreiraSaida;
        this.naoCombinadora = naoCombinadora;
    }

    public void run() {
        try {
            while (true) {
                ListaInteiros lista = new ListaInteiros();
                lista.popular();
                String nomeDesordenado = criarArquivo(lista, "desordenado");
                lista.ordenar();
                String nomeOrdenado = criarArquivo(lista, "ordenado");
                System.out.println("Arquivo criado pela: " + this.getName());
                mutex.acquire();

                Main.cont++;
                if (Main.cont == Main.MAX_TRABALHADORAS) {
                    barreiraSaida.acquire();
                    barreiraEntrada.release();
                }
                mutex.release();

                barreiraEntrada.acquire(); 
                listaArquivos.add(nomeOrdenado);

                barreiraEntrada.release();
                mutex.acquire();

                Main.cont--;
                if (Main.cont == 0) {
                    barreiraEntrada.acquire();
                    barreiraSaida.release();
                }
                mutex.release();

                barreiraSaida.acquire();
                naoCombinadora.acquire();
                barreiraSaida.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String criarArquivo(ListaInteiros arquivo, String Nome) throws IOException {
        String output = String.format("%s_%s.txt", this.getName(), Nome);
        ManipularArquivo.salvar(output, arquivo);
        return output;
    }
}
