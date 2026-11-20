// Package lab.kasir: semua class ada dalam satu package ini (Tahap 1-4).
package lab.kasir;

// Pesanan adalah class LOGIKA, tanpa kode GUI sama sekali.
// Class ini dipakai pada Tahap 1, dan dipakai lagi oleh KasirFrame (Tahap 3).
public class Pesanan {
    // Atribut private (enkapsulasi): subtotal semua item yang sudah ditambahkan.
    private double subtotal;

    // Constructor: subtotal dimulai dari 0.
    public Pesanan() {
        subtotal = 0;
    }

    // tambahItem: menambahkan harga x jumlah ke subtotal.
    // Contoh: tambahItem(15000, 2) menambah 30000 ke subtotal.
    public void tambahItem(double harga, int jumlah) {
        subtotal += harga * jumlah;
    }

    // Getter: membaca nilai subtotal saat ini.
    public double getSubtotal() {
        return subtotal;
    }

    // hitungTotal: total bayar setelah diskon bila berlaku.
    // Aturan harga: jika subtotal LEBIH dari 50.000, diskon 10 persen
    // (total = subtotal dikurangi 10% = subtotal x 0,9).
    // Contoh: 70000 x 0,9 = 63000. Jika tidak, kembalikan subtotal apa adanya.
    public double hitungTotal() {
        if (subtotal > 50000) {
            return subtotal * 0.9;
        }
        return subtotal;
    }

    // reset: mengembalikan subtotal ke 0.
    public void reset() {
        subtotal = 0;
    }
}
