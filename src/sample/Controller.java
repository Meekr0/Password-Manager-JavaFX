package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;

import java.io.*;

public class Controller
{

    String fileName;

    @FXML Button fileSelectButton;
    @FXML TextField passwordPromptText;
    @FXML PasswordField filePasswordField;

    @FXML TextField fileSelectText;

    @FXML TableView<Password> dataTable;
    @FXML TableColumn<Password, String> nameColumn;
    @FXML TableColumn<Password, String> passwordColumn;
    @FXML TableColumn<Password, String> categoryColumn;
    @FXML TableColumn<Password, String> loginColumn;
    @FXML TableColumn<Password, String> domainColumn;

    @FXML TextField newNameTextField;
    @FXML TextField newPasswordTextField;
    @FXML TextField newCategoryTextField;
    @FXML TextField newLoginTextField;
    @FXML TextField newDomainTextField;
    @FXML Button addButton;
    @FXML Button removeButton;
    @FXML Button removeCategoryButton;

    @FXML TextField newFileNameField;
    @FXML PasswordField newFilePasswordField;
    @FXML Button newFileButton;

    Cipher cipher = new Cipher(5);

    @FXML
    void selectFile()
    {

        String enteredPassword;
        String filePassword="INCORRECT";
        enteredPassword = filePasswordField.getText();
        filePasswordField.setText("");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./src"));

        File file = fileChooser.showOpenDialog(null);

        fileName = file.getName();

        BufferedReader passwordReader;
        try
        {

            String encryptedPassword;
            passwordReader = new BufferedReader(new FileReader("src\\password_"+fileName));
            encryptedPassword = passwordReader.readLine();
            filePassword = cipher.decrypt(encryptedPassword);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        if(filePassword.equals(enteredPassword)) {

            BufferedReader fileBufferedReader;
            try {

                fileSelectText.setText("Currently Selected: " + fileName);
                String currentLine;
                fileBufferedReader = new BufferedReader(new FileReader(file));

                ObservableList<Password> passwords = FXCollections.observableArrayList();

                while ((currentLine = fileBufferedReader.readLine()) != null) {

                    String[] currentLineStrings = currentLine.split(" ");
                    Password p = new Password(cipher.decrypt(currentLineStrings[0]), cipher.decrypt(currentLineStrings[1]),
                            cipher.decrypt(currentLineStrings[2]), cipher.decrypt(currentLineStrings[3]), cipher.decrypt(currentLineStrings[4]));
                    passwords.add(p);

                }

                dataTable.setItems(passwords);

            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
            fileSelectText.setText("Incorrect Password");


    }
    @FXML
    void createNewFile()
    {

        if(!newFileNameField.getText().equals("") && !newFilePasswordField.getText().equals("")) {

            try
            {
                File fileToCreate = new File(newFileNameField.getText());

                fileName = "password_" + fileToCreate.getName();
                writeToFile(cipher.encrypt(newFilePasswordField.getText()), false);

                fileName = fileToCreate.getName();
                fileSelectText.setText("Currently Selected: " + fileName);
                writeToFile("", false);

                newFileNameField.setText("");
                newFilePasswordField.setText("");

                dataTable.getItems().clear();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }
    @FXML
    void addButtonAction()
    {

        if(!newNameTextField.getText().equals("")
            && !newPasswordTextField.getText().equals("")
            && !newCategoryTextField.getText().equals(""))
        {

            Password passwordToAdd = new Password(newNameTextField.getText(),
                    newPasswordTextField.getText(), newLoginTextField.getText(),
                    newDomainTextField.getText(), newCategoryTextField.getText());

            dataTable.getItems().add(passwordToAdd);

            newNameTextField.setText("");
            newPasswordTextField.setText("");
            newCategoryTextField.setText("");
            newLoginTextField.setText("");
            newDomainTextField.setText("");

            writeToFile(cipher.encrypt(passwordToAdd.toString()), true);

        }

    }
    @FXML
    void removeButtonAction()
    {
        ObservableList<Password> allProduct, singleProduct;
        allProduct = dataTable.getItems();
        singleProduct = dataTable.getSelectionModel().getSelectedItems();
        singleProduct.forEach(allProduct::remove);

        writeToFile("", false);
        for(Password p : dataTable.getItems())
            writeToFile(cipher.encrypt(p.toString()), true);
    }
    @FXML
    void removeCategoryButtonAction()
    {

        if(!newCategoryTextField.getText().equals(""))
        {

            String categoryToRemove = newCategoryTextField.getText();
            newCategoryTextField.setText("");

            writeToFile("", false);

            for (Password p : dataTable.getItems())
            {

                if (!p.getCategory().equals(categoryToRemove))
                    writeToFile(cipher.encrypt(p.toString()), true);

            }

            BufferedReader bufferedReader;
            try
            {

                String currentLine;
                bufferedReader = new BufferedReader(new FileReader("src\\" + fileName));

                ObservableList<Password> passwords = FXCollections.observableArrayList();

                while ((currentLine = bufferedReader.readLine()) != null)
                {

                    String[] currentLineStrings = currentLine.split(" ");
                    Password p = new Password(cipher.decrypt(currentLineStrings[0]), cipher.decrypt(currentLineStrings[1]),
                            cipher.decrypt(currentLineStrings[2]), cipher.decrypt(currentLineStrings[3]), cipher.decrypt(currentLineStrings[4]));
                    passwords.add(p);

                }

                dataTable.setItems(passwords);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

    }

    @FXML
    void onNameEdit(TableColumn.CellEditEvent<Password, String> editEvent)
    {

        Password password = dataTable.getSelectionModel().getSelectedItem();
        password.setName(editEvent.getNewValue());

        writeToFile("", false);
        for(Password p : dataTable.getItems())
            writeToFile(cipher.encrypt(p.toString()), true);

    }
    @FXML
    void onPasswordEdit(TableColumn.CellEditEvent<Password, String> editEvent)
    {

        Password password = dataTable.getSelectionModel().getSelectedItem();
        password.setPassword(editEvent.getNewValue());

        writeToFile("", false);
        for(Password p : dataTable.getItems())
            writeToFile(cipher.encrypt(p.toString()), true);

    }
    @FXML
    void onLoginEdit(TableColumn.CellEditEvent<Password, String> editEvent)
    {

        Password password = dataTable.getSelectionModel().getSelectedItem();
        password.setLogin(editEvent.getNewValue());


        writeToFile("", false);
        for(Password p : dataTable.getItems())
            writeToFile(cipher.encrypt(p.toString()), true);

    }
    @FXML
    void onDomainEdit(TableColumn.CellEditEvent<Password, String> editEvent)
    {

        Password password = dataTable.getSelectionModel().getSelectedItem();
        password.setDomain(editEvent.getNewValue());

        writeToFile("", false);
        for(Password p : dataTable.getItems())
            writeToFile(cipher.encrypt(p.toString()), true);

    }
    @FXML
    void onCategoryEdit(TableColumn.CellEditEvent<Password, String> editEvent)
    {

        Password password = dataTable.getSelectionModel().getSelectedItem();
        password.setCategory(editEvent.getNewValue());

        writeToFile("", false);
        for(Password p : dataTable.getItems())
            writeToFile(cipher.encrypt(p.toString()), true);

    }

    @FXML
    public void initialize()
    {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Password, String>("Name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Password, String>("Password"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<Password, String>("Login"));
        domainColumn.setCellValueFactory(new PropertyValueFactory<Password, String>("Domain"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Password, String>("Category"));

        dataTable.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loginColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        domainColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    public void writeToFile(String str, boolean append)
    {

        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\"+fileName, append));
            if(append)
                bufferedWriter.append(str + "\n");
            else
                bufferedWriter.append(str);
            bufferedWriter.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


}
