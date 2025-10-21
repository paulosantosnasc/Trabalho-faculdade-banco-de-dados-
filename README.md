Sistema de Monitoramento de Vacinas
1. Informações do Projeto
Disciplina: Banco de Dados Relacional
Instituição: FAESA
Tema: Sistema de Monitoramento de Efeitos Adversos de Vacinação
Linguagem: Java (JDK 8+)
Banco de Dados: MySQL
Padrão de Projeto: MVC (Model–View–Controller)

3. Integrantes do Grupo
Paulo henrique Nascimento,Murillo daré, Eduardo Gobbi, Luis Felipe andrade

4. Estrutura do Projeto
   
src/main/java/br/com/faesa/monitorvacina/
├── conexion/      → Classe de conexão com o banco (ConexaoMySQL)

├── controller/    → Controladores (UsuarioController, RelatorioController)

├── model/         → Entidades (Pessoa, Usuario)

├── principal/     → Classe Principal (menu e execução)

└── utils/         → Utilitários (SplashScreen)

script.sql – Script de criação do banco
backup_banco.sql – Dump com dados de exemplo
README.md – Instruções

4. Requisitos Técnicos
Java 8+
MySQL 5.7 ou 8.x
Driver JDBC: mysql-connector-java
Maven (opcional)

Dependência Maven:
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
5. Banco de Dados
Criar o banco com:
mysql -u root -p < script.sql

Importar cópia com dados:
mysql -u root -p monitor_vacina_db < backup_banco.sql

Editar ConexaoMySQL.java:
private static final String DATABASE_NAME = "monitor_vacina_db";
private static final String USER = "root";
private static final String PASSWORD = "sua_senha";
6. Execução do Projeto
No Eclipse:
- Clique com o botão direito em Principal.java → Run As → Java Application

Linha de comando:
javac -d bin src/main/java/br/com/faesa/monitorvacina/**/*.java
java -cp bin br.com.faesa.monitorvacina.principal.Principal

Maven:
mvn clean compile exec:java -Dexec.mainClass="br.com.faesa.monitorvacina.principal.Principal"

7. Funcionalidades Implementadas
Splash Screen – Mostra nome do sistema e contagem de registros
Menu Principal – Inserir, Atualizar, Remover, Relatórios, Sair
Inserção – Permite cadastrar usuários (loop “inserir outro?”)
Atualização – Exibe registro atualizado
Remoção – Confirma exclusão e verifica FK
Relatórios – JOIN e GROUP BY
Script SQL – Criação completa do banco
Segurança – PreparedStatement contra SQL injection
8. Diagrama Entidade-Relacionamento
USUARIO (idUsuario PK, nome, cpf, dataNascimento, email, senha)
VACINA (idVacina PK, nome, fabricante)
REGISTROEFEITOADVERSO (idRegistro PK, dataVacinacao, dataRegistro, descricaoUsuario, idUsuario FK, idVacina FK)

USUARIO 1—N REGISTROEFEITOADVERSO
VACINA 1—N REGISTROEFEITOADVERSO
9. Observação sobre Segurança
Optamos por PreparedStatement (JDBC) em vez de concatenação literal para maior segurança (evitar SQL Injection).
10. Créditos
Projeto desenvolvido para avaliação prática da disciplina de Banco de Dados Relacional (FAESA).
11. Como o professor pode testar
1. Importar o projeto no Eclipse.
2. Executar script.sql ou backup_banco.sql.

Instalação e Execução no Linux (Ubuntu/Debian)
1. Instalar o Java (JDK 17 ou superior)
sudo apt update
sudo apt install openjdk-17-jdk -y
java -version

2. Instalar o MySQL Server
sudo apt install mysql-server -y
sudo systemctl enable mysql
sudo systemctl start mysql


Após instalar, entre no MySQL:

sudo mysql -u root


Defina uma senha para o usuário root (se ainda não tiver):

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'sua_senha';
FLUSH PRIVILEGES;
EXIT;

3. Importar o Banco de Dados

Baixe o arquivo script.sql ou backup_banco.sql do projeto e rode:

mysql -u root -p < script.sql


ou, se quiser importar com dados de exemplo:

mysql -u root -p monitor_vacina_db < backup_banco.sql

4. Clonar e compilar o projeto
git clone https://github.com/usuario/seu-projeto.git
cd seu-projeto
javac -d bin $(find src -name "*.java")

5. Executar o programa
java -cp bin br.com.faesa.monitorvacina.principal.Principal


Se aparecer erro de conexão, edite o arquivo:

src/main/java/br/com/faesa/monitorvacina/conexion/ConexaoMySQL.java


E altere as credenciais:

private static final String USER = "root";
private static final String PASSWORD = "sua_senha";

6. Finalizando

Após seguir os passos acima, o sistema abrirá no terminal com:

Splash screen mostrando os dados do banco

Menu com opções de Inserir, Atualizar, Remover e Relatórios
4. Rodar Principal.java.
5. Testar as opções do menu e relatórios.
