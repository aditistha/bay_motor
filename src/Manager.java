class Manager extends User {
    public Manager(String id, String name, String email, String password) {

        super(id, name, email, password);
    }
    @Override
    public String getUserType() {
        return "MANAGER";
    }
    public void allocateTask(Mechanic mechanic, Vehicle vehicle, String description) {
        Task task = new Task(vehicle, description);
        mechanic.addTask(task);
    }

    public void sendNotification(Customer customer, String message) {
        NotificationService.getInstance().sendNotification(customer, message);
    }
}

