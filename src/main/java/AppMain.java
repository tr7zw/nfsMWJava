import javax.microedition.midlet.MIDlet;

public class AppMain extends MIDlet {
   private TheGame theGame = new TheGame(this);

   public void destroyApp(boolean var1) {
      this.theGame.End();
   }

   public void pauseApp() {
      this.theGame.hideNotify();
   }

   public void startApp() {
      try {
         this.theGame.Start();
      } catch (Error var2) {
         this.notifyDestroyed();
      } catch (Exception var3) {
         this.notifyDestroyed();
      }

   }
}
