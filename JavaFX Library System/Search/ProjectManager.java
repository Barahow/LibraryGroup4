package Search;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class ProjectManager {
    private static ProjectManager ourInstance = new ProjectManager();  // private
    public GridPane gridPane;
    List<BorrowBook> borrowBookList = new ArrayList<>();

    public static ProjectManager getInstance() {
        return ourInstance;
    }

    private ProjectManager() {
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;

    }

    public List<BorrowBook> getBorrowBookList() {
        return borrowBookList;
    }

    public void setBorrowBookList(List<BorrowBook> borrowBookList) {
        this.borrowBookList = borrowBookList;
    }
}
