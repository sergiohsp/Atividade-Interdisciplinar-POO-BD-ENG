package Controller;

import Classes.Usuario;
import DataBase.ConnectionFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

public class ControllerAtualizarInfos implements Initializable {

    @FXML
    private Button btn_atualizar;

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
    
    @FXML
    private Label lb_idUser;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getPaises();

        btn_atualizar.setOnAction(vent -> {
            try {
                concluir();
            } catch (IOException ex) {
                Logger.getLogger(ControllerAtualizarInfos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        cb_pais.setOnAction(event -> {
            try {
                getEstados();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerAtualizarInfos.class.getName()).log(Level.SEVERE, null, ex);
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
            JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatórios.", "Atualizar Informações", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cpf.matches("\\d+")) {
            // Exibir mensagem de erro informando que o CPF deve conter apenas números
            JOptionPane.showMessageDialog(null, "CPF inválido. Deve conter apenas números.", "Atualizar Informações", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!celular.isEmpty() && !celular.matches("\\d+")) {
            // Exibir mensagem de erro informando que o número de celular deve conter apenas números
            JOptionPane.showMessageDialog(null, "Número de celular inválido. Deve conter apenas números.", "Atualizar Informações", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches(".+@.+\\..+")) {
            // Exibir mensagem de erro informando que o email é inválido
            JOptionPane.showMessageDialog(null, "Email inválido.", "Atualizar Informações", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.equals(emailConfirm) || !senha.equals(senhaConfirm)) {
            // Exibir mensagem de erro informando que os campos de email e senha devem ser iguais
            JOptionPane.showMessageDialog(null, "Os campos de confirmação de email e de senha devem ser iguais.", "Atualizar Informações", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double salarioValue;
        double metaValue;

        try {
            salarioValue = Double.parseDouble(salario);
            metaValue = Double.parseDouble(meta);
        } catch (NumberFormatException e) {
            // Exibir mensagem de erro informando que os campos de salário e meta devem ser numéricos
            JOptionPane.showMessageDialog(null, "Os campos de salário e meta devem ser numéricos.", "Atualizar Informações", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        // Realizar o cadastro do usuário
        
        try{
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        
        Usuario usuario = new Usuario();
        usuario.setId_user(Integer.parseInt(lb_idUser.getText()));
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);
        usuario.setCpf(cpf);
        usuario.setPais(pais);
        usuario.setEstado(estado);
        usuario.setCidade(cidade);
        usuario.setDdd_celular(Integer.parseInt(ddd));
        usuario.setCelular(Integer.parseInt(celular));
        usuario.setSalario(Double.parseDouble(salario));
        usuario.setMeta_financeira(Double.parseDouble(meta));
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setEmail_confirm(emailConfirm);
        usuario.setSenha_confirm(senhaConfirm);
        
        usuarios.add(usuario);
        
        Usuario.atualizaInfos(usuarios);
        
        chamaPerfil();
        
        } catch (Exception e){
            // Exibir mensagem de erro informando que o cadastro falhou
            JOptionPane.showMessageDialog(null, "Falha ao Atualizar Usuário: \n" + e + ".", "Atualizar Informações", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void chamaPerfil() throws IOException{

        int info = Usuario.puxarIdUser(tf_email.getText());

        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios = Usuario.puxarInfosUser(info);

        // Fechar a janela atual
        Stage stage = (Stage) btn_atualizar.getScene().getWindow();
        stage.close();
            
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Perfil.fxml"));
        Stage menuStage = new Stage();
        menuStage.setScene(new Scene(loader.load()));
        menuStage.setTitle("Meu Perfil");

        // Obtenha a referência do controlador do Menu.fxml
        ControllerPerfil controllerPerfil = loader.getController();
        controllerPerfil.setValues(info);

        menuStage.show();
    
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

            String query = "SELECT nome_estado FROM estados_paises " +
                    "JOIN paises ON estados_paises.id_pais = paises.id_pais " +
                    "WHERE paises.nome = '" + cb_pais.getValue() + "'";
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
    
    public void setValues(int id) throws SQLException{
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios = Usuario.puxarInfosUser(id);
        Usuario usuario = usuarios.get(0); // Obtém o primeiro usuário da lista (supondo que haja apenas um usuário com o ID especificado)
        
        String nome = usuario.getNome(); // Obtém o nome do usuário
        String sobrenome = usuario.getSobrenome(); 
        String cpf = usuario.getCpf(); 
        String pais = usuario.getPais();
        String estado = usuario.getEstado();
        String cidade = usuario.getCidade();
        int ddd = usuario.getDdd_celular();
        int telefone = usuario.getCelular();
        double salario = usuario.getSalario(); 
        double metaFinanceira = usuario.getMeta_financeira(); 
        String plano = "Basico"; 
        String email = usuario.getEmail();
        String senha = usuario.getSenha(); 
        int idUser = usuario.getId_user();
        
        String idUserString = String.valueOf(idUser);
        lb_idUser.setText(idUserString);


        tf_nome.setText(nome);
        tf_sobrenome.setText(sobrenome);
        
        tf_cpf.setText(cpf);
        
        cb_pais.setValue(pais);
        cb_estado.setValue(estado);
        
        tf_cidade.setText(cidade);
        

        tf_email.setText(email);

        String metaString = String.valueOf(metaFinanceira);
        tf_meta.setText(metaString);


        String salarioString = String.valueOf(salario);
        tf_salario.setText(salarioString);

        tf_senha.setText(senha);

        String dddString = String.valueOf(ddd);
        tf_ddd.setText(dddString);
        
        String telefoneString = String.valueOf(telefone);
        tf_celular.setText(telefoneString);
        
        
        
        if (cb_estado != null) {
            getEstados();
        }

        
    }
    
}
