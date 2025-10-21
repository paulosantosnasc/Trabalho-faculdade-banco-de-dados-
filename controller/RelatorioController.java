package br.com.faesa.monitorvacina.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.faesa.monitorvacina.conexion.ConexaoMySQL;

// classe que vai cuidar dos relatórios que o professor pediu no edital
public class RelatorioController {

    // Relatório 1 (com JOIN), pra mostrar o nome do usuário e da vacina em cada registro
    public void gerarRelatorioRegistrosPorUsuario() {
        
        // nosso SQL brabo com JOIN pra juntar as 3 tabelas e pegar os nomes
        String sql = "SELECT u.nome AS nome_usuario, v.nome AS nome_vacina, r.descricaoUsuario " +
                     "FROM RegistroEfeitoAdverso r " +
                     "JOIN Usuario u ON r.idUsuario = u.idUsuario " +
                     "JOIN Vacina v ON r.idVacina = v.idVacina";
        
        System.out.println("\n--- RELATÓRIO: Efeitos Adversos por Usuário ---");

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            boolean encontrouResultado = false;
            // pra cada linha que o banco retornar, a gente pega os dados
            while (rs.next()) {
                encontrouResultado = true;
                String nomeUsuario = rs.getString("nome_usuario");
                String nomeVacina = rs.getString("nome_vacina");
                String descricao = rs.getString("descricaoUsuario");
                
                System.out.println("Usuário: " + nomeUsuario + 
                                   " | Vacina: " + nomeVacina + 
                                   " | Descrição: " + (descricao != null ? descricao : "N/A"));
            }
            
            // se não achou nada, avisa o usuário
            if(!encontrouResultado) {
                System.out.println("Nenhum registro de efeito adverso encontrado.");
            }

        } catch (SQLException e) {
            System.err.println("!! ERRO ao gerar relatório de registros: " + e.getMessage());
        }
        System.out.println("-------------------------------------------------");
    }

    // Relatório 2 (com GROUP BY), pra contar quantos registros cada vacina tem
    public void gerarRelatorioContagemPorVacina() {

        // nosso SQL com GROUP BY pra agrupar por vacina e o COUNT pra contar
        String sql = "SELECT v.nome AS nome_vacina, COUNT(r.idRegistro) AS total_registros " +
                     "FROM Vacina v " +
                     "LEFT JOIN RegistroEfeitoAdverso r ON v.idVacina = r.idVacina " +
                     "GROUP BY v.nome";
        
        System.out.println("\n--- RELATÓRIO: Contagem de Efeitos por Vacina ---");
        
        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            boolean encontrouResultado = false;
            while (rs.next()) {
                encontrouResultado = true;
                // pega o nome da vacina e o total que o COUNT calculou
                String nomeVacina = rs.getString("nome_vacina");
                int total = rs.getInt("total_registros");
                
                System.out.println("Vacina: " + nomeVacina + " | Total de Registros: " + total);
            }
            
            if(!encontrouResultado) {
                System.out.println("Nenhuma vacina encontrada para gerar o relatório.");
            }

        } catch (SQLException e) {
            System.err.println("!! ERRO ao gerar relatório de contagem: " + e.getMessage());
        }
        System.out.println("-------------------------------------------------");
    }
}