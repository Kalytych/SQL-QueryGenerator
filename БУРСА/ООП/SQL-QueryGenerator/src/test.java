import com.google.gson.Gson;

public class test {

  public static void main(String[] args) {
    Gson gson = new Gson();
    String json = gson.toJson("Hello, Gson!");
    System.out.println(json); // Перевірте, чи виводиться "Hello, Gson!"
  }
}
