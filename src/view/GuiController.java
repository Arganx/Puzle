package view;

import javafx.animation.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.FileControl;
import model.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by qwerty on 09-Apr-17.
 */
public class GuiController {
    @FXML
    AnchorPane panel;

    @FXML
    Button button;

    @FXML
    Label timer;



    private List<Tile> tileList = new ArrayList<Tile>();
    boolean started;
    private FileControl fc = new FileControl();
    Tile tile1 = null, tile2 = null;
    int win=0;
    private long time = 0;
    private List<Long> tab=new ArrayList<Long>();

    @FXML
    private void initialize() throws IOException {
        for(int i=0;i<5;i++)
        {
            tab.add(Long.valueOf(fc.read()[i]));
        }
        BufferedImage bi = ImageIO.read(new File("C:\\Users\\qwerty\\IdeaProjects\\Puzle2.0\\out\\production\\Puzle2.0\\assets\\Trybson.jpg"));
        bi=fc.resizeImage(bi,800,600);
        BufferedImage part;
        Tile tile;
        int a = -266, b = 0;
        for (int i = 0; i < 9; i++) {
            a += 266;
            if (a > 797) {
                a = 0;
                b += 200;
            }
            part = bi.getSubimage(a, b, 266, 200);
            tile = new Tile(266, 200, part, i);
            tile.setLayoutX(a + i % 3 * 3);
            tile.setLayoutY(b + (i / 3) * 3);
            tile.setOrgX(tile.getLayoutX());
            tile.setOrgY(tile.getLayoutY());
            tileList.add(tile);
            tile.setFill(new ImagePattern(SwingFXUtils.toFXImage(tile.getPart(), null)));
        }

        panel.getChildren().addAll(tileList);


        Events();
    }

    private void updateTime() {
        long second = TimeUnit.MILLISECONDS.toSeconds(time);
        long minute = TimeUnit.MILLISECONDS.toMinutes(time);
        long hour = TimeUnit.MILLISECONDS.toHours(time);
        long millis = time - TimeUnit.SECONDS.toMillis(second);
        String timeString = String.format("%02d:%02d:%02d:%d", hour, minute,
                second, millis);
        timer.setText(timeString);
        time += 100;
    }

    Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(100),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    updateTime();
                }
            }));


    @FXML
    public void startHandle()
    {
        time=0;
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        started =true;
        win =0;
        for(int i=0;i<tileList.size();i++)
        {
            tileList.get(i).setLayoutX(tileList.get(i).getOrgX());
            tileList.get(i).setLayoutY(tileList.get(i).getOrgY());
        }
        Collections.shuffle(tileList);
        for (int i = 0; i < tileList.size(); i++) {
            Tile tile = tileList.get(i);
            int num = tile.getNum();
            tile.setFill(new ImagePattern(SwingFXUtils.toFXImage(tileList.get(num).getPart(), null)));
        }

    }

    void Events()
    {
        for (Tile tile: tileList) {
            tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (started == true) {
                        if (tile.isAlreadypressed()) {

                            if (tile1 == (Tile) event.getSource()) {
                                tile1 = null;
                                tile.setAlreadypressed(false);
                                tile.setStyle("-fx-effect: null");
                            } else if (tile2 == (Tile) event.getSource()) {
                                tile2 = null;
                                tile.setAlreadypressed(false);
                                tile.setStyle("-fx-effect: null");
                            }
                        } else if (!tile.isAlreadypressed()) {
                            if (tile1 == null) {
                                tile1 = (Tile) event.getSource();
                                tile.setAlreadypressed(true);
                                tile.setStyle("-fx-effect: innershadow(gaussian, #039ed3, 3, 1.0, 0, 0);");
                            } else if (tile2 == null) {
                                tile2 = (Tile) event.getSource();
                                tile.setAlreadypressed(true);
                                tile.setStyle("-fx-effect: innershadow(gaussian, #039ed3, 3, 1.0, 0, 0);");
                            }

                            if (tile1 != null & tile2 != null) {

                                Tile a= tile1;
                                Tile b= tile2;
                                double x = a.getLayoutX();
                                double y =a.getLayoutY();
                                int indexFirst = tileList.indexOf(tile1);
                                int indexSecond = tileList.indexOf(tile2);

                                PathTransition ptr = getPathTransition(a, b);
                                PathTransition ptr2 = getPathTransition(b, a);//tu problem
                                ParallelTransition pt = new ParallelTransition(ptr,ptr2);
                                pt.play();

                                pt.setOnFinished(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        a.setTranslateX(0);
                                        a.setTranslateY(0);
                                        b.setTranslateX(0);
                                        b.setTranslateY(0);

                                        a.setLayoutX(b.getLayoutX());
                                        a.setLayoutY(b.getLayoutY());
                                        b.setLayoutX(x);
                                        b.setLayoutY(y);
                                    }
                                });


                                tileList.get(indexFirst).setAlreadypressed(false);
                                tileList.get(indexSecond).setAlreadypressed(false);
                                tileList.get(indexFirst).setStyle("-fx-effect: null");
                                tileList.get(indexSecond).setStyle("-fx-effect: null");
                                Collections.swap(tileList,indexFirst,indexSecond);
                                for(int i=0;i<tileList.size();i++)
                                {
                                    if(i == tileList.get(i).getNum())
                                    {
                                        win++;
                                    }
                                }
                                if(win==tileList.size())
                                {
                                    started=false;
                                    timeline.stop();
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Wygrales");
                                    alert.setHeaderText("Wszystkie puzle sa poprawnie ulozone");
                                    long second = TimeUnit.MILLISECONDS.toSeconds(time);
                                    long minute = TimeUnit.MILLISECONDS.toMinutes(time);
                                    long hour = TimeUnit.MILLISECONDS.toHours(time);
                                    long millis = time - TimeUnit.SECONDS.toMillis(second);
                                    String timeString = String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);
                                    alert.setContentText("Twoj czas to: "+timeString);
                                    tab.add(time);
                                    tab.sort(comparator);
                                    if(time!=tab.get(5))
                                    {
                                        alert.setTitle("Gratulacje!");
                                        alert.setHeaderText("Najwyzszy wynik!");
                                        alert.setContentText("Jestes "+ (tab.indexOf(time)+1) + " w rankingu"+"\nInne wyniki:\n"
                                        +"1\t"+konwertuj(tab.get(0))+"\n2\t" + konwertuj(tab.get(1)) + "\n3\t" + konwertuj(tab.get(2))+ "\n4\t" +konwertuj(tab.get(3)) + "\n5\t" + konwertuj(tab.get(4)));
                                    }
                                    tab.remove(5);
                                    fc.add(tab.get(0).toString(),tab.get(1).toString(),tab.get(2).toString(),tab.get(3).toString(),tab.get(4).toString());
                                    alert.showAndWait();
                                }
                                win=0;
                                tile1 = null;
                                tile2 = null;

                            }
                        }
                    }
                }
            });
        }

    }
    String konwertuj(long time)
    {
        long second = TimeUnit.MILLISECONDS.toSeconds(time);
        long minute = TimeUnit.MILLISECONDS.toMinutes(time);
        long hour = TimeUnit.MILLISECONDS.toHours(time);
        long millis = time - TimeUnit.SECONDS.toMillis(second);
        return String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);
    }

    Comparator<Long> comparator = new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return o1.intValue() - o2.intValue();
        }
    };

    private PathTransition getPathTransition(Tile first, Tile second) {
        PathTransition ptr = new PathTransition();
        Path path = new Path();
        path.getElements().clear();
        path.getElements().add(new MoveToAbs(first));
        path.getElements().add(new LineToAbs(first, second.getLayoutX(), second.getLayoutY()));
        ptr.setPath(path);
        ptr.setNode(first);
        return ptr;
    }

    public static class MoveToAbs extends MoveTo {
        public MoveToAbs(Node node) {
            super(node.getLayoutBounds().getWidth() / 2,
                    node.getLayoutBounds().getHeight() / 2);
        }

    }
    public static class LineToAbs extends LineTo {
        public LineToAbs(Node node, double x, double y) {
            super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2, y -
                    node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
        }
    }

}
