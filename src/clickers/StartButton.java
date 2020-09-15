package clickers;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import virtualmouse.Mouse;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


public class StartButton extends Application {


    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Autoclick");
        primaryStage.setScene(new ButtonStage());
        primaryStage.sizeToScene();

        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setResizable(false);

        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }



    private static class ButtonStage extends Scene {

        private final Mouse mouse;

        private final Button bt;
        private final AtomicBoolean clicking = new AtomicBoolean(false);

        /**
         * Creates a Scene for a specific root Node.
         *
         * @throws NullPointerException if root is null
         */
        public ButtonStage() throws AWTException {
            super(new StackPane());
            mouse = new Mouse();

            bt = new Button(ToShow.READY.getLabel());

            StackPane root = (StackPane) getRoot();

            bt.setPrefSize(200, 30);
            bt.setMinSize(200, 30);
            bt.setMaxSize(200, 30);

            root.setAlignment(Pos.CENTER);

            root.getChildren().addAll(bt);

            bt.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                if (newPropertyValue) {
                    System.out.println("button on focus");
                    show(ToShow.READY);
                } else {
                    System.out.println("button out focus");
                    if (clicking.get()) {
                        show(ToShow.CLICKING);
                    }else {
                        show(ToShow.OUT_OF_FOCUS);
                    }
                }
            });

            setOnKeyPressed(event -> {
                KeyCode key = event.getCode();

                if (key == KeyCode.DIGIT0){
                    Click clicker = new Click(mouse, clicking);

                    clicker.setOnRunning(se -> bt.setText(ToShow.CLICKING.getLabel()));

                    clicker.setOnSucceeded(se -> bt.setText(bt.isFocused() ? ToShow.READY.getLabel() : ToShow.OUT_OF_FOCUS.getLabel()));

                    ExecutorService es = Executors.newFixedThreadPool(1);
                    es.execute(clicker);
                    es.shutdown();
                }
            });
        }

        public void show(ToShow toShow) {
            bt.setText(toShow.getLabel());
        }

    }

    public static class Click extends Task<String> {

        private final Mouse mouse;
        private final AtomicBoolean clicking;

        public Click(Mouse mouse, AtomicBoolean clicking) {
            this.mouse = mouse;
            this.clicking = clicking;
        }

        /**
         * Invoked when the Task is executed, the call method must be overridden and
         * implemented by subclasses. The call method actually performs the
         * background thread logic. Only the updateProgress, updateMessage, updateValue and
         * updateTitle methods of Task may be called from code within this method.
         * Any other interaction with the Task from the background thread will result
         * in runtime exceptions.
         *
         * @return The result of the background work, if any.
         */
        @Override
        protected String call() {
            clicking.set(true);
            Point position = new Point(mouse.getPosition());
            int count = 0;

            while (mouse.getPosition().equals(position)) {
                mouse.clickButton(1);
                mouse.delay(1);

                if (count++ > 1000000) {
                    mouse.moveMouseBy(100, 100);
                    mouse.moveMouseBy(-100, -100);
                    count = 0;
                }

            }
            clicking.set(false);
            return "done";
        }
    }

    private enum ToShow{
        READY(("Press 0 to start clicking!")),
        CLICKING(("Clicking!")),
        OUT_OF_FOCUS(("Give me the focus if I am to click!")),
        ;

        private final String label;

        ToShow(String label) {
            this.label = label;
        }

        public String getLabel(){
            return label;
        }
    }

}
