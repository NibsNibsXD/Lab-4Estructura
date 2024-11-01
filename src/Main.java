
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */


public class Main extends JFrame {
    private JuegoAhorcadoBase juego;
    private AdminPalabrasSecretas adminPalabras;
    private JLabel AImagen;
    private JLabel EPalabra;
    private JLabel lblIntentos;
    private JTextField textoLetra;
    private JButton btnEnviar;
    private JLabel TextoMensaje;
    private JButton btnReiniciar;

    private List<Character> letrasUsadas;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main() {
        adminPalabras = new AdminPalabrasSecretas();
        cargarPalabras();

        letrasUsadas = new ArrayList<>();

        setTitle("Lab #4 Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout());

        AImagen = new JLabel();
        AImagen.setHorizontalAlignment(JLabel.CENTER);
        add(AImagen, BorderLayout.NORTH);

        EPalabra = new JLabel("", SwingConstants.CENTER);
        EPalabra.setFont(new Font("Arial", Font.BOLD, 24));
        add(EPalabra, BorderLayout.CENTER);

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new GridLayout(4, 1));

        lblIntentos = new JLabel("Intentos: 6", SwingConstants.CENTER);
        panelControles.add(lblIntentos);

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Letra: "));
        textoLetra = new JTextField(5);
        panelEntrada.add(textoLetra);
        btnEnviar = new JButton("Enviar");
        panelEntrada.add(btnEnviar);
        panelControles.add(panelEntrada);

        TextoMensaje = new JLabel("", SwingConstants.CENTER);
        TextoMensaje.setForeground(Color.RED);
        panelControles.add(TextoMensaje);

        btnReiniciar = new JButton("Reiniciar");
        panelControles.add(btnReiniciar);

        add(panelControles, BorderLayout.SOUTH);

        btnEnviar.addActionListener(e -> procesarEntrada());

        btnReiniciar.addActionListener(e -> seleccionarModo());

        seleccionarModo();

        setVisible(true);
    }

    private void cargarPalabras() {
        adminPalabras.agregarPalabra("PATO");
        adminPalabras.agregarPalabra("GATO");
        adminPalabras.agregarPalabra("PERRO");
        adminPalabras.agregarPalabra("ERICK");
        adminPalabras.agregarPalabra("MANOS");
        adminPalabras.agregarPalabra("PATAS");
        adminPalabras.agregarPalabra("PANTALLA");
        adminPalabras.agregarPalabra("COMPUTADORA");
        adminPalabras.agregarPalabra("GALLETA");
        adminPalabras.agregarPalabra("CARRO");
        adminPalabras.agregarPalabra("CAMINAR");
        adminPalabras.agregarPalabra("SANDALIAS");
    }

    private void seleccionarModo() {
        String[] opciones = {"Modo Fijo", "Modo Azar"};
        int seleccion = JOptionPane.showOptionDialog(this, "Selecciona el modo de juego:",
                "Seleccionar Modo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, opciones, opciones[0]);

        if (seleccion == 0) {
            String palabraFija = JOptionPane.showInputDialog(this, "Ingresa la palabra para adivinar");
            if (palabraFija != null && !palabraFija.trim().isEmpty()) {
                juego = new JuegoAhorcadoFijo(palabraFija.trim().toUpperCase());
            } else {
                JOptionPane.showMessageDialog(this, "Palabra invalida. Seleccionando Modo Azar.");
                juego = new JuegoAhorcadoAzar(adminPalabras);
            }
        } else {
            juego = new JuegoAhorcadoAzar(adminPalabras);
        }

        iniciarJuego();
    }

    private void iniciarJuego() {
        letrasUsadas.clear();
        actualizarImagen();
        actualizarPalabra();
        lblIntentos.setText("Intentos: " + juego.getIntentos());
        TextoMensaje.setText("");
        textoLetra.setText("");
        textoLetra.setEnabled(true);
        btnEnviar.setEnabled(true);
    }

    private void actualizarImagen() {
        int intentosUsados = 6 - juego.getIntentos();
        String nombreImagen = "/imagenes/Hangman-" + intentosUsados + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(nombreImagen));
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        AImagen.setIcon(new ImageIcon(img));
    }

    private void actualizarPalabra() {
        EPalabra.setText(mostrarPalabraActual());
    }

    private String mostrarPalabraActual() {
        StringBuilder sb = new StringBuilder();
        for (char c : juego.getPalabraActual().toCharArray()) {
            sb.append(c).append(' ');
        }
        return sb.toString();
    }

    private void procesarEntrada() {
        String entrada = textoLetra.getText().toUpperCase().trim();
        TextoMensaje.setText("");

        if (entrada.isEmpty()) {
            TextoMensaje.setText("Ingresa una letra.");
            return;
        }

        if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
            TextoMensaje.setText("Solo una letra valida.");
            return;
        }

        char letra = entrada.charAt(0);

        if (letraUsada(letra)) {
            TextoMensaje.setText("Ya usaste la letra '" + letra + "'.");
            return;
        }

        letrasUsadas.add(letra);

        boolean acertado = juego.procesarLetra(letra);
        if (acertado) {
            TextoMensaje.setText("Correcto!");
        } else {
            TextoMensaje.setText("Incorrecto");
        }

        actualizarPalabra();
        actualizarImagen();
        lblIntentos.setText("Intentos: " + juego.getIntentos());

        if (juego.verificarGano()) {
            JOptionPane.showMessageDialog(this, "Ganaste! La palabra era: " + juego.getPalabraSecreta());
            deshabilitarEntrada();
        } else if (juego.verificarPerdio()) {
            JOptionPane.showMessageDialog(this, "Perdiste. La palabra era: " + juego.getPalabraSecreta());
            deshabilitarEntrada();
        }

        textoLetra.setText("");
    }

    private boolean letraUsada(char letra) {
        for (char c : letrasUsadas) {
            if (c == letra) return true;
        }
        return false;
    }

    private void deshabilitarEntrada() {
        textoLetra.setEnabled(false);
        btnEnviar.setEnabled(false);
    }

    private void reiniciarJuego() {
        seleccionarModo();
    }
}