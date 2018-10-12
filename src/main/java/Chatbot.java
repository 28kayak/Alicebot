import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

import java.io.File;


public class Chatbot {
    private static final boolean TRACE_MODE = false;
    static String botName = "super";


    public static void main(String[] args){
        try{
            String resourcePath = getResourcePath();

            System.out.println(Colors.ANSI_BLUE +  "Resource Path" + Colors.ANSI_RESET);
            System.out.println(Colors.ANSI_BLUE + resourcePath + Colors.ANSI_RESET);

            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcePath);
            ///Users/kaya/IdeaProjects/AliceBot/src/main/resources/bots/super/aimlif
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = "";

            while(true){
                System.out.print("You: ");
                textLine = IOUtils.readInputTextLine();
                if((textLine == null ) || (textLine.length() < 1)){
                    textLine = MagicStrings.null_input;
                }
                if (textLine.equals("q")) {
                    System.exit(0);
                }else if (textLine.equals("eq")){
                    bot.writeQuit();
                    System.exit(0);
                }
                else {
                    String request = textLine;
                    if (MagicBooleans.trace_mode){
                        System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
                    }
                    String response = chatSession.multisentenceRespond(request);
                    while (response.contains("&lt;")) {
                        response = response.replace("&lt;", "<");
                    }//while
                    while (response.contains("&gt;")){
                        response = response.replace("&gt;", ">");
                    }//while
                    System.out.println("Charlotte: " + response);

                    //Thread.sleep(10000);

                }//else
            }//while(true)

        }catch (Exception e){
            e.printStackTrace();
        }

    }//main
    private static String getResourcePath(){
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() -2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }//getResourcePath

}//class
