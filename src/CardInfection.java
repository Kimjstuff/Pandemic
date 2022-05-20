import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CardInfection {
    private int pandemicLevel = 5; //SKAL ÆNDRES TIL INPUT FRA BRUGER
    private ArrayList<String> InfectedCard = new ArrayList<>(); //used to setup game
    private ArrayList<String> InfectedCardinGame = new ArrayList<>(); //used in game

    public void startNewGame() {
        newGameCardShuffle();
    }

    public void newGameCardShuffle() {
        deleteSQLinfections(); //clear columns for new input
        readInfectionsFromFile(); //get infection cities from .txt and put in List
        putPandemicCardInArray();
        readFromArrayPutInSQL(); //loop List and put in SQL DB
        cardToArrayAndShuffleBack(); //take cards, put in list and shuffle then put back in SQL - THESE are the active in game
        countCardRowSQL();
    }

    public void putPandemicCardInArray() {

        for (int i = 0; i < pandemicLevel; i++) {
            InfectedCard.add("PANDEMIC");
        }
    }


    public void cardToArrayAndShuffleBack() { //SKAL SÆTTES OP TIL BRUGTE KORT OG IKKE HELE SQL-LISTEN evt. alternativt 2 arraylister
        try {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pandemic", "root", "Kimjensen101");
            String query = "SELECT cardname FROM infectioncard";
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String cityname = rs.getString(1);
                InfectedCardinGame.add(cityname);
            }
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO infectioncardingame (cardid, cardname) values (?, ?)");
            for (int i = 0; i < InfectedCard.size(); i++) {
                int max = 48 + pandemicLevel - i;
                int min = 0;
                int b = (int) (Math.random() * (max - min));
                preparedStatement.setInt(1, i + 1);
                preparedStatement.setString(2, InfectedCardinGame.get(b));
                preparedStatement.executeUpdate();
                InfectedCardinGame.remove(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void readFromArrayPutInSQL() {
        try {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pandemic", "root", "Kimjensen101");
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO infectioncard (cardid, cardname) values (?, ?)");
            for (int i = 0; i < InfectedCard.size(); i++) {
                preparedStatement.setInt(1, i + 1);
                preparedStatement.setString(2, InfectedCard.get(i));
                preparedStatement.executeUpdate();
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void readInfectionsFromFile() {
        String cityInfected;
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/infectedcity.txt"));
            while ((cityInfected = br.readLine()) != null) {
                InfectedCard.add(cityInfected);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public void deleteSQLinfections() {
        try {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pandemic", "root", "Kimjensen101");
            String deleteCardInfection = "TRUNCATE infectioncard"; //Delete all

            Statement statement = connect.createStatement();
            statement.executeUpdate(deleteCardInfection);

            String deleteCardInfectionShuffler = "TRUNCATE infectioncardingame"; //Delete all
            statement.executeUpdate(deleteCardInfectionShuffler);

            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void countCardRowSQL() {
        try {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pandemic", "root", "Kimjensen101");
            Statement statement = connect.createStatement();
            String rowCount = "SELECT COUNT(*) FROM infectioncard";
            String query = "select count(*) from infectioncard"; //get the number of rows
            ResultSet rs = statement.executeQuery(query); //
            rs.next();
            int count = rs.getInt(1);
            System.out.println("Number of cards : " + count);
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
