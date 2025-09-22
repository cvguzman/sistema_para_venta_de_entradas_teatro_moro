import java.util.Timer;
import  java.util.TimerTask;

public class Temporizador {
    private Timer timer;
    private final long tiempoLimite = 2*60*1000;
    public boolean tiempoAgotado = false;

    public void iniciar() {
        tiempoAgotado = false;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tiempoAgotado = true;
                timer.cancel();
            }
        }, tiempoLimite);
    }

    public void apagar() {
        timer.cancel();
    }
}
