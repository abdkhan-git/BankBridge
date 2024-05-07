package org.example.BankBridge.Controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.BankBridge.App;
import org.example.BankBridge.Database.model.User;
import org.example.BankBridge.Models.Client;
import org.example.BankBridge.Models.Model;
import org.example.BankBridge.Views.AccountType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignUpControllerTest {
    @Mock
    private TextField tfEmailAddress;
    @Mock
    private PasswordField tfPassword;
    @Mock
    private Button btnRegister;
    @Mock
    private Button btnSkipToLogin;
    @Mock
    private Scene scene;
    @Mock
    private Stage stage;
    @Mock
    private Model model;
    private App app;

    private SignUpController signUpController;

    @Before
    public void setUp() {
        signUpController = new SignUpController();
        signUpController.tfEmailAddress = tfEmailAddress;
        signUpController.tfPassword = tfPassword;
        signUpController.btnRegister = btnRegister;
        signUpController.btnSkipToLogin = btnSkipToLogin;
        model = mock(Model.class);
        when(Model.getInstance()).thenReturn(model);
    }

    @Test
    public void testRegisterBtnOnClick() throws FirebaseAuthException {
        try (MockedStatic<App> appMock = Mockito.mockStatic(App.class)) {
            appMock.when(() -> App.fauth.createUser(any(UserRecord.CreateRequest.class))).thenReturn(mock(UserRecord.class));

            when(tfEmailAddress.getText()).thenReturn("test@example.com");
            when(tfPassword.getText()).thenReturn("password");

            signUpController.registerBtnOnClick(null);

            verify(app.firebaseService, times(1)).addNewUserToDb(any(User.class), anyString());
            verify(model.getViewFactory(), times(1)).closeStage(any(Stage.class));
            verify(model.getViewFactory(), times(1)).showLoginWindow();
        }
    }

    @Test
    public void testSkipToLoginBtnOnClick() {
        when(tfEmailAddress.getScene()).thenReturn(scene);
        when(scene.getWindow()).thenReturn(stage);

        signUpController.skipToLoginBtnOnClick(null);

        verify(model.getViewFactory(), times(1)).closeStage(stage);
        verify(model.getViewFactory(), times(1)).showLoginWindow();
    }
}