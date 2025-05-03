# <img src="https://miro.medium.com/v2/resize:fit:1000/1*Vti9b2wt2v5lo1yLp7iVSw.png" width="150px"> RabbitMQ-Deep-Dive
All about RabbitMQ

## Mündəricat (Table of Contents)
1. [RabbitMQ nədir? (What is RabbitMQ?)](#-rabbitmq-nədir-what-is-rabbitmq)
2. [Mesajlaşma Sistemi nədir? (What is a Messaging System?)](#-mesajlaşma-sistemi-nədir-what-is-a-messaging-system)
3. [AMQP nədir? (What is AMQP?)](#-amqp-nədir-what-is-amqp)
4. [RabbitMQ-nun Arxitekturası (RabbitMQ Architecture)](#-rabbitmq-nun-arxitekturası-rabbitmq-architecture)
5. [Exchange növləri (Types of Exchanges)](#-exchange-növləri-types-of-exchanges)
6. [Queue və Binding-lər (Queues and Bindings)](#-queue-və-binding-lər-queues-and-bindings)
7. [Producer və Consumer (Producer and Consumer)](#-producer-və-consumer-producer-and-consumer)
8. [Routing Key və Pattern Matching](#-routing-key-və-pattern-matching)
9. [Dead Letter Queue (DLQ) nədir və nə üçün istifadə olunur?](#-dead-letter-queue-dlq-nədir-və-nə-üçün-istifadə-olunur)
10. [Message Acknowledgment (Əlavə təsdiqləmə mexanizmi)](#-message-acknowledgment-əlavə-təsdiqləmə-mexanizmi)
11. [Durability və Persistence anlayışları](#-durability-və-persistence-anlayışları)
12. [Spring Boot ilə RabbitMQ konfiqurasiyası](#-spring-boot-ilə-rabbitmq-konfiqurasiyası)
13. [Basic Producer və Consumer tətbiqləri](#-basic-producer-və-consumer-tətbiqləri)
14. [Retry mexanizmi və Error Handling](#-retry-mexanizmi-və-error-handling)
15. [DLQ ilə işləmək (DLQ Handling)](#-dlq-ilə-işləmək-dlq-handling)
16. [Fanout, Direct, Topic və Headers exchange misalları](#-fanout-direct-topic-və-headers-exchange-misalları)
17. [Message Converter və Serialization](#-message-converter-və-serialization)
18. [RabbitMQ Management Plugin və UI istifadə qaydası](#-rabbitmq-management-plugin-və-ui-istifadə-qaydası)
19. [Security: User, Permission və TLS](#-security-user-permission-və-tls)
20. [Monitoring və Metrics (Prometheus, Grafana inteqrasiyası)](#-monitoring-və-metrics-prometheus-grafana-inteqrasiyası)
21. [Cluster və High Availability (HA) Konfiqurasiyası](#-cluster-və-high-availability-ha-konfiqurasiyası)
22. [RabbitMQ Performans Tuning və Best Practices](#-rabbitmq-performans-tuning-və-best-practices)

---
    
## <img src="https://github.com/user-attachments/assets/9fea07e3-295c-4b10-8c10-99a087c3c14e" width="50px">  RabbitMQ nədir? (What is RabbitMQ?)

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

## <img src="https://github.com/user-attachments/assets/65a50f5a-af6d-475d-a556-352b11d90210" width="50px">  Mesajlaşma Sistemi nədir? (What is a Messaging System?)

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

## <img src="https://github.com/user-attachments/assets/d11eab4b-6c3e-4338-b717-91cbe7dcd26d" width="50px">  AMQP nədir? (What is AMQP?)

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

## <img src="https://github.com/user-attachments/assets/895ec9d6-54d3-4a0f-99b8-d6383b741514" width="50px"> RabbitMQ-nun Arxitekturası (RabbitMQ Architecture)

RabbitMQ — AMQP protokolu əsasında çalışan message broker-dir və özünün spesifik arxitekturası var. Bu arxitektura fərqli sistem və tətbiqlər arasında mesaj ötürülməsini etibarlı, çevik və asinxron şəkildə təşkil edir.

### 📌 RabbitMQ Arxitekturasının Əsas Komponentlər
- RabbitMQ arxitekturası aşağıdakı əsas komponentlər üzərində qurulub:
    - 📌 1️⃣ Producer (Mesaj Göndərən Tərəf):
        -  Producer — RabbitMQ-ya mesaj göndərən tətbiqdir. Mesajlar birbaşa Exchange-ə göndərilir, yoxsa queue-ya deyil!
        -  Qaydası: Producer → Exchange
    - 📌 2️⃣ Exchange (Mesaj Paylayıcısı)
        - Exchange — gələn mesajları qəbul edib, hansı queue-ya göndəriləcəyinə qərar verən komponentdir.
          Mesajın routing key və binding rules əsasında queue-lara yönləndirilməsi burada baş tutur.
        - Exchange Tipləri:
            - Direct Exchange
            - Fanout Exchange
            - Topic Exchange
            - Headers Exchange
    - 📌 3️⃣ Binding (Bağlantı Qaydası)
        - Exchange ilə Queue arasında olan bağlantıdır. Hər binding bir routing key və ya pattern əsasında qurulur.
          Yəni Exchange qərar verir ki, hansı mesaj hansı queue-ya getsin.
    - 📌 4️⃣ Queue (Mesaj Növbəsi)
        - Queue — mesajların müvəqqəti saxlanıldığı yerdir.
        - Consumer-lar gəlir və burdakı mesajları ardıcıllıqla oxuyur.
        - Qaydası: Mesaj növbəyə düşəndən sonra orda saxlanılır və consumer onu götürüb işləyənə qədər orda qalır.
    - 📌 5️⃣ Consumer (Mesaj Alan Tərəf)
        - Consumer — queue-dan mesaj götürən və işləyən tətbiq və ya prosesdir. Bir neçə consumer eyni queue-ya qoşula və mesajları paylaşa bilər.
    - 📌 6️⃣ Virtual Host (vHost)
        - RabbitMQ serverində təhlükəsizlik və izolyasiya təmin etmək üçün istifadə olunur.
          Bir server içində fərqli virtual host-lar yaradıb, istifadəçi və queue-ları ayıra bilərsən.
    - 📌 7️⃣ Connection & Channel
        - Connection — Producer və Consumer ilə RabbitMQ arasında qurulan TCP bağlantısıdır.
        - Channel — Connection üzərindən bir və ya bir neçə mesajlaşma əməliyyatını icra edən virtual bağlantıdır.
        - Nəticədə, bir Connection üzərindən çoxlu Channel-lar aça bilərsən.
    - 📌 RabbitMQ Arxitekturası — Diagram (Sözlə Təsviri):
```css
[Producer] 
     │  
     ▼  
 [Exchange]  
     │  
 ┌───┼──────────────┐
 │   │              │
 ▼   ▼              ▼
[Queue1]        [Queue2]
 │   │              │
 ▼   ▼              ▼
[Consumer1]   [Consumer2]
```

- Açıqlama:
    - ✅ Loose Coupling — tətbiqlər bir-birindən asılı olmur
    - ✅ Asinxron və ardıcıl mesaj ötürülməsi
    - ✅ Retry, durability və acknowledgment dəstəyi
    - ✅ Load balancing — bir neçə consumer eyni queue-dan mesaj alıb işləyə bilir
    - ✅ Scalability — broker-lər və queue-lar genişləndirilə bilir
    - ✅ Cluster və HA (High Availability) imkanları

### 📌 Real Ssenari Misalı:

- OrderService sifariş verir → mesajı Direct Exchange-ə göndərir
- Exchange baxır routing key-ə → OrderQueue-ya ötürür
- InvoiceService və StockService consumer kimi OrderQueue-dan mesajı alıb işləyir.
      
---

## <img src="https://github.com/user-attachments/assets/e9aa8833-35d7-4c2b-9f3d-2c573ba569c2" width="50px">  Exchange növləri (Types of Exchanges)

### 📌 Exchange Nədir?

Exchange — RabbitMQ-da Producer-dan gələn mesajları qəbul edən və routing rules (yönləndirmə qaydaları) əsasında hansı queue-ya getməli olduğunu müəyyən edən komponentdir.
Yəni Producer mesajı Exchange-ə göndərir, Exchange isə routing key və binding qaydalarına əsasən mesajı uyğun queue-ya ötürür.

### 📌 RabbitMQ-da Exchange Növləri (Types of Exchanges)

- RabbitMQ-da 4 əsas exchange növü var:
    - 📌 1️⃣ Direct Exchange:
        - Xüsusiyyəti:
            - Mesaj routing key əsasında uyğun queue-ya yönləndirilir.
            - Yəni mesajın routing key-i ilə queue-nun binding key-i tam uyğun olmalıdır.
        - İstifadə ssenarisi:
            - Əgər fərqli tip mesajları fərqli queue-lara yönləndirmək istəyirsənsə.
            ```vbnet
            Producer → routing key: "info" → Direct Exchange
            Queue binding key: "info"
            Mesaj → Queue-ya yönəldilir.                  
            ```
            
    - 📌 2️⃣ Fanout Exchange
        - Xüsusiyyəti:
            - Routing key-ə baxmır!
            - Gələn bütün mesajları ona bağlı olan bütün queue-lara göndərir.
        - İstifadə ssenarisi:
            - Bir mesajı eyni anda bir neçə servisin alması lazım olduqda (broadcast sistemləri).
            ```ngnix
            Producer → Fanout Exchange
            Fanout Exchange → Queue1, Queue2, Queue3
            ```
        - Hər üç queue eyni mesajı alacaq.
        ```markdown
        Producer → Fanout Exchange → Queue1
                                → Queue2
                                → Queue3
        ```
        
    - 📌 3️⃣ Topic Exchange
        - Xüsusiyyəti:
            - Mesajın routing key-i ilə queue-ların binding key-ləri pattern əsaslı yoxlanılır.
            - Burda * və # wildcard-ları istifadə olunur.
                - * → bir sözü təmsil edir.
                - "#" → sıfır və ya daha çox sözü təmsil edir.
        - İstifadə ssenarisi:
            - Çox çevik və kompleks routing-lər üçün.
        - Məsələn:
            - Routing key: order.created
            - Binding key: order.* → bu queue bu mesajı qəbul edəcək.
        - Diagram:
            ```java
            Producer → Topic Exchange → Queue (binding key = "order.*")
            ```

    - 📌 4️⃣ Headers Exchange
        - Xüsusiyyəti:
            - Routing key istifadə etmir.
            - Əvəzində, mesajın header-larındakı key-value cütlərinə əsaslanaraq queue-ya yönləndirir.
        - İstifadə ssenarisi:
            - Əgər mesaj yönləndirməsini routing key yox, metadata əsasında idarə etmək istəsən.
        - Məsələn:
            - Mesajın header-larında:
            ```ini
            type=invoice
            format=pdf
            ```
        - Queue isə bu şərtlərə uyğun mesajları qəbul edir.
        - Diagram:
            ```java
            Producer → Headers Exchange → Queue (header: type=invoice, format=pdf)
            ```

### 📌 Nəticə

- RabbitMQ-da Exchange-lər:
    - Mesajların hansı queue-ya getməli olduğunu müəyyən edir.
    - 4 fərqli növ var: Direct, Fanout, Topic, Headers
    - Hər biri fərqli ssenarilər üçün istifadə olunur və sistemi daha çevik və güclü edir.

---

## <img src="https://github.com/user-attachments/assets/33634d28-6ca7-4717-a9b1-9b1b96ba11c4" width="50px">  Queue və Binding-lər (Queues and Bindings)

### 📌 Queue nədir?

- Queue — RabbitMQ-da mesajların müvəqqəti olaraq saxlanıldığı növbədir.
  Yəni Exchange tərəfindən yönləndirilən mesajlar Queue-ya düşür və buradan Consumer-lar tərəfindən oxunub işlənir.

  
### 📌 Queue-nin əsas funksiyası:

- Mesajları müvəqqəti saxlayır.
- Mesajlar FIFO (First In, First Out) prinsipi ilə işlənir.
- Consumer gələn kimi, mesaj Queue-dan çıxarılıb ona ötürülür.
- Əgər heç bir Consumer yoxdursa, mesaj Queue-da gözləyir.
- Əgər durable (sabit) təyin edilibsə, server restart olsa belə, məlumat itmir.

### 📌 Queue Xüsusiyyətləri:

- 1️⃣ Name — Hər queue-nun unikal adı olur.
- 2️⃣ Durable — Queue və mesajlar RabbitMQ restart olsa belə qorunub saxlanır.
- 3️⃣ Exclusive — Yalnız müəyyən bir Connection tərəfindən istifadə edilir və o Connection bağlananda silinir.
- 4️⃣ Auto-delete — Queue-nun heç bir consumer-ı qalmadıqda avtomatik silinir.

### 📌 Queue-nun İş Qaydası:

1. Producer → Mesajı Exchange-ə göndərir.
2. Exchange → Yönləndirmə qaydasına görə mesajı Queue-ya ötürür.
3. Queue → Mesajı saxlayır.
4. Consumer → Queue-dan mesajı oxuyur və işləyir.

## 📌 Binding nədir?

Binding — Exchange ilə Queue arasındakı əlaqədir.
Yəni Exchange-dən gələn mesaj hansı Queue-ya düşəcək, bu Binding qaydaları ilə təyin olunur.

### 📌 Binding-in əsas xüsusiyyətləri:

- Routing key və ya pattern əsasında qurulur.
- Exchange növünə görə routing key-lər fərqli işləyə bilər.
- Bir Exchange bir neçə Queue-ya bağlı ola bilər.
- Bir Queue bir neçə Exchange-ə bağlana bilər.

### 📌 Binding Necə İşləyir?
Məsələn:
    - Direct Exchange istifadə edirsənsə:
        - Queue-ya order.created binding key ilə bağlanırsan.
        - Exchange-ə routing key order.created ilə mesaj gələndə bu Queue-ya yönəlir.
    - Topic Exchange-də isə pattern-lər:
        - order.* → order.created, order.updated mesajlarını qəbul edər.
        - order.# → order ilə başlayan bütün routing key-ləri qəbul edər.

### 📌 Queue və Binding — Diagramla
```markdown
Producer
    │
    ▼
 Exchange
    │
 ┌──┴────┐
 │Binding│
 └──┬────┘
    ▼
  Queue
    │
    ▼
 Consumer
```

### 📌 Queue və Binding Real Misal:
Scenario: OrderService sifariş yaradır və bu sifarişin StockService və InvoiceService-ə getməsi lazımdır.
    - order.created routing key ilə mesaj Exchange-ə göndərilir.
    - StockQueue və InvoiceQueue binding key order.created ilə Exchange-ə bağlıdır.
    - Exchange mesajı hər iki Queue-ya yönəldir.
    - Hər iki Consumer bu Queue-lardan mesajı götürüb işləyir.

### 📌 Nəticə

```java
// Komponent        	Vəzifəsi
//-----------------------------------------------------------------------------------------------
// Queue	            Mesajları müvəqqəti saxlayır və FIFO prinsipi ilə Consumer-lara ötürür.
// Binding	            Exchange ilə Queue arasındakı routing qaydasını təyin edir.
```

---

## <img src="https://github.com/user-attachments/assets/349bfbc7-6d0f-478a-884c-d8a2580ef541" width="50px">  Producer və Consumer (Producer and Consumer)

### 📌 Producer nədir?
Producer — RabbitMQ sistemində mesaj yaradan və bu mesajı Exchange-ə göndərən tətbiq və ya servisdir.
Yəni:
    - Producer, məsələn bir sifariş yarananda, onun məlumatlarını bir mesaj kimi formalaşdırır.
    - Bu mesajı RabbitMQ-ya (Exchange-ə) göndərir.
    - Producer mesajın hansı routing key-lə gedəcəyini də təyin edir.

### 📌 Producer Xüsusiyyətləri:

- İstənilən proqramlaşdırma dili və ya servis ola bilər.
- Mesajı yaratmaq, serialize etmək və Exchange-ə göndərmək məsuliyyətindədir.
- Mesajı hansı routing key ilə göndərəcəyini özü müəyyən edir.
- Mesajlar persistent (sabit) və ya transient (keçici) ola bilər.
        
### 📌 Producer-in İşi:

```java
Tətbiq → Mesajı yaradır → Exchange-ə göndərir
```

Misal:
    Sifariş yarandıqda:
    ```json
    {
      "orderId": 1234,
      "status": "created"
    }
    ```
   bu mesaj order.created routing key-lə OrderExchange-ə göndərilir.

### 📌 Consumer nədir?

Consumer — RabbitMQ sistemində Queue-dan mesajları oxuyub işləyən tətbiq və ya servisdir.
Yəni: 
    - RabbitMQ-da saxlanılan mesajları alır.
    - Bu mesajı parse və ya deserialize edir.
    - Lazım olan əməliyyatı həyata keçirir.

### 📌 Consumer Xüsusiyyətləri:

- İstənilən proqramlaşdırma dili və ya servis ola bilər.
- Queue-ya bağlanır və mesaj gəldikdə onu qəbul edir.
- Mesajı aldıqları zaman istəsələr acknowledge (təsdiq) göndərirlər ki, mesaj uğurla alındı.
- Auto Ack və ya Manual Ack variantları mövcuddur.

### 📌 Consumer-in İşi:

```css
Queue → Mesajı qəbul edir → İşləyir → Ack göndərir
```

Misal:
    - Stock Service Queue-dan order.created mesajını oxuyur və həmin məhsul üçün stok azaldır.

### 📌 Producer və Consumer Əlaqəsi — Ümumi Diagram:

```markdown
Producer
    │
    ▼
 Exchange
    │
  Binding
    │
    ▼
  Queue
    │
    ▼
 Consumer
```

### 📌 Real Misal Ssenari:

Scenario:
- Producer: OrderService → yeni sifariş yaradanda order.created mesajı göndərir.
- Queue: StockQueue və InvoiceQueue
- Consumer: StockService və InvoiceService bu Queue-lardan mesaj alır və öz işini görür.
    
İş axını:
- OrderService order.created mesajı yaradır və OrderExchange-ə göndərir.
- OrderExchange bu mesajı order.created routing key-lə StockQueue və InvoiceQueue-ya yönləndirir.
- StockService və InvoiceService bu Queue-lardan mesajı götürüb emal edir.
- Emal etdikdən sonra RabbitMQ-ya acknowledge göndərilir ki, mesaj uğurla işləndi.

### 📌 Nəticə
```java
Komponent	Vəzifəsi
Producer	Mesaj yaradır və Exchange-ə göndərir.
Consumer	Queue-dan mesajı alır və emal edir.
```

---

## <img src="https://github.com/user-attachments/assets/9bf57544-1b68-4b0e-a38f-f08231b2ba72" width="50px">  Routing Key və Pattern Matching

### 📌 Routing Key nədir?
- Routing Key — RabbitMQ-da Producer tərəfindən göndərilən mesajın hansı Queue-ya yönləndiriləcəyini Exchange-ə bildirmək üçün istifadə olunan açardır.
Yəni:
- Producer mesaj göndərərkən bir routing key təyin edir.
- Exchange isə bu routing key əsasında Binding qaydalarına baxaraq, mesajı uyğun Queue-ya ötürür.

### 📌 Routing key — string (mətn) şəklində olur və . (nöqtə) ilə bölünən sözlərdən ibarət ola bilər.
Məsələn: 
- order.created
- order.updated
- stock.reserved

### 📌 Pattern Matching nədir?
Pattern Matching — RabbitMQ-da Exchange növünə görə routing key-lərin pattern-lərlə uyğunlaşdırılmasıdır.
Bu, xüsusilə Topic Exchange istifadə edilərkən aktiv olur.

### 📌 İki əsas Pattern:
- `*` (asterisk) — yalnız bir sözün yerinə keçir.
- `#` (hash) — bir və ya daha çox sözün yerinə keçir.

### 📌 Routing Key və Pattern Matching — Exchange Növlərinə görə

#### 📌 1️⃣ Direct Exchange:

- Burada routing key tam uyğun gəlməlidir.
- Məsələn:
    - Routing key: order.created
    - Binding key: order.created → ✅ uyğun
    - Binding key: order.updated → ❌ uyğun deyil

 #### 📌 2️⃣ Topic Exchange:

 - Burada pattern matching istifadə olunur.
- `*` və `#` ilə daha dinamik routing mümkündür.

Misallar:
- Routing key: order.created
- Binding key: order.* → ✅ uyğun (order.created)
- Binding key: order.# → ✅ uyğun (order.created, order.updated, order.deleted)
- Binding key: *.created → ✅ uyğun (order.created, stock.created)
- Binding key: order.payment.* → routing key order.payment.done → ✅ uyğun

#### 📌 3️⃣ Fanout Exchange:
- Routing key əlaqəsizdir. Mesaj bütün bağlı Queue-lara göndərilir.

#### 📌 4️⃣ Headers Exchange:
- Routing key istifadə olunmur, əvəzində message header-lara baxılır.

### 📌 Routing Key və Pattern Matching Diagramı:

```markdown
Producer
   │
   ▼
 Exchange (Topic)
   │
   ├── Binding key: order.*
   │        │
   │        └── Queue: OrderQueue
   │
   ├── Binding key: stock.# 
   │        │
   │        └── Queue: StockQueue
   │
   ▼
Mesaj routing key: order.created → OrderQueue
Mesaj routing key: stock.updated.status → StockQueue
```

### 📌 Real Həyat Ssenarisi:

Scenario:
    - OrderService mesaj göndərir:
        - Routing key: order.created
        - StockService binding key: order.*
        - InvoiceService binding key: order.created
Neticə:
- order.created mesajı həm StockQueue, həm də InvoiceQueue-ya yönləndiriləcək.

### 📌 Nəticə
```java
Term	                 İzah
Routing Key	             Producer-in göndərdiyi mesaj üçün Exchange-ə hansı Queue-ya getməli olduğunu bildirən açar.
Pattern Matching	     Topic Exchange-də routing key-lərin * və # simvolları ilə pattern-lərə uyğunlaşdırılması.
```

---

## <img src="https://github.com/user-attachments/assets/1cf4879a-3043-45fa-a463-ddd7c2a058ac" width="50px">  Dead Letter Queue (DLQ) nədir və nə üçün istifadə olunur?

### 📌 Dead Letter Queue (DLQ) nədir?
- Dead Letter Queue (DLQ) — RabbitMQ-da və ümumiyyətlə mesajlaşma sistemlərində çatdırıla, işlənə və ya qəbul edilə bilməyən mesajların yönləndirildiyi xüsusi Queue-dur.

Yəni:
- Normal Queue-dan mesaj müəyyən səbəbdən işlənə bilmədikdə və ya rədd edildikdə, həmin mesaj DLQ-ya yönləndirilir.
- Bu Queue, problemləri təhlil etmək və lazım gələrsə, həmin mesajları təkrar işləmək üçün istifadə olunur.

### 📌 DLQ nə üçün istifadə olunur?
- 1️⃣ İşlənə Bilməyən Mesajların Saxlanması üçün
    - Mesaj Consumer tərəfindən qəbul edilmir və ya səhv baş verir.
- 2️⃣ Retry Mexanizmi Tətbiq Etmək üçün
    - DLQ-dakı mesajlar sonradan xüsusi bir servis vasitəsilə yenidən əsas Queue-a göndərilə bilər.
- 3️⃣ Sistem Təhlükəsizliyi və Stabil İdarəetmə üçün
    - Problemli mesajlar əsas Queue-dan çıxarılaraq DLQ-ya ötürülür, beləliklə sistem tıxanmaz.
- 4️⃣ Monitorinq və Debug məqsədilə
    - DLQ-da saxlanılan mesajlar problemi araşdırmaq və sistemdəki çatışmazlıqları tapmaq üçün istifadə olunur. 

### 📌 Mesajların DLQ-ya düşmə səbəbləri:
- 🔸 Message TTL (Time-To-Live) aşılması
    - Mesaj Queue-da müəyyən vaxt ərzində işlənməzsə və vaxtı bitərsə.
- 🔸 Queue dolarsa (max-length aşılırsa)
    - Queue dolu olduqda yeni mesajlar DLQ-ya yönləndirilə bilər.
- 🔸 Consumer mesajı rədd edərsə (Reject/Nack)
    - Mesaj işlənərkən Consumer basic.reject və ya basic.nack göndərib requeue=false etsə.
- 🔸 Routing qaydasına görə
    -  Mesaj Exchange-də heç bir Queue-ya uyğun gəlmədikdə və Alternate Exchange qurulmayıbsa.

### 📌 DLQ necə işləyir? (İş Axını)

- 1️⃣ Mesaj əsas Queue-ya gəlir.
- 2️⃣ Consumer mesajı qəbul edir.
- 3️⃣ Hər hansı səbəbdən:
    - Mesaj reject olunur.
    - TTL bitir.
    - Queue dolur.
- 4️⃣ Mesaj Dead Letter Exchange-ə (DLX) göndərilir.
- 5️⃣ DLX bu mesajı Dead Letter Queue-a yönləndirir.

### 📌 DLQ üçün Konfiqurasiya Nümunəsi:

Queue yaradanda dead-letter-exchange və dead-letter-routing-key parametrləri təyin olunur.
Misal (RabbitMQ Management UI və ya kodla):
    ```java
        Arguments:
        "x-dead-letter-exchange": "my-dlx"
        "x-dead-letter-routing-key": "dead.order"
    ```

### 📌 Real Həyat Ssenarisi:

- Scenario:
    - OrderService order.created mesajı göndərir.
    - StockService bu mesajı işləyir.
    - Əgər StockService uğursuzluqla nəticələnərsə və basic.reject edərsə, mesaj DLX-ə gedir.
    - DLX bu mesajı DeadOrderQueue-a yönləndirir.

### 📌 DLQ və DLX Əlaqəsi:

```java
Komponent	                   İzah
DLQ (Dead Letter Queue)	       Problemli mesajların toplandığı Queue.
DLX (Dead Letter Exchange)	   Problemli mesajları DLQ-ya yönləndirən Exchange.
```

### 📌 Nəticə

- DLQ mesajlaşma sistemlərinin etibarlı işləməsi və kritik səhvlərin idarə olunması üçün vacib mexanizmdir.
- Sən problemləri DLQ-da toplayıb analiz edə və lazım gəlsə retry mexanizmi yaza bilərsən.
- DLQ sistem performansını qoruyur, tıxacların və mesaj itkisi riskinin qarşısını alır.

--- 

## <img src="https://github.com/user-attachments/assets/ba9ca75d-182a-4d39-80f4-d4e298ba261b" width="50px">  Message Acknowledgment (Əlavə təsdiqləmə mexanizmi)

### 📌 Message Acknowledgment nədir?

- Message Acknowledgment (ACK) — Consumer tərəfindən mesajı uğurla aldığını və işlədiyini Broker-ə bildirmək üçün göndərilən təsdiq siqnalıdır.
- Bu mexanizm RabbitMQ-da mesajların itkisinin qarşısını almaq və etibarlı çatdırılmanı təmin etmək üçün istifadə olunur.

### 📌 Niyə lazımdır?

- Çünki:
    - Əgər Consumer mesajı alır, amma işləyərkən çökərsə və acknowledgment göndərməzsə, RabbitMQ həmin mesajı təkrar Queue-a qaytarır.
    - Beləliklə, mesaj ya başqa Consumer tərəfindən, ya da yenidən həmin Consumer ayağa qalxdıqda işlənir.
- Bu, at-least-once delivery (ən azı bir dəfə çatdırılma) zəmanəti verir.

### 📌 ACK Mexanizminin Növləri:

#### 📌 1️⃣ Automatic Acknowledgment

- Consumer mesajı aldıqdan dərhal acknowledgment göndərir.
- Əgər mesaj işlənərkən çökərsə, mesaj itə bilər.
- Tövsiyə edilmir — etibarlı sistemlər üçün risklidir.

```java
channel.basicConsume(queueName, true, consumer);
```
- Burada true → auto-ack

#### 📌 2️⃣ Manual Acknowledgment

- Consumer mesajı işlətdikdən sonra özü acknowledgment göndərir.
- Əgər işləmə zamanı problem olsa, acknowledgment göndərilmədiyi üçün mesaj Queue-da qalır və ya təkrar yönləndirilir.

```java
channel.basicConsume(queueName, false, consumer);
...
channel.basicAck(deliveryTag, false);
```

- Burada false → manual-ack

### 📌 NACK və Reject

- Əgər mesaj işlənə bilməzsə:

#### 📌 NACK (Negative Acknowledgment)

- Consumer mesajı işləyə bilmədiyini bildirir.
- İstəyinə görə:
    - `requeue = true` → Mesaj Queue-a geri qoyulur.
    - `requeue = false` → Mesaj DLQ-ya gedir (əgər DLX təyin olunubsa).

 ```java
channel.basicNack(deliveryTag, false, true);
```

### 📌 Reject

- Tək bir mesaj üçün NACK kimidir.
- Sadəcə həmin mesajı ya requeue, ya da DLQ-ya göndərir.

```java
channel.basicReject(deliveryTag, false);
```

### 📌 ACK İş Axını:

```css
Producer → Exchange → Queue → Consumer 
                      │
               (mesajı alır)
                      │
          İşləndimi? —►  Bəli → ACK → Mesaj silinir
                           Xeyr → NACK/Reject → DLQ və ya Queue-a qayıdış
```

### 📌 Real Həyat Ssenarisi:

- Scenario:
    - PaymentService mesaj alır.
    - Ödəniş işlənir.
    - Əgər uğurludursa:
        - `basicAck` göndərir.
    - Əgər bank sistemi bağlıdırsa:
        - `basicNack` ilə requeue edir.
    - Əgər istifadəçi kartı blokdursa:
        - `basicReject` ilə DLQ-ya göndərir.

### 📌 Message Acknowledgment Faydaları:

- ✅ Mesaj itkisinin qarşısını alır.
- ✅ Çökən consumer-lər zamanı mesajları qoruyur.
- ✅ Retry və DLQ mexanizmləri ilə inteqrasiya olunur.
- ✅ Etibarlı və sabit mesajlaşma infrastrukturu yaradır.

### 📌 Nəticə

```css
Növ	             İzah	                                 Risk
--------------------------------------------------------------------------------
Auto ACK	     Mesaj alınan kimi təsdiqlənir.	         İtki riski var.
Manual ACK	     İşlədikdən sonra təsdiqlənir.	         Təhlükəsizdir.
NACK	            İşləyə bilmədi, Queue-a və ya DLQ-ya.	 Təhlükəsizdir.
Reject	           Tək mesaj üçün rədd və ya requeue.	     Təhlükəsizdir.
```

---

## <img src="https://github.com/user-attachments/assets/e0d6a2c4-c525-4733-9277-5915d4869c71" width="50px">  Durability və Persistence anlayışları

### 📌 Durability və Persistence nədir?

- Bu iki termin hər ikisi mesajların və Queue-ların qalıcı olması ilə bağlı anlayışlardır, amma fərqli yerlərdə tətbiq olunur.

### 📌 1️⃣ Durability (Davamlılıq)

- Durability — RabbitMQ-da Queue-nun özünün qalıcı olması üçün istifadə olunan xüsusiyyətdir.
- Yəni:
    - Broker restart olanda durable olan Queue silinmir, sistem yenidən işə düşəndə də qalır.
    - Amma içindəki mesajlar yalnız persistent-dirsə saxlanır.

#### 📌 Queue yaradanda `durable` flag-i `true` qoymaq lazımdır:      

- Misal:
```java
channel.queueDeclare("my-queue", true, false, false, null);
```
- Burada:
    - `true` → bu Queue durable-dır.

##### 📌 Diqqət: Durability yalnız Queue-nun metadata-sını qoruyur. İçindəki mesajların qalması üçün Persistence də olmalıdır.

### 📌 2️⃣ Persistence (Daimi saxlanma)

- Persistence — mesajların diskinə yazılması deməkdir.
- Yəni:
    - Əgər mesaj persistent olaraq göndərilibsə və RabbitMQ çökərsə, sistem yenidən işə düşəndə həmin mesaj Queue-da qalır.
    - Əks halda mesaj RAM-da qalır və broker çökəndə itir.

#### 📌 Mesaj göndərərkən MessageProperties-də deliveryMode təyin olunur:

- 1 → non-persistent
- 2 → persistent

- Misal:
```java
AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
    .deliveryMode(2)
    .build();

channel.basicPublish("", "my-queue", props, "Hello".getBytes());
```

##### 📌 Diqqət: Persistent mesajların saxlanması üçün həm də Queue-nun durable olması lazımdır.

### 📌 Durability və Persistence İş Axını:
```css
Producer → Exchange → Durable Queue 
                    │
              Persistent Mesaj?
                   │
                 Bəli → Diskə yazılır
                  Xeyr → RAM-da qalır
```

### 📌 Fərq və Əlaqə:

```css
Anlayış	        Nəyə aiddir?	    Broker restart olarsa
-----------------------------------------------------------------
Durability	    Queue-nun özünə	    Queue saxlanır
Persistence	    Mesajlara	        Persistent mesajlar saxlanır
```

### 📌 Real Həyat Ssenarisi:

- Scenario:
    - Ödəniş əməliyyatı gedir.
    - Producer ödəniş mesajını `deliveryMode=2` ilə `durable` Queue-a göndərir.
    - RabbitMQ restart olsa belə:
        - Queue qalır.
        - Persistent mesajlar Queue-da qalır.
        - Sistem yenidən işə düşəndə ödəniş mesajı ordan götürülüb işlənə bilər.
     
### 📌 Nəticə

- ✅ Durability → Queue restartda saxlanır.
- ✅ Persistence → Mesaj restartda saxlanır.
- ✅ Hər ikisini birlikdə istifadə etmək etibarlı sistem dizaynı üçün mütləqdir.

--- 
