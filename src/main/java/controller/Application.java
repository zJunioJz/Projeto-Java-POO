package controller;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ControleDeEstoqueController::new);
    }
}
