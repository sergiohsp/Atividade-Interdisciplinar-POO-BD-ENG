/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Classes.Usuario;
import Controller.ControllerMenu;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


/**
 *
 * @author ifsc
 */



public class ControllerPerfil implements Initializable {

    @FXML
    private Button btn_apagarConta;

    @FXML
    private Button btn_cancelar;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_plano;

    @FXML
    private Label lb_cpf;

    @FXML
    private Label lb_email;

    @FXML
    private Label lb_meta;

    @FXML
    private Label lb_nome;

    @FXML
    private Label lb_plano;

    @FXML
    private Label lb_salario;

    @FXML
    private Label lb_senha;

    @FXML
    private Label lb_sobrenome;

    @FXML
    private Label lb_telefone;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     
        btn_cancelar.setOnAction(vent -> {
            try {
                cancelar();
            } catch (IOException ex) {
                Logger.getLogger(ControllerPerfil.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btn_editar.setOnAction(vent -> {
            try {
                editarInfos();
            } catch (IOException ex) {
                Logger.getLogger(ControllerPerfil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ControllerPerfil.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btn_plano.setOnAction(vent -> {
            try {
                selecionarPlano();
            } catch (IOException ex) {
                Logger.getLogger(ControllerPerfil.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btn_apagarConta.setOnAction(vent -> {
            try {
                apagarUser();
            } catch (IOException ex) {
                Logger.getLogger(ControllerPerfil.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        
    }
    
    public void cancelar() throws IOException {
        int info = Usuario.puxarIdUser(lb_email.getText());

        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios = Usuario.puxarInfosUser(info);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Menu.fxml"));
        Stage menuStage = new Stage();
        menuStage.setScene(new Scene(loader.load()));
        menuStage.setTitle("Menu Principal");

        // Obtenha a referência do controlador do Menu.fxml
        ControllerMenu controllerMenu = loader.getController();
        controllerMenu.setValues(info);

        // Fechar a janela atual
        Stage stage = (Stage) btn_cancelar.getScene().getWindow();
        stage.close();

        menuStage.show();
    }

    
    public void editarInfos() throws IOException, SQLException {
        
        int info = Usuario.puxarIdUser(lb_email.getText());
        // Fechar a janela atual
        Stage stage = (Stage) btn_editar.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AtualizarInfos.fxml"));
        Parent root = loader.load();

        Stage atualizarInfosStage = new Stage();
        atualizarInfosStage.setScene(new Scene(root));
        atualizarInfosStage.setTitle("Atualizar Informações");
        
        
        ControllerAtualizarInfos controllerAtu = loader.getController();
        controllerAtu.setValues(info);
        
        atualizarInfosStage.show();
    }
    
    public void selecionarPlano() throws IOException {
        int info = Usuario.puxarIdUser(lb_email.getText());
        // Fechar a janela atual
        Stage stage = (Stage) btn_plano.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Planos.fxml"));
        Parent root = loader.load();

        Stage planosStage = new Stage();
        planosStage.setScene(new Scene(root));
        planosStage.setTitle("Planos");
        
        ControllerPlanos controllerPlano = loader.getController();
        controllerPlano.setValues(info);
        
        planosStage.show();
    }
    
    
    public void setValues(int id){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios = Usuario.puxarInfosUser(id);
        Usuario usuario = usuarios.get(0); // Obtém o primeiro usuário da lista (supondo que haja apenas um usuário com o ID especificado)
        
        String nome = usuario.getNome(); // Obtém o nome do usuário
        String sobrenome = usuario.getSobrenome(); 
        String cpf = usuario.getCpf(); 
        int telefone = usuario.getCelular();
        double salario = usuario.getSalario(); 
        double metaFinanceira = usuario.getMeta_financeira(); 
        String plano = "Basico"; 
        String email = usuario.getEmail();
        String senha = usuario.getSenha(); 


        lb_nome.setText(nome);
        lb_sobrenome.setText(sobrenome);
        
        lb_cpf.setText(cpf);


        lb_email.setText(email);

        String metaString = String.valueOf(metaFinanceira);
        lb_meta.setText(metaString);


        lb_plano.setText(plano);

        String salarioString = String.valueOf(salario);
        lb_salario.setText(salarioString);

        lb_senha.setText(senha);

        String telefoneString = String.valueOf(telefone);
        lb_telefone.setText(telefoneString);

        
    }
    
    
    public void apagarUser() throws IOException{
        
        int input = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar sua conta?");
        
        if (input == 0) {
            int info = Usuario.puxarIdUser(lb_email.getText());
            Usuario.deleteUsuario(info);
   
            // Fechar a janela atual
            Stage stage = (Stage) btn_apagarConta.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
            
        }

        
    }
}
