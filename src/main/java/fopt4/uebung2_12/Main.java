package fopt4.uebung2_12;

import fopt4.uebung2_12.model.ContactModel;
import fopt4.uebung2_12.overview.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public void start(Stage stage) throws Exception
    {
        OverviewPresenter overviewPresenter = new OverviewPresenter();
        OverviewView overviewView = new OverviewView();
        ContactModel model = new ContactModel();

        overviewPresenter.setView(overviewView);
        overviewPresenter.setContactModel(model);
        overviewView.setPresenter(overviewPresenter);
        overviewPresenter.search();

        Scene scene = new Scene(overviewPresenter.getView(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Kontaktsystem - Hochschule Trier");
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
