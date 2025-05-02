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

RabbitMQ — açıq mənbə (open-source) kodlu, mesaj broker proqram təminatıdır. Yəni, proqramlar və xidmətlər arasında məlumat ötürmək üçün arada vasitəçi rolunu oynayan sistemdir.
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

## 📨 Mesajlaşma Sistemi nədir? (What is a Messaging System?)

Mesajlaşma Sistemi — fərqli tətbiqlər və ya servislər arasında məlumat ötürmək üçün istifadə olunan proqram təminatı arxitekturasıdır. Bu sistemlər tətbiqlər arasında mesaj (məlumat vahidi) göndərib almağa imkan yaradır. Məqsəd — fərqli sistemlər və komponentlər arasında etibarlı, asinxron, asılı olmayan və çevik məlumat mübadiləsi təmin etməkdir.

### 📌 Mesajlaşma Sisteminin Əsas Məqsədi

- Tətbiqlər arasındakı əlaqəni zəiflətmək (loose coupling)
- Asinxron məlumat ötürmək
- Etibarlı və ardıcıl məlumat çatdırmaq
- Yük balanslaşdırmaq və sistem performansını artırmaq
- Retry və error handling təmin etmək

### 📌 Mesajlaşma Sistemi necə işləyir?

- Mesajlaşma sistemi aşağıdakı komponentlərdən ibarətdir:
    - Producer (İstehsalçı) - Mesajı yaradan və göndərən tətbiq.
    - Message Broker / Messaging Middleware - Mesajları qəbul edən və müvafiq olaraq növbəyə və ya ünvanına çatdıran vasitəçi proqram. (RabbitMQ, Kafka, ActiveMQ kimi)
    - Queue və ya Topic - Mesajların saxlanıldığı müvəqqəti yaddaş sahəsi.
    - Consumer (İstehlakçı) - Mesajları qəbul edən və onları işləyən tətbiq və ya xidmət.
 
### 📌 Mesajlaşma Sistemi Növləri

1. Point-to-Point (Queue-based)
→ Mesaj bir Producer tərəfindən göndərilir və bir Consumer tərəfindən qəbul olunur. Mesaj qəbul olunduqdan sonra növbədən silinir.
2. Publish/Subscribe (Topic-based)
→ Producer mesajı bir Topic-ə göndərir və həmin Topic-ə abunə olan bütün Consumer-lar mesajı qəbul edir.

### 📌 Mesajlaşma Sisteminin Üstünlükləri

1. ✅ Asinxron İcra - Tətbiqlər eyni anda işləməyə ehtiyac duymur, mesajlar növbəyə düşür.
2. ✅ Scalability - Tətbiqlər və mesajlar çoxaldıqca sistem rahat genişlənə bilir.
3. ✅ Loose Coupling - Tətbiqlər bir-birindən asılı olmur, müstəqil işləyir.
4. ✅ Etibarlılıq - Mesajlar saxlanılır və sistem çökdükdə belə bərpa oluna bilir.
5. ✅ Load Balancing və Retry mexanizmi - Consumer-lar arasında yük bölünür və uğursuz mesajlar yenidən cəhd edilə bilir.

### 📌 Mesajlaşma Sistemləri hansı hallarda istifadə olunur?

- Mikroservis arxitekturalarında
- E-commerce sifariş sistemlərində
- Bildiriş və mail göndərmə sistemlərində
- Log və event toplama platformalarında
- Real-time chat və oyun sistemlərində
- Task queue və background job icrasında

---

## 🥸 AMQP nədir? (What is AMQP?)

AMQP (Advanced Message Queuing Protocol) — mesajlaşma sistemləri üçün hazırlanmış açıq standart protokoldur. Məqsədi, fərqli proqramlaşdırma dilləri və platformalar üzərində qurulmuş tətbiqlər arasında etibarlı, təhlükəsiz və ardıcıl mesajlaşma təmin etməkdir.
AMQP ilə müxtəlif sistemlər və tətbiqlər bir-biri ilə standart şəkildə mesaj göndərib ala bilir.
RabbitMQ da məhz bu protokolu əsas götürərək işləyir.

### 📌 AMQP-nin Məqsədi

- Platforma və dil müstəqilliyi → Fərqli texnologiyalar asanlıqla mesajlaşa bilir.
- Etibarlı və ardıcıl mesaj ötürülməsi → Mesajların çatdırılması və təhlükəsizliyi təmin olunur.
- Flexible routing və queue-lar üzərində idarəetmə
- Transaction və acknowledgment mexanizmləri

### 📌 AMQP necə işləyir?

AMQP protokolunda əsas anlayışlar:
    - Producer -> Mesaj göndərən tərəf
    - Exchange -> Mesajları qəbul edən və onların hansı queue-ya yönləndiriləcəyini müəyyən edən komponent.
    - Queue -> Mesajların saxlanıldığı yer. Consumer-lar burdan oxuyur.
    - Binding -> Exchange ilə Queue arasında olan əlaqə.
    - Consumer -> Mesajları qəbul edib işləyən tərəf.

### 📌 AMQP-nin Əsas Xüsusiyyətləri

- ✅ Message orientation -> Hər şey mesajlar üzərindən gedir.
- ✅ Reliable delivery -> Message acknowledgement, transaction və persistens dəstəyi var.
- ✅ Flexible routing -> Mesajlar müxtəlif qaydalara əsasən Exchange-lər vasitəsilə yönləndirilə bilir.
- ✅ Security -> Sertifikat və şifrələmə dəstəyi var.
- ✅ Interoperability -> Fərqli sistemlər və dillər bir-biri ilə problemsiz işləyə bilir.

### 📌 AMQP-nin Exchange Tipləri

1. Direct Exchange -> Mesaj routing key-ə görə birbaşa uyğun queue-ya gedir.
2. Fanout Exchange -> Mesaj gələn kimi bütün bağlı queue-lara göndərilir.
3. Topic Exchange -> Routing key pattern-lərinə görə queue-lara yönləndirilir.
4. Headers Exchange -> Mesaj header-lərinə əsasən yönləndirilir.

### 📌 AMQP nə üçün vacibdir?

- Mikroservis arxitekturasında tətbiqlər arasında əlaqə yaratmaq
- Enterprise səviyyəli mesajlaşma sistemləri
- Etibarlı və transaction əsaslı məlumat ötürülməsi
- Retry və error handling imkanları

---
