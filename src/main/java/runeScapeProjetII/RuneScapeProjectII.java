package runeScapeProjetII;

import runeScapeProjetII.controller.KeyboardController;
import runeScapeProjetII.controller.LogicController;
import runeScapeProjetII.controller.MouseController;
import runeScapeProjetII.view.MainWindowView;

public class RuneScapeProjectII {

    public void start() {
        MainWindowView view = new MainWindowView();

        LogicController logicController = new LogicController();
        MouseController mouseController = new MouseController(32);
        KeyboardController keyboardController = new KeyboardController();
        view.initialize(mouseController, keyboardController, logicController);
    }
}
