package sandbox.rmi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.function.Consumer;

interface RMISupplier<T> {
    T execute() throws RemoteException;
}

class Presenter {

    View view;
    RMIIncrementor model;
    public Presenter(View v) {
        view = v;
        model = initModel();
        installListeners();
    }

    public RMIIncrementor initModel() {
        try {
            String target = InetAddress.getLocalHost().getHostAddress();

            return (RMIIncrementor) Naming.lookup("rmi://" + target + "/incrementor");
        } catch (Exception e) {
            System.out.println("[client exception] " + e);
        }

        return null;
    }

    private void installListeners() {

        Button resetButton = view.getResetButton();
        Button incrementButton = view.getIncrementButton();

        incrementButton.setOnAction(this::onIncrementButtonAction);
        resetButton.setOnAction(this::onResetButtonAction);

    }


    private void onIncrementButtonAction(ActionEvent evt) {
        view.getResetButton().setDisable(true);
        view.getIncrementButton().setDisable(true);

        asyncCall(()-> model.increment(), this::rmiCallback);
    }

    private void onResetButtonAction(ActionEvent evt) {

        view.getResetButton().setDisable(true);
        view.getIncrementButton().setDisable(true);

        asyncCall(()-> model.reset(), this::rmiCallback);
    }

    private void rmiCallback(int c) {
        view.getLabel().setText(String.valueOf(c));
        view.getResetButton().setDisable(false);
        view.getIncrementButton().setDisable(false);
    }

    private <T> void asyncCall(RMISupplier<T> rmiCall, Consumer<T> fxCall) {
        new Thread(() -> startInThread(rmiCall, fxCall)).start();
    }

    private <T> void startInThread(RMISupplier<T> rmiCall, Consumer<T> fxCall) {
        try {
            T t = rmiCall.execute();
            Platform.runLater(() -> fxCall.accept(t));
        } catch (Exception e) {
            System.err.println("[rmi exception] " + e);
        }
    }



}

class View extends VBox {

    private Button incrementButton;
    private Button resetButton;

    private Label label;

    public View() {
        initView();
    }

    public void initView() {

        label = getLabel();

        getChildren().add(label);

        HBox buttonHbox = new HBox();

        incrementButton = getIncrementButton();
        resetButton = getResetButton();

        buttonHbox.getChildren().add(incrementButton);
        buttonHbox.getChildren().add(resetButton);

        getChildren().add(buttonHbox);
    }

    public Label getLabel() {
        if (label == null) {
            label = new Label("-");
        }

        return label;
    }

    public Button getIncrementButton() {
        if (incrementButton == null) {
            incrementButton = new Button("increment");
        }

        return incrementButton;
    }


    public Button getResetButton() {
        if (resetButton == null) {
            resetButton = new Button("reset");
        }

        return resetButton;
    }

}

public class IncrementorDemo extends Application {

    public void start(Stage stage) {

        View v = new View();
        Presenter p = new Presenter(v);

        Scene s = new Scene(v);

        stage.setScene(s);

        stage.setTitle("Incrementor Demo");

        stage.setWidth(400);
        stage.setHeight(200);
        stage.show();
    }


    public static void main (String[] args) {
        launch(args);
    }

}
