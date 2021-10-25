public class CaesarCode {
    public static String CaesarCode(String input, char offset){
        int wordLen= input.length();
        char[] word = new char[wordLen];
        offset=(char)(Character.toUpperCase(offset)-'A');
        input.toUpperCase().getChars(0,wordLen,word,0);
        for (int i = 0;i<wordLen;i++){
            if (word[i]+offset>'Z')
                word[i]=(char)(word[i]-(26-offset));
            else
                word[i]=(char)(word[i]+offset);
        }
        return new String(word);
    }
    public static String CaesarDecode(String input, char offset){
        int wordLen= input.length();
        char[] word = new char[wordLen];
        offset=(char)(Character.toUpperCase(offset)-'A');
        input.toUpperCase().getChars(0,wordLen,word,0);
        for (int i = 0;i<wordLen;i++){
            if (word[i]+offset>'Z')
                word[i]=(char)(word[i]+(26-offset));
            else
                word[i]=(char)(word[i]-offset);
        }
        return new String(word);
    }
}
