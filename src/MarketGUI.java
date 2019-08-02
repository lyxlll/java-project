import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MarketGUI extends JFrame implements ActionListener, ComponentListener {



    private DrawPanel dp;
    private JComboBox tickersymbol,dayPickerS, monthPickerS, yearPickerS,dayPickerE, monthPickerE, yearPickerE;

    public MarketGUI(){
        int monthDefault = 12;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.setBounds((int) width/4, (int) height/4, (int) width/2, (int) height/2);

        Container contentPanel = this.getContentPane();


        JPanel columnOfDatePicker = new JPanel(new GridLayout(10,2));

        JTextArea TickerSymbol = new JTextArea("Choose Ticker Symbol: ");
        TickerSymbol.setEditable(false);
        columnOfDatePicker.add(TickerSymbol);

        //choose ticker symbol.
        tickersymbol = new JComboBox<String>();
        tickersymbol.addItem("AAPL");
        tickersymbol.addItem("MSFT");
        columnOfDatePicker.add(tickersymbol);


        JTextArea startDate = new JTextArea("Start From: ");
        startDate.setEditable(false);
        columnOfDatePicker.add(startDate);

        JTextArea blank = new JTextArea("123");
        blank.setVisible(false);
        blank.setEditable(false);
        columnOfDatePicker.add(blank);

        JTextArea monthS = new JTextArea("Month: ");
        monthS.setEditable(false);
        columnOfDatePicker.add(monthS);

        dayPickerS = new JComboBox<Integer>();
        dayPickerS.addItem("Choose Month First");

        monthPickerS = new JComboBox<Integer>();
        for (int i = 1; i<=monthDefault; i++){
            monthPickerS.addItem(i);
        }

        //different month has different amount of days so I add a listener on month picker to change
        //day picker.

        monthPickerS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                if ((int)comboBox.getSelectedItem()%2==0)
                {
                    if( (int)comboBox.getSelectedItem()==2)
                    {
                        dayPickerS.removeAllItems();
                        for (int i =1; i<=28; i++)
                            dayPickerS.addItem(i);
                    }
                    else {
                        dayPickerS.removeAllItems();
                        for (int i =1; i<=30; i++)
                            dayPickerS.addItem(i);

                    }
                }
                else{
                    dayPickerS.removeAllItems();
                    for (int i =1; i<=31; i++)
                        dayPickerS.addItem(i);
                }
            }
        });
        columnOfDatePicker.add(monthPickerS);

        JTextArea dayS = new JTextArea("Day: ");
        System.out.println(monthPickerS.getSelectedItem());
        dayS.setEditable(false);
        columnOfDatePicker.add(dayS);

        columnOfDatePicker.add(dayPickerS);

        JTextArea yearS = new JTextArea("Year: ");
        yearS.setEditable(false);
        columnOfDatePicker.add(yearS);

        Calendar calendar = Calendar.getInstance();
        int yearnow = calendar.get(Calendar.YEAR);

        yearPickerS = new JComboBox<Integer>();
        for (int i = yearnow; i>=1990; i--){
            yearPickerS.addItem(i);
        }
        columnOfDatePicker.add(yearPickerS);

        JTextArea to = new JTextArea("To: ");
        to.setEditable(false);
        columnOfDatePicker.add(to);

        JTextArea blank9 = new JTextArea(" 222");
        blank9.setEditable(false);
        blank9.setVisible(false);
        columnOfDatePicker.add(blank9);

        JTextArea monthE = new JTextArea("Month: ");
        monthE.setEditable(false);
        columnOfDatePicker.add(monthE);

        dayPickerE = new JComboBox<Integer>();
        dayPickerE.addItem("Choose Month First");

        monthPickerE = new JComboBox<Integer>();
        for (int i = 1; i<=monthDefault; i++){
            monthPickerE.addItem(i);
        }
        monthPickerE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                if ((int)comboBox.getSelectedItem()%2==0)
                {
                    if( (int)comboBox.getSelectedItem()==2)
                    {
                        dayPickerE.removeAllItems();
                        for (int i =1; i<=28; i++)
                            dayPickerE.addItem(i);
                    }
                    else {
                        dayPickerE.removeAllItems();
                        for (int i =1; i<=30; i++)
                            dayPickerE.addItem(i);

                    }
                }
                else{
                    dayPickerE.removeAllItems();
                    for (int i =1; i<=31; i++)
                        dayPickerE.addItem(i);
                }
            }
        });
        columnOfDatePicker.add(monthPickerE);

        JTextArea dayE = new JTextArea("Day: ");
        dayE.setEditable(false);
        columnOfDatePicker.add(dayE);

        columnOfDatePicker.add(dayPickerE);


        JTextArea yearE = new JTextArea("Year: ");
        yearE.setEditable(false);
        columnOfDatePicker.add(yearE);

        yearPickerE = new JComboBox<Integer>();
        for (int i = yearnow; i>=1990; i--){
            yearPickerE.addItem(i);
        }
        columnOfDatePicker.add(yearPickerE);

        JTextArea blank2 = new JTextArea(" 222");
        blank2.setEditable(false);
        blank2.setVisible(false);
        columnOfDatePicker.add(blank2);

        JButton btn_start= new JButton("Search");
        btn_start.addActionListener(this);
        columnOfDatePicker.add(btn_start);


        columnOfDatePicker.setBackground(Color.white);
        contentPanel.add(columnOfDatePicker, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frm = new MarketGUI();
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        if (command.equals("Search")){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentTime = sdf.format(date);

            String starttime = yearPickerS.getSelectedItem()+"-"+monthPickerS.getSelectedItem()+"-"+dayPickerS.getSelectedItem();
            String endtime =   yearPickerE.getSelectedItem()+"-"+monthPickerE.getSelectedItem()+"-"+dayPickerE.getSelectedItem();

            Date stime = new Date();
            try {
                stime = sdf.parse(starttime);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            Date etime = new Date();
            try {
                etime = sdf.parse(endtime);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            //if user choose two right day then software will continue working.

            if (stime.before(etime)&& currentTime.compareTo(endtime)>0&&starttime.compareTo(currentTime)<0){

                //get the target URL

            String targetUrl = "http://quotes.wsj.com/"+tickersymbol.getSelectedItem()+
                    "/"+"historical-prices/download?MOD_VIEW=page&num_rows=300&startDate="+
                    monthPickerS.getSelectedItem()+"/"+
                    dayPickerS.getSelectedItem()+"/"+
                    yearPickerS.getSelectedItem()+"&endDate="+
                    monthPickerE.getSelectedItem()+"/"+dayPickerE.getSelectedItem()+"/"+yearPickerE.getSelectedItem();

            // Appear a new window. Initialize here.
               JDialog drawingWindow = new JDialog();
               Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

               drawingWindow.setBounds(new Rectangle(
                       (int) this.getBounds().getX() + 50,
                       (int) this.getBounds().getY() + 50,
                       (int) this.getBounds().getWidth(),
                       (int) this.getBounds().getHeight()

               ));

                Container container = drawingWindow.getContentPane();

                dp = new DrawPanel();

                dp.readFromUrl(targetUrl);
                container.add(dp,BorderLayout.CENTER);

                //get all information and display in a Scroll panel with a text area.
                JTextArea info = new JTextArea();
                info.setColumns(20);
                info.setRows(4);
                info.setEditable(false);
                List<String> day = new ArrayList<>();
                day.addAll(dp.getDay());
                List<Double> closing = new ArrayList<>();
                List<Double> open = new ArrayList<>();
                List<Double> high = new ArrayList<>();
                List<Double> low = new ArrayList<>();
                List<Double> volume = new ArrayList<>();
                high.addAll(dp.getHigh());
                low.addAll(dp.getLow());
                volume.addAll(dp.getVolume());
                closing.addAll(dp.getClosing());
                open.addAll(dp.getOpen());
                for (int i = 0; i<day.size(); i++){
                    info.append(day.get(i)+ "    Close: "+closing.get(i)+"     Open: "+open.get(i)+"   High: "+high.get(i)+ "   Low: "+low.get(i)+"   Volume: "+volume.get(i)+"\n");
                }

                JScrollPane jScrollPane = new JScrollPane();
                jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                jScrollPane.setMinimumSize(new Dimension(400, 100));
                jScrollPane.setPreferredSize(new Dimension(400, 100));
                jScrollPane.setViewportView(info);


                //Because I display all information in one line chart so I give different color to different information
                //Here are some comments in the top of the window.
                JLabel hint1 = new JLabel("Red line is close price.");
                hint1.setForeground(Color.red);

                JLabel hint2 = new JLabel("Magenta line is high price.");
                hint2.setForeground(Color.magenta);

                JLabel hint3 = new JLabel("Blue line is low price.");
                hint3.setForeground(Color.blue);

                JLabel hint4 = new JLabel("Yellow line is open price.");
                hint4.setForeground(Color.yellow);

                JLabel hint5 = new JLabel("Green line is volume(* 10^6).");
                hint5.setForeground(Color.green);

                JPanel tophints = new JPanel();
                tophints.add(hint1);
                tophints.add(hint2);
                tophints.add(hint3);
                tophints.add(hint4);
                tophints.add(hint5);

                container.add(tophints,BorderLayout.NORTH);
                container.add(jScrollPane,BorderLayout.SOUTH);
               drawingWindow.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
               drawingWindow.setVisible(true);

        }
        else{

        // Three type of errors of choosing time
            if (currentTime.compareTo(endtime)<0)
            {
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"End time should before the Current time! Pick again","Error",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                if (starttime.compareTo(currentTime)>0)
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),"Start time should before the Current time! Pick again","Error",JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),"Start time should before the End time! Pick again","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        }

    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
