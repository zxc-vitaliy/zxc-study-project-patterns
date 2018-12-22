package ru.turkov.mvc;

import ru.turkov.mvc.controller.Controller;
import ru.turkov.mvc.model.DBModel;
import ru.turkov.mvc.model.Model;
import ru.turkov.mvc.model.ObservableModel;
import ru.turkov.mvc.view.SwingGUI;
import ru.turkov.mvc.view.View;

public class Main {
    public static void main(String[] args) {
        Model model = new DBModel();
        ObservableModel observableModel = new ObservableModel(model);
        Controller controller = new Controller(observableModel);
        View view = new SwingGUI(controller);
        view.showMenu();
    }
}
