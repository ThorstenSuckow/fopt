package fopt4.uebung2_1;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public void start(Stage stage) throws Exception {
        MainPresenter mainPresenter = initApplication();
        mainPresenter.showOverviewView();
        Scene scene = new Scene(mainPresenter.getView(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Kontaktsystem - Hochschule Trier");
        stage.show();
    }

    private MainPresenter initApplication() {
        MainPresenter mainPresenter = new MainPresenter();
        MainView mainView = new MainView();
        OverviewPresenter overviewPresenter = new OverviewPresenter();
        OverviewView overviewView = new OverviewView();

        ContactModel model = new ContactModel();

        mainPresenter.setView(mainView);
        mainPresenter.setOverviewPresenter(overviewPresenter);

        overviewPresenter.setView(overviewView);
        overviewPresenter.setContactModel(model);
        overviewView.setPresenter(overviewPresenter);


        return mainPresenter;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
