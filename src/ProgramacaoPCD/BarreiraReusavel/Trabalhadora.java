package ProgramacaoPCD.BarreiraReusavel;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Trabalhadora extends Thread {

    private final ArrayList<String> listaArquivos;
    private final Semaphore barreiraEntrada;
    private final Semaphore barreiraSaida;
    private final  Semaphore mutexInsercaoLista;
    private final  Semaphore mutexContador;

    public Trabalhadora(ArrayList<String> lista, Semaphore barreiraEntrada,
                        Semaphore barreiraSaida,
                        Semaphore mutexInsercaoLista, Semaphore mutexContador) {

        this.listaArquivos = lista;

        this.barreiraEntrada = barreiraEntrada;
        this.barreiraSaida = barreiraSaida;
        this.mutexInsercaoLista = mutexInsercaoLista;
        this.mutexContador = mutexContador;
    }

    public void run() {
        try {
            while (true) {
                barreiraEntrada();

                ListaInteiros lista = new ListaInteiros();
                lista.popular();
                criarArquivo(lista, "desordenado");
                lista.ordenar();
                String nomeOrdenado = criarArquivo(lista, "ordenado");
                System.out.println("Arquivo criado pela: " + this.getNome());

                inserirNaFilaDeArquivos(nomeOrdenado);
                barreiraSaida();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirNaFilaDeArquivos(String nomeOrdenado) {
        try {
            System.out.println("Esperando para colocar o nome da lista" + getNome());

            mutexInsercaoLista.acquire();

            listaArquivos.add(nomeOrdenado);

            mutexInsercaoLista.release();

            System.out.println("Arquivo inserido na lista" + getNome());

        } catch (Exception e) {
            System.out.println("Erro no mutex");
        }
    }

    public void barreiraEntrada() {
        try {
            mutexContador.acquire();
            Main.contador++;
            if (Main.contador == Main.MAX_TRABALHADORAS) {
                barreiraSaida.acquire(); //fecha
                barreiraEntrada.release(); //abre
            }
            barreiraEntrada.acquire();
            barreiraEntrada.release();
            mutexContador.release();

        } catch (Exception e) {
            System.out.println("Erro no mutex");
        }

    }

    public void barreiraSaida() {
        try {
            mutexContador.acquire();
            Main.contador--;
            if (Main.contador == 0) {
                barreiraEntrada.acquire(); //fecha
                barreiraSaida.release(); //abre
            }
            mutexContador.release();
            barreiraSaida.acquire();
            barreiraSaida.release();

        } catch (Exception e) {
            System.out.println("Erro no mutex");
        }

    }

    private String criarArquivo(ListaInteiros arquivo, String Nome) throws IOException {
        String output = String.format("%s_%s.txt", this.getNome(), Nome);
        ManipularArquivo.salvar(output, arquivo);
        return output;
    }

    public String getNome() {
        return String.format("[%s]", this.getName());
    }

}
