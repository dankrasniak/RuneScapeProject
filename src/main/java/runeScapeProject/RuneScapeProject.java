package runeScapeProject;

import org.opencv.core.Core;
import org.opencv.objdetect.CascadeClassifier;
import runeScapeProject.model.ChickenClassifierModel;
import runeScapeProject.model.Model;

public class RuneScapeProject {

    public void start() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CascadeClassifier chickenDetector = new CascadeClassifier(
                "S:\\Projekty\\IdeaProjects\\RuneScapeProject\\src\\main\\resources\\cascade.xml");

        View view = new View();

        /* Possible options:
         *  - ChickenClassifierModel
         * */
        Model model = new ChickenClassifierModel(view, chickenDetector);

        Controller controller = new Controller(view, model);

        view.initialize(controller);

        // Observers
        view.addObserver(controller);
        view.addObserver(model);
    }
}
