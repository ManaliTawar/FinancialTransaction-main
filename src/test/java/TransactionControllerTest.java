import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.codescreen.controller.TransactionController;
import dev.codescreen.domain.AuthorizationRequest;
import dev.codescreen.domain.AuthorizationResponse;
import dev.codescreen.domain.LoadRequest;
import dev.codescreen.domain.LoadResponse;
import dev.codescreen.exception.TransactionError;
import dev.codescreen.exception.TransactionException;
import dev.codescreen.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void testAuthorizationTransaction_Success() {
        // Arrange
        String messageId = "123";
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        when(transactionService.authorizeTransaction(anyString(), any(AuthorizationRequest.class)))
                .thenReturn(authorizationResponse);

        // Act
        ResponseEntity<Object> responseEntity = transactionController.authorizationTransaction(messageId,
                authorizationRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(authorizationResponse, responseEntity.getBody());
    }

    @Test
    public void testAuthorizationTransaction_Exception() {
        // Arrange
        String messageId = "123";
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        String errorMessage = "Some error message";
        when(transactionService.authorizeTransaction(anyString(), any(AuthorizationRequest.class)))
                .thenThrow(new TransactionException(errorMessage));

        // Act
        ResponseEntity<Object> responseEntity = transactionController.authorizationTransaction(messageId,
                authorizationRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof TransactionError);
        TransactionError error = (TransactionError) responseEntity.getBody();
        assertEquals("Authorization failed", error.getMessage());
        assertEquals(errorMessage, error.getCode());

    }

    @Test
    public void testLoadFunds_Success() {
        // Arrange
        String messageId = "123";
        LoadRequest loadRequest = new LoadRequest();
        LoadResponse loadResponse = new LoadResponse();
        when(transactionService.loadFunds(anyString(), any(LoadRequest.class)))
                .thenReturn(loadResponse);

        // Act
        ResponseEntity<Object> responseEntity = transactionController.loadFunds(messageId, loadRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(loadResponse, responseEntity.getBody());
    }

    @Test
    public void testLoadFunds_Exception() {
        // Arrange
        String messageId = "123";
        LoadRequest loadRequest = new LoadRequest();
        String errorMessage = "Some error message";
        when(transactionService.loadFunds(anyString(), any(LoadRequest.class)))
                .thenThrow(new TransactionException(errorMessage));

        // Act
        ResponseEntity<Object> responseEntity = transactionController.loadFunds(messageId, loadRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof TransactionError);
        TransactionError error = (TransactionError) responseEntity.getBody();
        assertEquals("Load Funds failed", error.getMessage());
        assertEquals(errorMessage, error.getCode());
    }

}
