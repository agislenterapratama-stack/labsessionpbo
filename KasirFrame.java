// Package lab.kasir: semua class ada dalam satu package ini (Tahap 1-4).
package lab.kasir;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// extends JFrame: KasirFrame adalah jendela aplikasi kasir (Tahap 3 dan 4).
// Kerangka class ini disediakan di soal; bagian TODO-nya sudah dilengkapi:
// semua komponen dibuat, diatur posisinya dengan setBounds(), lalu
// ditambahkan ke jendela. Ukuran jendela 400 x 450, sesuai kerangka.
public class KasirFrame extends JFrame {
    // Atribut private (enkapsulasi), sesuai kerangka di soal.
    private JTextField txtMenu, txtHarga, txtJumlah;
    private JTextArea areaStruk;
    private JLabel lblTotal;
    private Pesanan pesanan = new Pesanan();
    // Komponen tambahan: tombol Tambah, Hitung Total, Cetak Struk, dan label status.
    private JButton btnTambah, btnHitung, btnCetak;
    private JLabel lblStatus;

    // Constructor: menyiapkan jendela, semua komponen, posisi, dan listener tombol.
    public KasirFrame() {
        // Judul jendela.
        super("Kasir Kantin UNS");
        // Ukuran jendela 400 x 450, sesuai kerangka di soal.
        setSize(400, 450);
        // setLayout(null): posisi tiap komponen diatur MANUAL dengan setBounds().
        setLayout(null);
        // EXIT_ON_CLOSE: program BERHENTI total saat jendela ditutup (tombol X).
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Label judul di atas form, sesuai gambar di soal.
        JLabel lblJudul = new JLabel("Kasir Kantin UNS");
        lblJudul.setBounds(20, 10, 360, 25);
        lblJudul.setFont(lblJudul.getFont().deriveFont(java.awt.Font.BOLD, 16f));
        add(lblJudul);

        // Label dan field Nama Menu.
        JLabel lblMenu = new JLabel("Nama Menu :");
        lblMenu.setBounds(20, 45, 90, 25);
        add(lblMenu);
        txtMenu = new JTextField();
        txtMenu.setBounds(115, 45, 255, 25);
        add(txtMenu);

        // Label dan field Harga.
        JLabel lblHarga = new JLabel("Harga :");
        lblHarga.setBounds(20, 75, 90, 25);
        add(lblHarga);
        txtHarga = new JTextField();
        txtHarga.setBounds(115, 75, 255, 25);
        add(txtHarga);

        // Label dan field Jumlah.
        JLabel lblJumlah = new JLabel("Jumlah :");
        lblJumlah.setBounds(20, 105, 90, 25);
        add(lblJumlah);
        txtJumlah = new JTextField();
        txtJumlah.setBounds(115, 105, 255, 25);
        add(txtJumlah);

        // Tombol Tambah.
        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(20, 140, 100, 30);
        btnTambah.addActionListener(e -> {
            tambahPesanan();
        });
        add(btnTambah);

        // Tombol Hitung Total.
        btnHitung = new JButton("Hitung Total");
        btnHitung.setBounds(130, 140, 130, 30);
        btnHitung.addActionListener(e -> {
            hitungTotalBayar();
        });
        add(btnHitung);

        // Area struk (JTextArea dalam JScrollPane), sesuai gambar di soal.
        areaStruk = new JTextArea();
        // setEditable(false): area struk tidak bisa diketik langsung pengguna.
        areaStruk.setEditable(false);
        JScrollPane scrollStruk = new JScrollPane(areaStruk);
        scrollStruk.setBounds(20, 180, 360, 120);
        add(scrollStruk);

        // Label total bayar, teks awal "Total Bayar : Rp 0".
        lblTotal = new JLabel("Total Bayar : Rp 0");
        lblTotal.setBounds(20, 310, 360, 25);
        lblTotal.setFont(lblTotal.getFont().deriveFont(java.awt.Font.BOLD));
        add(lblTotal);

        // Tombol Cetak Struk (Tahap 4).
        btnCetak = new JButton("Cetak Struk");
        btnCetak.setBounds(20, 345, 130, 30);
        // Listener tombol Cetak Struk, sesuai kerangka di soal (Tahap 4):
        // tombol menjalankan thread baru dengan start(), supaya jendela
        // tidak macet selama menunggu 2 detik.
        btnCetak.addActionListener(e -> {
            new Thread(() -> {
                // TODO di soal: ubah label status menjadi tulisan mencetak.
                // setText dipanggil lewat invokeLater supaya perubahan tampilan
                // dilakukan di Event Dispatch Thread (thread khusus Swing).
                SwingUtilities.invokeLater(() -> lblStatus.setText("Mencetak struk..."));
                try {
                    // Penantian 2 detik, dilakukan di thread baru (bukan di EDT),
                    // jadi tombol Tambah dan Hitung Total masih bisa ditekan.
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                // TODO di soal: ubah label status menjadi tulisan selesai.
                SwingUtilities.invokeLater(() -> lblStatus.setText("Struk selesai."));
            }).start();
        });
        add(btnCetak);

        // Label status (Tahap 4): menampilkan "Mencetak struk..." lalu "Struk selesai."
        // Teks awal dikosongkan; label terisi setelah tombol Cetak Struk ditekan.
        lblStatus = new JLabel("");
        lblStatus.setBounds(160, 345, 210, 30);
        add(lblStatus);
    }

    // tambahPesanan: dijalankan saat tombol Tambah diklik.
    // Mengambil isi field harga dan jumlah, memanggil pesanan.tambahItem(...),
    // menulis satu baris ke area struk, lalu mengosongkan field.
    private void tambahPesanan() {
        // VALIDASI: jika ada field kosong, tampilkan pesan dengan JOptionPane.
        if (txtMenu.getText().trim().isEmpty()
                || txtHarga.getText().trim().isEmpty()
                || txtJumlah.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi.",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nama = txtMenu.getText().trim();
        double harga;
        int jumlah;
        try {
            harga = Double.parseDouble(txtHarga.getText().trim());
            jumlah = Integer.parseInt(txtJumlah.getText().trim());
        } catch (NumberFormatException e) {
            // VALIDASI: harga atau jumlah bukan angka, ditangani dengan try-catch.
            JOptionPane.showMessageDialog(this, "Harga dan jumlah harus berupa angka.",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Semua validasi lolos: masukkan ke Pesanan (logika ada di class Pesanan).
        pesanan.tambahItem(harga, jumlah);
        // Menulis satu baris ke area struk, misalnya "Nasi Goreng x 2 @ Rp 15.000".
        areaStruk.append(nama + " x " + jumlah + " @ Rp " + formatRupiah(harga) + "\n");
        // Mengosongkan field supaya siap untuk menu berikutnya.
        txtMenu.setText("");
        txtHarga.setText("");
        txtJumlah.setText("");
    }

    // hitungTotalBayar: dijalankan saat tombol Hitung Total diklik.
    // Menampilkan hasil pesanan.hitungTotal() pada label total.
    private void hitungTotalBayar() {
        lblTotal.setText("Total Bayar : Rp " + formatRupiah(pesanan.hitungTotal()));
    }

    // formatRupiah: mengubah bilangan menjadi teks format Indonesia.
    // Contoh: 15000 menjadi "15.000", 63000 menjadi "63.000".
    private static String formatRupiah(double nilai) {
        return NumberFormat.getInstance(new Locale("id", "ID")).format(nilai);
    }
}
