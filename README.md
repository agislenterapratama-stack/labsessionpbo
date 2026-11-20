[Uploading README (3).md…]()
# labsessionpbo# Lab Session PBO Kelas 3B — Pertemuan 11 & 12
## Sistem Kasir Kantin UNS

**Nama:** Agis Lentera Pratama
**NIM:** L0325016

Satu aplikasi sederhana, Kasir Kantin UNS, dikerjakan bertahap dari
Tahap 1 sampai Tahap 4. Semua class cukup dalam satu package: `lab.kasir`.
Atribut class dibuat `private` (enkapsulasi), dan diakses lewat method
bila perlu. Kerangka kode yang disediakan di soal sudah dilengkapi
(bagian TODO sudah diisi).

> **Catatan penting:** file soal pertama yang dikirim ternyata salah.
> Versi ini dibuat dari soal yang benar (`LAB_SESSION_P11-12__1.pdf`).
> ZIP versi sebelumnya (dua package `lab.sesi1`/`lab.sesi2`, tiga kasir,
> race condition stok, tombol Reset) **sudah tidak berlaku** — jangan dipakai.

---

## Perbedaan soal yang benar dengan soal yang salah

| | Soal yang salah (versi lama) | Soal yang benar (versi ini) |
|---|---|---|
| Package | Dua package: `lab.sesi1` dan `lab.sesi2` | Satu package: `lab.kasir` |
| Class utama | `MainSesi1` dan `MainSesi2` terpisah | Satu `MainKasir` untuk semua tahap |
| `Pesanan` | Daftar item, `tambahItem(String, double, int)`, `hitungSubtotal()` | Satu atribut `private double subtotal`, `tambahItem(double, int)`, `getSubtotal()` |
| Kasir | 3 kasir (Dina, Eko, Fani) + Runnable, prioritas, `isAlive()` | 2 kasir (Dina dan Eko), `start()` + `join()` saja |
| Tugas tambahan | Race condition stok menu, laporan harian, jendela pertama, tombol Reset | Tidak ada — diganti Tahap 4: tombol Cetak Struk dengan thread |
| GUI | 520x510, tombol Tambah / Hitung Total / Reset | 400x450, tombol Tambah / Hitung Total / **Cetak Struk** + label status |
| Validasi | Nama kosong, bukan angka, nol/negatif | Field kosong atau bukan angka saja |

---

## Struktur file

```
KasirKantinUNS_AgisLenteraPratama_L0325016/
├── README.md                  <- file ini
├── compile.bat                <- compile (Windows)
├── run.bat                    <- jalankan aplikasi (Windows)
├── hasil/
│   ├── Hasil_Tahap1-2_run1.txt <- output Tahap 1 & 2, run ke-1 (asli)
│   ├── Hasil_Tahap1-2_run2.txt <- output Tahap 1 & 2, run ke-2 (asli)
│   └── Screenshot_Run_NetBeans.png <- screenshot run Agis di NetBeans
└── src/
    └── lab/kasir/
        ├── Pesanan.java       <- Tahap 1: logika pesanan, tanpa GUI
        ├── Kasir.java         <- Tahap 2: Kasir extends Thread
        ├── KasirFrame.java    <- Tahap 3 & 4: jendela kasir (Swing)
        └── MainKasir.java     <- titik awal: Tahap 1, Tahap 2, lalu GUI
```

Komentar di kode hanya di bagian penting: atribut, getter, setter,
overriding, `extends`, constructor, validasi, dan logika kunci.

---

## Cara compile & menjalankan

Butuh JDK 17 (atau lebih baru). Semua perintah dijalankan dari folder
proyek ini (folder yang berisi `src/`).

### Windows (paling mudah)

Klik dua kali, berurutan:

1. `compile.bat` — compile semua file ke folder `out/`
2. `run.bat` — menjalankan `MainKasir` (Tahap 1 dan 2 di console,
   lalu jendela GUI terbuka)

Atau lewat terminal (CMD / PowerShell), dari folder proyek:

```bat
javac -d out src\lab\kasir\*.java
java -cp out lab.kasir.MainKasir
```

### Linux / macOS

```bash
javac -d out $(find src -name '*.java')
java -cp out lab.kasir.MainKasir
```

### NetBeans / VS Code

Buat project Java baru, lalu salin folder `src/` ke dalam project
(sehingga ada `src/lab/kasir/...`). Jalankan class `MainKasir`
(klik kanan class → Run).

---

## Bukti run (screenshot Agis, NetBeans)

Agis menjalankan program ini di NetBeans di laptopnya (project Maven
`KasirKantinUns`, 4 source file ter-compile). Screenshot-nya ada di
`hasil/Screenshot_Run_NetBeans.png`:

![Hasil run di NetBeans: console Tahap 1 dan 2, dan jendela Kasir Kantin UNS](hasil/Screenshot_Run_NetBeans.png)

Yang terlihat di screenshot:

- Console Tahap 1: `Setelah 2 menu: subtotal = 45000.0, total = 45000.0`,
  `Setelah 3 menu: subtotal = 70000.0, total = 63000.0`,
  `Setelah reset: subtotal = 0.0` — cocok dengan hasil yang diharapkan.
- Console Tahap 2: Dina dan Eko muncul bergantian (pada run ini Dina
  yang tercetak pertama), dan `Semua pelanggan selesai dilayani`
  muncul paling akhir.
- Jendela `Kasir Kantin UNS` terbuka dengan benar: field Nama Menu /
  Harga / Jumlah, tombol Tambah dan Hitung Total, area struk kosong,
  `Total Bayar : Rp 0`, dan tombol Cetak Struk di bawah.
  (Label status di sebelah kanan tombol Cetak Struk masih kosong,
  karena tombol Cetak Struk belum ditekan — label itu baru terisi
  `Mencetak struk...` lalu `Struk selesai.` setelah tombol ditekan.)

---

## Tahap 1: Class Logika

`Pesanan` dengan atribut `private double subtotal`.

- `tambahItem(double harga, int jumlah)` menambahkan `harga x jumlah`
  ke subtotal.
- `getSubtotal()` membaca nilai subtotal.
- `hitungTotal()`: jika subtotal lebih dari 50.000, kembalikan subtotal
  dikurangi diskon 10 persen (subtotal x 0,9). Jika tidak, kembalikan
  subtotal apa adanya.
- `reset()` mengembalikan subtotal ke 0.

Data uji di `MainKasir`: Nasi Goreng 15.000 x 2, Es Teh 5.000 x 3,
Ayam Bakar 25.000 x 1.

**Hasil yang diukur (file `hasil/Hasil_Tahap1-2_run1.txt` dan `run2.txt`):**

```
Setelah 2 menu: subtotal = 45000.0, total = 45000.0
Setelah 3 menu: subtotal = 70000.0, total = 63000.0
Setelah reset: subtotal = 0.0
```

Setelah dua menu pertama total **45.000 (tanpa diskon)**. Setelah menu
ketiga subtotal **70.000** dan total **63.000** (diskon 10%:
70.000 x 0,9 = 63.000). Sesuai hasil yang diharapkan di soal.

---

## Tahap 2: Dua Kasir sebagai Thread

Kerangka `Kasir extends Thread` dari soal dilengkapi: `run()` mencetak
kalimat seperti `Kasir Dina melayani pelanggan ke-1`, untuk pelanggan
ke-1 sampai ke-5, dengan `Thread.sleep(500)` di tiap pelanggan.

`MainKasir` membuat dua kasir (Dina dan Eko), menjalankan keduanya
dengan `start()`, lalu `join()` untuk keduanya agar main mencetak
`Semua pelanggan selesai dilayani` setelah keduanya selesai.

**Hasil yang diukur (program dijalankan 2 kali):**

Run 1 — yang tercetak pertama adalah Eko:

```
Kasir Eko melayani pelanggan ke-1
Kasir Dina melayani pelanggan ke-1
Kasir Eko melayani pelanggan ke-2
Kasir Dina melayani pelanggan ke-2
...
Kasir Eko melayani pelanggan ke-5
Kasir Dina melayani pelanggan ke-5
Semua pelanggan selesai dilayani
```

Run 2 — yang tercetak pertama adalah Dina:

```
Kasir Dina melayani pelanggan ke-1
Kasir Eko melayani pelanggan ke-1
Kasir Dina melayani pelanggan ke-2
Kasir Eko melayani pelanggan ke-2
...
Kasir Dina melayani pelanggan ke-5
Kasir Eko melayani pelanggan ke-5
Semua pelanggan selesai dilayani
```

Output Dina dan Eko muncul **bergantian dan urutannya bisa berbeda
tiap dijalankan** (run 1 Eko dulu, run 2 Dina dulu) — jadi urutan output
tidak selalu sama. Kalimat `Semua pelanggan selesai dilayani` selalu
muncul **paling akhir**, karena `join()` membuat main menunggu kedua
kasir selesai. Tanpa `join()`, kalimat itu bisa tercetak lebih awal.

---

## Tahap 3: Tampilan GUI Kasir

`KasirFrame extends JFrame`, ukuran 400 x 450, `setLayout(null)`,
posisi diatur dengan `setBounds()`, sesuai kerangka dan gambar di soal.
Komponennya: judul `Kasir Kantin UNS`, field Nama Menu / Harga / Jumlah,
tombol Tambah dan Hitung Total, area struk (JTextArea dalam JScrollPane),
dan label `Total Bayar : Rp 0`.

- **Tombol Tambah:** mengambil isi field harga dan jumlah, memanggil
  `pesanan.tambahItem(...)`, menulis satu baris ke area struk
  (misalnya `Nasi Goreng x 2 @ Rp 15.000`), lalu mengosongkan field.
- **Tombol Hitung Total:** menampilkan hasil `pesanan.hitungTotal()`
  pada label total.
- **Validasi sederhana:** jika ada field kosong atau harga dan jumlah
  bukan angka, tampilkan pesan dengan `JOptionPane.showMessageDialog`.
  `NumberFormatException` ditangani dengan try dan catch.

**Hasil yang diharapkan:** dengan data uji Tahap 1, label total
menampilkan **63.000** setelah ketiga menu ditambahkan
(subtotal 70.000, diskon 10%). Logika yang sama sudah teruji di Tahap 1
(45.000 setelah 2 menu, 63.000 setelah 3 menu).

---

## Tahap 4: Tombol Cetak Struk dengan Thread

Ditambahkan tombol **Cetak Struk** dan sebuah label status di bawah
jendela. Saat tombol ditekan:

1. Label status menampilkan `Mencetak struk...`
2. Program menunggu 2 detik (`Thread.sleep(2000)`)
3. Label status menampilkan `Struk selesai.`

Penantian 2 detik dilakukan di **thread baru** (sesuai kerangka di soal:
tombol menjalankan thread baru dengan `start()`), supaya jendela tidak
macet. Selama 2 detik menunggu, tombol Tambah dan Hitung Total masih
bisa ditekan — karena yang tidur adalah thread cetak struk, bukan
Event Dispatch Thread (thread khusus Swing yang menangani tampilan
dan klik). Perubahan label dilakukan lewat `SwingUtilities.invokeLater`
supaya dikerjakan di thread khusus Swing tersebut.

---

## Verifikasi yang sudah dilakukan

- Compile: **berhasil, 0 error** (JDK 17 Temurin 17.0.21.1+2,
  4 file `.java`, 4 file class).
- Tahap 1: subtotal/total **45.000 / 45.000** setelah 2 menu,
  **70.000 / 63.000** setelah 3 menu, reset kembali ke 0.
- Tahap 2: dijalankan **2 kali** — urutan Dina/Eko berbeda
  (run 1 Eko dulu, run 2 Dina dulu), `Semua pelanggan selesai dilayani`
  selalu paling akhir.
- Tahap 3 & 4 (GUI): compile berhasil, kode mengikuti kerangka
  serta gambar di soal (ukuran 400x450, komponen, tombol, validasi,
  thread cetak struk), DAN sudah dijalankan Agis di NetBeans di
  laptopnya — lihat screenshot di atas: jendela tampil lengkap dan
  console-nya cocok. Logika di balik GUI (Pesanan dan Kasir) sudah
  teruji seperti di atas.

## Catatan untuk laporan

- Tangkapan layar untuk laporan: screenshot Agis
  (`hasil/Screenshot_Run_NetBeans.png`) sudah bisa dipakai untuk
  Tahap 1, Tahap 2, dan tampilan awal GUI. Kalau mau lengkap, tambah
  dua screenshot lagi: (1) jendela GUI setelah ketiga menu ditambahkan
  dan Hitung Total diklik (label menunjukkan Rp 63.000), dan
  (2) jendela GUI saat Cetak Struk ditekan (label status
  `Mencetak struk...`, lalu `Struk selesai.`).
- Pengamatan Tahap 2 untuk laporan: urutan output Dina/Eko tidak
  selalu sama (buktinya run 1 dan run 2 di folder `hasil/`).
