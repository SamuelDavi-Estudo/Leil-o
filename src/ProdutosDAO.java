/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement st;
    ResultSet rs;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11", "root", "2504");
            System.out.println("Conexão com o banco realizada com sucesso! ");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }
    }

    public void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {

        }
    }

    public int cadastrarProduto(ProdutosDTO produto) {

        int status;
        try {
            st = conn.prepareStatement("INSERT into produtos (nome, valor, status)VALUES (?,?,?)");
            st.setString(1, produto.getNome());
            st.setInt(2, produto.getValor());
            st.setString(3, produto.getStatus1());
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();

        }

    }

    public void venderProduto(int id) {
        try {
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        ArrayList<ProdutosDTO> listaProdutos = new ArrayList<>();
        try {
            st = conn.prepareStatement("SELECT * FROM produtos");
            rs = st.executeQuery();
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus1(rs.getString("status"));
                listaProdutos.add(produto);

            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar todos os filmes: " + ex.getMessage());
        }
        return listaProdutos;

    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM produtos WHERE status = 'vendido'";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {

                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus1(rs.getString("status"));
                produtosVendidos.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
        }
        return produtosVendidos;
    }

}
