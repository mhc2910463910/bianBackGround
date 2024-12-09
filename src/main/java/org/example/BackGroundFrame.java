package org.example;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
class BackGroundFrame extends JFrame {
    static ArrayList<MyButton> BackGroundBut=new ArrayList<>();
    static ArrayList<MyImageBut> BackGroundImg=new ArrayList<>();
    private final int WIDTH=1920;
    private final int HEIGHT=1080;
    private JMenuBar MenuBar;
    private JPanel mainPanel;
    private JPanel list;
    private final JPanel RightList=new JPanel();
    JPanel panelImgs=new JPanel();
    private final JScrollPane ImageBg=new JScrollPane(panelImgs);
    private String Url="http://www.netbian.com/";
    private String img_url="https://pic.netbian.com/uploads/allimg/220802/231950-165945359015c6.jpg";
    private MyImageBut downloadBut=new MyImageBut("下载","https://pic.netbian.com/uploads/allimg/220802/231950-165945359015c6.jpg");
    private final JPanel Text=new JPanel();
    private MyImageBut upBut;
    private MyImageBut downloadbut;
    private MyImageBut downBut;
    private JTextArea textArea;
    private int rows=1;
//    private JTextArea textArea2;
    private Thread thread;
    private int count=1;
    BackGroundSpider spider=new BackGroundSpider();
    {
        try {
            spider.Play(BackGroundSpider.url);
            spider.SearchUrl(BackGroundSpider.content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<28;i++){
            BackGroundImg.add(new MyImageBut("空....","null"));
        }
    }
    BackGroundFrame() throws MalformedURLException {
        this.setBounds(0,0,WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("彼岸桌面壁纸下载");
        this.setLayout(new BorderLayout());
        this.BuildMenu();
        this.BuildMainPanel();
        this.BuildList();
        this.BuildRightList();
//        this.BuildImageBg();
        this.BuildTextPanel();
//        this.BuildImageBg();
        ImageIcon icon=new ImageIcon("http://img.netbian.com/file/2023/0817/112202SWbdj.jpg");
        this.setIconImage(icon.getImage());
        textArea.setText("");

        textArea.append("彼岸桌面壁纸下载！\n");

        this.setVisible(true);
    }

    public void BuildMainPanel(){
        this.mainPanel=new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        //this.mainPanel.setBounds(0,0,720,405);
        mainPanel.setBackground(Color.BLACK);
        this.add(mainPanel,BorderLayout.CENTER);
    }
    public void BuildList(){
        list=new JPanel();
//        list.setBounds(0,0,200,1080);
        list.setBackground(Color.white);
//        list.setSize();
        list.setLayout(new GridLayout(15,2));
        for(MyButton s:BackGroundBut){
            s.setBackground(Color.white);
            s.setForeground(Color.black);
            s.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    thread =new Thread(new Runnable(){
                        @Override
                        public void run() {
                            for(MyButton ss : BackGroundBut){
                                ss.setBackground(Color.white);
                                ss.setForeground(Color.black);
                            }
                            s.setBackground(Color.BLACK);
                            s.setForeground(Color.WHITE);
                            BackGroundSpider.url=s.getUrl();
                            if(BackGroundImg.size()!=0){
                                BackGroundImg.removeAll(BackGroundImg);
                            }
                            count=1;
                            Url=s.getUrl();
                            try {
                                spider.Play(s.getUrl());
                                spider.searchHref(BackGroundSpider.content);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
//                            BuildRightList();
//                            BuildList();
                            BuildImageBg();
                            clearTextArea();
                            textArea.append("搜索完成:"+s.getUrl()+"\n");
                        }
                    });
                    clearTextArea();
                    textArea.append("正在搜索: "+s.getText()+" 类壁纸"+"\n");
                    thread.start();
                }
            });
//            MouseAdapter
            s.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("点击按钮");
                    int i=e.getButton();
                    switch(i){
                        case MouseEvent.BUTTON1:
                            System.out.println("左键点击");
                            break;
                        case MouseEvent.BUTTON2:
                            System.out.println("滚轮点击");
                            break;
                        case MouseEvent.BUTTON3:
                            System.out.println("鼠标右键点击");
                            break;
                    }
                }//点击触发

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("按下按钮");
                    int i=e.getButton();
                    switch(i){
                        case MouseEvent.BUTTON1:
                            System.out.println("左键按下");
                            break;
                        case MouseEvent.BUTTON2:
                            System.out.println("滚轮按下");
                            break;
                        case MouseEvent.BUTTON3:
                            System.out.println("鼠标右键按下");
                            break;
                    }
                    int clickcount=e.getClickCount();
                    System.out.println("点击了"+clickcount+"次");
                }//按下触发

                @Override
                public void mouseReleased(MouseEvent e) {
                    System.out.println("释放按钮");
                }//释放时触发

                @Override
                public void mouseEntered(MouseEvent e) {
                    System.out.println("移入组件触发");
                }//移入组件触发

                @Override
                public void mouseExited(MouseEvent e) {
                    System.out.println("移出按钮");
                }//移出组件触发
            });
            list.add(s);
        }
        this.add(list,BorderLayout.WEST);
    }
    void BuildImageBg()  {
        panelImgs.removeAll();
        repaint();
        panelImgs.setLayout(new GridLayout(14,2));
//        panelImgs.setSize(1080,2000);
        panelImgs.setPreferredSize(new Dimension(1080,4270));
        for(int i=0;i<BackGroundImg.size();i++){
            URL ImgUrl = null;
            try{
                ImgUrl=new URL(BackGroundImg.get(i).url);
                System.out.println(BackGroundImg.get(i).getUrl());
            }catch(MalformedURLException e){
                e.printStackTrace();
            }
            ImageIcon bg=new ImageIcon(ImgUrl);
            bg.setImage(bg.getImage().getScaledInstance(540,305,Image.SCALE_DEFAULT));
            JLabel label=new JLabel(bg);
            label.setSize(540,305);
            int finalI = i;
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clearTextArea();
                    textArea.append(BackGroundImg.get(finalI).getText()+"\n");
                    downloadBut=BackGroundImg.get(finalI);
                    img_url=BackGroundImg.get(finalI).getUrl();
                }
                @Override
                public void mousePressed(MouseEvent e) {

                }
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            label.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            DownLoadImage();
                        } catch (BadLocationException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
            panelImgs.add(label);
        }
        //2.把窗口面板设为内容面板并设为透明、流动布局。
        ImageBg.setOpaque(false);
        ImageBg.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        ImageBg.getVerticalScrollBar().setUnitIncrement(20);
//        ImageBg.setLayout(new GridLayout(3,3));
//        ImageBg.add(label,new Integer(Integer.MIN_VALUE));
        //3.之后把组件和面板添加到窗口面板就可以；
        this.setSize(960,960);
        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        ImageBg.updateUI();
        this.mainPanel.add(ImageBg,BorderLayout.CENTER);
    }
    void BuildTextPanel(){
        this.Text.setLayout(new GridLayout(1,3));
        this.Text.setLayout(new BorderLayout());
        this.Text.setBackground(Color.white);
         downloadbut=new MyImageBut("下载",img_url);

         upBut=new MyImageBut("上一页",img_url);
         downBut=new MyImageBut("下一页",img_url);
         textArea=new JTextArea(4,1);
//         textArea2=new JTextArea(6,1);
        textArea.setBackground(Color.black);
//        textArea2.setBackground(Color.white);
        textArea.setFont(new Font("宋体",Font.ITALIC,15));
//        textArea2.setFont(new Font("宋体",Font.ITALIC,20));
        textArea.setForeground(Color.white);
//        textArea2.setForeground(Color.black);
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }//击健触发

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("你按下了"+e.getKeyChar());
                System.out.println("你按下了"+e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    System.out.println("control键");
                }
                System.out.println("你按下了"+ KeyEvent.getKeyText(e.getKeyCode()));


            }//按下键盘触发

            @Override
            public void keyReleased(KeyEvent e) {

            }//释放时触发
            //getSource()
            //isActionKey()
            //isControlDown()
            //isAltDown()
            //isShiftDown()
        });
        this.Text.add(textArea,BorderLayout.CENTER);
//        this.Text.add(textArea2,BorderLayout.WEST);
        JPanel texts=new JPanel();
        texts.setLayout(new GridLayout(1,3));

        texts.add(upBut);
        texts.add(downloadbut);
        texts.add(downBut);
        downloadbut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextArea();
                textArea.append("开始下载:"+downloadBut.getText()+"\n");
                thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DownLoadImage();
                        } catch (BadLocationException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                thread.start();
            }
        });
        upBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(count>2) {
                    Url = Url.replaceAll("index_" + count + ".htm", "index_" + (--count) + ".htm");
                }else {
                    Url =Url.replaceAll("index_" + count + ".htm","");
                    count--;
                    if(count<=0){
                        count=1;
                    }
                }
                clearTextArea();
                textArea.append("正在搜索上一页\n");
//                clearTextArea();
                    if(BackGroundImg.size()!=0){
                        BackGroundImg.removeAll(BackGroundImg);
                    }
                    thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            textArea.setText("");
                            try {
                                spider.Play(Url);
                                spider.searchHref(BackGroundSpider.content);
//                                BuildRightList();
//                                rows++;
                                BuildImageBg();
                                clearTextArea();
                                textArea.append("搜索完成: "+Url+"\n");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    });
                    thread.start();
//                    textArea.append("搜索完成"+"\n");
                }
        });
        downBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("下一页");
                if(count<2) {
                    count++;
                    Url=Url+"/index_"+count+".htm";
                }else {
                    Url =Url.replaceAll("index_" + count + ".htm","index_"+(++count)+".htm");
                }
                clearTextArea();
                textArea.append("正在搜索下一页"+"\n");
//                clearTextArea();
                if(BackGroundImg.size()!=0){
                    BackGroundImg.removeAll(BackGroundImg);
                }
                thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        textArea.setText("");
                        try {
                            spider.Play(Url);
                            spider.searchHref(BackGroundSpider.content);
//                            BuildRightList();
                            BuildImageBg();
                            clearTextArea();
                            textArea.append("搜索完成: "+Url+"\n");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                thread.start();
//                textArea.append("搜索完成"+"\n");
            }
        });
        this.Text.add(texts,BorderLayout.SOUTH);
        this.mainPanel.add(this.Text,BorderLayout.SOUTH);
//        this.mainPanel.add(this.Text,BorderLayout.SOUTH);
    }
    void BuildRightList(){
//        this.RightList=new JPanel();
        this.RightList.removeAll();
        this.RightList.repaint();
        RightList.setBackground(Color.white);
        RightList.setBounds(1720,0,200,1080);
        RightList.setLayout(new GridLayout(15,2));
        for(MyImageBut s:BackGroundImg){
            JButton but=new JButton(s.getText().substring(0,5));
            but.setBackground(Color.white);
//            s.setSize(65,15);
            but.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    clearTextArea();
                    textArea.append("正在搜索: "+s.getText()+"\n");
//                    clearTextArea();
                    thread=new Thread(new Runnable() {
                        @Override
                        public void run() {

                            but.setBackground(Color.BLACK);
                            but.setForeground(Color.WHITE);
                            downloadBut=s;
                            img_url=s.getUrl();
                            BuildImageBg();
                            clearTextArea();
                            textArea.append("搜索成功: "+s.getUrl()+"\n");
//                    BuildTextPanel();
                            repaint();
                        }
                    });
                    thread.start();

                }
            });
            RightList.add(but);
        }
        this.RightList.updateUI();
        this.RightList.repaint();
//        this.add(RightList,BorderLayout.EAST);
    }
    public void BuildMenu(){
        this.MenuBar=new JMenuBar();
        this.setJMenuBar(MenuBar);
        JMenu File=new JMenu("文件");
        JMenu Edit=new JMenu("查看");
//        JMenu Set=new JMenu("设置");
        JMenuItem download=new JMenuItem("下载此图片");
        download.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                clearTextArea();
//                rows++;
                clearTextArea();
                textArea.append("正在下载: "+downloadBut.getText()+"\n");
                thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println("下载");
                        try {
                            DownLoadImage();
                        } catch (BadLocationException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                thread.start();
            }
        });
        JMenuItem upBg=new JMenuItem("上一页");
        JMenuItem downBg=new JMenuItem("下一页");
        File.add(download);
        Edit.add(upBg);
        Edit.add(downBg);
        MenuBar.add(File);
        MenuBar.add(Edit);
    }
    void DownLoadImage() throws BadLocationException {
            try{
                File file=new File("src/main/resources/Image");
                if(file.exists()){
                    System.out.println("文件夹已存在");
                }else{
                }
            }catch(SecurityException err){
                err.printStackTrace();
            }
//               / 构造URL
            URL url = null;
            try {
                url = new URL(img_url);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
            // 打开连接
            URLConnection con = null;
            try {
                con = url.openConnection();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            // 输入流
            InputStream is = null;
            try {
                is = con.getInputStream();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            String filename = "src/main/resources/Image/" + downloadBut.getText() + ".jpg";  //下载路径及下载图片名称
            File file = new File(filename);
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(file, true);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            // 开始读取
            while (true) {
                try {
                    if ((len = is.read(bs)) == -1) break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    os.write(bs, 0, len);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            clearTextArea();
            textArea.append("下载完成!\n");
        // 完毕，关闭所有链接
            try {
                os.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                is.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        void clearTextArea(){
            try{
                rows++;
                if(rows>4){
                    int end = textArea.getLineEndOffset(0);
                    textArea.replaceRange("", 0, end);
                    rows--;
                }
            }catch(Exception err){
                err.printStackTrace();
            }
        }
}
class MyButton extends JButton{
    private String text;
    private String url;
//    private JButton but;
    public MyButton(String text, String url) {
        this.text = text;
        this.url=url;
//        but=new JButton(text);
    }
    @Override
    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    @Override
    public String getText() {
        return text;
    }

}
class MyImageBut extends JButton{
    String text;
    String url;
    MyImageBut(String text,String url){
        this.text=text;
        this.url=url;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}




