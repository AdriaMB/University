//
// This file must be implemented when completing "ChatRobot activity"
//
import ui.ChatUI;
import ui.ChatClientFace;
import ui.ChatUIFace;
import faces.MessageListener;
import impl.ChatUserImpl;
import impl.ChatMessageImpl;
import impl.ChatChannelImpl;
import utils_rmi.ChatConfiguration;
import faces.IChatMessage;
import faces.IChatChannel;
import faces.IChatUser;
import faces.INameServer;
import faces.IChatServer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



/**
 * ChatRobot implementation
 * 
 * <p> Notice ChatRobot implements MessageListener for receiving messages. MUST not extend ChatClient.
 * 
 */

public class ChatRobot implements MessageListener
{
    private IChatUser robot;
    private IChatServer srv;

    private ChatConfiguration conf;
    public ChatRobot (ChatConfiguration conf) {
        this.conf = conf;
    }
    





   @Override
   public void messageArrived (IChatMessage msg) {
       //*****************************************************************
       // Activity: implement robot message processing
       // 8.2 Get the sender, destination and message of the text 
       try{
            IChatUser src = msg.getSender();
            Remote dst = msg.getDestination();
            String str = msg.getText();

            //8.3 Determine whether the message is private, comming from the server or public and print all info
            String res = "";
            if(msg.isPrivate()){

                //The message is private, so dst is a IChatUser
                IChatUser u_dst = (IChatUser) dst;
                res += "Source: " + src.getNick();
                res += "  Destination: " + u_dst.getNick();
                res += "  It is a private message";

                // 9.3 Construct a response to send 
                try{
                    System.out.println("I am about to send the message");
                    IChatMessage responsePrivate = new ChatMessageImpl(robot, u_dst, "I am a robot, and the only answer I've learned so far is that 1+1 = 2");
                    
                    u_dst.sendMessage(responsePrivate);
                    System.out.println(res);
                    return;

                }catch(Exception e){
                    System.out.println("There is an error: " + e);
                }
                


            }
            else{
                //he message is public, so dst is a IChatChannel
                IChatChannel c_dst = (IChatChannel)dst;
                if(src != null){
                    //The sender is someone from the channel
                    res += "Source: " + src.getNick();
                    res += "  Destination: " + c_dst.getName();
                    res += "  It is a public message from a user";




                }
                else{
                    //Control messages have no source. The message was sent by the server
                    res += "Source: Server";
                    res += "  Destination: ALL";
                    res += "  It is a server control message";

                    // 9.2 Send a message when a new user enters
                    System.out.println(str);
                    if(str.startsWith(ChatChannelImpl.JOIN)){
                        IChatUser robot = new ChatUserImpl(conf.getNick(), this);
                        String nick = str.substring(ChatChannelImpl.JOIN.length() + 1);
                        
                        IChatMessage response = new ChatMessageImpl(robot, c_dst, "Hello " + nick);
                        c_dst.sendMessage(response);
                    }


                }
            }
            System.out.println(res);





            // 9.1 Write the necessary code to classify the types of messages
            // (private or public, sent by server or not, JOIN or other)





       }catch(Exception e){
            System.out.println("ERROR: " + e);

       }

       
   }
   




   private void work () {
       
       String channelName = conf.getChannelName();
       if (channelName == null) channelName = "#Linux";
       System.out.println ("Robot will connect to server: '" + conf.getServerName() + "'" + 
               ", channel: '" + channelName + "'" + 
               ", nick: '" + conf.getNick() + "'" +        
               ", using name server: '" + conf.getNameServerHost() + ":" + conf.getNameServerPort() + "'");
       
       try {
           //*****************************************************************
           // Activity: implement robot connection and joining to channel
           // conf contains all the necessary info for identifying the NameServer, the ChatServer...


           // 1.1 Get host and port of the NameServer
           String nshost = conf.getNameServerHost();
           int nsport = conf.getNameServerPort();
           // 1.2 Get the reference
           INameServer reg = INameServer.getNameServer(nshost, nsport);
           //System.out.println(RemoteUtils.remote2String(reg));




           // 2.1 Get the name of the ChatServer provided in the serverName argument when launching the ChatRobot from command line (it is stored in conf)
           String chatServerName = conf.getServerName();
           // 2.2-3 Get the reference to the ChatServer with the INameServer lookup operation. Can produce exceptions
           srv = (IChatServer) reg.lookup(chatServerName);
           




           // 3.1 Create a ChatUserImpl to represent the robot, with the nickname of conf
           robot = new ChatUserImpl(conf.getNick(), this);
           System.out.println("The nickname of the ChatRobotImplementation is: " + conf.getNick());
           // 3.2 Register the ChatUserImpl using the connectUser method of IChatServer
           try{
                srv.connectUser(robot);
                System.out.println("ChatRobot connected successfully");
           }catch(Exception e){
                System.out.println("Error: " + e);

           }
           


            
           // 4.1 Obtain the channel LocateRegistry, print all the channels in the list and make sure the channelName is in it
           IChatChannel[] channelList = srv.listChannels();
           System.out.println("Channels available: ");
           for(int i = 0; i < channelList.length; i++){
                System.out.println(channelList[i].getName());
           }
           // 4.2 Check that the channel provided also appears in the channel list
           String channelNameString = conf.getChannelName();
           System.out.println("The channel is: " + channelNameString);
           System.out.println("The search result is: " + srv.getChannel(channelNameString));
           




           // 5.1 Get the channel with the getChannel() method
           IChatChannel channel = srv.getChannel(channelNameString);
           // 5.2 Use the join method on the channel and get the list of users. Display them
           IChatUser[] usersInChannel = channel.join(robot);
           System.out.println("Users connected to this channel: ");
           for(int i = 0; i < usersInChannel.length; i++){
                System.out.println(usersInChannel[i].getNick());
           }
           




           // 6.1 Construct a message using the ChatMessageImpl class. 
           //The constructor parameters can be (ChatUser, ChatChannel, String) or (ChatUser, ChatUser, String)
           IChatMessage mss = new ChatMessageImpl(robot, channel, "Hello everyone");
           // 6.2 Send the message over the channel
           channel.sendMessage(mss);




           // 7.1 Construct a message.
           IChatMessage mssPrivate = new ChatMessageImpl(robot, robot, "Hello, I am a bot");
           // 7.2 Go through the list of users and send the first user different from the bot the message
           for(int i = 0; i < usersInChannel.length; i++){
                if(!usersInChannel[i].getNick().equals(conf.getNick())){
                    //This first message is dst to robot. When received by another user, 
                    // ERROR: Message received while disconnected or connected with different nick
                    usersInChannel[i].sendMessage(mssPrivate);
                    //This second message is dst to the user. It can be read
                    IChatMessage mssPrivate2 = new ChatMessageImpl(robot, usersInChannel[i], "Hello, I am a bot");
                    usersInChannel[i].sendMessage(mssPrivate2);
                    //System.out.println("Private message sent");
                    break;
                }
           }
           

       } catch (Exception e) {
           System.out.println("Something went wrong: " + e);
       }
       
   }









   public static void main (String args [])  {
       ChatRobot cr = new ChatRobot (ChatConfiguration.parse (args));
       cr.work ();
   }
}
