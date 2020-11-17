package ProgramacaoPCD.BarreiraReusavel;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;
/// Combinadora fora da barreira
public class Combinadora extends MaeDeTodasAsThreads {

    private final ArrayList<String> listaDeArquivos;
    private final Semaphore semaforoCombinadora;

    public Combinadora(ArrayList<String> listaDeArquivos, Semaphore semaforoCombinadora) {
        this.listaDeArquivos = listaDeArquivos;
        this.semaforoCombinadora = semaforoCombinadora;
    }

    public void run() {
        try {
            while (true) {

                semaforoCombinadora.acquire(4);

                // TRABALHO
                threadPrint("\nCombinadora trabalhando");
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
            threadPrint(nome + " carregando...");
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
