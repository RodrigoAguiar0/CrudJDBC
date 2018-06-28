import java.sql.*;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws SQLException {
        Connection conn;

        conn = conexao();

        String sql;
        PreparedStatement statement;
        Statement secondStatement;
        int i = 0;
        while (i != -1 ){
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - INSERT");
            System.out.println("2 - SELECT");
            System.out.println("3 - UPDATE");
            System.out.println("4 - DELETE");
            Scanner scanner = new Scanner(System.in);
            i = Integer.parseInt(scanner.nextLine());

            switch (i){
                case 1:
                    System.out.println("Início do INSERT no banco de dados");
                    sql = "INSERT INTO Users (username, password, fullname, email) VALUES (?, ?, ?, ?)";

                    statement = conn.prepareStatement(sql);
                    System.out.println("Insira um apelido: ");
                    statement.setString(1, scanner.nextLine());
                    System.out.println("Insira uma senha: ");
                    statement.setString(2, scanner.nextLine());
                    System.out.println("Insira o nome completo: ");
                    statement.setString(3, scanner.nextLine());
                    System.out.println("Insira um email");
                    statement.setString(4, scanner.nextLine());

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Um novo usuário foi inserido corretamente");
                    }
                    break;

                case 2:
                    System.out.println("Início do SELECT no banco de dados");


                    sql = "SELECT * FROM Users";

                    secondStatement = conn.createStatement();
                    ResultSet result = secondStatement.executeQuery(sql);

                    int count = 0;

                    while (result.next()){
                        String name = result.getString(2);
                        String pass = result.getString(3);
                        String fullname = result.getString("fullname");
                        String email = result.getString("email");

                        String output = "Usuário #%d: %s - %s - %s - %s";
                        System.out.println(String.format(output, ++count, name, pass, fullname, email));
                    }
                    break;

                case 3:
                    System.out.println("Início do UPDATE no banco de dados");


                    sql = "UPDATE Users SET password=?, fullname=?, email=? WHERE username=?";

                    statement = conn.prepareStatement(sql);
                    System.out.println("Insira o apelido do usuário que deseja modificar: ");
                    statement.setString(4, scanner.nextLine());
                    System.out.println("Insira a nova senha desejada: ");
                    statement.setString(1, scanner.nextLine());
                    System.out.println("Insira o novo nome completo desejado: ");
                    statement.setString(2, scanner.nextLine());
                    System.out.println("Insira o novo email desejado: ");
                    statement.setString(3, scanner.nextLine());

                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Um usuário existente foi atualizado");
                    }else{
                        System.out.println("Usuário não encontrado :(");
                    }
                    break;
                case 4:
                    System.out.println("Início do DELETE no banco de dados");

                    sql = "DELETE FROM Users WHERE username=?";

                    statement = conn.prepareStatement(sql);
                    System.out.println("Digite o apelido do usuário que deseja apagar: ");
                    statement.setString(1, scanner.nextLine());

                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Um usuário foi deletado com sucesso");
                    }else{
                        System.out.println("Usuário não encontrado");
                    }
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
        conn.close();
    }

    public static Connection conexao() {
        String dbURL = "jdbc:mysql://localhost:3306/sampledb";
        String username = "root";
        String password = "";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                System.out.println("Conectado");
                return conn;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
