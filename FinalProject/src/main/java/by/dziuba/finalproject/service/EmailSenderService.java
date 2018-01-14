package by.dziuba.finalproject.service;

public interface EmailSenderService {
    boolean sendEmail(String message, String subject, String emailAddress);
}
