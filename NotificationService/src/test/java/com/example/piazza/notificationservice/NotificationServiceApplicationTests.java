//package com.example.piazza.notificationservice;
//
//import com.example.piazza.notificationservice.model.Notification;
//import com.example.piazza.notificationservice.repository.NotificationRepository;
//import org.junit.jupiter.api.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.*;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        properties = {
//                // Use H2 in-memory for JPA
//                "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
//                "spring.datasource.driverClassName=org.h2.Driver",
//                "spring.jpa.hibernate.ddl-auto=create-drop"
//        }
//)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class NotificationServiceApplicationTests {
//
//    @LocalServerPort
//    int port;
//
//    @Autowired
//    TestRestTemplate restTemplate;
//
//    @Autowired
//    NotificationRepository repository;
//
//    // Prevent Rabbit auto-config from failing by mocking the core beans
//    @MockBean
//    ConnectionFactory rabbitConnectionFactory;
//    @MockBean
//    RabbitTemplate rabbitTemplate;
//
//    private String baseUrl() {
//        return "http://localhost:" + port + "/notifications";
//    }
//
//    @Test @Order(1)
//    void createNotification_positive() {
//        Notification notif = new Notification();
//        notif.setUserId("user123");
//        notif.setType("info");
//        notif.setMessage("Welcome!");
//
//        ResponseEntity<Notification> resp =
//                restTemplate.postForEntity(baseUrl(), notif, Notification.class);
//
//        assertEquals(HttpStatus.OK, resp.getStatusCode());
//        assertNotNull(resp.getBody());
//        assertTrue(resp.getBody().getId() > 0);
//    }
//
//    @Test @Order(2)
//    void getNotificationById_positive() {
//        Notification saved = repository.save(
//                new Notification("Test message", null, "user123", "info", false, LocalDateTime.now())
//        );
//
//        ResponseEntity<Notification> resp =
//                restTemplate.getForEntity(baseUrl() + "/" + saved.getId(), Notification.class);
//
//        assertEquals(HttpStatus.OK, resp.getStatusCode());
//        assertNotNull(resp.getBody());
//        assertEquals("user123", resp.getBody().getUserId());
//    }
//
//    @Test @Order(3)
//    void getNotificationById_negative_notFound() {
//        ResponseEntity<Notification> resp =
//                restTemplate.getForEntity(baseUrl() + "/99999", Notification.class);
//
//        assertEquals(HttpStatus.OK, resp.getStatusCode());
//        assertNull(resp.getBody());
//    }
//
//    @Test @Order(4)
//    void markAsRead_edge() {
//        Notification saved = repository.save(
//                new Notification("Edge case", null, "user321", "update", false, LocalDateTime.now())
//        );
//
//        ResponseEntity<Notification> resp = restTemplate.exchange(
//                baseUrl() + "/" + saved.getId() + "/read",
//                HttpMethod.PUT,
//                HttpEntity.EMPTY,
//                Notification.class
//        );
//
//        assertEquals(HttpStatus.OK, resp.getStatusCode());
//        assertTrue(resp.getBody().isRead());
//    }
//
//    @Test @Order(5)
//    void archiveNotification_positive() {
//        Notification saved = repository.save(
//                new Notification("Archiving test", null, "user321", "warning", false, LocalDateTime.now())
//        );
//
//        ResponseEntity<Notification> resp = restTemplate.exchange(
//                baseUrl() + "/" + saved.getId() + "/archive",
//                HttpMethod.PUT,
//                HttpEntity.EMPTY,
//                Notification.class
//        );
//
//        assertEquals(HttpStatus.OK, resp.getStatusCode());
//        assertTrue(resp.getBody().isArchived());
//    }
//
//    @Test @Order(6)
//    void filterNotifications_positive() {
//        Notification n1 = new Notification("Msg A", null, "filterUser", "alert", false, LocalDateTime.now());
//        Notification n2 = new Notification("Msg B", null, "filterUser", "alert", true,  LocalDateTime.now());
//        repository.saveAll(Arrays.asList(n1, n2));
//
//        String url = baseUrl() + "/filter?userId=filterUser&type=alert&read=false";
//        ResponseEntity<Notification[]> resp =
//                restTemplate.getForEntity(url, Notification[].class);
//
//        assertEquals(HttpStatus.OK, resp.getStatusCode());
//        assertEquals(1, resp.getBody().length);
//    }
//
//    @Test @Order(7)
//    void deleteNotification_positive() {
//        Notification saved = repository.save(
//                new Notification("To delete", null, "userDel", "delete", false, LocalDateTime.now())
//        );
//        restTemplate.delete(baseUrl() + "/" + saved.getId());
//
//        assertFalse(repository.findById(saved.getId()).isPresent());
//    }
//
//    @Test @Order(8)
//    void getAllNotifications_edge() {
//        ResponseEntity<Notification[]> resp =
//                restTemplate.getForEntity(baseUrl(), Notification[].class);
//
//        assertEquals(HttpStatus.OK, resp.getStatusCode());
//        assertNotNull(resp.getBody());
//    }
//}
