// Package lab.kasir: semua class ada dalam satu package ini (Tahap 1-4).
package lab.kasir;

// extends Thread: Kasir adalah sebuah thread, jadi bisa berjalan sendiri
// dengan start(). Kerangka class ini disediakan di soal (Tahap 2),
// bagian TODO-nya sudah dilengkapi di method run() di bawah.
class Kasir extends Thread {
    // Atribut private (enkapsulasi): nama kasir, misalnya "Dina".
    private String nama;

    // Constructor: mengisi nama kasir saat objek dibuat.
    // Ditulis tanpa "public" (package-private), sama seperti kerangka di soal.
    Kasir(String nama) {
        this.nama = nama;
    }

    // Getter: membaca nama kasir.
    public String getNama() {
        return nama;
    }

    // Override run(): pekerjaan thread ini, yaitu melayani 5 pelanggan
    // satu per satu. TODO di soal: cetak nama kasir dan nomor pelanggan (i).
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            // Mencetak kalimat seperti "Kasir Dina melayani pelanggan ke-1".
            System.out.println("Kasir " + nama + " melayani pelanggan ke-" + i);
            try {
                // Jeda 500 ms sebelum melayani pelanggan berikutnya.
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
