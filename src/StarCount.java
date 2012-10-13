import java.io.*;

/**
 * 
 */

/**
 * @author srvmdk
 * Licensed under GPL
 * See the LICENSE file to learn more
 */
public class StarCount {
	static char a ='*', b=' ';
	public StarCount() {
	
	}
	public static void main(String[] args) throws IOException, FileNotFoundException
	{
		FileReader fr = new FileReader("abc.txt");
		BufferedReader br = new BufferedReader(fr);
		int chars, i, count, j=0;
		String buffer;
		char curr, prev;
		while((buffer=br.readLine())!=null){
			if(j==0){
				System.out.print(buffer.length()+"-");
				j=1;
			}
			i=0;
			prev=buffer.charAt(0);
			count=-1;
			while(i<buffer.length()){
				curr=buffer.charAt(i);
				if(curr != prev || (i==buffer.length()-1)){
					if(prev==a){
						System.out.print("a"+(count+1)+",");
						count=0;
					}
					else if(prev==b){
						System.out.print("b"+(count+1)+",");
						count=0;
					}
				}
				else{
					count++;
				}
				i++;
				prev=curr;
			}
			if(buffer.charAt(buffer.length()-1)==a){
				System.out.print("a1-");
			}
			else if(buffer.charAt(buffer.length()-1)==b){
				System.out.print("b1-");
			}
		}
	}
}
