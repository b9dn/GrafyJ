package gui;

import init.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Controller {

    @FXML
    AnchorPane pane;
    @FXML
    private TextField wymiarytext;
    @FXML
    private TextField wartoscitext;
    @FXML
    private Button generujbutton;
    @FXML
    private TextField pathtext;
    @FXML
    private Button wczytajbutton;
    @FXML
    private Button zapiszbutton;
    @FXML
    private RadioButton bfsbutton;
    @FXML
    private RadioButton dijcheck;
    @FXML
    private TextField dialog;
    @FXML
    private TextField bfstext;

    private Graph g;

    @FXML
    public void generuj() {
        try {
            if (g != null) {
                Drawer.clean(pane, g);
            }

            String a = wymiarytext.getText();
            String b = wartoscitext.getText();
            String[] wym = a.split(":");
            String[] war = b.split(":");
            int h = Integer.parseInt(wym[0]);
            int w = Integer.parseInt(wym[1]);
            double min = Double.parseDouble(war[0]);
            double max = Double.parseDouble(war[1]);

            generujSprawdzenie(h, w, min, max);

            g = new Graph(h, w, min, max);
            bfsbutton.setSelected(true);
            dialog.setText("Wygenerowano graf");
            Drawer.draw(pane, 70, 930, 100, 680, g);
            bfsg();

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            dialog.setText("Podano złe dane wejściowe do generacji grafu");
        } catch (MyException e) {
            dialog.setText(e.getMessage());
        }

    }

    private void generujSprawdzenie(int h, int w, double min, double max) throws MyException {
        if (h < 1 || w < 1) {
            throw new MyException("Wymiary grafu muszą być dodatnie", null, 0);
        }
        if (min < 0 || max < 0 || min > max) {
            throw new MyException("Zakres wartości musi być dodatni i min<max", null, 0);
        }

    }

    @FXML
    public void wczytaj() {
        try {
            if (g != null) {
                Drawer.clean(pane, g);
            }

            dijcheck.setDisable(true);
            g = ReadGraph.readGraph(pathtext.getText());

            Drawer.draw(pane, 70, 930, 100, 680, g);
            bfsbutton.setSelected(true);

            dialog.setText("Wczytano graf");
            bfsg();

        } catch (IOException | MyException e) {
            dialog.setText(e.getMessage());
        }
    }

    @FXML
    public void zapisz() {
        if (g == null) {
            dialog.setText("Brak grafu");
        } else {
            try {
                g.writeToFile(pathtext.getText());
                dialog.setText("Zapisano graf");
            } catch (IOException e) {
                dialog.setText(e.getMessage());
            }

        }
    }

    @FXML
    public void bfsg() {
        if (g == null) {
            dialog.setText("Brak grafu do BFS");
        } else {
            if (g instanceof DijkstraGraph)
                ((DijkstraGraph) g).cleanDijkstraLines();

            Bfs b = new Bfs(g);
            boolean b1 = b.bfsRun();
            if (b1) {
                bfstext.setText("Spójny");
                dijcheck.setDisable(false);
            } else {
                bfstext.setText("Niespójny");
                dijcheck.setDisable(true);
            }
        }
    }

    @FXML
    public void dij() {
        if (g == null) {
            dialog.setText("Brak grafu do Dijkstry");
        } else {
            Bfs b = new Bfs(g);
            if (b.bfsRun()) {
                g = new DijkstraGraph(g);
                ((DijkstraGraph) g).setPane(pane);
            } else {
                dialog.setText("Dijkstra wymaga spójnego grafu");
            }
        }
    }
}
