## Tanıtım

- Spring Boot ile Veteriner Yönetim Sistemi Projesi geliştirildi API hazırlandı

## API Temel Özellikleri
### Hayvanların ve Sahiplerinin (customer) Yönetimi

- Hayvanları kaydetme, bilgilerini güncelleme, görüntüleme ve silme
- Hayvan sahiplerini kaydetme, bilgilerini güncelleme, görüntüleme ve silme
- Hayvan sahipleri isme göre filtrelenecek şekilde end point 
- Hayvanlar isme göre filtrelenecek şekilde end point
- Hayvan sahibinin sistemde kayıtlı tüm hayvanlarını görüntülemek için API end point 

### Uygulanan Aşıların Yönetimi

- Hayvanlara uygulanan aşıları kaydetme, bilgilerini güncelleme, görüntüleme ve silme
- Eğer hastaya ait aynı tip aşının (adı ve kodu aynı olan aşı) aşı koruyuculuk bitiş tarihi daha gelmemiş ise sisteme yeni aşı girilememesi kontrolü
- Hayvan id’sine göre belirli bir hayvana ait tüm aşı kayıtlarını listelemek için gerekli API end point
- Kullanıcının aşı koruyuculuk bitiş tarihi yaklaşan hayvanları listeleyebilmesi için kullanıcının gireceği başlangıç ve bitiş tarihlerine göre aşı koruyuculuk tarihi bu aralıkta olan hayvanların listesini geri döndüren API end  point'i

### Randevu Yönetimi

- Hayvanların aşı ve muayene randevularının oluşturulması, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi
- Randevular sisteme tarih ve saat içerecek şekilde kaydedilmesi
- Hayvanların her türlü muayenesi için doktorlardan uygun tarihlerde ve saatlerde randevu oluşturulması.( Her doktor için sadece saat başı randevu oluşturulabilir. Bir muayenenin sabit olarak bir saat süreceğini kabul edildi.)
- Randevu kaydı oluştururken doktorun girilen tarihte müsait günü olup olmadığı eğer ki müsait günü varsa randevu kayıtlarında girilen saatte başka bir randevusu olup olmadığı kontrol edilmelidir. Her iki durum şartı sağlanırsa randevu oluşturulmalıdır. Şart sağlanmaz ise “throw new RuntimeException("Doktor bu tarihte çalışmamaktadır!/Girilen saatte başka bir randevu mevcuttur.");” gibi hata mesajı fırlatılmak
- Randevular kullanıcı tarafından girilen tarih aralığına ve doktora göre filtrelenmelidir.(randevu için kliniği arayan müşterilerin doktor ve gün taleplerinde uygunluk olup olmadığını sorgulamak için kullanılacaktır.)
- Randevular kullanıcı tarafından girilen tarih aralığına ve hayvana göre filtrelenmelidir.

### Veteriner Doktor Yönetimi

- Veteriner doktorların kaydedilmesi, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi
- Doktorların Müsait Günlerinin Yönetimi
- Doktorların müsait günlerini ekleme, bilgilerini güncelleme, görüntüleme ve silme

### UML diyagram

![image](https://github.com/HazalOZER/vet/assets/109552542/bb14e08f-2944-4e7b-bde2-19a7ebe21118)


