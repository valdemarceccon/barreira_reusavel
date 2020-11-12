package ProgramacaoPCD.BarreiraReusavel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManipularArquivo {

    public static void salvar(String nome, ListaInteiros objeto) throws IOException {
        final Path arquivo = Paths.get(nome);
        try (final OutputStream outputStream = Files.newOutputStream(arquivo)) {
            for (Integer integer : objeto.getList()) {
                outputStream.write(String.format("%d\n", integer).getBytes());
            }
        }
    }

    public static ListaInteiros abrir(String nome) throws IOException {
        final Path arquivo = Paths.get(nome);
        ListaInteiros lista = new ListaInteiros();

        for (String num : Files.readAllLines(arquivo)) {
            if (!num.trim().isEmpty()) {
                lista.getList().add(Integer.parseInt(num));
            }
        }

        return lista;
    }
}
