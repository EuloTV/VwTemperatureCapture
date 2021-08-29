package controller;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.NumberTextField;
import model.Measurement;

public class TemperatureCaptureView extends Application {

	private final ObservableList<String> temperatureUnitsList = FXCollections
	        .observableArrayList(
	        		new String("K"), 
	        		new String("°C"),
	        		new String("°F")
	        		);
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {		
		Scene scene = new Scene(new Group());
		configureStage(stage);		
		final Label label = new Label("Temperaturerfassung");
		label.setFont(new Font("Arial", 24));		
		final Text pointInTimeText = new Text("Datum und Uhrzeit:");
		DatePicker datePicker = new DatePicker();
		datePicker.setValue(LocalDate.of(2021, 1, 1));
		datePicker.getValue();
		final Spinner<Integer> hourSpinner = new Spinner<Integer>(0, 23, 0);
		hourSpinner.setMaxWidth(55);
		final Text colonText = new Text(":");
		final Spinner<Integer> minuteSpinner = new Spinner<Integer>(0, 59, 0);
		minuteSpinner.setMaxWidth(55);
		final Text oclockText = new Text("Uhr");		
		final HBox pointInTimeHBox = new HBox();
		pointInTimeHBox.setSpacing(5);
		pointInTimeHBox.setPadding(new Insets(10, 0, 0, 10));
		pointInTimeHBox.getChildren().addAll(pointInTimeText, datePicker, hourSpinner, colonText, minuteSpinner, oclockText);		
		final Text temperatureText = new Text("Temperatur:");
		final NumberTextField temperatureTextField = new NumberTextField();
		temperatureTextField.setPromptText("0.0");
		ComboBox<String> temperatureBox = new ComboBox<>();
		temperatureBox.getItems().addAll(this.temperatureUnitsList);
		temperatureBox.getSelectionModel().selectFirst();
		final HBox temperatureHBox = new HBox();
		temperatureHBox.setSpacing(5);
		temperatureHBox.setPadding(new Insets(10, 0, 0, 10));
		temperatureHBox.getChildren().addAll(temperatureText, temperatureTextField, temperatureBox);	
		
		final Button captureButton = new Button("neuen Messwert erfassen");		
		TableView<Measurement> captureTable = new TableView<>();
		TableColumn<Measurement, String> pointInTimeCol = new TableColumn<>("erfasst am");
		TableColumn<Measurement, String> kelvinCol = new TableColumn<>("T in K");
		TableColumn<Measurement, String> celsiusCol = new TableColumn<>("T in °C");
		TableColumn<Measurement, String> fahrenheitCol = new TableColumn<>("T in °F");		
		pointInTimeCol.prefWidthProperty().bind(captureTable.widthProperty().multiply(0.4));		
		captureTable.getColumns().addAll(pointInTimeCol, kelvinCol, celsiusCol, fahrenheitCol);		
		ObservableList<Measurement> measurements = FXCollections.observableArrayList();	
		pointInTimeCol.setCellValueFactory(new PropertyValueFactory("pointInTimeString"));
		kelvinCol.setCellValueFactory(new PropertyValueFactory("temperatureKelvinString"));
		celsiusCol.setCellValueFactory(new PropertyValueFactory("temperatureCelsiusString"));
		fahrenheitCol.setCellValueFactory(new PropertyValueFactory("temperatureFahrenheitString"));		
		captureTable.setItems(measurements);
		captureTable.setMaxSize(450, 450);		
		captureButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				addMeasurement(datePicker, hourSpinner, minuteSpinner, 
						temperatureTextField, temperatureBox, captureTable, measurements);
			}
		});		
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, pointInTimeHBox, temperatureHBox, captureButton, captureTable);	
		((Group) scene.getRoot()).getChildren().addAll(vbox);	
		stage.setScene(scene);
		stage.show();	
	}

	private void configureStage(Stage stage) {
		
		stage.setTitle("Temperaturerfassung");
		stage.setWidth(470);
		stage.setHeight(590);
		stage.setResizable(false);
		
	}

	private void addMeasurement(DatePicker datePicker, final Spinner hourSpinner,
			final Spinner minuteSpinner, final NumberTextField temperatureTextField,
			ComboBox<String> temperatureBox, TableView<Measurement> captureTable,
			ObservableList<Measurement> measurements) {
		
		Measurement measurement = 
				new Measurement(datePicker.getValue(), (int)hourSpinner.getValue(), (int)minuteSpinner.getValue());
		if (temperatureBox.getValue().contentEquals("K")) {
			measurement.setTemperatureKelvin(temperatureTextField.getText());
			measurement.kelvinToCelsius(measurement.getTemperatureKelvin());
			measurement.kelvinToFahrenheit(measurement.getTemperatureKelvin());
		} else if (temperatureBox.getValue().contentEquals("°C")) {
			measurement.setTemperatureCelsius(temperatureTextField.getText());
			measurement.celsiusToKelvin(measurement.getTemperatureCelsius());
			measurement.kelvinToFahrenheit(measurement.getTemperatureKelvin());
		} else if (temperatureBox.getValue().contentEquals("°F")) {
			measurement.setTemperatureFahrenheit(temperatureTextField.getText());
			measurement.fahrenheitToKelvin(measurement.getTemperatureFahrenheit());
			measurement.kelvinToCelsius(measurement.getTemperatureKelvin());
		}
		measurements.add(measurement);
		captureTable.refresh();
		
	}
	
}
