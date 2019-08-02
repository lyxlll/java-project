import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class DrawPanel extends JPanel {
    String targeturl;
    List<List<String>> all;
    List<String[]> allString;
    List<String> points;
    List<String> day;
    List<Double> closing;
    List<Double> open;
    List<Double> high;
    List<Double> low;
    List<Double> volume;
    int index = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    List<Long> longtime;

    public DrawPanel(){

        setBackground(Color.lightGray);

    }


    //getter for every list of information
    public List<String> getDay() {
        return day;
    }

    public List<Double> getClosing() {
        return closing;
    }

    public List<Double> getOpen() {
        return open;
    }

    public List<Double> getHigh() {
        return high;
    }

    public List<Double> getLow() {
        return low;
    }

    public List<Double> getVolume() {
        return volume;
    }


    public void paint(Graphics g) {

        longtime = new ArrayList<>();
        for (String d: day){
            try {
                Date date = sdf.parse(d);
                long longdate = date.getTime();
                longtime.add(longdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(longtime);
        super.paint(g);
        int sleft = this.getX() + 15;
        int stop = this.getY() + 15;
        int sright = sleft + this.getWidth() -15;
        int sbottom = stop + this.getHeight() - 40;

        int yaxisStart = sbottom -25;
        int yaxisEnd = stop;
        int xaxisStart = sleft + 40;
        int xaxisEnd = sright;
        double yscale = (yaxisEnd - yaxisStart) /200.0;


        g.setColor(Color.BLACK);
        g.drawLine(xaxisStart,yaxisEnd,xaxisStart,yaxisStart);
        g.drawLine(xaxisStart,yaxisStart,xaxisEnd,yaxisStart);
        int tickInt = 60;
        int count=0;

        //if user choose some day has no data there will have "No Data" on the x axis scale
        if (!day.isEmpty()){
        //draw x axis scale
        for (int xt = xaxisStart +tickInt; xt < xaxisEnd; xt += tickInt) {

            g.drawLine(xt, yaxisStart + 5, xt, yaxisStart - 5);
            g.drawString(day.get(count), xt - 7 , yaxisStart + 20);
            count++;
            if (count== day.size())
                break;
        }}
        else {
            for (int xt = xaxisStart +tickInt; xt < xaxisEnd; xt += tickInt) {

                g.drawLine(xt, yaxisStart + 5, xt, yaxisStart - 5);
                g.drawString("No Data", xt - 7 , yaxisStart + 20);

            }
        }

        //draw y axis Scale

        for (int vt = 200; vt > 0; vt -= 10) {
            int y = yaxisStart + (int)(vt * yscale);
            g.drawLine(xaxisStart - 5, y, xaxisStart + 5, y);
            g.drawString(Integer.toString(vt), xaxisStart - 38 , y + 5);
        }

        //draw different color lines
        // Because they really close to each other, I only display the number of closing and volume on the line chart,
        //other numbers are displayed in the text area in tht bottom.
        g.setColor(Color.red);
        int xn = -1;
        int yn = -1;
        index = day.size();
        for (int i = 0; i < index; i++) {

            int x = xaxisStart + tickInt*(i+1);
            int y = yaxisStart + (int)(closing.get(i) * yscale);
            if (xn > 0) {
                g.drawLine(xn, yn, x, y);
            }
            g.drawString(closing.get(i).toString(),x,y);
            xn = x;
            yn = y;
        }

        xn = -1;
        yn = -1;
        g.setColor(Color.magenta);
        for (int i = 0; i < index; i++) {

            int x = xaxisStart + tickInt*(i+1);
            int y = yaxisStart + (int)(high.get(i) * yscale);
            if (xn > 0) {
                g.drawLine(xn, yn, x, y);
            }
            xn = x;
            yn = y;
        }
        xn = -1;
        yn = -1;
        g.setColor(Color.blue);
        for (int i = 0; i < index; i++) {

            int x = xaxisStart + tickInt*(i+1);
            int y = yaxisStart + (int)(low.get(i) * yscale);
            if (xn > 0) {
                g.drawLine(xn, yn, x, y);
            }

            xn = x;
            yn = y;
        }
        xn = -1;
        yn = -1;
        g.setColor(Color.yellow);
        for (int i = 0; i < index; i++) {

            int x = xaxisStart + tickInt*(i+1);
            int y = yaxisStart + (int)(open.get(i) * yscale);
            if (xn > 0) {
                g.drawLine(xn, yn, x, y);
            }
            xn = x;
            yn = y;
        }
        xn = -1;
        yn = -1;
        g.setColor(Color.green);
        for (int i = 0; i < index; i++) {

            int x = xaxisStart + tickInt*(i+1);
            int y = yaxisStart + (int)((volume.get(i)/1000000) * yscale);
            if (xn > 0) {
                g.drawLine(xn, yn, x, y);
            }
            g.drawString(volume.get(i).toString(),x,y);
            xn = x;
            yn = y;
        }
    }


    //Get information from the target url.

    public void readFromUrl(String targeturl){
        this.targeturl = targeturl;


        //The url return me 301 Moved Permanently, than I searched on google and fond the redirect solution, and it worked.
        try
        {
            URL url = new URL(targeturl);
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
                System.out.println("Error URL");
                return;
            }
            BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));

            //Distribute different information to different lists.
            String current;
            all = new ArrayList<>();
            allString = new ArrayList<>();
            points = new ArrayList<String>();
            day = new ArrayList<>();
            closing = new ArrayList<>();
            open = new ArrayList<>();
            high = new ArrayList<>();
            low = new ArrayList<>();
            volume = new ArrayList<>();
            while((current = in.readLine()) != null)
            {
                String[] s = current.split(",");
                List<String> ssss = new ArrayList<>();
                for(String ss : s){
                    ssss.add(ss);
                }
                all.add(ssss);
                allString.add(s);
                System.out.println(current);
            }
            all.remove(0);
            for(List<String> a : all){

                day.add(a.get(0));
                open.add(Double.parseDouble(a.get(1)));
                high.add(Double.parseDouble(a.get(2)));
                low.add(Double.parseDouble(a.get(3)));
                closing.add(Double.parseDouble(a.get(4)));
                volume.add(Double.parseDouble(a.get(5)));

            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
