package view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.primefaces.event.FileUploadEvent;
import database.DbCategoryOrderEntity;
import search.Reader;

@ManagedBean
@ViewScoped
public class ViewInteractionsHandler {

    private Reader reader = new Reader();
    private ArrayList<DbCategoryOrderEntity> entityList;
    public ArrayList<DbCategoryOrderEntity> tableList;

    public ArrayList<DbCategoryOrderEntity> getTableList() {
        return tableList;
    }

    public void setTableList(ArrayList<DbCategoryOrderEntity> tableList) {
        this.tableList = tableList;
    }

    public void handleFileUpload(FileUploadEvent event) {
        FileInputStream in = null;

        try {
            in = (FileInputStream) event.getFile().getInputstream();
            excelToEntities(in);

            FacesMessage message = new FacesMessage("Done!", event.getFile().getFileName() + " is uploaded. Inserted: " + reader.recordCounter + " Errors: " + reader.exceptionCounter);
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (IOException | InvalidFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void excelToEntities(FileInputStream fin) throws IOException, InvalidFormatException {

        entityList = reader.readFromExcel(fin);
        tableList = entityList;
    }
}