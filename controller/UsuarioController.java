package br.com.faesa.monitorvacina.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.faesa.monitorvacina.conexion.ConexaoMySQL;
import br.com.faesa.monitorvacina.model.Usuario;

// essa é a classe que vai ter a lógica pra mexer com o Usuario
public class UsuarioController implements IController {

    // aqui a gente implementa o método "inserir" que veio do "contrato" IController
    @Override
    public void inserir(Object obj) {
        
        // a gente recebe um "Object", mas sabemos que é um Usuario, então convertemos
        Usuario novoUsuario = (Usuario) obj;

        // nosso comando SQL pra botar um usuario novo na tabela
        String sql = "INSERT INTO Usuario (nome, cpf, dataNascimento, email, senha) VALUES (?, ?, ?, ?, ?)";
        
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            // 1. chama nossa classe de conexão pra pegar a ponte com o banco
            conexao = ConexaoMySQL.getConexao();
            
            // 2. prepara o comando SQL pra ser executado
            pstmt = conexao.prepareStatement(sql);

            // 3. agora a gente bota os dados do objeto "novoUsuario" nos lugares dos "?"
            pstmt.setString(1, novoUsuario.getNome());
            pstmt.setString(2, novoUsuario.getCpf());
            pstmt.setDate(3, Date.valueOf(novoUsuario.getDataNascimento()));
            pstmt.setString(4, novoUsuario.getEmail());
            pstmt.setString(5, novoUsuario.getSenha());
            
            // 4. manda executar o comando lá no banco de dados
            System.out.println("Mandando o INSERT para o banco...");
            pstmt.executeUpdate();
            System.out.println("Usuário '" + novoUsuario.getNome() + "' salvo!");

        } catch (SQLException e) {
            System.err.println("!! ERRO ao salvar usuário: " + e.getMessage());
            
        } finally {
            // 5. esse bloco é super importante, pra garantir que a gente feche a conexão
            try {
                if (pstmt != null) pstmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("!! ERRO ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // método pra atualizar um usuário que já existe no banco
    @Override
    public void atualizar(Object obj) {
        Usuario usuarioParaAtualizar = (Usuario) obj;
        
        String sql = "UPDATE Usuario SET nome = ?, cpf = ?, dataNascimento = ?, email = ?, senha = ? WHERE idUsuario = ?";
        
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = ConexaoMySQL.getConexao();
            pstmt = conexao.prepareStatement(sql);

            // preenche os "?" do UPDATE com os dados novos
            pstmt.setString(1, usuarioParaAtualizar.getNome());
            pstmt.setString(2, usuarioParaAtualizar.getCpf());
            pstmt.setDate(3, Date.valueOf(usuarioParaAtualizar.getDataNascimento()));
            pstmt.setString(4, usuarioParaAtualizar.getEmail());
            pstmt.setString(5, usuarioParaAtualizar.getSenha());
            pstmt.setInt(6, usuarioParaAtualizar.getIdUsuario()); // O 'I' de IdUsuario é maiúsculo
            
            System.out.println("Mandando o UPDATE para o banco...");
            pstmt.executeUpdate();
            System.out.println("Usuário com ID " + usuarioParaAtualizar.getIdUsuario() + " foi atualizado!");

        } catch (SQLException e) {
            System.err.println("!! ERRO ao atualizar usuário: " + e.getMessage());
        } finally {
            // fecha tudo no final pra não dar ruim
            try {
                if (pstmt != null) pstmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("!! ERRO ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // método pra apagar um usuário do banco
    @Override
    public void remover(Object obj) {
        Usuario usuarioParaRemover = (Usuario) obj;
        
        String sql = "DELETE FROM Usuario WHERE idUsuario = ?";
        
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = ConexaoMySQL.getConexao();
            pstmt = conexao.prepareStatement(sql);

            // pro DELETE a gente só precisa do ID do usuário
            pstmt.setInt(1, usuarioParaRemover.getIdUsuario()); // O 'I' de IdUsuario é maiúsculo
            
            System.out.println("Mandando o DELETE para o banco...");
            pstmt.executeUpdate();
            System.out.println("Usuário com ID " + usuarioParaRemover.getIdUsuario() + " foi removido!");

        } catch (SQLException e) {
            System.err.println("!! ERRO ao remover usuário: " + e.getMessage());
        } finally {
            // fecha tudo sempre
            try {
                if (pstmt != null) pstmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("!! ERRO ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // método pra buscar todos os usuários no banco
    @Override
    public List<Object> listarTodos() {
        List<Object> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoMySQL.getConexao();
            pstmt = conexao.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            System.out.println("Buscando lista de usuários...");

            while (rs.next()) {
                Usuario usuario = new Usuario();
                
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                
                listaUsuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.err.println("!! ERRO ao listar usuários: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("!! ERRO ao fechar conexão: " + e.getMessage());
            }
        }
        return listaUsuarios;
    }
}