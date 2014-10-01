import java.io.*;
import java.util.*;

/**
 * WordToArt - A Java application to generate ASCII alphabetic art according to user preferences
 * version 0.1
 * @author Saurav Modak <support [at] linuxb [dot] in
 * website http://innovations.linuxb.in
 */

/**
 * License Information
 * 
 * In short, you are free to copy, distribute, edit, study the source code and make changes. You
 * can also distribute your edited sources provided you do that under a free software license
 * commonly known as open-source.
 * 
 * This program is licensed under GPL v2 and statement of the license must be distributed with the
 * source code
 */

/** 
 * Feature Wishlist
 * 
 * my wishlist with this program, please help if you can contribute code, complete these features
 * or suggest more features
 */

// TODO configurable size of the output, currently its limited to 8 lines
// TODO symbols support and multiple character and smiley support like <3, :-) etc
// TODO accept word from command line arguments

/**
 *  Current features
 * Read ascii art instructions from a file and generate output accordingly
 * Spaces before each word and above each word
 * Multiple lines of ASCII art, currently supports just one line
 * Command line parameters: -c, to get custom character from characters.conf file; and -m to enable multiline mode.
 */

public class WordToArt {
	static char a =' ', b ='@';
	static boolean multiline = false;
	// a and b are the words that can be used in ASCII art, change it according to your preferences
	// a and b get updated through characters.conf file if -c parameter is found on command line
	// First line is "a". 2nd is "b"
	// multiline is set to false as default, but can be activated with -m parameter on command line
	// sa: String array for storing ASCII art instructions
	
	private String word; //the word to be converted to ASCII art
	
	/**
	 * public WordToArt(String)
	 * Constructor that just copies user supplied word to a private variable
	 * @params w: the word
	 */
	
	public WordToArt(String w) {
		word = w;
	}

	/**
	 * public static void main (String [])
	 * main function that accepts the word from stdin and creates and object
	 * later args will be used for command line input
	 * @param args: non-functional currently
	 */
	
	public static void main(String[] args) throws IOException
	{
		//Parsing input parameters
		String s="";//stores the words to convert if multiline mode is DISABLED
		String [] w = new String [0];//stores the words to convert if multiline mode is ENABLED
		boolean empty = true;//checks if a word was introduced as parameter
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (empty){
			for(int tmp=0;tmp<args.length;tmp++) {
				if(args[tmp].contains("-c"))
					initChar();
				else if(args[tmp].contains("-m"))
					 multiline = true;
				else{
					empty = false;
					
					if (multiline){
						if (w.length == 0)
							w = new String [args.length - tmp];//Initializes the string array which will content the words in multiline mode.
						w[w.length - (args.length - tmp)] = args[tmp];
					}
					else
						s += args[tmp] + " ";
				}	
			}
			
			if (empty){
				System.out.print("Enter Word: ");
				args = br.readLine().split(" ");
				
				//Reseting options
				w = new String [0];
				multiline = false;
				a = ' ';
				b = '@';
			}
		}
		
		WordToArt obj;

		try {
			if(!multiline){
				obj = new WordToArt(s);
				obj.generateArt();
			}
			else{
				for(String word : w){
					obj = new WordToArt(word);
					obj.generateArt();
				}	
			}
		}
		catch(NullPointerException npe) {
			System.out.println("Could not find definition for one of the character of your string in chars.dat");
		}
	}
	
	 /**
	 * public void initChar()
	 * read the words from the .conf file and set the characters  to use in ASCII art.
	 */
	 
	public static void initChar() throws FileNotFoundException, IOException {
		FileReader fr = new FileReader("characters.conf");
		BufferedReader br1 = new BufferedReader(fr);
		String s1 = br1.readLine();
		a = s1.charAt(0);
		s1 = br1.readLine();
		b = s1.charAt(0);
	}
	
	/**
	 * public void generateArt()
	 * read ASCII art instructions from a file and copy it to an array of Strings	
	 * then print the art according to instructions 
	 */
	
	public void generateArt() throws FileNotFoundException, IOException, NullPointerException
	{
		/**
		 * This part of the functions copies the ASCII art instructions from the chars.dat file
		 * to an array
		 */
		int len = this.word.length(), i = 0;
		// len: length of supplied word
		// i: counter
		char ch; // current character
		String sa[] = new String[len]; //sa: String array for storing art instructions
		String buffer; // read buffer from file, one line at a time
		while (i<len){
			ch = this.word.charAt(i);
			String token;
			if(ch == '-') {
				token="=";
			}
			else {
				token="-";
			}
			FileReader fr = new FileReader("chars.dat");
			BufferedReader br1 = new BufferedReader(fr);
			while((buffer=br1.readLine()) != null){ // continue reading until EOF
				StringTokenizer str = new StringTokenizer(buffer, token);
				if(str.nextToken().equalsIgnoreCase(Character.toString(ch))){ // if this line equals the instructions we are looking for
					sa[i]=buffer; // copy the instruction to array
					br1.close();
					break;
				}
			}
			i++;
		}
				
		/**
		 * This part of the function prints the ASCII art
		 */
		
		//printing the upward spaces
		
		//counting total number of chars in a word
		
		int l=0, j, k;
		String rough;
		// l : total length of ASCII art line
		// j, k: counters
		// rough: for throwing unused tokens
		for(i=0; i<len; i++){
			String token;
			if(sa[i].charAt(0) == '-') {
				token="=";
			}
			else {
				token="-";
			}
			StringTokenizer str = new StringTokenizer(sa[i], token);
			rough = str.nextToken();
			l+=Integer.parseInt(str.nextToken());
		}
		
		//printing the spaces above a line
		
		for(j=0; j<2; j++){
			for(k=0; k<((l+2*len)+2); k++){
				System.out.print(b);
			}
			System.out.println();
		}
		
		
		//printing the words
		
		int line; //line number to be printed 
		for(line=1; line<=8; line++){
			for(i=0; i<len; i++){
				String token;
				if(sa[i].charAt(0) == '-') {
					token="=";
				}
				else {
					token="-";
				}
				StringTokenizer str = new StringTokenizer(sa[i], token);
				
				//printing first spaces for each word
				
				for(j=0; j<2; j++){
					System.out.print(b);
				}
				
				//skipping first two tokens
				for(j=0; j<2; j++){
					rough=str.nextToken();
				}
				
				// skipping already printed lines
				
				for(j=1; j<line;j++){
					rough=str.nextToken();
				}
				
				// print the art
				StringTokenizer str2=new StringTokenizer(str.nextToken(), ",");
				char c;
				String s;
				int t;
				// c: the character a or b
				// t: number of times
				while(str2.hasMoreTokens()){
					s=str2.nextToken();
					c=s.charAt(0);
					t=Integer.parseInt(s.substring(1));
					if(c=='a'){
						for(j=0;j<t;j++){
							System.out.print(a);
						}
					}
					else if(c=='b'){
						for(j=0;j<t;j++){
							System.out.print(b);
						}
					}
				}
			}
			for(j=0;j<2;j++){
				System.out.print(b);
			}
			System.out.println();
		}
		
		// for spaces below line
		
		for(j=0; j<2; j++){
			for(k=0; k<((l+2*len)+2); k++){
				System.out.print(b);
			}
			System.out.println();
		}
	}
}
