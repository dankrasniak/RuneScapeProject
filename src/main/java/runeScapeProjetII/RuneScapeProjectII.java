package runeScapeProjetII;

import runeScapeProjetII.controller.KeyboardController;
import runeScapeProjetII.controller.logic.LogicController;
import runeScapeProjetII.controller.MouseController;
import runeScapeProjetII.controller.logic.StructuralNNLogicController;
import runeScapeProjetII.view.MainWindowView;

public class RuneScapeProjectII {

    public void start() {
        MainWindowView view = new MainWindowView();

        LogicController logicController = new StructuralNNLogicController();
        MouseController mouseController = new MouseController(32);
        KeyboardController keyboardController = new KeyboardController();
        view.initialize(mouseController, keyboardController, logicController);
    }
}
