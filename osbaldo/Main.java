
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


public class Main{

    public static void main(String[] args) {
        Tenedor[] tenedores = new Tenedor[5];
        Filosofo[] filosofos = new Filosofo[5];

        for(int i = 0; i < tenedores.length; i++){
            tenedores[i] = new Tenedor();
        }

        filosofos[0] = new Filosofo("Marco", tenedores[0], tenedores[1], ColorHilo.RED);
        filosofos[1] = new Filosofo("Osbaldo",tenedores[1], tenedores[2], ColorHilo.GREEN);
        filosofos[2] = new Filosofo("Maribel",tenedores[2], tenedores[3], ColorHilo.CYAN);
        filosofos[3] = new Filosofo("Sunny", tenedores[3], tenedores[4], ColorHilo.PURPLE);
        filosofos[4] = new Filosofo("Desiree", tenedores[4], tenedores[0], ColorHilo.YELLOW);

        (new Thread(filosofos[0])).start();
        (new Thread(filosofos[1])).start();
        (new Thread(filosofos[2])).start();
        (new Thread(filosofos[3])).start();
        (new Thread(filosofos[4])).start();

    }
}

class Tenedor{
    public ReentrantLock m = new ReentrantLock();
}

class Filosofo implements Runnable {
    private final String nombre;
    private Tenedor i;
    private Tenedor d;
	private String color;

    private int contador;

    public Filosofo(String nombre, Tenedor i, Tenedor d, String color){

        this.nombre = nombre;
        this.i = i;
        this.d = d;
		this.color = color;

        contador = 0;
    }


    public void comer(){
        Random random = new Random();
        try{
            i.m.lock();
            d.m.lock();
            try{
                System.out.println(color + "El filosofo " + nombre + " comenzo a comer " + Thread.currentThread().getName() );
                Thread.sleep(random.nextInt(2000));
                contador++;
                System.out.println(color + "El filosofo " +  nombre + " terminó de comer " +  contador +  " veces");
            }finally {
                i.m.unlock();
                d.m.unlock();
            }

        }catch(InterruptedException e){

        }
    }

    public void pensar(){
        Random random = new Random();
        try{
            System.out.println(color + "El filosofo " + nombre + " se puso a pensar");
            Thread.sleep(random.nextInt(2500));
        }catch (InterruptedException e){
            System.out.println(color + "El filosofo " + nombre + " fue interrumpido mientras pensaba");
        }
    }

    @Override
    public void run() {
        while(contador < 3){
            comer();
            pensar();
        }

        System.out.println(color + nombre + " se fue a dormir");
    }


}

