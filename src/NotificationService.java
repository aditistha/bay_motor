class NotificationService {
    private static NotificationService instance;

    private NotificationService() {}

    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void sendNotification(Customer customer, String message) {
        System.out.println("Notification to " + customer.name + ": " + message);
    }

    public void notifyCustomer(Customer customer) {
        sendNotification(customer, "Your vehicle service is complete!");
    }
}
