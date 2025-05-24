package client;

public class InstructorDto {
    private Long id;
    private String name;
    private String email;
    private String password;  

    public InstructorDto() {}

    public InstructorDto(Long id, String name) {
        this.id = id;
        this.name = name;
        
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
