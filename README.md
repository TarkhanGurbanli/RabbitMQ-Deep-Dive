# RabbitMQ-Deep-Dive
All about RabbitMQ


## Mündəricat (Table of Contents)
1. **RabbitMQ nədir? (What is RabbitMQ?)**
2. **Mesajlaşma Sistemi nədir? (What is a Messaging System?)**
3. **AMQP nədir? (What is AMQP?)**
4. **RabbitMQ-nun Arxitekturası (RabbitMQ Architecture)**
5. **Exchange növləri (Types of Exchanges)**
6. **Queue və Binding-lər (Queues and Bindings)**
7. **Producer və Consumer (Producer and Consumer)**
8. **Routing Key və Pattern Matching**
9. **Dead Letter Queue (DLQ) nədir və nə üçün istifadə olunur?**
10. **Message Acknowledgment (Əlavə təsdiqləmə mexanizmi)**
11. **Durability və Persistence anlayışları**
12. **Spring Boot ilə RabbitMQ konfiqurasiyası**
13. **Basic Producer və Consumer tətbiqləri**
14. **Retry mexanizmi və Error Handling**
15. **DLQ ilə işləmək (DLQ Handling)**
16. **Fanout, Direct, Topic və Headers exchange misalları**
17. **Message Converter və Serialization**
18. **RabbitMQ Management Plugin və UI istifadə qaydası**
19. **Security: User, Permission və TLS**
20. **Monitoring və Metrics (Prometheus, Grafana inteqrasiyası)**
21. **Cluster və High Availability (HA) Konfiqurasiyası**
22. **RabbitMQ Performans Tuning və Best Practices**

---
    
## 🐰 RabbitMQ nədir? (What is RabbitMQ?)

- RabbitMQ — açıq mənbə (open-source) kodlu, mesaj broker proqram təminatıdır. Yəni, proqramlar və xidmətlər arasında məlumat ötürmək üçün arada vasitəçi rolunu oynayan sistemdir.
RabbitMQ əsasən Advanced Message Queuing Protocol (AMQP) standartına əsaslanır və server-lə client-lər arasında mesajların etibarlı şəkildə ötürülməsini təmin edir.

### 📌 RabbitMQ nə işə yarayır?

- RabbitMQ tətbiqlər arasında məlumatları asinxron və etibarlı şəkildə ötürməyə kömək edir.
Məsələn:
    - Bir sistem sifarişi qəbul edib RabbitMQ-ya mesaj göndərir.
    - Başqa sistemlər isə bu mesajı oxuyub öz işlərini yerinə yetirirlər.
- Bu şəkildə:
    - Sistemlər bir-birindən asılı olmur
    - Mesajlar itmir
    - Yük balanslaşdırması və performans artır
    - Retry və error management rahat olur

---      
