package controller;

import dao.ProdutoDAO;
import model.Confeitaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Esta classe controla o inventário de bolos e sobremesas em uma confeitaria.
 * Permite adicionar, editar, listar e remover produtos do estoque.
 */
public class ControleDeEstoqueController extends JFrame {
    private JTextField codigoField, marcaField, nomeProdutoField, quantidadeField, dataValField, precoUnitarioField, notaFiscalField;
    private JButton cadastrarButton, listarButton, removerButton, editarButton;
    private JTextArea outputArea;
    private JComboBox<Confeitaria> codigoRemoverField;
    private ProdutoDAO produtoDAO;
    private JLabel precoTotalLabel;

    /**
     * Construtor da classe ControleDeEstoqueController.
     * Inicializa a interface gráfica e configura os componentes.
     */
    public ControleDeEstoqueController() {
        setTitle("Controle de Inventário de Bolos e Sobremesas");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/java/img/icon.png");
        setIconImage(icon);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        // Painel para os botões
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Aumentar a fonte
        Font font = new Font("Arial", Font.PLAIN, 18);

        // Labels e campos de texto
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel codigoLabel = new JLabel("Código:");
        codigoLabel.setFont(font);
        inputPanel.add(codigoLabel, gbc);
        gbc.gridy++;
        JLabel marcaLabel = new JLabel("Marca:");
        marcaLabel.setFont(font);
        inputPanel.add(marcaLabel, gbc);
        gbc.gridy++;
        JLabel nomeProdutoLabel = new JLabel("Nome do Produto:");
        nomeProdutoLabel.setFont(font);
        inputPanel.add(nomeProdutoLabel, gbc);
        gbc.gridy++;
        JLabel precoLabel = new JLabel("Preço:");
        precoLabel.setFont(font);
        inputPanel.add(precoLabel, gbc);
        gbc.gridy++;
        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeLabel.setFont(font);
        inputPanel.add(quantidadeLabel, gbc);
        gbc.gridy++;
        JLabel dataValidadeLabel = new JLabel("Data de Validade:");
        dataValidadeLabel.setFont(font);
        inputPanel.add(dataValidadeLabel, gbc);
        gbc.gridy++;
        JLabel notaFiscalLabel = new JLabel("Nota Fiscal:");
        notaFiscalLabel.setFont(font);
        inputPanel.add(notaFiscalLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        codigoField = new JTextField(20);
        codigoField.setPreferredSize(new Dimension(200, 30));
        codigoField.setFont(font);
        inputPanel.add(codigoField, gbc);
        gbc.gridy++;
        marcaField = new JTextField(20);
        marcaField.setPreferredSize(new Dimension(200, 30));
        marcaField.setFont(font);
        inputPanel.add(marcaField, gbc);
        gbc.gridy++;
        nomeProdutoField = new JTextField(20);
        nomeProdutoField.setPreferredSize(new Dimension(200, 30));
        nomeProdutoField.setFont(font);
        inputPanel.add(nomeProdutoField, gbc);
        gbc.gridy++;
        precoUnitarioField = new JTextField(20);
        precoUnitarioField.setPreferredSize(new Dimension(200, 30));
        precoUnitarioField.setFont(font);
        inputPanel.add(precoUnitarioField, gbc);
        gbc.gridy++;
        quantidadeField = new JTextField(20);
        quantidadeField.setPreferredSize(new Dimension(200, 30));
        quantidadeField.setFont(font);
        inputPanel.add(quantidadeField, gbc);
        gbc.gridy++;
        dataValField = new JTextField(20);
        dataValField.setPreferredSize(new Dimension(200, 30));
        dataValField.setFont(font);
        inputPanel.add(dataValField, gbc);

        gbc.gridy++;
        notaFiscalField = new JTextField(20);
        notaFiscalField.setPreferredSize(new Dimension(200, 30));
        notaFiscalField.setFont(font);
        inputPanel.add(notaFiscalField, gbc);

        // Botões
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setPreferredSize(new Dimension(150, 30));
        cadastrarButton.setBackground(new Color(255, 192, 203));
        cadastrarButton.setFont(font);
        inputPanel.add(cadastrarButton, gbc);
        gbc.gridy++;
        listarButton = new JButton("Listar");
        listarButton.setPreferredSize(new Dimension(150, 30));
        listarButton.setBackground(new Color(255, 192, 203));
        listarButton.setFont(font);
        inputPanel.add(listarButton, gbc);
        gbc.gridy++;
        removerButton = new JButton("Remover");
        removerButton.setPreferredSize(new Dimension(150, 30));
        removerButton.setBackground(new Color(255, 192, 203));
        removerButton.setFont(font);
        inputPanel.add(removerButton, gbc);
        gbc.gridy++;
        editarButton = new JButton("Editar");
        editarButton.setPreferredSize(new Dimension(150, 30));
        editarButton.setBackground(new Color(255, 192, 203));
        editarButton.setFont(font);
        inputPanel.add(editarButton, gbc);
        gbc.gridy++;
        codigoRemoverField = new JComboBox<>();
        codigoRemoverField.setPreferredSize(new Dimension(200, 30));
        codigoRemoverField.setFont(font);
        inputPanel.add(codigoRemoverField, gbc);

        // Adicionando renderizador personalizado para o JComboBox
        codigoRemoverField.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Confeitaria) {
                    Confeitaria produto = (Confeitaria) value;
                    setText(Integer.toString(produto.getCodigo()));
                }
                return this;
            }
        });

        outputArea = new JTextArea(10, 40);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 18));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        precoTotalLabel = new JLabel("Preço Total: 0.0");
        precoTotalLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(precoTotalLabel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarProduto();
            }
        });

        listarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lerProduto();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerProduto();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarProduto();
            }
        });

        produtoDAO = new ProdutoDAO();
        lerProduto();
        setVisible(true);

        codigoRemoverField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Confeitaria produtoSelecionado = (Confeitaria) codigoRemoverField.getSelectedItem();
                if (produtoSelecionado != null) {
                    codigoField.setText(Integer.toString(produtoSelecionado.getCodigo()));
                    marcaField.setText(produtoSelecionado.getMarca());
                    nomeProdutoField.setText(produtoSelecionado.getNomeProduto());
                    quantidadeField.setText(Integer.toString(produtoSelecionado.getQuantidade()));
                    dataValField.setText(produtoSelecionado.getDataVal());
                    precoUnitarioField.setText(Double.toString(produtoSelecionado.getPreco()));
                    notaFiscalField.setText(Integer.toString(produtoSelecionado.getNotaFiscal()));
                }
            }
        });
    }

    /**
     * Cadastra um novo produto no estoque da confeitaria.
     *
     * @param codigo      O código do produto.
     * @param marca       A marca do produto.
     * @param nomeProduto O nome do produto.
     * @param quantidade  A quantidade do produto.
     * @param dataVal     A data de validade do produto.
     */

    private void cadastrarProduto() {
        int codigo;
        String marca = marcaField.getText();
        String nomeProduto = nomeProdutoField.getText();
        double preco; // Change the type to double
        int quantidade;
        String dataVal = dataValField.getText();
        int nota_fiscal;

        try {
            codigo = Integer.parseInt(codigoField.getText());
            preco = Double.parseDouble(precoUnitarioField.getText()); // Correct parsing to double
            quantidade = Integer.parseInt(quantidadeField.getText());
            nota_fiscal = Integer.parseInt(notaFiscalField.getText());
        } catch (NumberFormatException ex) {
            showErrorDialog("Preencha todos os campos corretamente.");
            return;
        }


        if (codigo != 0 && marca != null && nomeProduto != null && preco != 0 && quantidade != 0 && dataVal != null && nota_fiscal != 0) {
            produtoDAO.cadastrarProduto(codigo, marca, nomeProduto, preco, quantidade, dataVal, nota_fiscal);
            outputArea.append("Produto cadastrado:" + "\nCódigo: " + codigo + "\nMarca: " + marca + "\nProduto: " + nomeProduto + "\nPreço: " + preco + "\nQuantidade: " + quantidade + "\nData de Validade: " + dataVal + "\nNota Fiscal: " + nota_fiscal + "\n");
            outputArea.append("\n");
            clearFields();
            atualizarListaDeProdutos();
        } else {
            showErrorDialog("Preencha todos os campos corretamente.");
        }
    }

    /**
     * Edita um produto existente no banco de dados.
     *
     * @param codigo      O código do produto a ser editado.
     * @param marca       A nova marca do produto.
     * @param nomeProduto O novo nome do produto.
     * @param quantidade  A nova quantidade do produto em estoque.
     * @param dataVal     A nova data de validade do produto.
     */

    private void editarProduto() {
        int codigo;
        String marca = marcaField.getText();
        String nomeProduto = nomeProdutoField.getText();
        double preco; // Change the type to double
        int quantidade;
        String dataVal = dataValField.getText();
        int notaFiscal;

        try {
            codigo = Integer.parseInt(codigoField.getText());
            preco = Double.parseDouble(precoUnitarioField.getText()); // Correct parsing to double
            quantidade = Integer.parseInt(quantidadeField.getText());
            notaFiscal = Integer.parseInt(notaFiscalField.getText());
        } catch (NumberFormatException ex) {
            showErrorDialog("Preencha todos os campos corretamente.");
            return;
        }

        if (codigo != 0 && marca != null && nomeProduto != null && preco >= 0 && quantidade != 0 && dataVal != null && notaFiscal >= 0) {
            produtoDAO.editarProduto(codigo, marca, nomeProduto, preco, quantidade, dataVal, notaFiscal);
            outputArea.append("Produto editado com sucesso!" + "\nCódigo: " + codigo + "\nMarca: " + marca + "\nProduto: " + nomeProduto + "\nPreco: " + preco + "\nQuantidade: " + quantidade + "\nData de Validade: " + dataVal + "\nNota Fiscal: " + notaFiscal + "\n");
            outputArea.append("\n");
            clearFields();
            atualizarListaDeProdutos();
        } else {
            showErrorDialog("Preencha todos os campos corretamente.");
        }
    }

    /**
     * Remove um produto do estoque da confeitaria.
     *
     * @param codigo O código do produto a ser removido.
     */
    private void removerProduto() {
        Confeitaria produtoSelecionado = (Confeitaria) codigoRemoverField.getSelectedItem();

        if (produtoSelecionado != null) {
            int codigo = produtoSelecionado.getCodigo();
            produtoDAO.removerProduto(codigo);
            outputArea.append("Produto removido com sucesso!" + "\nCódigo: " + codigo + "\n");
            outputArea.append("\n");
            atualizarListaDeProdutos();
        } else {
            showErrorDialog("Selecione um código para excluir.");
        }
    }

    private void lerProduto() {
        outputArea.setText("");
        codigoRemoverField.removeAllItems();
        codigoRemoverField.addItem(null);
        codigoRemoverField.addItem(new Confeitaria(0, "", "", (double) 0, 0, "", 0));

        ResultSet resultSet = produtoDAO.listarProdutos();

        try {
            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                String marca = resultSet.getString("marca");
                String nomeProduto = resultSet.getString("nome");
                double preco = resultSet.getDouble("preco");
                int quantidade = resultSet.getInt("quantidade");
                String dataVal = resultSet.getString("data_validade");
                int notaFiscal = resultSet.getInt("nota_fiscal");
                Confeitaria confeitaria = new Confeitaria(codigo, marca, nomeProduto, preco, quantidade, dataVal, notaFiscal);
                outputArea.append("Produtos listados: " + "\nCódigo: " + codigo + "\nMarca: " + marca + "\nProduto: " + nomeProduto + "\nPreco: " + preco + "\nQuantidade: " + quantidade + "\nData de Validade: " + dataVal + "\nNota Fiscal: " + notaFiscal + "\n\n");
                codigoRemoverField.addItem(confeitaria);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        atualizarPrecoTotal();
    }

    private void atualizarPrecoTotal() {
        double precoTotal = 0.0;

        ResultSet resultSet = produtoDAO.listarProdutos();
        try {
            while (resultSet.next()) {
                double preco = resultSet.getDouble("preco");
                int quantidade = resultSet.getInt("quantidade");
                precoTotal += preco * quantidade;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //precoTotalLabel.setText("Preço Total: " + precoTotal);
        String precoTotalFormatado = String.format("%.2f", precoTotal);

        precoTotalLabel.setText("Preço Total: " + precoTotalFormatado);
    }

    private void atualizarListaDeProdutos() {
        lerProduto();
    }

    private void clearFields() {
        codigoField.setText("");
        marcaField.setText("");
        nomeProdutoField.setText("");
        quantidadeField.setText("");
        dataValField.setText("");
    }

    /**
     * Exibe uma caixa de diálogo de erro com a mensagem fornecida.
     *
     * @param message A mensagem de erro a ser exibida.
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

}
