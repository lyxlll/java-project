import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.*;
public class Test {
    public static void main(String args[]) {


        try
        {
            URL url = new URL("http://quotes.wsj.com/AAPL/historical-prices/download?MOD_VIEW=page&num_rows=300&startDate=3/4/2018&endDate=3/5/2018");
            URLConnection urlConnection = url.openConnection();
            URLConnection connection = null;
            if(urlConnection instanceof URLConnection)
            {
                connection =  urlConnection;
                String redirect = connection.getHeaderField("Location");
                if (redirect != null){
                    connection = new URL(redirect).openConnection();
                }
            }
            else
            {
                System.out.println("请输入 URL 地址");
                return;
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String urlString = "";
            String current;
            while((current = in.readLine()) != null)
            {
                String[] s = current.split(",");
                List temp = Arrays.asList(current.split(","));
                for (int i = 0; i<4 ;i++)
                {
                   s[i] = s[i].substring(0,s[i].length()-1);
                }
                urlString += current;
                System.out.println(current);
                System.out.println(temp);
                System.out.print(s[0]+s[1]+s[2]+s[3]);
            }
            System.out.println(urlString);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}