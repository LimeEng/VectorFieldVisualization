package core;

import core.view.ColorStrategy;
import hacky.PredefinedVectorFields;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import noise.OpenSimplexNoise;
import operations.vector.VectorOperation;

public class VisualizationApp extends Application {

    private FieldSimulation field;
    private FxCanvas canvas;

    private double minX = -20;
    private double maxX = 20;
    private double minY = minX;
    private double maxY = maxX;
    private int nbrOfPoints = 10000;
    private boolean randomAge = true;
    private VectorOperation vectorField;

    private boolean drawFlag = true;

    private final double SCALE_DELTA = 1.1;

    private static final OpenSimplexNoise simplex = new OpenSimplexNoise(42);

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Hello World!");

        vectorField = v -> v;

        canvas = new FxCanvas();
        field = new FieldSimulation(minX, maxX, minY, maxY, nbrOfPoints, randomAge, vectorField);

        ComboBox<ColorStrategy> colorComboBox = new ComboBox<>();
        colorComboBox.getItems()
                .setAll(ColorStrategy.values());
        colorComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    canvas.setColorStrategy(newValue);
                });
        colorComboBox.getSelectionModel()
                .select(ColorStrategy.ANGLE);

        ComboBox<PredefinedVectorFields> vectorFieldComboBox = new ComboBox<>();
        vectorFieldComboBox.getItems()
                .setAll(PredefinedVectorFields.values());
        vectorFieldComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    vectorField = VectorOperation.of(newValue.vectorField);
                    resetSimulation();
                });
        vectorFieldComboBox.getSelectionModel()
                .select(PredefinedVectorFields.CIRCLE_DETAIL);

        HBox controlPanel = new HBox();
        controlPanel.getChildren()
                .addAll(colorComboBox, vectorFieldComboBox);

        controlPanel.setFillHeight(true);
        for (Node n : controlPanel.getChildren()) {
            HBox.setHgrow(n, Priority.ALWAYS);
        }

        VBox root = new VBox();
        root.getChildren()
                .addAll(controlPanel, canvas);

        root.setFillWidth(true);
        for (Node n : root.getChildren()) {
            VBox.setVgrow(n, Priority.ALWAYS);
        }

        root.setOnScroll(event -> {
            if (event.getDeltaY() == 0) {
                return;
            }
            double factor = (event.getDeltaY() < 0) ? SCALE_DELTA : 1 / SCALE_DELTA;
            minX *= factor;
            maxX *= factor;
            minY *= factor;
            maxY *= factor;

            // Zoom into the image.
            // image.setScaleX(image.getScaleX() * factor);
            // image.setScaleY(image.getScaleY() * factor);
            // Calculate displacement of zooming position.
            // double currentMouseX = event.getSceneX();
            // double currentMouseY = event.getSceneY();
            // double dx = (currentMouseX - minX) * (factor - 1);
            // double dy = (currentMouseY - maxY) * (factor - 1);
            // // Compensate for displacement.
            // minX = minX - dx;
            // maxY = maxY - dy;

            resetSimulation();
        });

        // root.setOnMousePressed(event -> {
        // pressedX = event.getSceneX();
        // pressedY = event.getSceneY();
        // });
        //
        // root.setOnMouseDragged(event -> {
        // double deltaX = MathUtil.clamp(event.getSceneX() - pressedX, -0.1,
        // 0.1);
        // double deltaY = MathUtil.clamp(event.getSceneY() - pressedY, -0.1,
        // 0.1);
        // minX += deltaX;
        // maxX += deltaX;
        // minY += deltaY;
        // maxY += deltaY;
        // resetSimulation();
        // pressedX = event.getSceneX();
        // pressedY = event.getSceneY();
        // event.consume();
        // });

        root.setOnKeyPressed(event -> {
            double offset = (maxX - minX) / 10;
            boolean hit = true;
            switch (event.getCode()) {
            case W:
                minY += offset;
                maxY += offset;
                break;
            case S:
                minY -= offset;
                maxY -= offset;
                break;
            case D:
                minX += offset;
                maxX += offset;
                break;
            case A:
                minX -= offset;
                maxX -= offset;
                break;
            case P:
                drawFlag = !drawFlag;
            default:
                hit = false;
                break;
            }
            if (hit) {
                resetSimulation();
            }
        });

        root.setStyle("-fx-background-color: black");

        canvas.widthProperty()
                .bind(root.widthProperty());
        canvas.heightProperty()
                .bind(root.heightProperty());

        Timeline timeLine = new Timeline();
        timeLine.getKeyFrames()
                .add(new KeyFrame(Duration.millis(20), (e) -> {
                    if (drawFlag) {
                        simulationStep();
                        canvas.draw(minX, maxX, minY, maxY);
                    }
                }));
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        Scene scene = new Scene(root, 1200, 900);

        stage.setTitle("Vector field");
        stage.setScene(scene);
        stage.show();
    }

    private void resetSimulation() {

        double xSpace = (maxX - minX) / 7;
        double ySpace = (maxY - minY) / 7;
        xSpace = 0;
        ySpace = 0;

        field.reset(minX + xSpace, maxX - xSpace, minY + ySpace, maxY - ySpace, nbrOfPoints, randomAge, vectorField);
        canvas.clearScreen();
    }

    private void simulationStep() {
        field.update();
        canvas.load(field.getPoints(), field.getPreviousPosition());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
