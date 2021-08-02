import java.sql.*;
import java.util.*;

public class Main {

    private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static Connection connection = null;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        connection = getConnection();
        extractNeededExercise();
    }

    private static void extractNeededExercise() throws SQLException {
        System.out.println("Please enter exercise number:");
        int exerciseNumber = Integer.parseInt(scan.nextLine());
        switch (exerciseNumber) {
            case 2:
                exerciseTwo();
                break;
            case 3:
                exerciseThree();
                break;
            case 4:
                exerciseFour();
                break;
            case 5:
                exerciseFive();
                break;
            case 7:
                exerciseSeven();
                break;
            case 8:
                exerciseEight();
                break;
            case 9:
                exerciseNine();
                break;
        }
    }


    private static Connection getConnection() throws SQLException {
        System.out.println("Please, enter your username:");
        String user = scan.nextLine();
        System.out.println("Please, enter your password:");
        String password = scan.nextLine();

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        return DriverManager.getConnection(URL_CONNECTION + DATABASE_NAME, properties);
    }

    private static void exerciseTwo() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT v.`name`,COUNT(DISTINCT m.`id`) AS `minions_count` FROM `villains` AS v\n" +
                "JOIN `minions_villains` AS mv\n" +
                "ON v.`id`=mv.`villain_id`\n" +
                "JOIN `minions`  AS m ON mv.`minion_id` = m.`id`\n" +
                "GROUP BY v.`id`\n" +
                "HAVING `minions_count`>15\n" +
                "ORDER BY `minions_count` DESC;");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d %n", resultSet.getString("name"), resultSet.getInt("minions_count"));
        }
    }

    private static void exerciseThree() throws SQLException {
        System.out.println("Please, enter villain id:");
        int villainId = Integer.parseInt(scan.nextLine());

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT v.`name` AS `villain_name`,m.`name` AS `minion_name`,m.`age` FROM `villains` AS v\n" +
                "JOIN `minions_villains` AS mv\n" +
                "ON v.`id`=mv.`villain_id`\n" +
                "JOIN `minions`  AS m ON mv.`minion_id` = m.`id`\n" +
                "WHERE v.`id`=?;");
        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            System.out.printf("Villain: %s %n1. %s %d%n",
                    resultSet.getString("villain_name"), resultSet.getString("minion_name"), resultSet.getInt("age"));
            int counter = 2;
            while (resultSet.next()) {
                System.out.printf("%d. %s %d%n", counter++, resultSet.getString("minion_name"), resultSet.getInt("age"));
            }
        } else {
            System.out.printf("No villain with ID %d exists in the database.%n",villainId);
        }
    }

    private static void exerciseFour() throws SQLException {
        System.out.println("Please, enter minion name,age,town and villain name:");
        String[] minionInput = scan.nextLine().split(": ");
        String[] villainInput=scan.nextLine().split(": ");
        String[] minionInfo=minionInput[1].split(" ");
        String minionName=minionInfo[0];
        int age= Integer.parseInt(minionInfo[1]);
        String minionTown=minionInfo[2];
        String villainName=villainInput[1];

        boolean isTownExist=checkIfEntityExist("towns",minionTown);
        if (!isTownExist) {
            PreparedStatement preparedStatement = connection.prepareStatement( "INSERT INTO `towns`(name) VALUES (?);");
            preparedStatement.setString(1,minionTown);
            preparedStatement.execute();
            System.out.printf("Town %s was added to the database.%n",minionTown);
        }
        int idTown=getIdByEntityName("towns",minionTown);

        boolean isVillainExist=checkIfEntityExist("villains",villainName);
        if (!isVillainExist) {
            PreparedStatement preparedStatement = connection.prepareStatement( "INSERT INTO `villains`(`name`,`evilness_factor`) VALUES (?,?)");
            preparedStatement.setString(1,villainName);
            preparedStatement.setString(2,"evil");
            preparedStatement.execute();
            System.out.printf("Villain %s was added to the database.%n",villainName);
        }
        int idVillain=getIdByEntityName("villains",villainName);
        PreparedStatement preparedStatementMinions = connection.prepareStatement( "INSERT INTO `minions`(`name`,`age`,`town_id`) VALUES (?,?,?);");
        preparedStatementMinions.setString(1,minionName);
        preparedStatementMinions.setInt(2,age);
        preparedStatementMinions.setInt(3,idTown);

        preparedStatementMinions.execute();
        int idMinion=getIdByEntityName("minions",minionName);

        PreparedStatement preparedStatementMinionsVillains = connection.prepareStatement( "INSERT INTO `minions_villains`(minion_id,villain_id) VALUES (?,?);");
        preparedStatementMinionsVillains.setInt(1,idMinion);
        preparedStatementMinionsVillains.setInt(2,idVillain);
        preparedStatementMinionsVillains.execute();
        System.out.printf("Successfully added %s to be minion of %s%n",minionName,villainName);
    }

    private static void exerciseFive() throws SQLException {
        System.out.println("Enter country name:");
        String countryName=scan.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE towns \n" +
                "SET name= UPPER(name) \n" +
                "WHERE country=?;");
        preparedStatement.setString(1,countryName);
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows>0) {
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT name FROM `towns`\n" +
                    "WHERE `country`=?;");
            preparedStatement1.setString(1,countryName);
            ResultSet resultSet=preparedStatement1.executeQuery();
            List<String> towns=new ArrayList<>();
            while(resultSet.next()){
                towns.add(resultSet.getString("name"));
            }
            System.out.printf("%d town names were affected.%n",affectedRows);
            System.out.print(towns);
        } else {
            System.out.printf("No town names were affected.%n");
        }
    }

    private static void exerciseSeven() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT `name` FROM minions;");
        ResultSet resultSet=preparedStatement.executeQuery();
        Deque<String> minionsNames=new ArrayDeque<>();
        while(resultSet.next()) {
            minionsNames.add(resultSet.getString("name"));
        }
        for (int i = 0; i <minionsNames.size() ; i++) {
            System.out.println(minionsNames.poll());
            System.out.println(minionsNames.pollLast());
        }
    }

    private static void exerciseEight() throws SQLException {
        System.out.println("Enter minions id:");
        String[] ids= scan.nextLine().split(" ");

        for (int i = 0; i <ids.length ; i++) {
            PreparedStatement preparedStatement = connection.prepareStatement( "UPDATE `minions`\n" +
                    "SET `age`=`age`+1,`name`=LOWER(`name`)\n" +
                    "WHERE `id` = ?;");

            preparedStatement.setInt(1, Integer.parseInt(ids[i]));

            preparedStatement.executeUpdate();
        }

        PreparedStatement preparedStatement1 = connection.prepareStatement( "SELECT `name`,`age` FROM `minions`;");
        ResultSet resultSet=preparedStatement1.executeQuery();
        while (resultSet.next()) {
            System.out.printf("%s %d %n",resultSet.getString("name"),resultSet.getInt("age"));
        }

    }

    private static void exerciseNine() throws SQLException {
        System.out.println("Enter minion id:");
        int minionId= Integer.parseInt(scan.nextLine());
        CallableStatement usp_get_older = connection.prepareCall( "CALL usp_get_older(?)");
        usp_get_older.setInt(1,minionId);
        usp_get_older.execute();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT `name`,`age` FROM `minions` WHERE `id`=?");
        preparedStatement.setInt(1,minionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.printf("%s %d %n",resultSet.getString("name"),resultSet.getInt("age"));
    }

    private static boolean checkIfEntityExist(String tableName, String entity) throws SQLException {
        String query=String.format("SELECT COUNT(name) AS `count` FROM %S WHERE name='%s';",tableName,entity);
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet=preparedStatement.executeQuery();
        resultSet.next();
        if (resultSet.getInt("count")>0) {
            return true;
        }
        return false;
    }

    private static int getIdByEntityName(String tableName,String entityName) throws SQLException {
        String query=String.format("SELECT `id` FROM %s WHERE `name`='%s';",tableName,entityName);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }
}
