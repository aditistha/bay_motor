package com.baymoters.users;

import com.baymoters.customer.Customer;
import com.baymoters.services.NotificationService;
import com.baymoters.task.Task;
import com.baymoters.vehicles.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerTest {

    private Manager manager;
    private Mechanic mechanicMock;
    private Vehicle vehicleMock;
    private Customer customerMock;

    @BeforeEach
    void setUp() {
        manager = new Manager("1", "John Doe", "john.doe@example.com", "password123");
        mechanicMock = mock(Mechanic.class); // Mocking Mechanic
        vehicleMock = mock(Vehicle.class);  // Mocking Vehicle
        customerMock = mock(Customer.class); // Mocking Customer
    }

    @Test
    void testGetUserType() {
        assertEquals("MANAGER", manager.getUserType(), "User type should be 'MANAGER'");
    }

    @Test
    void testAllocateTask() {
        String taskDescription = "Fix engine issue";
        manager.allocateTask(mechanicMock, vehicleMock, taskDescription);

        // Verify that the `addTask` method was called on the Mechanic mock
        verify(mechanicMock).addTask(any(Task.class));
    }

    @Test
    void testSendNotification() {
        String message = "Your car is ready for pickup";
        NotificationService notificationServiceMock = mock(NotificationService.class);
        
        // Stubbing the NotificationService singleton
        when(NotificationService.getInstance()).thenReturn(notificationServiceMock);
        
        manager.sendNotification(customerMock, message);

        // Verify that the sendNotification method was called
        verify(notificationServiceMock).sendNotification(customerMock, message);
    }
}
