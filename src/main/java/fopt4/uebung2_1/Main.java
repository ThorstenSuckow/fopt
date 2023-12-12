package fopt4.uebung2_1;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Closing either window does not end the main thread, only closing all windows
 * closes the main thread.
 * The primary stage is constructed by the platform, subsequent stage objects can be
 * created by the application and must run  on the JavaFX application thread.
 * If the primary stage is closed before the additional edit-dialog gets closed,
 * the JavaFX Application threads tracks any additional dialogs living currently
 * in the JavaFX Application Thread. If no more objects live in the JavaFX application
 * thread, the main thread ends (if no other foreground threads are persisting withing
 * the main thread, that is).
 *
 * From the docs for javafx.application.Application:
 * <quote>
 *     [The JavaFX runtime]
 *     Waits for the application to finish, which happens when either of the following occur:
 *          the application calls Platform.exit()
 *          the last window has been closed and the implicitExit attribute on Platform is true
 * </quote>
 *
 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html">...</a>
 */
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
