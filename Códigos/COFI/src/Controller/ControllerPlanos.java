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



public class ControllerPlanos implements Initializable {

    @FXML
    private Button btn_basico;

    @FXML
    private Button btn_cancelar;

    @FXML
    private Button btn_plus;

    @FXML
    private Button btn_super;

    @FXML
    private Label lb_idUser;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        btn_basico.setOnAction(vent -> {
            try {
                JOptionPane.showMessageDialog(null, "O Plano Básico já está selecionado","Seleção de Plano", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(ControllerPlanos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_super.setOnAction(vent -> {
            try {
                JOptionPane.showMessageDialog(null, "O Plano Super ainda não está disponível! Enviaremos um e-mail quando estiver liberado","Seleção de Plano", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(ControllerPlanos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_plus.setOnAction(vent -> {
            try {
                JOptionPane.showMessageDialog(null, "O Plano SuperPlus ainda não está disponível! Enviaremos um e-mail quando estiver liberado.","Seleção de Plano", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(ControllerPlanos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_cancelar.setOnAction(vent -> {
            try {
                cancelar();
            } catch (IOException ex) {
                Logger.getLogger(ControllerPlanos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });


        
    }  
    
        public void cancelar() throws IOException {
            abrirMenu(Integer.parseInt(lb_idUser.getText()));
        }
        

    public void setValues(int id){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios = Usuario.puxarInfosUser(id);
        Usuario usuario = usuarios.get(0); // Obtém o primeiro usuário da lista (supondo que haja apenas um usuário com o ID especificado)
        int id_user = usuario.getId_user(); // Obtém o nome do usuário
        
        double meta = usuario.getMeta_financeira();

        
        String idString = String.valueOf(id_user);
        lb_idUser.setText(idString);
    }
    
    private void abrirMenu(int info) throws IOException {
        // Fechar a janela atual
        Stage stage = (Stage) btn_cancelar.getScene().getWindow();
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
