package org.example.BankBridge.Controllers.Client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.BankBridge.Models.Model;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientMenuControllerTest {
    private ClientMenuController controller;

    @Mock
    private Button dashboardBtn;

    @Mock
    private Button transactionBtn;

    @Mock
    private Button accountsBtn;

    @Mock
    private Button logoutBtn;

    @Mock
    private Model model;

    @Mock
    private Scene scene;

    @Mock
    private Stage stage;

    @Before
    public void setUp() {
        controller = new ClientMenuController();
        controller.dashboardBtn = dashboardBtn;
        controller.transactionBtn = transactionBtn;
        controller.accountsBtn = accountsBtn;
        controller.logoutBtn = logoutBtn;
        model = mock(Model.class);
        when(Model.getInstance()).thenReturn(model);
    }

    @Test
    public void testInitialize() {
        controller.initialize(null, null);

        verify(dashboardBtn, times(1)).setOnAction(any());
        verify(transactionBtn, times(1)).setOnAction(any());
        verify(accountsBtn, times(1)).setOnAction(any());
        verify(logoutBtn, times(1)).setOnAction(any());
    }

    @Test
    public void testLogoutButtonClicked() {
        when(dashboardBtn.getScene()).thenReturn(scene);
        when(scene.getWindow()).thenReturn(stage);

        controller.initialize(null, null);
        controller.logoutBtn.fire();

        verify(model.getViewFactory(), times(1)).closeStage(stage);
        verify(model.getViewFactory(), times(1)).showLoginWindow();
    }
}