# RabbitMQ-Deep-Dive
All about RabbitMQ

<style>
  /* Modern CSS styling */
  :root {
    --primary: #ff6600;
    --secondary: #663399;
    --dark: #333;
    --light: #f8f9fa;
    --success: #28a745;
    --info: #17a2b8;
  }
  
  body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    line-height: 1.6;
    color: var(--dark);
  }
  
  h1, h2, h3 {
    color: var(--secondary);
    border-bottom: 1px solid #eee;
    padding-bottom: 0.3em;
  }
  
  h2 {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-top: 1.5em;
  }
  
  .toc {
    background-color: var(--light);
    padding: 1.5em;
    border-radius: 8px;
    margin-bottom: 2em;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }
  
  .toc-list {
    columns: 2;
    column-gap: 2em;
  }
  
  .toc-item {
    margin-bottom: 0.5em;
    break-inside: avoid;
  }
  
  .toc-link {
    color: var(--primary);
    text-decoration: none;
    transition: all 0.3s ease;
    display: inline-block;
    padding: 0.2em 0;
  }
  
  .toc-link:hover {
    color: var(--secondary);
    transform: translateX(5px);
  }
  
  .section-icon {
    width: 30px;
    height: 30px;
    vertical-align: middle;
  }
  
  .feature-box {
    background-color: #f8f9fa;
    border-left: 4px solid var(--primary);
    padding: 1em;
    margin: 1em 0;
    border-radius: 0 4px 4px 0;
  }
  
  .diagram {
    background-color: #f5f5f5;
    padding: 1em;
    border-radius: 4px;
    font-family: monospace;
    overflow-x: auto;
  }
  
  .nav-buttons {
    display: flex;
    gap: 10px;
    margin: 2em 0;
    flex-wrap: wrap;
  }
  
  .nav-button {
    background-color: var(--primary);
    color: white;
    padding: 0.5em 1em;
    border-radius: 4px;
    text-decoration: none;
    transition: all 0.3s ease;
  }
  
  .nav-button:hover {
    background-color: var(--secondary);
    transform: translateY(-2px);
  }
  
  @media (max-width: 768px) {
    .toc-list {
      columns: 1;
    }
  }
</style>

<div class="toc">
  <h2>📋 Mündəricat (Table of Contents)</h2>
  <div class="toc-list">
    <div class="toc-item"><a href="#what-is-rabbitmq" class="toc-link">1. RabbitMQ nədir? (What is RabbitMQ?)</a></div>
    <div class="toc-item"><a href="#messaging-system" class="toc-link">2. Mesajlaşma Sistemi nədir?</a></div>
    <div class="toc-item"><a href="#amqp" class="toc-link">3. AMQP nədir?</a></div>
    <div class="toc-item"><a href="#architecture" class="toc-link">4. RabbitMQ-nun Arxitekturası</a></div>
    <div class="toc-item"><a href="#exchange-types" class="toc-link">5. Exchange növləri</a></div>
    <div class="toc-item"><a href="#queues-bindings" class="toc-link">6. Queue və Binding-lər</a></div>
    <!-- Add more TOC items as needed -->
  </div>
</div>

<div class="nav-buttons">
  <a href="#what-is-rabbitmq" class="nav-button">Start Reading →</a>
  <a href="https://rabbitmq.com" target="_blank" class="nav-button">Official Docs</a>
</div>

## <img src="https://github.com/user-attachments/assets/9fea07e3-295c-4b10-8c10-99a087c3c14e" width="30" height="30" id="what-is-rabbitmq"> RabbitMQ nədir? (What is RabbitMQ?)

<div class="feature-box">
RabbitMQ — açıq mənbə (open-source) kodlu, mesaj broker proqram təminatıdır. Yəni, proqramlar və xidmətlər arasında məlumat ötürmək üçün arada vasitəçi rolunu oynayan sistemdir.
</div>

### 📌 RabbitMQ nə işə yarayır?

- RabbitMQ tətbiqlər arasında məlumatları asinxron və etibarlı şəkildə ötürməyə kömək edir.
- Məsələn:
    - Bir sistem sifarişi qəbul edib RabbitMQ-ya mesaj göndərir.
    - Başqa sistemlər isə bu mesajı oxuyub öz işlərini yerinə yetirirlər.

<div class="diagram">
[Producer] → [RabbitMQ] → [Consumer]
</div>

<div class="nav-buttons">
  <a href="#messaging-system" class="nav-button">Next: Mesajlaşma Sistemi →</a>
  <a href="#toc" class="nav-button">↑ Table of Contents</a>
</div>

## <img src="https://github.com/user-attachments/assets/65a50f5a-af6d-475d-a556-352b11d90210" width="30" height="30" id="messaging-system"> Mesajlaşma Sistemi nədir? (What is a Messaging System?)

<div class="feature-box">
Mesajlaşma Sistemi — fərqli tətbiqlər və ya servislər arasında məlumat ötürmək üçün istifadə olunan proqram təminatı arxitekturasıdır.
</div>

### 📌 Mesajlaşma Sisteminin Əsas Məqsədi

- Tətbiqlər arasındakı əlaqəni zəiflətmək (loose coupling)
- Asinxron məlumat ötürmək
- Etibarlı və ardıcıl məlumat çatdırmaq

<div class="nav-buttons">
  <a href="#amqp" class="nav-button">Next: AMQP →</a>
  <a href="#toc" class="nav-button">↑ Table of Contents</a>
</div>

<!-- Continue with other sections following the same pattern -->

## <img src="https://github.com/user-attachments/assets/895ec9d6-54d3-4a0f-99b8-d6383b741514" width="30" height="30" id="architecture"> RabbitMQ-nun Arxitekturası (RabbitMQ Architecture)

<div class="feature-box">
RabbitMQ — AMQP protokolu əsasında çalışan message broker-dir və özünün spesifik arxitekturası var.
</div>

### 📌 RabbitMQ Arxitekturasının Əsas Komponentlər

<div class="diagram">
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
</div>

<div class="nav-buttons">
  <a href="#exchange-types" class="nav-button">Next: Exchange Types →</a>
  <a href="#toc" class="nav-button">↑ Table of Contents</a>
</div>

<!-- Continue with all other sections -->

<div class="nav-buttons" style="margin-top: 3em;">
  <a href="#what-is-rabbitmq" class="nav-button">↑ Back to Top</a>
  <a href="#toc" class="nav-button">Table of Contents</a>
</div>
