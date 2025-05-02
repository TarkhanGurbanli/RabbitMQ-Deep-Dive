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

        

