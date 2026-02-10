import java.net.*;
import java.util.*;
import java.io.*;


public class ServerGuessNumber implements Runnable{
	Socket s;
	int id;
	
	public ServerGuessNumber(Socket client, int i){
		s=client;
		id = i;
	}

	public void run(){
		try{
			Random rand = new Random();
			int number = rand.nextInt(10) + 1;
			Scanner in = new Scanner(s.getInputStream());
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			//Interface:
			out.printf("100 Try to guess the number that I thought from 1 to 10. \r\n");
			out.printf("For guessing, type: NUM <x> \r\n");
			out.printf("For quitting, type: QUIT \r\n");
			boolean exit = false;	
			while(!exit){
				try{
					String request = in.nextLine();
					out.printf("Received\r\n");
					if(request.startsWith("NUM")){
						String[] aux = request.split("\\s+");
						int guess = Integer.parseInt(aux[1]);
						if(guess > 0 && guess < 11){
							if(guess > number){
								out.printf("202 Too high. Try again\r\n");
								
							}//if(>number)
								
							else if(guess == number){
								out.printf("200 You guessed right. End of game\r\n");
								
								exit = true;
								
							}//if(==number)
								
							else{
								out.printf("201 Too low. Try again\r\n");
								
							}//else(<number)
										
						}
						
						else{
							throw new NumberFormatException("No negative numbers nor 0");
							
						}//else(negative or 0)
									
					}//if(NUM)
						
					else if(request.equals("QUIT")){
						out.printf("300 You quit. Git gud\r\n");
						exit = true;
						
					}//else if(QUIT)
						
					else{
						out.printf("400 Order not comprenhensible. Try again \r\n");
						
					}//else
								
				}//try
				
				catch(ArrayIndexOutOfBoundsException e){			
					out.printf("401 Incorrect parameter. Only positive integers from 1 - 10 allowed\r\n");
					
				}//catch
				
				catch(NumberFormatException e){
					out.printf("401 Incorrect parameter. Only positive integers from 1- 10 allowed\r\n");
								
				}//catch
							
			}//while(true)
				
			out.printf("Bye\r\n");
			System.out.println("Client " + id + " closing");
			s.close();
			
		}//try
		
		catch(IOException e){
			System.out.println(e);
			
		}//catch
		
		
	}//Run()
	
	public static void main(String[]args){
			try{
				ServerSocket ss = new ServerSocket(7777);
				int i = 1;
				while(true){
					System.out.println("Ready");
					Socket s = ss.accept();
					System.out.println("Connection established");
					
					System.out.println("Client " +  i);
					(new Thread(new ServerGuessNumber(s, i))).start();
					i++;

				}//while
				
			}//try
			
			catch(IOException e){
				System.out.println(e);
				
			}//catch
			
	}//main

	
	
}//class SERVER_GUESS_NUMBER
