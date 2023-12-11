package fopt4.uebung1_10;


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
        DetailPresenter detailPresenter = new DetailPresenter();
        DetailView detailView = new DetailView();
        ContactModel model = new ContactModel();

        mainPresenter.setView(mainView);
        mainPresenter.setOverviewPresenter(overviewPresenter);
        mainPresenter.setDetailPresenter(detailPresenter);

        overviewPresenter.setView(overviewView);
        overviewPresenter.setMainPresenter(mainPresenter);
        overviewPresenter.setContactModel(model);
        overviewView.setPresenter(overviewPresenter);

        detailPresenter.setView(detailView);
        detailPresenter.setMainPresenter(mainPresenter);
        detailPresenter.setContactModel(model);
        detailView.setPresenter(detailPresenter);

        return mainPresenter;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
