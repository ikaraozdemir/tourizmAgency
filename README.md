# Patika Turizm Acente Sistemi

Patika Full Stack Web Develpment Bootcamp kapsamında geliştirilen bu turizm acente sistemi uygulamasında temel düzeyde otel yönetimi, sezon yönetimi, oda yönetimi ve rezervasyon işlemleri yapılabilmektedir.

## Teknolojiler
* Java SE 8
* Swing Framework
* PostgreSQL 16
* IntelliJ IDEA Community Edition 2023.3.3 

## Kurulum

1. Projeyi klonlayın.
2. Sisteminizde JDK 1.8 versiyonunun kurulu olduğundan emin olun.
3. proje klasörü içinde bulunan PostgreSQL backup dosyasını içe aktarın.
4. Veritabanı bağlantısı öncesinde kullnıcı adı ve parolayı güncelleyin.

## Proje Değerlendirme Kriterleri  
6. maddeyi karşılayan kod satırları core paketi/Database sınıfı içinde verilmiştir.
7. maddeyi karşılayan kod satırları view paketi/AdminView sınıfı içinde verilmiştir.
   * loadUserTable() ve loadUserComponent() metotları
8. maddeyi karşılayan kod satırları view paketi/EmployeeView sınıfı içinde verilmiştir.
   * loadHotelTable() metodu -otelleri listele-, loadHotelComponent() metodu -Otel ile birlikte tesis özellikleri, sezon ve pansiyon tipi ekle;otel sil, otele oda, oda özellikleri ve fiyat ekle-
   * loadSeasonTable() metodu -sezonları listele-, loadSeasonComponent() metodu -Sezon sil-
   * loadRoomTable() metodu -odaları listele-, loadRoomComponent() metodu -Oda güncelle, sil-
   * loadReservationTable() metodu -rezervasyonları listele-, loadReservationComponent() metodu -rezervasyon sil, güncelle-
9. maddeyi karşılayan kod satırları (24-44) view paketi/LoginView sınıfında verilmiştir.  
10. maddeyi karşılayan kod satırları view paketi/HotelView sınıfı içinde verilmiştir.
11. maddeyi karşılayan kod satırları (141-166) view paketi/HotelView sınıfı içinde verilmiştir.
12. maddeyi karşılayan kod satırları (179-188) view paketi/HotelView sınıfı içinde verilmiştir.
13. maddeyi karşılayan kod satırları view paketi/RoomView sınıfı içinde verilmiştir.
14. maddeyi karşılayan kod satırları view paketi/RoomView sınıfı içinde verilmiştir.
15. maddeyi karşılayan kod satırları view/EmployeeView sınıfındadır, loadHotelComponent() metodunda istenilen kriterlere uygun filtreleme yapılmaktadır.
16. Arama sonuçları view paketi/RoomView sınıfına ait loadSearchedRoomTable() metodu kullanılarak listelenir.
17. view/ReservationView sınıfında, 63-67  satırları arasında toplan ödeme tutarı hesaplanmaktadır.
18. view/ReservationView sınıfında, 110-158 satırları arasında rezervasyon bilgileri alınıp kayıt yapılmaktadır.
19. business/Reservation sınıfında, 32-37 satırları arasında stok azaltılmaktadır.
20. maddeyi karşılayan kod satırları view paketi/EmployeeView sınıfı içinde verilmiştir. loadReservationTable() metodu listeleme yapmaktadır.
21. maddeyi karşılayan kod satırları view paketi/EmployeeView sınıfı içinde verilmiştir. loadReservationComponent() metodu ilgili metotları çağırarak güncelleme yapar.
22. maddeyi karşılayan kod satırları view paketi/EmployeeView sınıfı içinde verilmiştir. loadReservationComponent() metodu ilgili metotları çağırarak silme işlemi yapar.
23. business/Reservation sınıfında, 56. satırdaki kodla stok artırılmaktadır.
24. core/Helper sınıfı, showMessage() metodu ile uygun pop up mesajları verilmektedir.
25. core/Helper sınıfı, showMessage() metodu ile hata mesajları verilmektedir.

## Yapılamayanlar

Normalde hatasız çalışan programım video çekmek için çalıştırdığımda NullPointerException hatası verdi. Zamanım olmadığı için bu şekilde yüklüyorum.
Revizyonda video ile birlikte yükleyeceğim.
