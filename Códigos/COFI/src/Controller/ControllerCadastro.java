package Controller;

import Classes.Usuario;
import DataBase.ConnectionFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ControllerCadastro implements Initializable {

    @FXML
    private Button btn_concluir;

    @FXML
    private ComboBox<String> cb_estado;

    @FXML
    private ComboBox<String> cb_pais;

    @FXML
    private TextField tf_celular;

    @FXML
    private TextField tf_cidade;

    @FXML
    private TextField tf_cpf;

    @FXML
    private TextField tf_ddd;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_emailConfirm;

    @FXML
    private TextField tf_meta;

    @FXML
    private TextField tf_nome;

    @FXML
    private TextField tf_salario;

    @FXML
    private TextField tf_senha;

    @FXML
    private TextField tf_senhaConfirm;

    @FXML
    private TextField tf_sobrenome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getPaises();

        btn_concluir.setOnAction(vent -> {
            try {
                concluir();
            } catch (IOException ex) {
                Logger.getLogger(ControllerCadastro.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        cb_pais.setOnAction(event -> {
            try {
                getEstados();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerCadastro.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void concluir() throws IOException {
        // Realizar as validações dos campos
        String nome = tf_nome.getText();
        String sobrenome = tf_sobrenome.getText();
        String cpf = tf_cpf.getText();
        String pais = cb_pais.getValue();
        String estado = cb_estado.getValue();
        String cidade = tf_cidade.getText();
        String ddd = tf_ddd.getText();
        String celular = tf_celular.getText();
        String salario = tf_salario.getText();
        String meta = tf_meta.getText();
        String email = tf_email.getText();
        String senha = tf_senha.getText();
        String emailConfirm = tf_emailConfirm.getText();
        String senhaConfirm = tf_senhaConfirm.getText();

        if (nome.isEmpty() || sobrenome.isEmpty() || cpf.isEmpty() || pais == null || estado == null || cidade.isEmpty() ||
                ddd.isEmpty() || celular.isEmpty() || salario.isEmpty() || meta.isEmpty() || email.isEmpty() || senha.isEmpty() ||
                emailConfirm.isEmpty() || senhaConfirm.isEmpty()) {
            // Exibir mensagem de erro informando que todos os campos devem ser preenchidos
            JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatórios.", "Erro para se Cadastrar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cpf.matches("\\d+")) {
            // Exibir mensagem de erro informando que o CPF deve conter apenas números
            JOptionPane.showMessageDialog(null, "CPF inválido. Deve conter apenas números.", "Erro para se Cadastrar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!celular.isEmpty() && !celular.matches("\\d+")) {
            // Exibir mensagem de erro informando que o número de celular deve conter apenas números
            JOptionPane.showMessageDialog(null, "Número de celular inválido. Deve conter apenas números.", "Erro para se Cadastrar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches(".+@.+\\..+")) {
            // Exibir mensagem de erro informando que o email é inválido
            JOptionPane.showMessageDialog(null, "Email inválido.", "Erro para se Cadastrar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.equals(emailConfirm) || !senha.equals(senhaConfirm)) {
            // Exibir mensagem de erro informando que os campos de email e senha devem ser iguais
            JOptionPane.showMessageDialog(null, "Os campos de confirmação de email e de senha devem ser iguais.", "Erro para se Cadastrar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double salarioValue;
        double metaValue;

        try {
            salarioValue = Double.parseDouble(salario);
            metaValue = Double.parseDouble(meta);
        } catch (NumberFormatException e) {
            // Exibir mensagem de erro informando que os campos de salário e meta devem ser numéricos
            JOptionPane.showMessageDialog(null, "Os campos de salário e meta devem ser numéricos.", "Erro para se Cadastrar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        // Realizar o cadastro do usuário
        
        try{
        Usuario usuario = new Usuario();
        usuario.cadastraUsuario(nome, sobrenome, cpf, pais, estado, cidade, ddd, celular, salarioValue, metaValue, email, senha);
        
            // Fechar a janela atual
            Stage stage = (Stage) btn_concluir.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e){
            // Exibir mensagem de erro informando que o cadastro falhou
            JOptionPane.showMessageDialog(null, "Falha ao Cadastrar Usuário: \n" + e + ".", "Erro para se Cadastrar", JOptionPane.ERROR_MESSAGE);
        }

    }



    private void getPaises() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConexao();
            stmt = conn.createStatement();
            String query = "SELECT nome FROM paises";
            rs = stmt.executeQuery(query);

            ObservableList<String> paises = FXCollections.observableArrayList();
            while (rs.next()) {
                String nomePais = rs.getString("nome");
                paises.add(nomePais);
            }

            cb_pais.setItems(paises);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.conexaoEnd(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void getEstados() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConexao();
            stmt = conn.createStatement();

            String selectedPais = cb_pais.getSelectionModel().getSelectedItem();
            String query = "SELECT nome_estado FROM estados_paises " +
                    "JOIN paises ON estados_paises.id_pais = paises.id_pais " +
                    "WHERE paises.nome = '" + selectedPais + "'";
            rs = stmt.executeQuery(query);

            ObservableList<String> estados = FXCollections.observableArrayList();
            while (rs.next()) {
                String nomeEstado = rs.getString("nome_estado");
                estados.add(nomeEstado);
            }

            cb_estado.setItems(estados);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.conexaoEnd(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
