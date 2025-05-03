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
12. [Retry mexanizmi və Error Handling](#-retry-mexanizmi-və-error-handling)
13. [DLQ ilə işləmək (DLQ Handling)](#-dlq-ilə-işləmək-dlq-handling)
14. [Fanout, Direct, Topic və Headers exchange misalları](#-fanout-direct-topic-və-headers-exchange-misalları)
15. [Message Converter və Serialization](#-message-converter-və-serialization)
16. [RabbitMQ Management Plugin və UI istifadə qaydası](#-rabbitmq-management-plugin-və-ui-istifadə-qaydası)
17. [Security: User, Permission və TLS](#-security-user-permission-və-tls)
18. [Monitoring və Metrics (Prometheus, Grafana inteqrasiyası)](#-monitoring-və-metrics-prometheus-grafana-inteqrasiyası)
19. [RabbitMQ Performans Tuning və Best Practices](#-rabbitmq-performans-tuning-və-best-practices)

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

| Ad           | Protokol   | Təyinat         |
| :----------- | :--------- | :-------------- |
| **RabbitMQ** | AMQP       | General-purpose |
| **ActiveMQ** | AMQP + JMS | Enterprise      |
| **Qpid**     | AMQP       | Apache Project  |


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
 
| Exchange Növü | Routing Key İstifadəsi | Yönləndirmə Qaydası                  | İstifadə Ssenarisi                  |
| :------------ | :--------------------- | :----------------------------------- | :---------------------------------- |
| **Direct**    | Var                    | Tam uyğun routing key                | Fərqli tip mesajları bölmək         |
| **Fanout**    | Yox                    | Bütün queue-lara göndərir            | Broadcast və event yayımı           |
| **Topic**     | Var                    | Pattern (wildcard `*`, `#`) əsasında | Çevik və pattern əsaslı yönləndirmə |
| **Headers**   | Yox                    | Mesaj header-larına əsasən           | Metadata əsaslı routing             |


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

| Komponent   | Vəzifəsi                                                                |
| :---------- | :---------------------------------------------------------------------- |
| **Queue**   | Mesajları müvəqqəti saxlayır və FIFO prinsipi ilə Consumer-lara ötürür. |
| **Binding** | Exchange ilə Queue arasındakı routing qaydasını təyin edir.             |


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

| Komponent    | Vəzifəsi                              |
| :----------- | :------------------------------------ |
| **Producer** | Mesaj yaradır və Exchange-ə göndərir. |
| **Consumer** | Queue-dan mesajı alır və emal edir.   |


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

| Term                 | İzah                                                                                          |
| :------------------- | :-------------------------------------------------------------------------------------------- |
| **Routing Key**      | Producer-in göndərdiyi mesaj üçün Exchange-ə hansı Queue-ya getməli olduğunu bildirən açar.   |
| **Pattern Matching** | Topic Exchange-də routing key-lərin `*` və `#` simvolları ilə pattern-lərə uyğunlaşdırılması. |


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

| Komponent                      | İzah                                             |
| :----------------------------- | :----------------------------------------------- |
| **DLQ (Dead Letter Queue)**    | Problemli mesajların toplandığı Queue.           |
| **DLX (Dead Letter Exchange)** | Problemli mesajları DLQ-ya yönləndirən Exchange. |


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

| Növ            | İzah                                  | Risk            |
| :------------- | :------------------------------------ | :-------------- |
| **Auto ACK**   | Mesaj alınan kimi təsdiqlənir.        | İtki riski var. |
| **Manual ACK** | İşlədikdən sonra təsdiqlənir.         | Təhlükəsizdir.  |
| **NACK**       | İşləyə bilmədi, Queue-a və ya DLQ-ya. | Təhlükəsizdir.  |
| **Reject**     | Tək mesaj üçün rədd və ya requeue.    | Təhlükəsizdir.  |


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

| Anlayış         | Nəyə aiddir?    | Broker restart olarsa        |
| :-------------- | :-------------- | :--------------------------- |
| **Durability**  | Queue-nun özünə | Queue saxlanır               |
| **Persistence** | Mesajlara       | Persistent mesajlar saxlanır |


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

## <img src="https://github.com/user-attachments/assets/d7e1ac77-d9a5-47d9-b050-73886f41d6f0" width="50px">  Retry mexanizmi və Error Handling

### 📌 Retry mexanizmi nədir?

- Retry — mesajın Consumer tərəfindən işlənməsində problem çıxanda, həmin mesajın müəyyən qədər yenidən cəhd edilməsi prosesidir.
- Məsələn:

- Consumer mesajı götürür.
- İşləyərkən exception çıxır.
- Retry policy varsa, həmin mesaj müəyyən say və interval ilə təkrar işlənir.

### 📌 Spring Boot-da Retry üçün 2 əsas üsul:

- 🔸 1️⃣ Listener səviyyəsində `@Retryable` ilə
- 🔸 2️⃣ `RetryTemplate` və ya `SimpleRabbitListenerContainerFactory` ilə global konfiqurasiya

### 📌 1️⃣ Listener səviyyəsində `@Retryable`

- Consumer Service:
```java
@Service
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000) // 2 saniyə aralıqla
    )
    public void receiveMessage(String message) {
        System.out.println("Mesaj gəldi: " + message);
        if (message.contains("error")) {
            throw new RuntimeException("Xəta baş verdi!");
        }
        System.out.println("Mesaj uğurla icra olundu.");
    }

    @Recover
    public void recover(Exception e, String message) {
        System.out.println("Mesaj Retry limitini keçdi. Recovery başladı: " + message);
    }
}
```

#### 📌 Burada:

- `maxAttempts` → maksimum retry sayı
- `@Backoff(delay = 2000)` → retry-lər arası gecikmə (ms)
- `@Recover` → Retry limitindən sonra işləyəcək metod

### 📌 2️⃣ Global Retry konfiqurasiyası (Container səviyyəsində)

- `RabbitConfig.java`:
```java
@Configuration
public class RabbitMQConfig {

    // diger bean-lar...

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        RetryInterceptorBuilder.StatelessRetryInterceptorBuilder retryInterceptor = RetryInterceptorBuilder.stateless()
            .maxAttempts(5)
            .backOffOptions(1000, 2.0, 10000); // delay, multiplier, max delay

        factory.setAdviceChain(retryInterceptor.build());
        return factory;
    }
}
```

#### 📌 Burada:

- `maxAttempts` → maksimum cəhd sayı
- `backOffOptions` →
    - `1000` → ilk gecikmə 1 saniyə
    - `2.0` → hər dəfə 2 qat artır
    - `10000` → maksimum 10 saniyə gecikmə
 
### 📌 Dead Letter Queue (DLQ) ilə Retry

- Retry limitindən sonra hələ uğursuzdursa → RabbitMQ həmin mesajı DLQ-a yönləndirə bilər.
- `Queue konfiqurasiyası`

```java
@Bean
public Queue mainQueue() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-dead-letter-exchange", "dlx-exchange");
    args.put("x-dead-letter-routing-key", "dlq-routingKey");
    return new Queue("main-queue", true, false, false, args);
}

@Bean
public Queue deadLetterQueue() {
    return new Queue("dlq-queue", true);
}
```

- `DLX` və `Binding`
```java
@Bean
public DirectExchange dlxExchange() {
    return new DirectExchange("dlx-exchange");
}

@Bean
public Binding dlqBinding() {
    return BindingBuilder.bind(deadLetterQueue())
        .to(dlxExchange())
        .with("dlq-routingKey");
}
```

- Beləliklə Retry limitindən sonra RabbitMQ mesajı DLQ-a göndərəcək → orda başqa Consumer işləyə bilər.

### 📌 Error Handling (Xəta idarəsi)

- Consumer içində istənilən error handling strategiyası tətbiq edə bilərsən:
    - try-catch ilə
    - `@Recover` metodu ilə
    - Retry mexanizmi ilə birlikdə DLQ-a göndərməklə

 ### 📌 Nəticə

- ✅ Retry mexanizmi → Consumer uğursuz olsa, mesajı təkrar işləməyə imkan verir
- ✅ @Retryable və RetryTemplate ilə qurulur
- ✅ DLQ ilə Retry limiti aşan mesajlar təhlükəsiz şəkildə yönləndirilir
- ✅ Error Handling → hər cəhd uğursuzluğunda loglama, bildiriş və ya DLQ opsiyası verir

---

## <img src="https://github.com/user-attachments/assets/8b554438-76d4-4306-a75b-73d090fc9426" width="50px">  DLQ ilə işləmək (DLQ Handling)

### 📌 Dead Letter Queue (DLQ) nədir?

- DLQ (Dead Letter Queue) — RabbitMQ-da başqa bir queue-dan rejected, expired və ya nack edilən (negative acknowledgment) mesajların yönləndirilə biləcəyi xüsusi queue-dur.
- Yəni:
    - Mesaj normal queue-da işlənə bilmir.
    - Retry limitini aşır və ya ack alınmır.
    - RabbitMQ o mesajı DLQ-a göndərir.
- Bu, problemli mesajların itirilməməsi və sonradan analiz/işlənməsi üçün əla bir mexanizmdir.

### 📌 DLQ nə üçün istifadə olunur?

- ✅ Retry limitindən sonra mesajları itirməmək üçün
- ✅ Problemli və ya zərərli mesajları ayırıb analiz etmək üçün
- ✅ Əlavə monitorinq və loglama üçün
- ✅ Manual şəkildə sonradan işləmək üçün

### 📌 DLQ Quruluşu və Mexanizmi

- Mesajın DLQ-a düşməsi üçün 3 əsas səbəb:
    1. Message rejected (ack alınmadı və requeue = false)
    2. Message TTL bitdi (Time To Live)
    3. Queue limit doldu və yeni mesaj gələndə köhnə mesajlar DLQ-a düşdü

### 📌 DLQ Konfiqurasiya Necə Olur?
#### 1️⃣ DLX (Dead Letter Exchange) yaradılır
#### 2️⃣ DLQ Queue yaradılır
#### 3️⃣ Normal Queue yaradılarkən `x-dead-letter-exchange` və `x-dead-letter-routing-key` parametr verilir

### 📌 Spring Boot və RabbitMQ ilə DLQ nümunəsi:

#### `RabbitConfig.java`

```java
@Configuration
public class RabbitConfig {

    public static final String MAIN_QUEUE = "main-queue";
    public static final String DLQ_QUEUE = "dlq-queue";
    public static final String DLX_EXCHANGE = "dlx-exchange";
    public static final String DLQ_ROUTING_KEY = "dlq-routingKey";

    // Main Queue
    @Bean
    public Queue mainQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", DLQ_ROUTING_KEY);
        return new Queue(MAIN_QUEUE, true, false, false, args);
    }

    // Dead Letter Queue
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ_QUEUE, true);
    }

    // DLX Exchange
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    // DLQ Binding
    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(dlxExchange())
                .with(DLQ_ROUTING_KEY);
    }
}
```

### 📌 Consumer Tərəfi

- Main Queue Consumer:
```java
@Service
public class MessageConsumer {

    @RabbitListener(queues = RabbitConfig.MAIN_QUEUE)
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("Gələn mesaj: " + message);
            if (message.contains("error")) {
                throw new RuntimeException("Problemli mesaj!");
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.out.println("Mesaj reject edildi və DLQ-a göndərildi: " + message);
            channel.basicReject(tag, false); // false → requeue etmir → DLQ-a gedir
        }
    }
}
```

### DLQ Consumer
```java
@Service
public class DLQConsumer {

    @RabbitListener(queues = RabbitConfig.DLQ_QUEUE)
    public void processDeadLetter(String message) {
        System.out.println("DLQ mesajı: " + message);
        // burda log, db insert və ya xüsusi işlər görə bilərsən
    }
}
```

### 📌 Mesaj TTL ilə DLQ

- Əgər mesajın müəyyən müddət işlənmədiyini istəmirsənsə:
- Main Queue-a TTL ver

```java
Map<String, Object> args = new HashMap<>();
args.put("x-dead-letter-exchange", DLX_EXCHANGE);
args.put("x-message-ttl", 5000); // 5 saniyə
```

- Mesaj 5 saniyə içində işlənməsə → avtomatik DLQ-a düşəcək.

### 📌 DLQ Handling Prosesi:

- ✅ Main Queue-da mesaj gələr
- ✅ Problem çıxsa:
    - → Retry varsa, Retry olur
    - → Retry yox və ya limiti aşdısa, basicReject / nack edilirsə → DLQ-a düşür
- ✅ DLQ-da ayrı Consumer bu mesajı oxuyur, log yazır, DB-yə saxlayır və ya bildiriş göndərir
- ✅ İstəyirsənsə, DLQ mesajlarını manual olaraq təkrar Main Queue-a da göndərə bilərsən

### 📌 Nəticə

- 🔸 DLQ problemli mesajların təhlükəsiz saxlanması və idarəsi üçün əla vasitədir
- 🔸 RabbitMQ ilə Spring Boot-da DLQ konfiqurasiyası çox sadə və çevikdir
- 🔸 Retry mexanizmi ilə birlikdə istifadə olunanda sistem çox dayanıqlı olur
- 🔸 DLQ-dan istənilən vaxt monitorinq və ya admin panel vasitəsilə mesajlar baxıla və idarə oluna bilər

---

## <img src="https://github.com/user-attachments/assets/8b554438-76d4-4306-a75b-73d090fc9426](https://github.com/user-attachments/assets/b3413a0d-187d-4271-8a46-843ae4296fff" width="50px">  Fanout, Direct, Topic və Headers exchange misalları

### 📌 RabbitMQ Exchange Nədir?

- Exchange — RabbitMQ-da mesajların hansı queue-ya yönləndiriləcəyinə qərar verən mexanizmdir.
  Producer mesajı Exchange-ə göndərir → Exchange mesajı Routing Key və Exchange tipinə görə uyğun Queue-ya yönləndirir.

### 📌 Exchange Növləri və Misallar

- 🎛️ 1️⃣ Fanout Exchange
    - ✅ Bütün bağlı Queue-lara Routing Key olmadan mesaj göndərir.
    - Yəni mesaj gələn kimi bütün bağlı queue-lara yayılır.
- 📖 Misal:
    - `Exchange`: `fanout-exchange`
    - `Queues`: `queue1`, `queue2`
- Producer → fanout-exchange → queue1 və queue2
- Kod:
```java
@Bean
FanoutExchange fanoutExchange() {
    return new FanoutExchange("fanout-exchange");
}

@Bean
Binding binding1() {
    return BindingBuilder.bind(queue1()).to(fanoutExchange());
}

@Bean
Binding binding2() {
    return BindingBuilder.bind(queue2()).to(fanoutExchange());
}
```

- İstifadə:
```java
rabbitTemplate.convertAndSend("fanout-exchange", "", "Salam Fanout!");
```
- 📝 Routing Key istifadə edilmir.

- 🎛️ 2️⃣ Direct Exchange
    - ✅ Mesaj Routing Key-ə tam uyğun olan queue-ya yönləndirilir.
 
- 📖 Misal:
- `Exchange`: `direct-exchange`
- `Queues`: `errorQueue`, `infoQueue`
- Producer → direct-exchange → Routing Key = "error" → errorQueue

Kod:
```java
@Bean
DirectExchange directExchange() {
    return new DirectExchange("direct-exchange");
}

@Bean
Binding errorBinding() {
    return BindingBuilder.bind(errorQueue())
        .to(directExchange())
        .with("error");
}

@Bean
Binding infoBinding() {
    return BindingBuilder.bind(infoQueue())
        .to(directExchange())
        .with("info");
}
```

- İstifadə:
```java
rabbitTemplate.convertAndSend("direct-exchange", "error", "Error baş verdi");
rabbitTemplate.convertAndSend("direct-exchange", "info", "Info mesajı");
```

- 🎛️ 3️⃣ Topic Exchange
    - ✅ Mesaj Routing Key Pattern-inə görə yönləndirilir.
 
- Wildcard-lar:
    - `*` → 1 söz
    - `#` → 0 və ya daha çox söz
 
- 📖 Misal:
    - `Exchange`: `topic-exchange`
 
- Queues:
    - `queue.error`
    - `queue.all`
 
- Binding Key-lər
    - `queue.error` → `log.error`
    - `queue.all` → `log.#`

Kod:
```java
@Bean
TopicExchange topicExchange() {
    return new TopicExchange("topic-exchange");
}

@Bean
Binding errorBinding() {
    return BindingBuilder.bind(queueError())
        .to(topicExchange())
        .with("log.error");
}

@Bean
Binding allBinding() {
    return BindingBuilder.bind(queueAll())
        .to(topicExchange())
        .with("log.#");
}
```

- İstifadə:
```java
rabbitTemplate.convertAndSend("topic-exchange", "log.error", "Error log!");
rabbitTemplate.convertAndSend("topic-exchange", "log.info.database", "DB info");
```

- ✅ log.error → queue.error və queue.all-a
- ✅ log.info.database → yalnız queue.all-a

- 🎛️ 4️⃣ Headers Exchange
    - ✅ Mesajın header-larına görə yönləndirilir, Routing Key istifadə edilmir.
 
- Misal:
    - `Exchange`: `headers-exchange`
    - `Queue`: `queue1`
 
- Header Şərti:
    - `type: admin`
    - `format: json`

 Kod:
 ```java
@Bean
HeadersExchange headersExchange() {
    return new HeadersExchange("headers-exchange");
}

@Bean
Binding headerBinding() {
    Map<String, Object> headerValues = new HashMap<>();
    headerValues.put("type", "admin");
    headerValues.put("format", "json");

    return BindingBuilder.bind(queue1())
        .to(headersExchange())
        .whereAll(headerValues)
        .match();
}
```

- İstifadə:
```java
MessageProperties props = new MessageProperties();
props.setHeader("type", "admin");
props.setHeader("format", "json");

Message message = new Message("Header message".getBytes(), props);

rabbitTemplate.send("headers-exchange", "", message);
```

- ✅ Yalnız header-lar uyğun olsa → queue1-a göndərilir.

### 📌 Cədvəl Xülasəsi:
| Exchange Növü | Routing Key    | Pattern Dəstəyi | Header Dəstəyi | Təsvir                       |
| :------------ | :------------- | :-------------- | :------------- | :--------------------------- |
| **Fanout**    | Yox            | Yox             | Yox            | Bütün queue-lara yayır       |
| **Direct**    | Dəqiq uyğunluq | Yox             | Yox            | Eyni Routing Key-li queue-ya |
| **Topic**     | Var            | `*` və `#`      | Yox            | Pattern ilə yönləndirir      |
| **Headers**   | Yox            | Yox             | Var            | Header-lara görə yönləndirir |


### 📌 Nəticə

- 🔸 Hər Exchange növünün öz üstün və istifadə yeri var:
    - `Fanout` → yayım
    - `Direct` → dəqiq yönləndirmə
    - `Topic` → pattern-lə dinamik yönləndirmə
    - `Headers` → header-larla şərti yönləndirmə

---

## <img src="https://github.com/user-attachments/assets/ea25c00d-2cce-40aa-90ac-1d3c01d73a8b" width="50px">  Message Converter və Serialization

### 📌 Message Converter və Serialization nədir?

- RabbitMQ-da biz obyektləri və ya məlumatları mesaj şəklində göndəririk. Bu mesajlar isə byte array şəklində RabbitMQ-da saxlanılır və ötürülür.
- Burada:
    - `Serialization` — obyektin byte array-ə çevrilməsi prosesidir.
    - `Deserialization` — byte array-in təkrar obyektə çevrilməsi.
- Message Converter isə — bu çevirmə prosesini idarə edən komponentdir.

### 📌 Niyə Message Converter lazımdır?

- 👉 RabbitMQ yalnız byte array göndərə və ala bilir.
- 👉 Biz Java obyektlərini göndərmək istəyirik.
- 👉 Bunu etmək üçün:
    - Obyekti serializasiya edirik (mesaja çeviririk).
    - Mesaj gəldikdə isə deserializasiya edib obyekt halına salırıq.
- Bu çevirməni isə Message Converter edir.

### 📌 Spring Boot-da Message Converter Növləri

- Spring Boot RabbitMQ integration-da bir neçə converter mövcuddur:

| Converter                                 | Təsvir                                                  |
| :---------------------------------------- | :------------------------------------------------------ |
| **SimpleMessageConverter**                | String, byte\[], Serializable obyektləri serialize edir |
| **Jackson2JsonMessageConverter**          | Java obyektləri JSON-a və ya JSON-dan çevirir           |
| **ContentTypeDelegatingMessageConverter** | ContentType-a əsaslanaraq converter seçir               |

### 📖 Məsələn: Jackson2JsonMessageConverter istifadə edək

- 👉 Java obyektlərini JSON şəklində serialize edib göndərmək üçün Jackson istifadə edirik.

#### 📌 Konfiqurasiya:
```java
@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
```

#### 📖 Producer İstifadəsi:
```java
User user = new User("Elvin", 22);
rabbitTemplate.convertAndSend("exchange-name", "routing-key", user);
```

#### 📖 Consumer İstifadəsi:
```java
@RabbitListener(queues = "queue-name")
public void receiveMessage(User user) {
    System.out.println("Gelen user: " + user.getName());
}
```

- Bu zaman automatik olaraq obyekt JSON-a çevrilir və geri obyektə deserialize olunur.

### 📌 Serialization (Serializable) nədir?

- Java-da bir obyektin byte stream-ə çevrilməsi üçün Serializable interfeysindən istifadə olunur.
- Misal:

```java
public class User implements Serializable {
    private String name;
    private int age;
}
```

- SimpleMessageConverter bu cür obyektləri serialize edib göndərə bilir. Amma JSON ilə işləmək daha çevik və rahatdır.

### 📌 Message Properties

- Mesaj göndərərkən, mesaja content-type, headers, priority və s. kimi əlavə məlumatlar da ötürmək olur.
- Misal:

```java
MessageProperties props = new MessageProperties();
props.setContentType("application/json");

Message message = new Message("Salam".getBytes(), props);
rabbitTemplate.send("exchange", "routingKey", message);
```

### 📌 Nəticə

| Anlayış                                   | Təsvir                                                |
| :---------------------------------------- | :---------------------------------------------------- |
| **Serialization**                         | Obyekti byte array-ə çevirmək                         |
| **Deserialization**                       | Byte array-dən obyekt yaratmaq                        |
| **Message Converter**                     | Mesajları serialize/deserialze edən Spring komponenti |
| **SimpleMessageConverter**                | String və Serializable obyektlər üçün                 |
| **Jackson2JsonMessageConverter**          | JSON formatlı obyektlər üçün                          |
| **ContentTypeDelegatingMessageConverter** | Content-type-a əsaslanıb converter seçir              |

### 📌 Bonus: Niyə Jackson daha çox istifadə olunur?
- ✅ JSON platformasından asılı deyil
- ✅ İnsan oxuya bilir
- ✅ Sistemlər arasında data ötürmək üçün ideal
- ✅ Spring Boot-da default dəstəklənir

---

## <img src="https://github.com/user-attachments/assets/3abd8827-526c-487d-a7f0-afb5a2e9b131" width="50px">  RabbitMQ Management Plugin və UI istifadə qaydası

### 📌 RabbitMQ Management Plugin nədir?

- RabbitMQ Management Plugin — RabbitMQ üçün web əsaslı idarəetmə panelidir.
- Bu panel vasitəsilə:
    - Queue-ları,
    - Exchange-ləri,
    - Binding-ləri,
    - Producer və Consumer-ləri,
    - Mesajların vəziyyətini,
    - DLQ-ları və s. vizual şəkildə idarə və izləmək mümkündür.
 
### 📌 Web UI-a necə daxil olunur?

- 👉 Default olaraq Web UI `http://localhost:15672` portunda işləyir.
- Default istifadəçi adı və şifrə:
    - username: guest
    - password: guest
- (Ancaq guest istifadəçisi yalnız localhost-dan daxil ola bilər)

### 📌 RabbitMQ Web UI-da nələr var?

- 📊 Dashboard
    - Server status
    - Node-ların durumu
    - Mesaj sayı
    - Connection sayı və load göstəriciləri
- 📨 Queues
    - Mövcud queue-ları görmək
    - Queue yaratmaq
    - Queue parametrlərini dəyişmək
    - Queue-ya test mesajı göndərmək
    - Queue-da olan mesajları vizual görmək və delete etmək
- 🔀 Exchanges
    - Mövcud exchange-ləri görmək
    - Yeni exchange yaratmaq
    - Binding-ləri vizual izləmək    
    - Mesajları test göndərmək üçün interface
- 🔗 Bindings
    - Queue və Exchange-lər arasındakı bağlantıları göstərir
    - Hansi routing key ilə hansı queue-ya yönləndiyini görə bilirsən
- 👥 Users
    - İstifadəçi yaratmaq
    - İcazə vermək (permissions)
    - User-ləri silmək və ya şifrəsini dəyişmək
- 🌐 Connections & Channels
    - Mövcud connection-ları və consumer-ləri izləmək
    - Hər channel-in statistikasını görmək

 ### 📌 Yeni Queue və ya Exchange necə əlavə olunur?

 - Web UI → Queues → Add a new queue

- Burada:
    - Queue name
    - Durability
    - Auto-delete
    - Arguments (DLQ, TTL və s. üçün) qeyd edib Add queue düyməsini klik edirsən.
 
- Eyni şəkildə: Exchanges → Add a new exchange
- Burada:
- Exchange adı
- Növü (Direct, Fanout, Topic, Headers)
- Durability və Auto-delete seçimi qeyd olunur.

### 📌 Mesaj göndərmək və test etmək

- Exchanges bölməsində:
1. Hər hansı exchange seç
2. "Publish message" bölməsinə gir
3. Routing key və mesajı yaz
4. Content-type və properties daxil et
5. Publish düyməsini sıx
- Queue-da həmin mesajı izləyə bilirsən.

### 📌 DLQ və Retry-ləri izləmək

- DLQ queue-ları ayrıca Queue bölməsində görsənir
- Mesaj sayını, vəziyyətini və move-to, delete əmrlərini tətbiq edə bilərsən

### 📌 RabbitMQ Management UI-nin üstünlükləri
- ✅ Real-time monitorinq
- ✅ Manual test mesaj göndərmək
- ✅ Queue və Exchange-ləri vizual idarə etmək
- ✅ Connection və Channel-ları izləmək
- ✅ DLQ və Retry proseslərini izləmək
- ✅ User və Permission idarəsi

### 📌 Docker ilə RabbitMQ Management UI

- Əgər Docker istifadə edirsənsə:

```docker
docker run -d --hostname rabbit-host --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

- Bu komanda ilə Management Plugin-lə `RabbitMQ` konteyneri açılır və `http://localhost:15672` üzərindən girə bilirsən.

### 📌 Nəticə

| İmkan                                | İzah                                                       |
| :----------------------------------- | :--------------------------------------------------------- |
| **Queue və Exchange idarəsi**        | Vizual queue və exchange yaratmaq                          |
| **Mesaj göndərmək və izləmək**       | Web interfeysdən test mesaj göndərmək və queue monitorinqi |
| **DLQ və Retry idarəsi**             | DLQ queue-larını vizual izləmək                            |
| **Connection və Channel monitorinq** | Canlı bağlantıları izləmək                                 |
| **User və Permission idarəsi**       | Web UI üzərindən user yaratmaq və icazə vermək             |

---

## <img src="https://github.com/user-attachments/assets/367f05da-1faf-4062-a65d-8ae0da83b7f6" width="50px">  Security: User, Permission və TLS

### 📌 RabbitMQ Security Nədir?
- RabbitMQ-da təhlükəsizlik sistemi authentication, authorization və communication security (TLS) üzərində qurulub:
    - 1️⃣ Authentication (kimlik yoxlaması)
    - 2️⃣ Authorization (icazə idarəsi)
    - 3️⃣ TLS (şifrələnmiş bağlantı)

#### 📌 1️⃣ User və Authentication

- RabbitMQ-da hər bir istifadəçi username və password ilə tanıdılır.

- 📦 İstifadəçi yaratmaq:
- Terminalda:

```bash
rabbitmqctl add_user yeni_user yeni_password
```

- 📦 İstifadəçini silmək:

```bash
rabbitmqctl delete_user yeni_user
```

#### 📌 2️⃣ Permission və Authorization

- Hər user-in hansı virtual host-da hansı exchange və queue-lara nə tip əməliyyat edə biləcəyini təyin etmək olur.
- Permission-lar 3 hissəyə bölünür:
    - Configure — Exchange və Queue-ları yaratmaq, dəyişmək
    - Write — Mesaj göndərmək
    - Read — Mesaj oxumaq və Queue-dan götürmək
 
- 📦 Permission vermək:

```bash
rabbitmqctl set_permissions -p / virtual_host yeni_user ".*" ".*" ".*"
```

- Burda:
    - `-p /` → virtual host
    - `.*` → Regex pattern (hər şeyi icazə verir)
 
- Məhdudlaşdırmaq üçün:

```bash
rabbitmqctl set_permissions -p / my_vhost yeni_user "^queue_name$" "^exchange_name$" "^queue_name$"
```

#### 📌 3️⃣ TLS (Transport Layer Security)

- RabbitMQ default olaraq plain TCP istifadə edir.
- `TLS` (SSL) ilə RabbitMQ bağlantılarını şifrələmək mümkündür.

- 📦 TLS konfiqurasiyası üçün:
- `rabbitmq.conf faylında:`
```properties
listeners.ssl.default = 5671

ssl_options.cacertfile = /path/to/ca_certificate.pem
ssl_options.certfile   = /path/to/server_certificate.pem
ssl_options.keyfile    = /path/to/server_key.pem
ssl_options.verify     = verify_peer
ssl_options.fail_if_no_peer_cert = true
```

- 📦 Portlar:
    - 5672 — plain TCP
    - 5671 — TLS ilə şifrələnmiş bağlantı

### 📌 Virtual Host-lar

- RabbitMQ-da Virtual Host (vhost) — izolyasiya mühitidir.
- Fərqli tətbiqlər və istifadəçilər üçün ayrıca virtual host-lar yaradıla və permission-lar ona görə verilə bilər.

- 📦 Vhost yaratmaq:

```bash
rabbitmqctl add_vhost my_vhost
```

- 📦 User-i Vhost-a icazə vermək:

```bash
rabbitmqctl set_permissions -p my_vhost yeni_user ".*" ".*" ".*"
```

### 📌 Management UI ilə Security idarəsi

- Web UI-dan:
    - Admin bölməsində user-ləri yaratmaq
    - Hər user üçün virtual host permission-ları təyin etmək
    - TLS bağlantılarının statusunu izləmək olur

### 📌 Nəticə
 
| Təhlükəsizlik Mexanizmi   | İzah                                                     |
| :------------------------ | :------------------------------------------------------- |
| **User Authentication**   | RabbitMQ-da istifadəçi hesabları yaratmaq                |
| **Permission Management** | İstifadəçiyə hansı əməliyyatı edə biləcəyini təyin etmək |
| **TLS Encryption**        | Mesaj və bağlantıları şifrələmək                         |
| **Virtual Host**          | İstifadəçi və tətbiqləri izolyasiya etmək                |

---

## <img src="https://github.com/user-attachments/assets/6cf7ce46-9640-4f9b-9f8f-0433222063d8" width="50px">  Monitoring və Metrics (Prometheus, Grafana inteqrasiyası)

### 📌 RabbitMQ Monitoring və Metrics Nədir?
- RabbitMQ-da serverin sağlamlığı, message trafiki, queue doluluğu, consumer statusu, connection sayı, acknowledgment gecikməsi və s. kimi məlumatları real-time və ya history olaraq izləmək üçün monitoring        sistemləri istifadə olunur.

### 📌 RabbitMQ-da Monitoring üsulları:
- 1️⃣ RabbitMQ Management Plugin (built-in UI)
- 2️⃣ Prometheus Exporter + Prometheus + Grafana (pro səviyyəsində)

### 📌 Prometheus və Grafana İnteqrasiyası
- 📦 Prometheus nədir?
    - → Time-series data toplayıb saxlama və query etmə sistemi.
    - → RabbitMQ-nun metriklərini çəkib toplayır.

- 📦 Grafana nədir?
    - → Prometheus-dan gələn metrikləri dashboard və vizual qrafik halında göstərən open-source monitorinq və analiz platformasıdır.
 
### 📌 RabbitMQ → Prometheus Exporter → Prometheus → Grafana

#### 📌 1️⃣ RabbitMQ Prometheus Exporter quraşdırmaq

- RabbitMQ server-də Prometheus üçün exporter plugin əlavə edirsən:

```bash
rabbitmq-plugins enable rabbitmq_prometheus
```

- Bu zaman RabbitMQ metrics endpoint açır:

```bash
http://localhost:15692/metrics
```

- Bu endpoint-dən Prometheus metricsləri scrape edəcək.

#### 📌 2️⃣ Prometheus Konfiqurasiyası

- Prometheus-un `prometheus.yml` faylına RabbitMQ exporter-in endpointini əlavə et:

```yaml
scrape_configs:
  - job_name: 'rabbitmq'
    static_configs:
      - targets: ['localhost:15692']
```

- Prometheus-u restart et:

```bash
./prometheus --config.file=prometheus.yml
```

#### 📌 3️⃣ Grafana İnteqrasiyası

- Grafana Web UI → Add Data Source → Prometheus seç → Prometheus URL:

```arduino
http://localhost:9090
```

- Dashboard əlavə etmək üçün:

    - Grafana Marketplace-dən RabbitMQ üçün hazır dashboard ID-lərini import edə bilərsən.
      → Məsələn: RabbitMQ Overview dashboard ID: 10991
      və ya RabbitMQ üçün öz panelini düzəldə bilərsən.

### 📌 Ən önəmli RabbitMQ Metrics-lər:

| Metrik                                   | İzah                                                            |
| :--------------------------------------- | :-------------------------------------------------------------- |
| `rabbitmq_queue_messages_ready`          | Queue-da gözləyən mesaj sayı                                    |
| `rabbitmq_queue_messages_unacknowledged` | Consumer-lar tərəfindən alınmış, lakin ack olunmamış mesaj sayı |
| `rabbitmq_queue_messages`                | Queue-da ümumi mesaj sayı                                       |
| `rabbitmq_connections`                   | RabbitMQ-da aktiv bağlantı sayı                                 |
| `rabbitmq_channels`                      | Aktiv channel sayı                                              |
| `rabbitmq_consumers`                     | Consumer sayı                                                   |

### 📌 RabbitMQ Monitoring üçün Tam Setup Flow:

```scss
RabbitMQ
   │
   │ (metrics, /metrics endpoint)
   │
Prometheus Exporter (rabbitmq_prometheus plugin)
   │
   │ (scrape configs)
   │
Prometheus (metrics database)
   │
   │ (query & time-series data)
   │
Grafana (dashboards və vizual qrafiklər)

```

### 📌 Nəticə:

| Komponent                      | Rol                                            |
| :----------------------------- | :--------------------------------------------- |
| **RabbitMQ Management Plugin** | Sadə Web UI izləmə                             |
| **Prometheus Exporter**        | Metrikləri Prometheus formatında çıxarır       |
| **Prometheus**                 | Metrikləri toplayıb saxlayır                   |
| **Grafana**                    | Prometheus-dan gələn datanı vizualizasiya edir |

### 📌 Docker ile bunu etmek

- 📦 docker-compose.yml — Full Monitoring Stack

```yaml
version: '3.8'

services:
  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
      - "15692:15692"  # Prometheus exporter portu
    environment:
      RABBITMQ_DEFAULT_USER: admin
      RABBITMQ_DEFAULT_PASS: admin
    volumes:
      - rabbitmq_data:/var/lib/rabbitmq
    networks:
      - monitoring-net
    command: >
      bash -c "rabbitmq-plugins enable --offline rabbitmq_prometheus &&
               rabbitmq-server"

  prometheus:
    image: prom/prometheus
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
      - "9090:9090"
    networks:
      - monitoring-net

  grafana:
    image: grafana/grafana
    container_name: grafana
    ports:
      - "3000:3000"
    networks:
      - monitoring-net
    volumes:
      - grafana_data:/var/lib/grafana

volumes:
  rabbitmq_data:
  grafana_data:

networks:
  monitoring-net:
    driver: bridge
```


- 📦 prometheus.yml — Prometheus konfiqurasiya faylı
    - Bu faylı docker-compose.yml ilə eyni qovluqda saxla:

```yaml
global:
  scrape_interval: 5s

scrape_configs:
  - job_name: 'rabbitmq'
    static_configs:
      - targets: ['rabbitmq:15692']
```

### 📌 Başlatmaq:

```bash
docker-compose up -d
```

### 📌 İstifadə Linkləri:

| Servis              | URL                                                            |
| :------------------ | :------------------------------------------------------------- |
| RabbitMQ Management | [http://localhost:15672](http://localhost:15672) → admin/admin |
| Prometheus UI       | [http://localhost:9090](http://localhost:9090)                 |
| Grafana UI          | [http://localhost:3000](http://localhost:3000) → admin/admin   |


### 📌 Grafana-da Dashboard Import:

1. Grafana Web UI → Dashboards → Import
2. Marketplace-dən RabbitMQ dashboard id: 10991 yaz
3. Prometheus datasource seç və import et ✅

### 📌 Qısa Overview:
- RabbitMQ → 5672 (AMQP), 15672 (UI), 15692 (Prometheus exporter)
- Prometheus → 9090 (metrics query və monitor)
- Grafana → 3000 (dashboard)

---

## <img src="https://github.com/user-attachments/assets/fa8185f4-986a-471b-a548-5999939a6dbd" width="50px"> RabbitMQ Performans Tuning və Best Practices

### 📌 RabbitMQ Performans Tuning Parametrləri

- 1️⃣ Disk və I/O Performansı
    - RabbitMQ disk heavy broker-dir, mesajlar və metadata çox zaman diskə yazılır.
    - SSD istifadə et.
    - Disk latency < 1 ms olmalı.
 
- 2️⃣ File Descriptors
    - Hər bağlantı üçün open file descriptor tələb olunur.
    - OS səviyyəsində limit artırılmalı.
 
- 3️⃣ Queue Sayını Azalt
    - Çox sayda queue → performansı aşağı salar.
    - Əgər mümkün olsa, az queue, çox consumer yanaşması saxla.
 
- 4️⃣ Message Size
    - Mesaj ölçüsü 100KB-dan yuxarı olmamalı.
    - Çox böyük datanı mesajla göndərmək əvəzinə external storage istifadə et (məs. S3) və link göndər.
 
- 5️⃣ Batch Acknowledgment
    - Hər mesaj üçün ayrı acknowledgment əvəzinə batch ilə təsdiqləmə istifadə et.
    - Java/Spring Boot nümunəsi:
 
```java
channel.basicAck(deliveryTag, true); // multiple=true
```

- 6️⃣ Consumer Prefetch Limit
    - Consumer-ə eyni anda neçə mesaj göndərilə bilər deyə limit qoy.
 
- Nümunə:

```java
channel.basicQos(10);
```

- → Eyni anda 10 mesaj göndər.

### 📌 RabbitMQ Best Practices

- ✅ Connection Pooling istifadə et
    - Bir application üçün çoxlu connection yaratmaq əvəzinə, connection pool qur.
 
- ✅ Dedicated Network
    - Broker-lər üçün xüsusi network subnet istifadə et (Docker-da internal: true network).

- ✅ DLQ və Retry mexanizmi
    - Hər queue üçün DLQ konfiqurasiya et. Retry mexanizmi ilə transient error-ları idarə et.

- ✅ Monitoring
    - Həmişə Prometheus + Grafana və ya RabbitMQ Management Plugin ilə metrikləri izləyin:
        - queue depth
        - unacked message
        - consumer count
        - connection sayısı
        - disk latency
     
- ✅ Producer və Consumer Load Balancing
    - Multiple producer və consumer-lar yerləşdir.
    - Horizontal scaling → container sayını artırmaqla.

- ✅ Mirrored Queue (HA) çox istifadə etmə.
    - Yalnız critical queue-ları mirror et. Çünki every mirror = 2x network + disk load.

- ✅ TTL (Time To Live)
    - Mesaj və ya queue üçün TTL təyin et.
    - Lazımsız mesajlar sistemdə qalıb yük salmasın.

- Nümunə:

```json
{"x-message-ttl":60000}
```

- → 60 saniyə sonra mesaj silinsin.

### 📊 Vacib Monitor Metriklər

| Metrik                          | İzah                                            |
| :------------------------------ | :---------------------------------------------- |
| `queue_messages_ready`          | Göndərilməmiş mesaj sayı                        |
| `queue_messages_unacknowledged` | Consumer-ə göndərilmiş, ack gəlməyən mesaj sayı |
| `connections`                   | Aktiv bağlantı sayı                             |
| `disk_free`                     | Boş disk sahəsi                                 |
| `memory_used`                   | İstifadə olunan RAM                             |


### 📌 Nəticə:

- Performansı artırmaq üçün:
    - ✅ Disk və RAM-ı optimallaşdır
    - ✅ Mesaj ölçüsünü idarə et
    - ✅ Prefetch və batch acknowledgment istifadə et
    - ✅ DLQ və Retry mexanizmi qur
    - ✅ Monitoring quraraq hər dəqiqə sistem sağlamlığını yoxla
    - ✅ Lazy queue və TTL tətbiq et
 
 ---
