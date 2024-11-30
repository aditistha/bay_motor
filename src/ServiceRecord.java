import java.time.LocalDate;

class ServiceRecord {
    private LocalDate serviceDate;
    private String description;
    private Mechanic servicedBy;

    public ServiceRecord(LocalDate serviceDate, String description, Mechanic servicedBy) {
        this.serviceDate = serviceDate;
        this.description = description;
        this.servicedBy = servicedBy;
    }
}
