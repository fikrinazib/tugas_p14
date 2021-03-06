import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class transaksi implements JDBC {
    public String Nama_Barang;
    public Integer No_Faktur;
    public Integer Harga_Barang;
    public Integer Jumlah;
    public Integer SubTotal;
    public Integer Discount;
    public Integer Total_Harga;
   
    Scanner input = new Scanner (System.in);

    public void display(){
        System.out.println("Data Transaksi");

        String sql ="SELECT * FROM transaksi";
        conn = DriverManager.getConnection(url,"root","");
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);

        while(result.next()){
            System.out.print("\nNo Faktur\t  : ");
            System.out.print(result.getInt("No_Faktur"));
            System.out.print("\nNama Barang\t  : ");
            System.out.print(result.getString("Nama_Barang"));
            System.out.print("\nHarga Barang\t\t  : ");
            System.out.print(result.getInt("Harga_Barang"));
            System.out.print("\nJumlah : ");
            System.out.print(result.getInt("Jumlah"));
            System.out.print("\nSubTotal : ");
            System.out.print(result.getInt("SubTotal"));
            System.out.print("\nDiscount : ");
            System.out.print(result.getInt("Disc"));
            System.out.print("\nTotal Harga\t  : ");
            System.out.print(result.getInt("Total_Harga"));
            System.out.print("\n");  
        }
   }

   public void insert()throws SQLException {
     System.out.println("Masukkan Data Transaksi");
    try{
        //NoFaktur   
        System.out.println("Inputkan No Faktur : ");
        No_Faktur = input.nextInt();

        //NamaBarang
        System.out.println("Inputkan Nama Barang : ");
        Nama_Barang = input.nextLine();

        //HargaBarang
        System.out.println("Inputkan Harga Barang : ");
        Harga_Barang = input.nextInt(); 

        //Jumlah
        System.out.println("Inputkan Jumlah : ");
        Jumlah = input.nextInt();

        //SubTotal
        SubTotal = Jumlah * Harga_Barang;

        //Discount
        if (SubTotal >= 100000){
            Discount = SubTotal * 30/100;
        }

        else if (SubTotal >= 50000){
            Discount = SubTotal * 20/100; 
        }

        else if (SubTotal >= 25000){
            Discount = SubTotal * 10/100;
        }

        else {
            Discount = 0;

        }

        //TotalHarga
        Total_Harga = SubTotal - Discount;

        String sql = "INSERT INTO transaksi (No_Faktur, Nama_Barang, Harga_Barang, Jumlah, SubTotal, Discount, Total_Harga) VALUES ('"+No_Faktur+"','"+Nama_Barang+"','"+Harga_Barang+"','"+Jumlah+"','"+SubTotal+"','"+Discount+"','"+Total_Harga+"')";
        conn = DriverManager.getConnection(url,"root","");
        Statement statement = conn.createStatement();
        statement.execute(sql);
        System.out.println("Berhasil input data!!");
    }
    catch (SQLException e) {
        System.err.println("Terjadi kesalahan input data");
    } 
    catch (InputMismatchException e) {
        System.err.println("Inputan harus berupa angka");
       }
    }

   public void update(){
    System.out.print("Ubah Data Transaksi");

    try {
        display();
        System.out.print("\nMasukkan No Faktur yang akan di ubah : ");
        Integer No_Faktur = Integer.parseInt(input.nextLine());
        
        String sql = "SELECT * FROM transaksi WHERE No_Faktur = " +No_Faktur;
        conn = DriverManager.getConnection(url,"root","");
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        
        if(result.next()){
            
            System.out.print("Nama Barang ["+result.getString("Nama_Barang")+"]\t: ");
            String NamaBarang = input.nextLine();
               
            sql = "UPDATE transaksi SET NamaBarang='"+NamaBarang+"' WHERE No_Faktur='"+No_Faktur+"'";
            //System.out.println(sql);

            if(statement.executeUpdate(sql) > 0){
                System.out.println("Berhasil memperbaharui data  (No_Faktur "+No_Faktur+")");
            }
        }
        statement.close();        
    } 
    catch (SQLException e) {
        System.err.println("Terjadi kesalahan dalam mengedit data");
        System.err.println(e.getMessage());
    }
   }

   public void search(){
    System.out.print("Cari Data Transaksi");

    System.out.print("Masukkan No Faktur yang ingin dilihat : ");    
    Integer keyword = Integer.parseInt(input.nextLine());

    String sql = "SELECT * FROM transaksi WHERE NoFaktur LIKE '%"+keyword+"%'";
    conn = DriverManager.getConnection(url,"root","");
    Statement statement = conn.createStatement();
    ResultSet result = statement.executeQuery(sql);

    while(result.next()){
        System.out.print("\nNo Faktur\t  : ");
        System.out.print(result.getInt("No_Faktur"));
        System.out.print("\nNama Barang\t  : ");
        System.out.print(result.getString("Nama_Barang"));
        System.out.print("\nHarga Barang\t\t  : ");
        System.out.print(result.getInt("Harga_Barang"));
        System.out.print("\nJumlah : ");
        System.out.print(result.getInt("Jumlah"));
        System.out.print("\nSubTotal : ");
        System.out.print(result.getInt("SubTotal"));
        System.out.print("\nDiscount : ");
        System.out.print(result.getInt("Discount"));
        System.out.print("\nTotal Harga\t  : ");
        System.out.print(result.getInt("Total_Harga"));
        System.out.print("\n");  
    }
   }

   public void delete(){
    System.out.print("Hapus Data");

    try{
        display();
        System.out.print("\nMasukan No Faktur yang akan Anda Hapus : ");
        Integer No_Faktur= Integer.parseInt(input.nextLine());
        
        String sql = "DELETE FROM transaksi WHERE No_Faktur = "+ No_Faktur;
        conn = DriverManager.getConnection(url,"root","");
        Statement statement = conn.createStatement();
        //ResultSet result = statement.executeQuery(sql);
        
        if(statement.executeUpdate(sql) > 0){
            System.out.println("Berhasil menghapus data transaksi (Nomor "+No_Faktur+")");
        }
   }
    catch(SQLException e){
        System.out.println("Terjadi kesalahan dalam menghapus data");
    }
    catch(Exception e){
        System.out.println("masukan data yang benar");
    }
  }
}
