package com.cs.vault;

public class Enc {

    public static char [] alphabet = "abcdefghijklmnopqrstuvwxyz0123456789~`!@#£€$¢¥§%°^&*()-_+={}[]|\\/:;\"'<>,.?".toUpperCase().toCharArray();

   public static String str = "";
   public static String keyword = "PASCAL".toUpperCase();





    public static String cipher(String str, String key){

        String cipher_text="";
        for (int i = 0; i < str.length(); i++) {


            // converting in range 0-25
            int x = (get_index(str.charAt(i)) + get_index(key.charAt(i))) % 73;

            // convert into alphabets(ASCII)
//                    x += 'A';

            cipher_text += (char) get_elem(x);


        }


        return cipher_text;
    }

    public static String origin(String str, String key){

        String origin_text="";
        for (int i = 0; i < str.length(); i++) {


            // converting in range 0-25
            int x = ((get_index(str.charAt(i)) - get_index(key.charAt(i)))+73) % 73;

            // convert into alphabets(ASCII)
//                    x += 'A';

            origin_text += (char) get_elem(x);


        }


        return origin_text;
    }

    public static int get_index (char elem){

        int indx =0;
        for(int j = 0; j < alphabet.length; j++){
            if (elem==(alphabet[j]))
                indx=j;

        }
        return indx;
    }

    public static char get_elem(int indx){

        char my_char = 0;
        for(int j = 0; j < alphabet.length; j++){
            if (indx==j)
                my_char=alphabet[indx];

        }
        return my_char;
    }

    public static String generateKey(String str, String key)
    {
        int x = str.length();

        for (int i = 0; ; i++)
        {
            if (x == i)
                i = 0;
            if (key.length() == str.length())
                break;
            key+=(key.charAt(i));
        }
        return key;
    }

}
