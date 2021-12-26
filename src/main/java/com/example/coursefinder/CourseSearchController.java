package com.example.coursefinder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseSearchController implements Initializable {
    @FXML
    private TableView<CourseSearchModel> courseTableView;
    @FXML
    private TableColumn<CourseSearchModel, Integer> idTableColumn;
    @FXML
    private TableColumn<CourseSearchModel, String> titleTableColumn;
    @FXML
    private TableColumn<CourseSearchModel, String> addressTableColumn;
    @FXML
    private TableColumn<CourseSearchModel, Integer> priceTableColumn;
    @FXML
    private TableColumn<CourseSearchModel, String> emailTableColumn;
    @FXML
    private TableColumn<CourseSearchModel, String> descriptionTableColumn;
    @FXML
    private TextField keyWordTextField;
    
    ObservableList<CourseSearchModel> courseSearchModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connectDB = dataBaseConnection.getDBConnection();

        String courseViewQuery = "select id, title,address,price,email,description from courses";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(courseViewQuery);

            while (queryOutput.next()) {
                Integer queryCourseID = queryOutput.getInt("id");
                String queryTitle = queryOutput.getString("title");
                String queryAddress = queryOutput.getString("address");
                int queryPrice = queryOutput.getInt("price");
                String queryEmail = queryOutput.getString("email");
                String queryDescription = queryOutput.getString("description");

                courseSearchModelObservableList.
                        add(new CourseSearchModel(queryCourseID, queryTitle, queryAddress, queryPrice, queryEmail, queryDescription));
            }
            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

            courseTableView.setItems(courseSearchModelObservableList);

            FilteredList<CourseSearchModel> filteredData = new FilteredList<>(courseSearchModelObservableList, b -> true);

            keyWordTextField.textProperty().addListener((observable ,oldValue, newValue)->{

                filteredData.setPredicate(courseSearchModel -> {

                    //Если нет значений в поиске , тогда возвращает все что есть
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();

                    if (courseSearchModel.getTitle().toLowerCase().indexOf(searchKeyword) > -1){
                        return true; //Означает что мы нашли значение названия курса
                    }
                    else if(courseSearchModel.getDescription().toLowerCase().indexOf(searchKeyword)> -1){
                        return true;
                    }
                    else if(courseSearchModel.getAddress().toLowerCase().indexOf(searchKeyword)> -1){
                        return true;
                    }
                    else if(courseSearchModel.getEmail().toLowerCase().indexOf(searchKeyword)> -1){
                        return true;
                    }
                    else if(courseSearchModel.getPrice().toString().toLowerCase().indexOf(searchKeyword)> -1){
                        return true;
                    } else {
                        return false; // ничего не нашлось
                    }

                });
            });

            SortedList<CourseSearchModel> sortedData = new SortedList<>(filteredData);

            //связываем отсортированный результат с table view
            sortedData.comparatorProperty().bind(courseTableView.comparatorProperty());

            //применяем фильтрованные данные к table view
            courseTableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(CourseSearchController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}