package com.github.colorlines.javafx;

import com.github.colorlines.domain.*;
import com.google.common.base.Function;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Stanislav Kurilin
 */
public class App extends Application {
    public void go() {
        super.launch(new String[]{});
    }

    static boolean blocked = true;
    static Function<Turn, Void> listener;

    public void block(boolean block) {
        blocked = block;
    }

    public void setTurnListener(Function<Turn, Void> listener) {
        this.listener = listener;
    }

    static Group root;
    static Area area;
    int width = 9;
    int height = 9;

    static Position start;
    static TurnValidator turnValidator;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World");
        root = new Group();
        Scene scene = new Scene(root, 300, 250);
        draw();
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void refresh(Area area, TurnValidator turnValidator) {
        this.area = area;
        this.turnValidator = turnValidator;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                draw();
            }
        });

    }

    private void draw() {
        if (root == null) return;
        ;
        root.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setHgap(height);
        grid.setVgap(width);
        grid.setPadding(new Insets(0, 0, 0, 10));

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Button btn = new Button();
                btn.setLayoutX(100);
                btn.setLayoutY(80);
                final Position position = Position.create(x, y);
                final String l = (area == null || !area.contains(position)) ? (x + ":" + y) : (area.take(position).color().name());
                btn.setText(l);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (blocked) return;
                        if (start == null) {
                            if (area.contains(position))
                                start = position;
                        } else {
                            final Position start1 = start;
                            start = null;
                            if (turnValidator.isValid(area, new Turn() {
                                @Override
                                public Ball original() {
                                    return area.take(start1);
                                }

                                @Override
                                public Position moveTo() {
                                    return position;
                                }
                            })) {
                                listener.apply(new Turn() {
                                    @Override
                                    public Ball original() {
                                        return area.take(start1);
                                    }

                                    @Override
                                    public Position moveTo() {
                                        return position;
                                    }
                                });

                            }

                        }
                    }
                });
                grid.add(btn, x, y);

            }
        }
        root.getChildren().add(grid);
    }
}
