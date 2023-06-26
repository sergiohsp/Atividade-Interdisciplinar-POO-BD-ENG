package Classes;

import DataBase.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Usuario {

    private int id_user;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String pais;
    private String estado;
    private String cidade;
    private int ddd_celular;
    private int celular;
    private double salario;
    private double meta_financeira;
    private String email;
    private String senha;
    private String email_confirm;
    private String senha_confirm;

    public int getId_user() {
        return id_user;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getDdd_celular() {
        return ddd_celular;
    }
    public void setDdd_celular(int ddd_celular) {
        this.ddd_celular = ddd_celular;
    }

    public int getCelular() {
        return celular;
    }
    public void setCelular(int celular) {
        this.celular = celular;
    }

    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getMeta_financeira() {
        return meta_financeira;
    }
    public void setMeta_financeira(double meta_financeira) {
        this.meta_financeira = meta_financeira;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail_confirm() {
        return email_confirm;
    }
    public void setEmail_confirm(String email_confirm) {
        this.email_confirm = email_confirm;
    }

    public String getSenha_confirm() {
        return senha_confirm;
    }
    public void setSenha_confirm(String senha_confirm) {
        this.senha_confirm = senha_confirm;
    }
    
    
    
    
    public static boolean verificarCredenciais(String email, String senha) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConexao();
            
            String query = "SELECT * FROM usuariosCadastrados WHERE email = ? AND senha = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Há resultados, imprima os dados
                System.out.println("Dados encontrados:");
                do {
                    String emailRetornado = rs.getString("email");
                    String senhaRetornada = rs.getString("senha");
                    System.out.println("Email: " + emailRetornado);
                    System.out.println("Senha: " + senhaRetornada);
                } while (rs.next());
                return true;
            } else {
                // Não há resultados
                System.out.println("Nenhum dado encontrado.");
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                ConnectionFactory.conexaoEnd(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void cadastraUsuario(String nome, String sobrenome, String cpf, String pais, String estado, String cidade, String ddd, String celular, double salario, double meta, String email, String senha) {
            
        String INSERT = "INSERT INTO usuariosCadastrados(nome, sobrenome, cpf, pais, estado, cidade, ddd_celular, celular, salario, meta_financeira, email, senha, email_confirm, senha_confirm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
            
        try{
                
            //Criando Conexao
                conn = ConnectionFactory.getConexao();
            //Criando Statement
                PreparedStatement pst;
            //Chamando Query de Inserir
                pst = conn.prepareStatement(INSERT);
            //Puxando dados separadamente
                pst.setString(1, nome);
                pst.setString(2, sobrenome);
                pst.setString(3, cpf);
                pst.setString(4, pais);
                pst.setString(5, estado);
                pst.setString(6, cidade);
                pst.setInt(7, Integer.parseInt(ddd));
                pst.setInt(8, Integer.parseInt(celular));
                pst.setDouble(9, salario);
                pst.setDouble(10, meta);
                pst.setString(11, email);
                pst.setString(12, senha);
                pst.setString(13, email); // email_confirm
                pst.setString(14, senha); // senha_confirm
                
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "Usuário INSERIDO com Sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            //Encerrando as Conexoes
                ConnectionFactory.conexaoEnd(conn, pst);
                
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Erro ao Inserir dados no Banco: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
           
    public static Integer puxarIdUser(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer id_user = null;

        try {
            conn = ConnectionFactory.getConexao();

            String query = "SELECT id_user FROM usuariosCadastrados WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Há resultados, extrair nome e meta financeira
                id_user = rs.getInt("id_user");
            } else {
                // Não há resultados
                JOptionPane.showMessageDialog(null, "Nenhum dado encontrado para o email: " + email, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.conexaoEnd(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id_user;
    }
    
    public static ArrayList<Usuario> puxarInfosUser(int id_user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConexao();

            String query = "SELECT * FROM usuariosCadastrados WHERE id_user = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_user);

            rs = stmt.executeQuery();

            while (rs.next()) {
                // Há resultados, extrair informações do usuário
                Usuario usuario = new Usuario();
                usuario.setId_user(rs.getInt("id_user"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setPais(rs.getString("pais"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setCidade(rs.getString("cidade"));
                usuario.setDdd_celular(rs.getInt("ddd_celular"));
                usuario.setCelular(rs.getInt("celular"));
                usuario.setSalario(rs.getDouble("salario"));
                usuario.setMeta_financeira(rs.getDouble("meta_financeira"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setEmail_confirm(rs.getString("email_confirm"));
                usuario.setSenha_confirm(rs.getString("senha_confirm"));

                usuarios.add(usuario);
            }

            if (usuarios.isEmpty()) {
                // Não há resultados
                JOptionPane.showMessageDialog(null, "Nenhum dado encontrado para o ID do usuário: " + id_user, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.conexaoEnd(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usuarios;
    }

    public static Integer puxarIdUserbyNome(String nome) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer id_user = null;

        try {
            conn = ConnectionFactory.getConexao();

            String query = "SELECT id_user FROM usuariosCadastrados WHERE nome = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nome);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Há resultados, extrair nome e meta financeira
                id_user = rs.getInt("id_user");
            } else {
                // Não há resultados
                JOptionPane.showMessageDialog(null, "Nenhum dado encontrado para o nome: " + nome, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.conexaoEnd(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id_user;
    }
    
    public static void atualizaInfos(ArrayList<Usuario> ArrayUser) {

        Usuario usuario = ArrayUser.get(0); // Obtém o primeiro usuário da lista (supondo que haja apenas um usuário com o ID especificado)
        String nomeUsuario = usuario.getNome();


        String novoNome = usuario.getNome();
        String novoSobrenome = usuario.getSobrenome();
        String novoCPF = usuario.getCpf();
        String novoPais = usuario.getPais();
        String novoEstado = usuario.getEstado();
        String novaCidade = usuario.getCidade();
        int novoDDD = usuario.getDdd_celular();
        int novoCelular = usuario.getCelular();
        double novoSalario = usuario.getSalario();
        double novaMetaFinanceira = usuario.getMeta_financeira();
        String novoEmail = usuario.getEmail();
        String novaSenha = usuario.getSenha();
        String novoConfirmEmail = usuario.getEmail_confirm();
        String novaConfirmSenha = usuario.getSenha_confirm();
        int id = usuario.getId_user();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConexao();

            String query = "UPDATE usuariosCadastrados SET nome = ?, sobrenome = ?, cpf = ?, pais = ?, estado = ?, cidade = ?, ddd_celular = ?, celular = ?, salario = ?, meta_financeira = ?, email = ?, senha = ?, email_confirm = ?, senha_confirm = ? WHERE id_user = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, novoNome);
            stmt.setString(2, novoSobrenome);
            stmt.setString(3, novoCPF);
            stmt.setString(4, novoPais);
            stmt.setString(5, novoEstado);
            stmt.setString(6, novaCidade);
            stmt.setInt(7, novoDDD);
            stmt.setInt(8, novoCelular);
            stmt.setDouble(9, novoSalario);
            stmt.setDouble(10, novaMetaFinanceira);
            stmt.setString(11, novoEmail);
            stmt.setString(12, novaSenha);
            stmt.setString(13, novoConfirmEmail);
            stmt.setString(14, novaConfirmSenha);
            stmt.setInt(15, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Informações atualizadas com sucesso para o ID do usuário: " + id, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum dado encontrado para o ID do usuário: " + id, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar informações no Banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                ConnectionFactory.conexaoEnd(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteUsuario(int idUser) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConexao();

            String query = "DELETE FROM usuariosCadastrados WHERE id_user = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso. ID do usuário: " + idUser, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum dado encontrado para o ID do usuário: " + idUser, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar usuário do Banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                ConnectionFactory.conexaoEnd(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
}
