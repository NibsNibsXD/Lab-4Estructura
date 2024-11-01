/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */
public abstract class JuegoAhorcadoBase implements JuegoAhorcado {
    protected String palabraSecreta;
    protected String palabraActual;
    protected int intentos;

    public JuegoAhorcadoBase() {
        this.intentos = 6; 
    }

    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    public String getPalabraActual() {
        return palabraActual;
    }

    public int getIntentos() {
        return intentos;
    }

    protected void decrementarIntentos() {
        if (intentos > 0) {
            intentos--;
        }
    }

    protected void inicializarPalabraActual() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < palabraSecreta.length(); i++) {
            sb.append('_');
        }
        palabraActual = sb.toString();
    }

    @Override
    public abstract void inicializarPalabraSecreta();

    @Override
    public abstract void jugar();

    protected abstract void actualizarPalabraActual(char letra);

    protected abstract boolean verificarLetra(char letra);

    protected abstract boolean hasGanado();
    
    public abstract boolean procesarLetra(char letra);
    public abstract boolean verificarGano();
    public abstract boolean verificarPerdio();
}