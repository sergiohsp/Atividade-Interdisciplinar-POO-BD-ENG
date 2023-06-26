/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Classes.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author ifsc
 */
public class ControllerMenu implements Initializable {
    
    @FXML
    private Button btn_ativo;

    @FXML
    private Button btn_passivo;

    @FXML
    private Button btn_perfil;

    @FXML
    private Label lb_meta;

    @FXML
    private Label lb_nome;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btn_perfil.setOnAction(vent -> {
            try {
                chamaPerfil();
            } catch (IOException ex) {
                Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btn_ativo.setOnAction(vent -> {
            JOptionPane.showMessageDialog(null, "Seus Ativos não estão disponíveis no momento.", "Erro Página Ativos", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btn_passivo.setOnAction(vent -> {
            JOptionPane.showMessageDialog(null, "Seus Passivos não estão disponíveis no momento.", "Erro Página Passivos", JOptionPane.INFORMATION_MESSAGE);
        });
        
        
        
    }   
    
    public void chamaPerfil() throws IOException{

        int info = Usuario.puxarIdUserbyNome(lb_nome.getText());

        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios = Usuario.puxarInfosUser(info);

        // Fechar a janela atual
        Stage stage = (Stage) btn_perfil.getScene().getWindow();
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
    
    public void setValues(int id){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios = Usuario.puxarInfosUser(id);
        Usuario usuario = usuarios.get(0); // Obtém o primeiro usuário da lista (supondo que haja apenas um usuário com o ID especificado)
        String nomeUsuario = usuario.getNome(); // Obtém o nome do usuário
        double meta = usuario.getMeta_financeira();
        
        lb_nome.setText(nomeUsuario);
        
        String metaString = String.valueOf(meta);
        lb_meta.setText(metaString);
    }
    
    
}
