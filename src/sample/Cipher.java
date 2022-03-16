package sample;

public class Cipher {

    private int key;

    public Cipher(int key) {
        this.key = key;
    }

    public String encrypt(String str)
    {

        StringBuilder result = new StringBuilder();
        for(char c : str.toCharArray())
        {

            if(c >= 'a' && c <= 'z')
            {

                c += key;
                if(c > 'z')
                    c = (char)(c - 'z' + 'a' - 1);

            }
            else if(c >= 'A' && c <= 'Z')
            {

                c += key;
                if(c > 'Z')
                    c = (char)(c - 'Z' + 'A' - 1);

            }

            result.append(c);

        }

        return result.toString();

    }
    public String decrypt(String str) {

        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {

            if (c >= 'a' && c <= 'z') {
                c -= key;
                if (c < 'a')
                    c = (char) (c + 'z' - 'a' + 1);
            } else if (c >= 'A' && c <= 'Z') {

                c -= key;
                if (c < 'A')
                    c = (char) (c + 'Z' - 'A' + 1);

            }

            result.append(c);

        }

        return result.toString();
    }

}
