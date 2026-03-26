package fr.dimitar.web.contact.forms;

public class ContactForm {

    private String message;
    private String name;
    private String email;

    public ContactForm() {
        this.message = null;
        this.name = null;
        this.email = null;
    }

    public String getMessage() {
        if (message == null || message.isBlank()) {
            return null;
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        if(name == null || name.isBlank()) {
            return null;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        if (email == null || email.isBlank()){
            return null;
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
