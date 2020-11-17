package ProgramacaoPCD.BarreiraReusavel;

public class MaeDeTodasAsThreads extends Thread{

    public String getNome() {
        return String.format("[%s]", this.getName());
    }
    void threadPrint(String texto) {
        System.out.println(getNome() + " diz: " + texto);

    }

}
