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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


/**
 *
 * @author ifsc
 */



public class ControllerLogin implements Initializable {

    @FXML
    private Button btn_cadastro;

    @FXML
    private Button btn_entrar;

    @FXML
    private PasswordField tf_senha;

    @FXML
    private TextField tf_email;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     
        btn_cadastro.setOnAction(vent -> {
            try {
                chamaCadastro();
            } catch (IOException ex) {
                Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btn_entrar.setOnAction(vent -> {
            try {
                System.out.println(tf_email.getText());
                System.out.println(tf_senha.getText());
                
                chamaMenu();
            } catch (IOException ex) {
                Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        


        
    }    
    
    public void chamaCadastro() throws IOException{
        // Fechar a janela atual
        Stage stage = (Stage) btn_cadastro.getScene().getWindow();
        stage.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Cadastro.fxml"));

        Stage cadastro = new Stage();
        cadastro.setScene(new Scene(loader.load()));
        cadastro.setTitle("Cadastro de Usuario");
        cadastro.showAndWait();

    
}
    
    public void chamaMenu() throws IOException {
        // Obtenha o email e senha dos campos de texto
        String email = tf_email.getText();
        String senha = tf_senha.getText();

        // Verifique as credenciais
        boolean credenciaisValidas = Usuario.verificarCredenciais(email, senha);

        if (credenciaisValidas) {
            // Credenciais válidas, abra o Menu.fxml
            int info = Usuario.puxarIdUser(email);

            // Chame o método abrirMenu com o parâmetro info
            abrirMenu(info);
        } else {
            // Credenciais inválidas, mostre uma mensagem de erro e mantenha a tela de login aberta
            JOptionPane.showMessageDialog(null, "Credenciais inválidas. Verifique o email e a senha ou Cadastre-se!", "Erro ao Realizar o Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirMenu(int info) throws IOException {
        // Fechar a janela atual
        Stage stage = (Stage) btn_entrar.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Menu.fxml"));
        Stage menuStage = new Stage();
        menuStage.setScene(new Scene(loader.load()));
        menuStage.setTitle("Menu Principal");

        // Obtenha a referência do controlador do Menu.fxml
        ControllerMenu controllerMenu = loader.getController();
        controllerMenu.setValues(info);

        // Exiba o menuStage como uma janela modal
        menuStage.show();
    }

    
    
    
    
}
