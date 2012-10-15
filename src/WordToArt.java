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
 * This program is licensed under GPL v3 and statement of the license must be distributed with the
 * source code
 */

/** 
 * Feature Wishlist
 * 
 * my wishlist with this program, please help if you can contribute code, complete these features
 * or suggest more features
 */

// TODO configurable characters except stars and spaces
// TODO configuration file to read configurations
// TODO configurable size of the output, currently its limited to 8 lines
// TODO multiple lines of ASCII art, currently supports just one line
// TODO symbols support and multiple character and smiley support like <3, :-) etc
// TODO accept word from command line arguments

/**
 *  Current features
 * Read ascii art instructions from a file and generate output accordingly
 * Spaces before each word and above each word
 */

public class WordToArt {
	static char a =' ', b ='@';
	
	// a and b are the words that can be used in ASCII art, change it according to your preferences
	// later this will be read from a configuration file
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
		String s="";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if(args.length < 1) {
			System.out.print("Enter Word: ");
			s = br.readLine();
		}
		else {
			for(int tmp=0;tmp<args.length;tmp++) {
				s+=(args[tmp]+" ");
			}
		}
		try {
			WordToArt obj = new WordToArt(s);
			obj.generateArt();
		}
		catch(NullPointerException npe) {
			System.out.println("Could not find definition for one of the character of your string in chars.dat");
		}
	}
	
	 /**
	 * public void initChar()
	 * read the words from the .conf file and set the characters  to use in ASCII art.
	 */
	 
	public void initChar() throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(".conf");
		BufferedReader br1 = new BufferedReader(fr);
		String s1 = br1.readLine();
		a = s1.charAt(0);
		b = s1.charAt(2);
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
		initChar();
		int len = this.word.length(), i = 0;
		// len: length of supplied word
		// i: counter
		char ch; // current character
		String sa[] = new String[len]; //sa: String array for storing art instructions
		String buffer; // read buffer from file, one line at a time
		while (i<len){
			ch = this.word.charAt(i);
			FileReader fr = new FileReader("chars.dat");
			BufferedReader br1 = new BufferedReader(fr);
			while((buffer=br1.readLine()) != null){ // continue reading until EOF
				StringTokenizer str = new StringTokenizer(buffer, "-");
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
			StringTokenizer str = new StringTokenizer(sa[i], "-");
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
				StringTokenizer str = new StringTokenizer(sa[i], "-");
				
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