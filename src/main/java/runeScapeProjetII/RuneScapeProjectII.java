package runeScapeProjetII;

import runeScapeProjetII.controller.KeyboardController;
import runeScapeProjetII.controller.MouseController;
import runeScapeProjetII.view.MainWindowView;

public class RuneScapeProjectII {

    public void start() {
        MainWindowView view = new MainWindowView();

        MouseController mouseController = new MouseController();
        KeyboardController keyboardController = new KeyboardController();
        view.initialize(mouseController, keyboardController);
    }
}
