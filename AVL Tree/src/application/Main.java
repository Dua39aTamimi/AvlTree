package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {
	Avl avlt = new Avl();
	int max = 0;
	int total = 0;
	AvlNode maxNode = avlt.root;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			Label title = new Label("Welcome");
			title.setStyle("-fx-font-size:30px;-fx-text-fill:#bb8588");
			title.setPadding(new Insets(10, 0, 0, 190));
			VBox left = new VBox(20);
			left.setPadding(new Insets(45, 0, 0, 30));
			Button load = new Button("Load Files");
			load.setMaxWidth(Double.MAX_VALUE);
			Button search = new Button("Search");
			search.setDisable(true);
			search.setMaxWidth(Double.MAX_VALUE);
			Button maxFreq = new Button("Max Frequancy");
			maxFreq.setDisable(true);
			maxFreq.setMaxWidth(Double.MAX_VALUE);
			Button totalBabies = new Button("Total Babies");
			totalBabies.setDisable(true);
			totalBabies.setMaxWidth(Double.MAX_VALUE);
			Button print = new Button("Print");
			print.setDisable(true);
			print.setMaxWidth(Double.MAX_VALUE);
			left.getChildren().addAll(load, search, maxFreq, totalBabies, print);

			root.setTop(title);
			root.setLeft(left);
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setInitialDirectory(new File("c:"));
			Label dir=new Label();
			VBox v=new VBox();
			ScrollPane sp = new ScrollPane();
			GridPane gp1 = new GridPane();
			gp1.add(new Label("File Name"), 0, 0);
			gp1.add(new Label("File Size"), 1, 0);
			gp1.setVgap(20);
			gp1.setHgap(10);
			gp1.setPadding(new Insets(10, 10, 10, 10));
			sp.setContent(gp1);
			sp.setMaxWidth(200);
			sp.setMaxHeight(300);
			v.getChildren().addAll(dir,sp);
			v.setPadding(new Insets(45, 0, 0, 30));
			load.setOnAction(e -> {
				File selectedDirectory = directoryChooser.showDialog(primaryStage);
				File files = new File(selectedDirectory.getAbsolutePath());
				File filesList[] = files.listFiles();
				dir.setText(selectedDirectory.getAbsolutePath());
				Scanner sc = null;
				int i = 0;
				for (File file : filesList) {

					try {
						sc = new Scanner(file);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					gp1.add(new Label(file.getName()), 0, i + 1);
					gp1.add(new Label(file.length() + ""), 1, i + 1);
					while (sc.hasNextLine()) {
						String s = sc.nextLine();
						String[] line = s.split(",");

						char[] m = file.getName().toCharArray();
						String g = "" + m[m.length - 8] + m[m.length - 7] + m[m.length - 6] + m[m.length - 5];

						int year = Integer.parseInt(g);

						avlt.insert(new AvlNode(new Name(line[0], line[1].charAt(0)),
								new Frequency(year, Integer.parseInt(line[2]))));

					}
					i++;
				}
				root.setCenter(v);
				
				if (avlt.root != null) {
					search.setDisable(false);
					maxFreq.setDisable(false);
					totalBabies.setDisable(false);
					print.setDisable(false);
				}

			}

			);

			search.setOnAction(e -> {
				TextField nameField = new TextField();
				nameField.setPromptText("Enter the Baby Name.");
				ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList("Female", "Male"));
				Button got = new Button("get");
				HBox hbox = new HBox(10);
				hbox.getChildren().addAll(nameField, cb, got);
				Label nameLabel = new Label("Baby Name: ");
				Label genderLabel = new Label("Baby Gender: ");
				HBox h2 = new HBox(15);
				;
				h2.getChildren().addAll(nameLabel, genderLabel);

				GridPane gp = new GridPane();
				gp.setHgap(10);
				gp.setVgap(10);
				gp.setPadding(new Insets(10, 10, 10, 10));
				gp.add(new Label("Year"), 0, 0);
				gp.add(new Label("Frequency"), 1, 0);
				ScrollPane scrollPane = new ScrollPane();
				scrollPane.setMaxWidth(200);
				scrollPane.setMaxHeight(200);
				Label avgFre = new Label("Average Frequency: ");
				VBox vb = new VBox(10);
				vb.getChildren().addAll(hbox, h2, scrollPane, avgFre);
				vb.setPadding(new Insets(45, 0, 0, 30));
				root.setCenter(vb);

				got.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (gp.getChildren() != null) {
							gp.getChildren().clear();
							gp.add(new Label("Year"), 0, 0);
							gp.add(new Label("Frequency"), 1, 0);
						}
						String name = nameField.getText();
						char g;
						if (cb.getValue().compareTo("Female") == 0)
							g = 'F';
						else
							g = 'M';
						AvlNode n = avlt.search(name, g);
						if (n != null) {
							nameLabel.setText("Baby Name: " + n.name.name);
							if (n.name.gender == 'F')
								genderLabel.setText("Baby Gender: Female");
							else
								genderLabel.setText("Baby Gender: Male");

							Node temp = n.list.first;
							Label[][] ls = new Label[2][n.list.size];

							int j = 0;
							while (temp != null) {
								// d.add(temp.data);
								ls[0][j] = new Label(temp.data.year + "");
								ls[1][j] = new Label(temp.data.fre + "");

								gp.add(ls[0][j], 0, j + 1);
								gp.add(ls[1][j], 1, j + 1);

								temp = temp.next;
								j++;
							}
							scrollPane.setContent(gp);

							double sum = 0;
							Node temp2 = n.list.first;
							for (int i = 0; i < n.list.size; i++) {

								sum += temp2.data.fre;
								temp2 = temp2.next;
							}
							double avg = sum / n.list.size;
							avgFre.setText("Average Frequency: " + avg);
						} else {
							Alert alertType = new Alert(AlertType.ERROR);
							alertType.setContentText(name + ", " + g + " Not Found!!");
							alertType.show();
						}
					}
				});
			});

			Label maxNameFre = new Label("The Popular Name all over the years is:");
			HBox hb = new HBox(maxNameFre);

			hb.setPadding(new Insets(45, 0, 0, 30));
			maxFreq.setOnAction(e -> {
				root.setCenter(hb);
				max(avlt);
				maxNameFre.setText("The Popular Name all over the years is: \n" + maxNode.name.name + " "
						+ maxNode.name.gender + " " + max);

			});

			TextField yearTextField = new TextField();
			yearTextField.setPromptText("Enter the year.");
			Button done = new Button("Done");
			HBox hb3 = new HBox(10);
			hb3.getChildren().addAll(yearTextField, done);

			VBox ba = new VBox(10);
			ba.getChildren().addAll(hb3);
			ba.setPadding(new Insets(45, 0, 0, 30));
			totalBabies.setOnAction(e -> {
				root.setCenter(ba);
			});

			done.setOnAction(e -> {
				int y = Integer.parseInt(yearTextField.getText());
				sum(avlt, y);
				Label res = new Label("The number of Babies were born at " + y + " equal to " + total);
				ba.getChildren().add(res);
				total = 0;
			});
			print.setOnAction(e -> {
				try {
					FileChooser fileChooser = new FileChooser();

					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)",
							"*.txt");
					fileChooser.getExtensionFilters().add(extFilter);

					File file = fileChooser.showSaveDialog(primaryStage);

					FileWriter myWriter = new FileWriter(file);
					
					String s=avlt.levelOrder().toString();
					
					myWriter.write(s);
					myWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			Scene scene = new Scene(root, 500, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void max(Avl avlt) {

		// inorder(avlt.root,0);
		treverse(avlt.root);
	}

	private void treverse(AvlNode r) {

		if (r != null) {
			int freq = r.list.printFre();

			if (max < freq) {
				max = freq;
				maxNode = r;
			}

			treverse(r.left);

			treverse(r.right);

		}

	}

	public void sum(Avl avlt, int year) {
		treverse2(avlt.root, year);
	}

	private void treverse2(AvlNode r, int y) {

		if (r != null) {
			Node t = r.list.search(y);
			if (t != null) {
				total += t.data.fre;
			}

			treverse2(r.left, y);

			treverse2(r.right, y);

		}

	}
}
