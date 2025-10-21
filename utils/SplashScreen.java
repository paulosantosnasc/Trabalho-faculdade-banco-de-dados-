package br.com.faesa.monitorvacina.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.faesa.monitorvacina.conexion.ConexaoMySQL;

public class SplashScreen {

    // método que a gente vai chamar pra mostrar a tela de início
    public void exibir() {

        // pega a contagem de cada tabela
        int totalUsuarios = contarRegistros("Usuario");
        int totalVacinas = contarRegistros("Vacina");
        int totalRegistrosEfeitos = contarRegistros("RegistroEfeitoAdverso");
        
        System.out.println("############################################################");
        System.out.println("#");
        System.out.println("#         SISTEMA DE MONITORAMENTO DE EFEITOS ADVERSOS");
        System.out.println("#");
        System.out.println("############################################################");
        System.out.println("#");
        System.out.println("# TOTAL DE REGISTROS EXISTENTES:");
        System.out.println("# 1 - USUARIOS: " + totalUsuarios);
        System.out.println("# 2 - VACINAS: " + totalVacinas);
        System.out.println("# 3 - REGISTROS DE EFEITOS: " + totalRegistrosEfeitos);
        System.out.println("#");
        System.out.println("############################################################");
        System.out.println("# CRIADO POR:");
        System.out.println("# - Paulo Henrique do Nascimento");
        System.out.println("# - Luis Felipe Andrade");
        System.out.println("# - Murillo Daré");
        System.out.println("# - Eduardo Gobbi");
        System.out.println("#");
        System.out.println("############################################################");
        System.out.println("# DISCIPLINA: BANCO DE DADOS");
        System.out.println("# PROFESSOR: Howard Roatti");
        System.out.println("############################################################\n");
    }
    
    // método privado que faz a contagem de registros em uma tabela específica
    private int contarRegistros(String nomeTabela) {
        String sql = "SELECT COUNT(*) FROM " + nomeTabela;
        int total = 0;
        
        // a gente tem que abrir e fechar a conexão pra cada contagem
        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // se o resultado tiver alguma coisa, a gente pega o primeiro valor (que é a contagem)
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("!! ERRO ao contar registros da tabela " + nomeTabela + ": " + e.getMessage());
        }
        
        return total;
    }
}