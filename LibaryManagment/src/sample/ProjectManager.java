package Search;

import javafx.scene.layout.GridPane;

public class ProjectManager {
    private static ProjectManager ourInstance = new ProjectManager();
    GridPane gridPane;

    public static ProjectManager getInstance() {
        return ourInstance;
    }

    private ProjectManager() {
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }
}
