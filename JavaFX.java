
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class JavaFXAppTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		SplitPane splitPane = new SplitPane(new PanelLeft(), new PanelRight());
		splitPane.setDividerPositions(0.4, 0.6);
		primaryStage.setScene(new Scene(new BorderPane(splitPane)));
		primaryStage.setWidth(700);
		primaryStage.setHeight(400);
		primaryStage.setTitle("Fast paste");
		primaryStage.show();
	}

	private class PanelLeft extends BorderPane {
		private TextField textField;
		private ListView<String> listView;
		private Button buttonAdd;


		public PanelLeft() {
			textField = new TextField();
			textField.setPromptText("Начните вводить текст для поиска...");
			listView = new ListView<String>(FXCollections.observableArrayList("Hello", "HI"));
			buttonAdd = new Button("add");
			setTop(textField);
			setCenter(listView);
			BorderPane.setAlignment(buttonAdd, Pos.CENTER);
			setBottom(buttonAdd);
		}
	}

	private class PanelRight extends BorderPane {
		private TextArea textArea;
		private Button buttonEdit;
		private Button buttonDelete;

		public PanelRight() {
			textArea = new TextArea();
			setCenter(textArea);

			buttonEdit = new Button("edit");
			buttonDelete = new Button("delete");
			HBox box = new HBox();
			HBox.setHgrow(buttonEdit, Priority.ALWAYS);
			box.getChildren().addAll(buttonEdit, buttonDelete);
			BorderPane.setAlignment(box, Pos.CENTER);
			setBottom(box);
		}
	}
}
