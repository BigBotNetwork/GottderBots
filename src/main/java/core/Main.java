package core;

import commands.bank;
import commands.bots;
import commands.help;
import commands.register;
import listener.Message;
import listener.commandListener;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import util.SECRETS;

import javax.security.auth.login.LoginException;
import java.awt.*;

import static util.SECRETS.VERSION;

public class Main {

    public static int Timer=0;
    public static String user="krank";
    public static String password="krank";
    public static String urlempty="jdbc:mysql://localhost/";

    public static JDABuilder builder;
    public static EmbedBuilder Embed = new EmbedBuilder()
            .setColor(Color.CYAN)
            .setFooter("© Gott der Bots v." + VERSION, "http://www.baggerstation.de/testseite/bots/Gott.png");



    public static void main(String[] Args) {
        builder = new JDABuilder(AccountType.BOT).setToken(SECRETS.Token).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE);
        builder.addEventListener(new commandListener());
        builder.addEventListener(new Message());

        commandHandler.commands.put("help", new help());
        commandHandler.commands.put("bank", new bank());
        commandHandler.commands.put("bots", new bots());
        commandHandler.commands.put("register", new register());

        builder.setGame(Game.of(Game.GameType.DEFAULT, "Gestartet!"));
        GameAnimator.start();



        try {
            JDA jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
