
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */
public class AdminPalabrasSecretas { 
    private List<String> palabras;
    private Random random;

    public AdminPalabrasSecretas() {
        this.palabras = new ArrayList<>();
        this.random = new Random();
    }

    public void agregarPalabra(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            throw new IllegalArgumentException("La palabra no puede ser nula o estar vacía.");
        }
        palabras.add(palabra.trim().toUpperCase());
    }

    public String seleccionarPalabraAlAzar() {
        if (palabras.isEmpty()) {
            throw new IllegalStateException("No hay palabras disponibles para seleccionar.");
        }
        int indice = random.nextInt(palabras.size());
        return palabras.get(indice);
    }

    public List<String> getPalabras() {
        return new ArrayList<>(palabras);
    }

    public boolean eliminarPalabra(String palabra) {
        if (palabra == null) {
            return false;
        }
        return palabras.remove(palabra.trim().toUpperCase());
    }

    public void limpiarPalabras() {
        palabras.clear();
    }
}