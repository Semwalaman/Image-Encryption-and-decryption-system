import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 class Image_Cryptography extends JFrame implements ActionListener
{
	JTextField t;
	JButton b1;
	JButton b2,b3;
	JLabel l1,l2;
    File f=new File("1.jpg");
	
	Image_Cryptography()
	{
		 t=new JTextField(20);
		 b1=new JButton("Encrypt");
		 b2=new JButton("Decrypt");
         b3=new JButton("Choose");
         l1=new JLabel("Enter the key");
         l2=new JLabel("   File Not Chosen");
         setLayout(new FlowLayout(5));
         add(l1);
		 add(t);
         add(b3);
         add(l2);
		 add(b1);
		 add(b2);
         
		 b1.addActionListener(this);
		 b2.addActionListener(this);
         b3.addActionListener(this);
		 
	}


  int key[] = new int[10];

   public void initialize(String k)
   {
   for( int i=9;i>=0;i--)
   {
    if(i-(10-k.length())>=0)
    {key[i]=Character.getNumericValue(k.charAt(i-(10-k.length())));}
    else
    key[i]=0;
   }
   key_generation();
   }



   int P10[] = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };
    int P8[] = { 6, 3, 7, 4, 8, 5, 10, 9 };
 
    int key1[] = new int[8];
    int key2[] = new int[8];
 
    int[] IP = { 2, 6, 3, 1, 4, 8, 5, 7 };
    int[] EP = { 4, 1, 2, 3, 2, 3, 4, 1 };
    int[] P4 = { 2, 4, 3, 1 };
    int[] IP_inv = { 4, 1, 3, 5, 7, 2, 8, 6 };
 
    int[][] S0 = { { 1, 0, 3, 2 },
                   { 3, 2, 1, 0 },
                   { 0, 2, 1, 3 },
                   { 3, 1, 3, 2 } };
    int[][] S1 = { { 0, 1, 2, 3 },
                   { 2, 0, 1, 3 },
                   { 3, 0, 1, 0 },
                   { 2, 1, 0, 3 } };
 
 
    void key_generation()                                            //KEY GENERATOR
    {
        int key_[] = new int[10];
 
        for (int i = 0; i < 10; i++) {
            key_[i] = key[P10[i] - 1];
        }
 
        int Ls[] = new int[5];
        int Rs[] = new int[5];
 
        for (int i = 0; i < 5; i++) {
            Ls[i] = key_[i];
            Rs[i] = key_[i + 5];
        }
 
        int[] Ls_1 = shift(Ls, 1);
        int[] Rs_1 = shift(Rs, 1);
 
        for (int i = 0; i < 5; i++) {
            key_[i] = Ls_1[i];
            key_[i + 5] = Rs_1[i];
        }
 
        for (int i = 0; i < 8; i++) {
            key1[i] = key_[P8[i] - 1];
        }
 
        int[] Ls_2 = shift(Ls, 2);
        int[] Rs_2 = shift(Rs, 2);
 
        for (int i = 0; i < 5; i++) {
            key_[i] = Ls_2[i];
            key_[i + 5] = Rs_2[i];
        }
 
        for (int i = 0; i < 8; i++) {
            key2[i] = key_[P8[i] - 1];
        }
 
        System.out.println("Your Key-1 :");
 
        for (int i = 0; i < 8; i++)
            System.out.print(key1[i] + " ");
 
        System.out.println();
        System.out.println("Your Key-2 :");
 
        for (int i = 0; i < 8; i++)
            System.out.print(key2[i] + " ");
    }
 

 
    int[] shift(int[] ar, int n)                                    //Shift FUNCTION circular
    {
        while (n > 0) {
            int temp = ar[0];
            for (int i = 0; i < ar.length - 1; i++) {
                ar[i] = ar[i + 1];
            }
            ar[ar.length - 1] = temp;
            n--;
        }
        return ar;
    }
 
 
    String encryption(int[] plaintext)                                     //Encrytion
    {
        String d="";
        int[] arr = new int[8];
 
        for (int i = 0; i < 8; i++) {
            arr[i] = plaintext[IP[i] - 1];
        }
        int[] arr1 = function_(arr, key1);
 
        int[] after_swap = swap(arr1, arr1.length / 2);
 
        int[] arr2 = function_(after_swap, key2);
 
        int[] ciphertext = new int[8];
 
        for (int i = 0; i < 8; i++) {
            ciphertext[i] = arr2[IP_inv[i] - 1];
               d=d+Integer.toString(ciphertext[i]);
        }
       return d;
   
    }
 
  
 
    String binary_(int val)                                        // s box refernce
    {
        if (val == 0)
            return "00";
        else if (val == 1)
            return "01";
        else if (val == 2)
            return "10";
        else
            return "11";
    }
 
    
  
 
    int[] function_(int[] ar, int[] key_)                            // operation to be done
    {
 
        int[] l = new int[4];
        int[] r = new int[4];
 
        for (int i = 0; i < 4; i++) {
            l[i] = ar[i];
            r[i] = ar[i + 4];
        }
 
        int[] ep = new int[8];
 
        for (int i = 0; i < 8; i++) {
            ep[i] = r[EP[i] - 1];
        }
 
        for (int i = 0; i < 8; i++) {
            ar[i] = key_[i] ^ ep[i];
        }
 
        int[] l_1 = new int[4];
        int[] r_1 = new int[4];
 
        for (int i = 0; i < 4; i++) {
            l_1[i] = ar[i];
            r_1[i] = ar[i + 4];
        }
 
        int row, col, val;
 
        row = Integer.parseInt("" + l_1[0] + l_1[3], 2);
        col = Integer.parseInt("" + l_1[1] + l_1[2], 2);
        val = S0[row][col];
        String str_l = binary_(val);
 
        row = Integer.parseInt("" + r_1[0] + r_1[3], 2);
        col = Integer.parseInt("" + r_1[1] + r_1[2], 2);
        val = S1[row][col];
        String str_r = binary_(val);
 
        int[] r_ = new int[4];
        for (int i = 0; i < 2; i++) {
            char c1 = str_l.charAt(i);
            char c2 = str_r.charAt(i);
            r_[i] = Character.getNumericValue(c1);
            r_[i + 2] = Character.getNumericValue(c2);
        }
        int[] r_p4 = new int[4];
        for (int i = 0; i < 4; i++) {
            r_p4[i] = r_[P4[i] - 1];
        }
 
        for (int i = 0; i < 4; i++) {
            l[i] = l[i] ^ r_p4[i];
        }
 
        int[] output = new int[8];
        for (int i = 0; i < 4; i++) {
            output[i] = l[i];
            output[i + 4] = r[i];
        }
        return output;
    }
 
   


 
    int[] swap(int[] array, int n)                                   //swap of halfs
    {
        int[] l = new int[n];
        int[] r = new int[n];
 
        for (int i = 0; i < n; i++) {
            l[i] = array[i];
            r[i] = array[i + n];
        }
 
        int[] output = new int[2 * n];
        for (int i = 0; i < n; i++) {
            output[i] = r[i];
            output[i + n] = l[i];
        }
 
        return output;
    }
 
   
 
    String decryption(int[] ar)                                    //decryption function
    {
        String s="";
        int[] arr = new int[8];
 
        for (int i = 0; i < 8; i++) {
            arr[i] = ar[IP[i] - 1];
        }
 
        int[] arr1 = function_(arr, key2);
 
        int[] after_swap = swap(arr1, arr1.length / 2);
 
        int[] arr2 = function_(after_swap, key1);
 
        int[] decrypted = new int[8];
 
        for (int i = 0; i < 8; i++) {
            decrypted[i] = arr2[IP_inv[i] - 1];
            s=s+Integer.toString(decrypted[i]);
        }
 
        return s;
    }
 
 




    int c=0;

	
	public void actionPerformed(ActionEvent e)                                      //Action Listener function defined
	{
		
		String s=e.getActionCommand();
		int r=0;
		int k=0;
		k=Integer.parseInt(t.getText());
		


		if(s.equals("Encrypt"))                 //encryption calling
		{ if(c==0)
           { 
            JOptionPane.showMessageDialog(null,"Select File First");
            return;
        }
            c=0;
            System.out.println(k);
        initialize(Integer.toBinaryString(k));
       
	 	System.out.println("Encryption to process");
           	/* JFileChooser jf=new JFileChooser();
            jf.showOpenDialog(null);
          //  jf.setLocationRelativeTo(null);
            File f=jf.getSelectedFile();*/
            try{
            FileInputStream fis=new FileInputStream(f);
            byte data[]=new byte[fis.available()];
            fis.read(data);
            String j="";
            int o=0;
            int a[]=new int[8];
            for(byte b:data)
            {
                String l=Integer.toBinaryString(b);
              for( int i=7;i>=0;i--)
              {
               if(i-(8-l.length())>=0)
               {a[i]=Character.getNumericValue(l.charAt(i-(8-l.length())));}
               else
               a[i]=0;
              }
                j=encryption(a);
                data[o]=(byte)(Integer.parseInt(j,2));
                System.out.println(data[o]);
                o++;
            }	
            FileOutputStream fos=new FileOutputStream(f);
            fos.write(data);
            fos.close();
            fis.close();
            l2.setText("              Select File   ");

            JOptionPane.showMessageDialog(null,"Done");	

		}
    catch(Exception u)
    {System.out.println(u);}
    }


    else if(s.equals("Choose"))                //file selection
    { 
        c=1;
        System.out.println("Hello");
        JFileChooser jf=new JFileChooser();
       
        jf.showOpenDialog(null);
      //  jf.setLocationRelativeTo(null);
         f=jf.getSelectedFile();
         l2.setText(f.getName());
    }


		else                       //decryption calling
		{
            if(c==0)
           { JOptionPane.showMessageDialog(null,"Select File First");
            return;}
            c=0;
			System.out.println("Decryption to process");
			System.out.println(k);
        initialize(Integer.toBinaryString(k));
       
            /*JFileChooser jf=new JFileChooser();
            jf.showOpenDialog(null);
          //  jf.setLocationRelativeTo(null);
             f=jf.getSelectedFile();*/
            try{
            FileInputStream fis=new FileInputStream(f);
            byte data[]=new byte[fis.available()];
            fis.read(data);
            String j="";
            int o=0;
            int a[]=new int[8];
            for(byte b:data)
            {
                String l=Integer.toBinaryString(b);
              for( int i=7;i>=0;i--)
              {
               if(i-(8-l.length())>=0)
               {a[i]=Character.getNumericValue(l.charAt(i-(8-l.length())));}
               else
               a[i]=0;
              }
                j=decryption(a);
                data[o]=(byte)(Integer.parseInt(j,2));
                System.out.println(data[o]);
                o++;
            }
            FileOutputStream fos=new FileOutputStream(f);
            fos.write(data);
            fos.close();
            fis.close();
            l2.setText("                    Select File   ");
            JOptionPane.showMessageDialog(null,"Done");	

		}
    catch(Exception u)
    {System.out.println(u);}
			System.out.println("no problem");
		}

	}
	
	public static void main(String args[])                                                  //main function
	{
		Image_Cryptography obj=new Image_Cryptography();
		obj.setLocationRelativeTo(null);
		obj.setSize(280,300);
       
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
