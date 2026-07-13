package com.dn5.week1.designpatterns.solid.dip;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationServiceTest {

    /** A fake MessageSender used only to capture what was sent, proving DIP works with any implementation. */
    private static class CapturingSender implements MessageSender {
        String lastTo;
        String lastMessage;

        @Override
        public void send(String to, String message) {
            this.lastTo = to;
            this.lastMessage = message;
        }
    }

    @Test
    void notificationServiceWorksWithEmailSender() {
        CapturingSender fake = new CapturingSender();
        NotificationService service = new NotificationService(fake);

        service.notify("alice@example.com", "hello");

        assertEquals("alice@example.com", fake.lastTo);
        assertEquals("hello", fake.lastMessage);
    }

    @Test
    void notificationServiceWorksWithAnyInjectedSender() {
        NotificationService emailService = new NotificationService(new EmailSender());
        NotificationService smsService = new NotificationService(new SmsSender());

        // no exception means both concrete senders satisfy the abstraction correctly
        emailService.notify("bob@example.com", "hi via email");
        smsService.notify("555-1234", "hi via sms");
    }
}
