// Package lab.kasir: semua class ada dalam satu package ini (Tahap 1-4).
package lab.kasir;

import javax.swing.SwingUtilities;

// MainKasir adalah titik awal aplikasi Kasir Kantin UNS.
// Class ini menjalankan Tahap 1 (uji logika Pesanan), Tahap 2
// (simulasi dua kasir), lalu membuka jendela GUI (Tahap 3 dan 4).
// Jalankan dengan: java lab.kasir.MainKasir
public class MainKasir {
    public static void main(String[] args) throws InterruptedException {
        // ==================== TAHAP 1: Class Logika ====================
        System.out.println("=== Tahap 1: Class Logika ===");
        Pesanan pesanan = new Pesanan();
        // Data uji dari soal: Nasi Goreng 15.000 x 2.
        pesanan.tambahItem(15000, 2);
        // Data uji dari soal: Es Teh 5.000 x 3.
        pesanan.tambahItem(5000, 3);
        // Setelah dua menu pertama: subtotal 45000, total 45000 (tanpa diskon).
        System.out.println("Setelah 2 menu: subtotal = " + pesanan.getSubtotal()
                + ", total = " + pesanan.hitungTotal());
        // Data uji dari soal: Ayam Bakar 25.000 x 1.
        pesanan.tambahItem(25000, 1);
        // Setelah menu ketiga: subtotal 70000, total 63000 (diskon 10%).
        System.out.println("Setelah 3 menu: subtotal = " + pesanan.getSubtotal()
                + ", total = " + pesanan.hitungTotal());
        // Menguji reset(): subtotal kembali ke 0.
        pesanan.reset();
        System.out.println("Setelah reset: subtotal = " + pesanan.getSubtotal());
        System.out.println();

        // ==================== TAHAP 2: Dua Kasir sebagai Thread ====================
        System.out.println("=== Tahap 2: Dua Kasir sebagai Thread ===");
        // Membuat dua kasir: Dina dan Eko.
        Kasir dina = new Kasir("Dina");
        Kasir eko = new Kasir("Eko");
        // Menjalankan keduanya dengan start(): masing-masing berjalan di thread baru.
        dina.start();
        eko.start();
        // join(): main menunggu kedua kasir selesai sebelum lanjut.
        dina.join();
        eko.join();
        // Kalimat ini selalu muncul PALING AKHIR, setelah kedua kasir selesai.
        System.out.println("Semua pelanggan selesai dilayani");
        System.out.println();

        // ==================== TAHAP 3 & 4: Tampilan GUI Kasir ====================
        // Membuka jendela GUI. invokeLater menjalankan pembuatan jendela di
        // Event Dispatch Thread (EDT), thread khusus Swing yang menangani
        // semua tampilan dan klik, supaya jendela tampil dengan benar dan aman.
        SwingUtilities.invokeLater(() -> {
            KasirFrame frame = new KasirFrame();
            frame.setVisible(true);
        });
    }
}
