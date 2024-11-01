/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */
public class JuegoAhorcadoFijo extends JuegoAhorcadoBase {

    public JuegoAhorcadoFijo(String palabraSecreta) {
        this.palabraSecreta = palabraSecreta.toUpperCase(); 
        inicializarPalabraSecreta();
    }

    @Override
    public void inicializarPalabraSecreta() {
        inicializarPalabraActual();
        this.intentos = 6; 
    }

    @Override
    public void jugar() {
        
    }

    @Override
    protected void actualizarPalabraActual(char letra) {
        letra = Character.toUpperCase(letra);
        StringBuilder nuevaPalabra = new StringBuilder(palabraActual);
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                nuevaPalabra.setCharAt(i, letra);
            }
        }
        palabraActual = nuevaPalabra.toString();
    }

    @Override
    protected boolean verificarLetra(char letra) {
        letra = Character.toUpperCase(letra);
        boolean encontrada = palabraSecreta.indexOf(letra) >= 0;
        if (encontrada) {
            actualizarPalabraActual(letra);
        } else {
            decrementarIntentos();
        }
        return encontrada;
    }

    @Override
    protected boolean hasGanado() {
        return !palabraActual.contains("_");
    }

    public boolean procesarLetra(char letra) {
        return verificarLetra(letra);
    }

    public boolean verificarGano() {
        return hasGanado();
    }

    public boolean verificarPerdio() {
        return intentos <= 0 && !hasGanado();
    }
}


