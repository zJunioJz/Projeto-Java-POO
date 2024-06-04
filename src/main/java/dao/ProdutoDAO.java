package dao;
import java.sql.*;
import model.Confeitaria;

/**
        * Esta classe fornece métodos para interagir com a tabela de produtos em um banco de dados SQLite.
 */
public class ProdutoDAO {
    private Connection connection;
    /**
     * Construtor padrão que inicializa a conexão com o banco de dados e cria a tabela de produtos, se não existir.
     */

    public ProdutoDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:estoque.db");
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS produtos (" +
                            "codigo INTEGER PRIMARY KEY, " +
                            "marca TEXT, " +
                            "nome TEXT, " +
                            "preco REAL, " +
                            "quantidade INTEGER, " +
                            "data_validade TEXT, " +
                            "nota_fiscal INTEGER" +
                            ")"
            );
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adiciona um novo produto ao banco de dados.
     *
     * @param codigo      O código do produto.
     * @param marca       A marca do produto.
     * @param nomeProduto O nome do produto.
     * @param preco       O preço do produto.
     * @param quantidade  A quantidade do produto.
     * @param dataVal     A data de validade do produto.
     * @param nota_fiscal A nota fiscal do produto.
     */
    public void cadastrarProduto(int codigo, String marca, String nomeProduto, Double preco, int quantidade, String dataVal, int nota_fiscal) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO produtos (" +
                            "codigo, " +
                            "marca, " +
                            "nome, " +
                            "preco, " +
                            "quantidade, " +
                            "data_validade, " +
                            "nota_fiscal" +
                            ") VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, codigo);
            statement.setString(2, marca);
            statement.setString(3, nomeProduto);
            statement.setDouble(4, preco);
            statement.setInt(5, quantidade);
            statement.setString(6, dataVal);
            statement.setInt(7, nota_fiscal);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Edita um produto existente no banco de dados.
     *
     * @param codigo      O código do produto a ser editado.
     * @param marca       A nova marca do produto.
     * @param nomeProduto O novo nome do produto.
     * @param preco       O novo preço do produto.
     * @param quantidade  A nova quantidade do produto.
     * @param dataVal     A nova data de validade do produto.
     * @param nota_fiscal A nova nota fiscal do produto.
     */
    public void editarProduto(int codigo, String marca, String nomeProduto, Double preco, int quantidade, String dataVal, int nota_fiscal) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE produtos SET " +
                            "marca = ?, " +
                            "nome = ?, " +
                            "preco = ?, " +
                            "quantidade = ?, " +
                            "data_validade = ?, " +
                            "nota_fiscal = ? " +
                            "WHERE codigo = ?"
            );
            statement.setString(1, marca);
            statement.setString(2, nomeProduto);
            statement.setDouble(3, preco);
            statement.setInt(4, quantidade);
            statement.setString(5, dataVal);
            statement.setInt(6, nota_fiscal);
            statement.setInt(7, codigo);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Remove um produto do banco de dados.
     *
     * @param codigo O código do produto a ser removido.
     */

    public void removerProduto(int codigo) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM produtos WHERE codigo = ?");
            statement.setInt(1, codigo);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Lista todos os produtos armazenados no banco de dados.
     *
     * @return Um ResultSet contendo os dados dos produtos listados.
     */

    public ResultSet listarProdutos() {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM produtos");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    /**
     * Retorna um objeto Confeitaria correspondente ao produto com o código especificado.
     *
     * @param codigo O código do produto a ser procurado.
     * @return O objeto Confeitaria representando o produto encontrado, ou null se nenhum produto corresponder ao código fornecido.
     */

    public Confeitaria getProdutoPorCodigo(int codigo) {
        Confeitaria produto = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM produtos WHERE codigo = ?");
            statement.setInt(1, codigo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String marca = resultSet.getString("marca");
                String nomeProduto = resultSet.getString("nome");
                Double preco_produto = resultSet.getDouble("preco_produto");
                int quantidade = resultSet.getInt("quantidade");
                String dataVal = resultSet.getString("data_validade");
                int nota_fiscal = resultSet.getInt("nota_fiscal");
                produto = new Confeitaria(codigo, marca, nomeProduto, preco_produto, quantidade, dataVal, nota_fiscal);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produto;
    }

}
