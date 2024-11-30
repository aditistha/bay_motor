class WalkInCustomer extends Customer {
    public WalkInCustomer(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }
    @Override
    public String getUserType() {
        return "WALK_IN_CUSTOMER";
    }
    public RegisteredCustomer upgrade() {
        return new RegisteredCustomer(this.id, this.name, this.password, this.email);
    }
}
